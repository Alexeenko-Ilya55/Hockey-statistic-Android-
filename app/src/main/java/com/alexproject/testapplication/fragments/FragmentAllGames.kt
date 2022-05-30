package com.alexproject.testapplication.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexproject.domain.models.Game
import com.alexproject.testapplication.R
import com.alexproject.testapplication.adapters.GamesAdapter
import com.alexproject.testapplication.app.appComponent
import com.alexproject.testapplication.contracts.GameClickListener
import com.alexproject.testapplication.databinding.FragmentAllGamesBinding
import com.alexproject.testapplication.objects.*
import com.alexproject.testapplication.viewModels.FragmentAllGamesViewModel
import com.alexproject.testapplication.viewModels.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import javax.inject.Inject

class FragmentAllGames : Fragment(), GameClickListener {
    private lateinit var binding: FragmentAllGamesBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: FragmentAllGamesViewModel
    private var listGames: MutableList<List<Game>> = mutableListOf()
    private val dataStoreKey = stringPreferencesKey(DATE_LAST_UPDATE)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        requireActivity().setTitle(R.string.fragmentAllGames)
        binding = FragmentAllGamesBinding.inflate(inflater, container, false)
        context?.appComponent?.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[FragmentAllGamesViewModel::class.java]

        tabDate.forEach { it ->
            val dayIndex = if (it == LocalDate.now()) TODAY_INDEX
            else it.dayOfWeek.value

            binding.scheduleGamesByDateTabLayout.addTab(
                binding.scheduleGamesByDateTabLayout.newTab().setText(
                    getString(
                        R.string.tabNames,
                        if (dayIndex == TODAY_INDEX)
                            getString(R.string.today)
                        else
                            DaysOfTheWeek.values().first { it.index == dayIndex }.short,
                        formatDate(it.toString())
                    )
                )
            )
            listGames.add(emptyList())
        }
        binding.scheduleGamesByDateTabLayout.getTabAt(TODAY_TAB_ITEM_INDEX)?.select()
        lifecycleScope.launchWhenStarted {
            viewModel.loadGamesByDate(LocalDate.now().toString()).collectLatest {
                listGames[binding.scheduleGamesByDateTabLayout.selectedTabPosition] = it
                initRecyclerAdapter(it)
            }
        }
        binding.scheduleGamesByDateTabLayout.addOnTabSelectedListener(object :
            TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val tabPosition = tab?.position ?: TODAY_TAB_ITEM_INDEX
                if (listGames[tabPosition] == emptyList<Game>()) {
                    lifecycleScope.launchWhenStarted {
                        viewModel.loadGamesByDate(tabDate[tabPosition].toString()).collectLatest {
                            listGames[tabPosition] = it
                            initRecyclerAdapter(it)
                        }
                    }
                } else
                    initRecyclerAdapter(listGames[tabPosition])
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.updateGames(tabDate)
            lifecycleScope.launchWhenStarted {
                setUpdateDate(LocalDate.now().toString())
            }
            binding.swipeRefresh.isRefreshing = false
        }

        return binding.root
    }

    private fun initRecyclerAdapter(games: List<Game>) {
        binding.rcView.layoutManager = LinearLayoutManager(context)
        val adapter = GamesAdapter(games, this@FragmentAllGames)
        binding.rcView.adapter = adapter
    }

    override fun buttonGameFavoriteClicked(gameId: Int, isFavorite: Boolean) {
        if (isFavorite)
            viewModel.addGameToFavorites(gameId)
        else
            viewModel.deleteGameFromFavorites(gameId)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.default_action_bar, menu)
        menu.setGroupVisible(R.id.group_favorites, false)
        menu.setGroupVisible(R.id.group_search_button, false)
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text).hint =
            getString(R.string.search_game)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val selectedTab = binding.scheduleGamesByDateTabLayout.selectedTabPosition
                newText?.let {
                    initRecyclerAdapter(listGames[selectedTab].filter {
                        it.homeTeam.name.lowercase().contains(newText.lowercase()) ||
                                it.awayTeam.name.lowercase().contains(newText.lowercase())
                    })
                }
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun itemGameClicked(gameId: Int) =
        findNavController().navigate(R.id.fragmentGame, bundleOf(GAME_ID to gameId))

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settings)
            findNavController().navigate(R.id.fragmentSettings)
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatDate(date: String): String {
        val formatInputDate = SimpleDateFormat("yyyy-MM-dd")
        val formatOutputDate = SimpleDateFormat("dd.MM")
        val docDate = formatInputDate.parse(date)
        return formatOutputDate.format(docDate!!)
    }

    override fun onResume() {
        lifecycleScope.launch(Dispatchers.IO) {
            if (getUpdateDate() != LocalDate.now().toString()) {
                lifecycleScope.launch(Dispatchers.Main) {
                    showUpdateDialog()
                }
            }
        }
        super.onResume()
    }

    private val tabDate: List<LocalDate> = listOf(
        LocalDate.now().minusDays(3),
        LocalDate.now().minusDays(2),
        LocalDate.now().minusDays(1),
        LocalDate.now(),
        LocalDate.now().plusDays(1),
        LocalDate.now().plusDays(2),
        LocalDate.now().plusDays(3)
    )

    private fun showUpdateDialog() {
        with(AlertDialog.Builder(context)) {
            setTitle(R.string.update)
            setMessage(R.string.updateGames)
            setNegativeButton(getString(R.string.cancel)) { _, _ ->
                Log.i("MyLog", CANCEL_PRESSED)
            }
            setPositiveButton(R.string.update) { _, _ ->
                viewModel.updateGames(tabDate)
                lifecycleScope.launchWhenStarted {
                    setUpdateDate(LocalDate.now().toString())
                }
            }
            create().show()
        }
    }

    private suspend fun setUpdateDate(date: String) =
        requireContext().dataStore.edit { settings ->
            settings[dataStoreKey] = date
        }

    private suspend fun getUpdateDate() = requireContext().dataStore.data.first()[dataStoreKey]
}
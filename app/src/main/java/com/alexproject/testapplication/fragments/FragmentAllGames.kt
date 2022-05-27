package com.alexproject.testapplication.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
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
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.time.LocalDate
import javax.inject.Inject

class FragmentAllGames : Fragment(), GameClickListener {
    private lateinit var binding: FragmentAllGamesBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: FragmentAllGamesViewModel
    private var listGames: MutableList<List<Game>> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllGamesBinding.inflate(inflater, container, false)
        context?.appComponent?.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[FragmentAllGamesViewModel::class.java]
        tabDate.forEach {
            val dayIndex = if(it == LocalDate.now()) TODAY_INDEX
                            else it.dayOfWeek.value

            binding.tabLayout2.addTab(binding.tabLayout2.newTab().setText(
                getString(R.string.tabNames,dayOfTheWeek(dayIndex) ,formatDate(it.toString()))))
            listGames.add(emptyList())
        }
        binding.tabLayout2.getTabAt(TODAY_TAB_ITEM_INDEX)?.select()
        lifecycleScope.launchWhenStarted {
            viewModel.loadGamesByDate(LocalDate.now().toString())
                .collectLatest { initRecyclerAdapter(it) }
        }

        binding.tabLayout2.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
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

    override fun itemGameClicked(gameId: Int) =
        findNavController().navigate(R.id.fragmentGame, bundleOf(GAME_ID to gameId))

    @SuppressLint("SimpleDateFormat")
    private fun formatDate(date: String): String {
        val formatInputDate = SimpleDateFormat("yyyy-MM-dd")
        val formatOutputDate = SimpleDateFormat("dd.MM")
        val docDate = formatInputDate.parse(date)
        return formatOutputDate.format(docDate!!)
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

    private fun dayOfTheWeek(dayIndex: Int): String{
        return when(dayIndex){
            WeekDays.MONDAY.index -> Week.MONDAY.short
            WeekDays.TUESDAY.index -> Week.TUESDAY.short
            WeekDays.WEDNESDAY.index -> Week.WEDNESDAY.short
            WeekDays.THURSDAY.index -> Week.THURSDAY.short
            WeekDays.FRIDAY.index -> Week.FRIDAY.short
            WeekDays.SATURDAY.index -> Week.SATURDAY.short
            WeekDays.SUNDAY.index -> Week.SUNDAY.short
            else -> getString(R.string.today)
        }
    }
}
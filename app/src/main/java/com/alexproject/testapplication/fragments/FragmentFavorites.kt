package com.alexproject.testapplication.fragments

import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexproject.domain.models.Game
import com.alexproject.domain.models.Team
import com.alexproject.testapplication.R
import com.alexproject.testapplication.adapters.GamesAdapter
import com.alexproject.testapplication.adapters.RecyclerAdapterTeam
import com.alexproject.testapplication.app.appComponent
import com.alexproject.testapplication.contracts.GameClickListener
import com.alexproject.testapplication.contracts.TeamClickListener
import com.alexproject.testapplication.databinding.FragmentFavoritesBinding
import com.alexproject.testapplication.objects.GAME_ID
import com.alexproject.testapplication.objects.TEAM_ID
import com.alexproject.testapplication.objects.Tab
import com.alexproject.testapplication.viewModels.FragmentFavoritesViewModel
import com.alexproject.testapplication.viewModels.ViewModelFactory
import com.alexproject.testapplication.views.TabItemClickListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentFavorites : Fragment(), GameClickListener, TeamClickListener, TabItemClickListener {

    private lateinit var binding: FragmentFavoritesBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: FragmentFavoritesViewModel

    private var listFavoritesGames: List<Game> = emptyList()
    private var listFavoritesTeams: List<Team> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        requireActivity().setTitle(R.string.fragmentFavorites)
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        context?.appComponent?.inject(this)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[FragmentFavoritesViewModel::class.java]

        lifecycleScope.launchWhenStarted {
            viewModel.loadFavoritesGames().collectLatest {
                listFavoritesGames = it
                initGameAdapter(it)
            }
        }

        binding.tabLayout.setTabNames(
            listOf(getString(R.string.gameTabItem), getString(R.string.teamsTabItem))
        )
        binding.tabLayout.setClickListener(this)
        return binding.root
    }

    private fun initGameAdapter(games: List<Game>) =
        lifecycleScope.launch(Dispatchers.Main) {
            binding.rcView.layoutManager = LinearLayoutManager(context)
            val adapter = GamesAdapter(games, this@FragmentFavorites)
            binding.rcView.adapter = adapter
        }


    private fun initTeamAdapter(teams: List<Team>) =
        lifecycleScope.launch(Dispatchers.Main) {
            binding.rcView.layoutManager = LinearLayoutManager(context)
            val adapter = RecyclerAdapterTeam(teams, this@FragmentFavorites)
            binding.rcView.adapter = adapter
        }


    override fun buttonGameFavoriteClicked(gameId: Int, isFavorite: Boolean) {
        if (isFavorite)
            viewModel.addGameToFavorites(gameId)
        else
            viewModel.deleteGameFromFavorites(gameId)
    }

    override fun buttonTeamFavoriteClicked(teamId: Int, isFavorite: Boolean) {
        if (isFavorite)
            viewModel.addTeamToFavorites(teamId)
        else
            viewModel.deleteTeamFromFavorites(teamId)
    }

    override fun itemTeamClicked(teamId: Int) =
        findNavController().navigate(R.id.fragmentTeam, bundleOf(TEAM_ID to teamId))


    override fun itemGameClicked(gameId: Int) =
        findNavController().navigate(R.id.fragmentGame, bundleOf(GAME_ID to gameId))

    override fun positionActiveTabChanged(activeTabIndex: Int) {
        binding.tabLayout.activeTabIndex = activeTabIndex
        if (activeTabIndex == Tab.GAMES.index) {
            initGameAdapter(listFavoritesGames)

        } else {
            lifecycleScope.launchWhenStarted {
                viewModel.loadFavoritesTeams().collectLatest {
                    listFavoritesTeams = it
                    initTeamAdapter(it) }
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.default_action_bar, menu)
        menu.setGroupVisible(R.id.group_favorites, false)
        menu.setGroupVisible(R.id.group_search_button, false)
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(binding.tabLayout.activeTabIndex == Tab.GAMES.index)
                    searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text).hint =
                        getString(R.string.search)
                newText?.let {
                    if (binding.tabLayout.activeTabIndex == Tab.GAMES.index)
                        initGameAdapter(listFavoritesGames.filter {
                            it.homeTeam.name.lowercase().contains(newText.lowercase()) ||
                                    it.awayTeam.name.lowercase().contains(newText.lowercase())
                        })
                    else
                        initTeamAdapter(listFavoritesTeams.filter {
                            it.name.lowercase().contains(newText.lowercase())
                        })
                }
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.settings)
            findNavController().navigate(R.id.fragmentSettings)
        return super.onOptionsItemSelected(item)
    }
}
package com.alexproject.testapplication.fragments

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
import com.alexproject.domain.models.Team
import com.alexproject.testapplication.R
import com.alexproject.testapplication.adapters.GamesAdapter
import com.alexproject.testapplication.adapters.RecyclerAdapterTeam
import com.alexproject.testapplication.app.appComponent
import com.alexproject.testapplication.contracts.GameClickListener
import com.alexproject.testapplication.contracts.TeamClickListener
import com.alexproject.testapplication.databinding.FragmentFavoritesBinding
import com.alexproject.testapplication.objects.GAME_ID
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        context?.appComponent?.inject(this)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[FragmentFavoritesViewModel::class.java]

        lifecycleScope.launchWhenStarted {
            viewModel.loadFavoritesGames().collectLatest { initGameAdapter(it) }
        }

        binding.tabLayout.setTabNames(
            listOf(
                getString(R.string.gameTabItem),
                getString(R.string.teamsTabItem)
            )
        )
        binding.tabLayout.setClickListener(this)
        return binding.root
    }

    private fun initGameAdapter(games: List<Game>) {
        lifecycleScope.launch(Dispatchers.Main) {
            binding.rcView.layoutManager = LinearLayoutManager(context)
            val adapter = GamesAdapter(games, this@FragmentFavorites)
            binding.rcView.adapter = adapter
        }
    }

    private fun initTeamAdapter(teams: List<Team>) {
        lifecycleScope.launch(Dispatchers.Main) {
            binding.rcView.layoutManager = LinearLayoutManager(context)
            val adapter = RecyclerAdapterTeam(teams, this@FragmentFavorites)
            binding.rcView.adapter = adapter
        }
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

    override fun itemTeamClicked(teamId: Int) {
        findNavController().navigate(R.id.fragmentTeam)
    }

    override fun itemGameClicked(gameId: Int) {
        val bundle = bundleOf(GAME_ID to gameId)
        findNavController().navigate(R.id.fragmentGame, bundle)
    }

    override fun positionActiveTabChanged(activeTabIndex: Int) {
        if (activeTabIndex == 0) {
            lifecycleScope.launchWhenStarted {
                viewModel.loadFavoritesGames().collectLatest { initGameAdapter(it) }
            }
        } else {
            lifecycleScope.launchWhenStarted {
                viewModel.loadFavoritesTeams().collectLatest { initTeamAdapter(it) }
            }
        }
    }
}
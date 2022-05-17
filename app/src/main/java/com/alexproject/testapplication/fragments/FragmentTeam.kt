package com.alexproject.testapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexproject.domain.models.Game
import com.alexproject.testapplication.R
import com.alexproject.testapplication.adapters.GamesAdapter
import com.alexproject.testapplication.adapters.StatisticTableAdapter
import com.alexproject.testapplication.app.appComponent
import com.alexproject.testapplication.contracts.GameClickListener
import com.alexproject.testapplication.contracts.GameFavorites
import com.alexproject.testapplication.contracts.TeamFavorites
import com.alexproject.testapplication.databinding.FragmentTeamBinding
import com.alexproject.testapplication.models.StatisticTable
import com.alexproject.testapplication.objects.COUNTRY_ID
import com.alexproject.testapplication.objects.LEAGUE_ID
import com.alexproject.testapplication.objects.TEAM_ID
import com.alexproject.testapplication.viewModels.FragmentTeamViewModel
import com.alexproject.testapplication.viewModels.ViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentTeam : Fragment(), TeamFavorites, GameFavorites, GameClickListener {

    private lateinit var binding: FragmentTeamBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: FragmentTeamViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeamBinding.inflate(inflater, container, false)
        context?.appComponent?.inject(this)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[FragmentTeamViewModel::class.java]

        val teamId = arguments?.getInt(TEAM_ID)!!
        val countryId = arguments?.getInt(COUNTRY_ID)!!
        val leagueId = arguments?.getInt(LEAGUE_ID)!!

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.loadTeamById(teamId).collectLatest { team ->
                initUI(team.name, team.logo, binding.teamEmblem, binding.teamName)
            }
        }
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.loadLeagueById(leagueId).collectLatest {
                initUI(
                    getString(R.string.league_name, it.name),
                    it.logo,
                    binding.leagueLogo,
                    binding.leagueName
                )
            }
        }
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.loadCountryById(countryId).collectLatest {
                initUI(
                    getString(R.string.country_name, it.name),
                    it.flag,
                    binding.countryFlag,
                    binding.countryName
                )
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.loadAllGamesForTeam(teamId).collectLatest {
                initGamesAdapter(it)
            }
        }
        binding.leagueButton.setOnClickListener {

        }
        return binding.root
    }

    private fun initGamesAdapter(games: List<Game>) {
        binding.rcView.layoutManager = LinearLayoutManager(context)
        val adapter = GamesAdapter(games, this@FragmentTeam)
        binding.rcView.adapter = adapter
    }

    private fun initStatisticAdapter(statistic: List<StatisticTable>) {
        binding.rcView.layoutManager = LinearLayoutManager(context)
        val adapter = StatisticTableAdapter()
        adapter.statistic = statistic
        binding.rcView.adapter = adapter
    }

    private fun initUI(name: String, imageUrl: String?, imageView: ImageView, textView: TextView) {
        lifecycleScope.launch(Dispatchers.Main) {
            Picasso.get().load(imageUrl).into(imageView)
            textView.text = name
        }
    }

    override fun addGameToFavorites(gameId: Int) = viewModel.addGameToFavorites(gameId)

    override fun deleteGameFromFavorites(gameId: Int) = viewModel.deleteGameFromFavorites(gameId)

    override fun addTeamToFavorites(teamId: Int) = viewModel.addTeamToFavorites(teamId)

    override fun deleteTeamFromFavorites(teamId: Int) = viewModel.deleteTeamFromFavorites(teamId)

    override fun buttonGameFavoriteClicked(gameId: Int, isFavorite: Boolean) {
        if (isFavorite)
            addGameToFavorites(gameId)
        else
            deleteGameFromFavorites(gameId)
    }

    override fun itemGameClicked(gameId: Int) =
        findNavController().navigate(R.id.fragmentGame)
}
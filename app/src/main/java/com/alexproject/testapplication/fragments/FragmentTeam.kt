package com.alexproject.testapplication.fragments

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
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
import com.alexproject.testapplication.adapters.StatisticTableAdapter
import com.alexproject.testapplication.app.appComponent
import com.alexproject.testapplication.contracts.GameClickListener
import com.alexproject.testapplication.contracts.GameFavorites
import com.alexproject.testapplication.contracts.TeamFavorites
import com.alexproject.testapplication.databinding.FragmentTeamBinding
import com.alexproject.testapplication.models.StatisticTable
import com.alexproject.testapplication.objects.*
import com.alexproject.testapplication.viewModels.FragmentTeamViewModel
import com.alexproject.testapplication.viewModels.ViewModelFactory
import com.alexproject.testapplication.views.TabItemClickListener
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject
import kotlin.properties.Delegates

class FragmentTeam : Fragment(), TeamFavorites, GameFavorites, GameClickListener,
    TabItemClickListener {

    private lateinit var binding: FragmentTeamBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: FragmentTeamViewModel
    private var teamId by Delegates.notNull<Int>()
    private var leagueId by Delegates.notNull<Int>()
    private lateinit var allGames: List<Game>
    private lateinit var menu: Menu
    private lateinit var team: Team


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        requireActivity().setTitle(R.string.fragmentTeam)
        binding = FragmentTeamBinding.inflate(inflater, container, false)
        context?.appComponent?.inject(this)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[FragmentTeamViewModel::class.java]

        teamId = arguments?.getInt(TEAM_ID)!!

        lifecycleScope.launchWhenStarted {
            viewModel.loadTeamById(teamId).collectLatest {
                team = it
                initUI(team.name, team.logo, binding.teamEmblem, binding.teamName)
                if (team.isFavorite) {
                    menu.findItem(R.id.favorites).setIcon(R.drawable.favorites_enable)
                }
            }
        }

        binding.tabLayout.setTabNames(
            listOf(
                getString(R.string.results),
                getString(R.string.calendar),
                getString(R.string.statistic)
            )
        )
        binding.tabLayout.setClickListener(this)

        lifecycleScope.launchWhenStarted {
            viewModel.loadAllGamesForTeam(teamId).collectLatest {
                allGames = it
                allGames.filterGamesResults()
                leagueId = it.first().league.id
                initUI(
                    allGames.first().league.name,
                    allGames.first().league.logo,
                    binding.leagueLogo,
                    binding.leagueName
                )
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
        Picasso.get().load(imageUrl).into(imageView)
        textView.text = name
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
        findNavController().navigate(R.id.fragmentGame, bundleOf(GAME_ID to gameId))

    override fun positionActiveTabChanged(activeTabIndex: Int) {
        when (activeTabIndex) {
            Tab.RESULTS.index -> lifecycleScope.launchWhenStarted {
                initGamesAdapter(allGames.filterGamesResults())
            }
            Tab.CALENDAR.index -> lifecycleScope.launchWhenStarted {
                initGamesAdapter(allGames.filterGamesCalendar())
            }
            Tab.STATISTIC_TABLE.index -> lifecycleScope.launchWhenStarted {
                viewModel.loadStatisticsTable(leagueId).collectLatest {
                    initStatisticAdapter(it)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        this.menu = menu
        requireActivity().menuInflater.inflate(R.menu.default_action_bar, menu)
        menu.setGroupVisible(R.id.group_search, false)
        menu.setGroupVisible(R.id.group_search_button, false)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item == menu.findItem(R.id.favorites)) {
            team.isFavorite = !team.isFavorite
            if (team.isFavorite) {
                item.setIcon(R.drawable.favorites_enable)
                addTeamToFavorites(teamId)
            } else {
                item.setIcon(R.drawable.favorite_not_enable)
                deleteTeamFromFavorites(teamId)
            }
        } else
            findNavController().navigate(R.id.fragmentSettings)
        return super.onOptionsItemSelected(item)
    }
}
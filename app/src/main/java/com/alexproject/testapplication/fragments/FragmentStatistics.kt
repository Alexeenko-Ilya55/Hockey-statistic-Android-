package com.alexproject.testapplication.fragments

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexproject.domain.models.Game
import com.alexproject.domain.models.League
import com.alexproject.testapplication.R
import com.alexproject.testapplication.adapters.GamesAdapter
import com.alexproject.testapplication.adapters.LeagueChangeListAdapter
import com.alexproject.testapplication.adapters.StatisticTableAdapter
import com.alexproject.testapplication.app.appComponent
import com.alexproject.testapplication.contracts.GameClickListener
import com.alexproject.testapplication.contracts.LeagueClickListener
import com.alexproject.testapplication.databinding.DialodChangeLeagueFragmentBinding
import com.alexproject.testapplication.databinding.FragmentStatisticsBinding
import com.alexproject.testapplication.models.StatisticTable
import com.alexproject.testapplication.objects.*
import com.alexproject.testapplication.viewModels.FragmentStatisticsViewModel
import com.alexproject.testapplication.viewModels.ViewModelFactory
import com.alexproject.testapplication.views.TabItemClickListener
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates
import kotlin.properties.ReadOnlyProperty

class FragmentStatistics : Fragment(), GameClickListener, TabItemClickListener,
    LeagueClickListener {

    private lateinit var binding: FragmentStatisticsBinding
    private lateinit var bindingDialog: DialodChangeLeagueFragmentBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: FragmentStatisticsViewModel

    private var statisticTable: List<StatisticTable> = emptyList()
    private var listGames: List<Game> = emptyList()
    private var leagueId by Delegates.notNull<Int>()
    private lateinit var favoriteLeague: League
    private lateinit var dialog: AlertDialog
    private val dataStoreKey = intPreferencesKey(FAVORITE_LEAGUE_ID)
    private var listLeagues: List<League> = emptyList()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        requireActivity().setTitle(R.string.fragmentStatistic)
        binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        context?.appComponent?.inject(this)

        viewModel =
            ViewModelProvider(this, viewModelFactory)[FragmentStatisticsViewModel::class.java]

        lifecycleScope.launch {
            leagueId = readFavoriteLeague() ?: DEFAULT_LEAGUE_ID
            arguments?.getInt(LEAGUE_ID)?.let { leagueId = it }
            loadLeague(leagueId)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.loadAllLeagues().collectLatest {
                listLeagues = it
            }
        }

        binding.buttonChangeLeague.setOnClickListener {
            showChangeLeagueDialog(listLeagues)
        }

        binding.tabLayout.setTabNames(
            listOf(
                getString(R.string.statisticTable),
                getString(R.string.results),
                getString(R.string.calendar)
            )
        )
        binding.tabLayout.setClickListener(this)
        return binding.root
    }

    private fun initUI(league: League) {
        binding.apply {
            leagueName.text = league.name
            Picasso.get().load(league.logo).into(leagueEmblem)
        }
        loadStatisticTable(league.id)
    }

    private fun loadLeague(leagueId: Int) = lifecycleScope.launchWhenStarted {
        viewModel.loadLeagueById(leagueId).collectLatest {
            favoriteLeague = it
            initUI(favoriteLeague)
        }
    }

    private fun loadStatisticTable(leagueId: Int) {
        lifecycleScope.launchWhenStarted {
            viewModel.loadStatistic(leagueId).collectLatest {
                statisticTable = it
                initStatisticAdapter(statisticTable)
            }
        }
    }

    private fun initStatisticAdapter(statisticTable: List<StatisticTable>) {
        binding.rcView.layoutManager = LinearLayoutManager(context)
        val adapter = StatisticTableAdapter()
        adapter.statistic = statisticTable
        binding.rcView.adapter = adapter
    }

    private fun initGamesAdapter(games: List<Game>) {
        binding.rcView.layoutManager = LinearLayoutManager(context)
        binding.rcView.adapter = GamesAdapter(games, this)
    }


    override fun buttonGameFavoriteClicked(gameId: Int, isFavorite: Boolean) {
        if (isFavorite)
            viewModel.addGameToFavorites(gameId)
        else
            viewModel.deleteGameFromFavorites(gameId)
    }

    override fun itemGameClicked(gameId: Int) =
        findNavController().navigate(R.id.fragmentGame, bundleOf(GAME_ID to gameId))

    override fun positionActiveTabChanged(activeTabIndex: Int) {
        binding.tabLayout.activeTabIndex = activeTabIndex
        when (activeTabIndex) {
            TabsFragmentStatistic.STATISTIC.index -> lifecycleScope.launchWhenStarted {
                initStatisticAdapter(statisticTable)
            }
            TabsFragmentStatistic.RESULTS.index -> loadGames(
                leagueId,
                TabsFragmentStatistic.RESULTS
            )
            TabsFragmentStatistic.CALENDAR.index -> loadGames(
                leagueId,
                TabsFragmentStatistic.CALENDAR
            )
        }
    }

    private fun loadGames(leagueId: Int, tab: TabsFragmentStatistic) {
        if (listGames.isEmpty()) {
            lifecycleScope.launchWhenStarted {
                viewModel.loadAllGamesForLeague(leagueId).collectLatest {
                    listGames = it
                    initGamesAdapter(chooseFilter(listGames, tab))
                }
            }
        } else
            initGamesAdapter(chooseFilter(listGames, tab))
    }

    private fun chooseFilter(listGames: List<Game>, tab: TabsFragmentStatistic): List<Game> {
        return if (tab == TabsFragmentStatistic.RESULTS)
            listGames.filterGamesResults()
        else
            listGames.filterGamesCalendar()
    }

    private fun showChangeLeagueDialog(leagueList: List<League>) {
        bindingDialog = DialodChangeLeagueFragmentBinding.inflate(layoutInflater)
        initLeagueAdapter(leagueList)
        bindingDialog.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    initLeagueAdapter(listLeagues.filter {
                        it.name.lowercase().contains(newText.lowercase())
                    })
                }
                return true
            }
        })
        dialog = with(AlertDialog.Builder(context)) {
            setTitle(R.string.chooseLeague)
            setNegativeButton(getString(R.string.cancel)) { _, _ ->
                Log.i("MyLog", CANCEL_PRESSED)
            }
            setView(bindingDialog.root)
            show()
        }
    }

    private fun initLeagueAdapter(leagueList: List<League>) {
        bindingDialog.rcView.layoutManager = LinearLayoutManager(context)
        bindingDialog.rcView.adapter =
            LeagueChangeListAdapter(leagueList, this@FragmentStatistics, favoriteLeague)
    }

    override fun itemClicked(league: League) {
        leagueId = league.id
        initUI(league)
        dialog.dismiss()
        lifecycleScope.launch {
            saveFavoriteLeague(league.id)
        }
    }

    private suspend fun saveFavoriteLeague(leagueId: Int) =
        requireContext().dataStore.edit { settings ->
            settings[dataStoreKey] = leagueId
        }

    private suspend fun readFavoriteLeague() = requireContext().dataStore.data.first()[dataStoreKey]

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        requireActivity().menuInflater.inflate(R.menu.default_action_bar, menu)
        menu.setGroupVisible(R.id.group_favorites, false)
        menu.setGroupVisible(R.id.group_search, false)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.search_button)
            showChangeLeagueDialog(listLeagues)
        else
            findNavController().navigate(R.id.fragmentSettings)
        return super.onOptionsItemSelected(item)
    }
}


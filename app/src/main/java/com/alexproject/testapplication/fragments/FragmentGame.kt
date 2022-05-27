package com.alexproject.testapplication.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexproject.domain.models.EventsAdapterItem
import com.alexproject.domain.models.Game
import com.alexproject.testapplication.R
import com.alexproject.testapplication.adapters.GameEventsAdapter
import com.alexproject.testapplication.adapters.GamesAdapter
import com.alexproject.testapplication.app.appComponent
import com.alexproject.testapplication.contracts.GameClickListener
import com.alexproject.testapplication.databinding.FragmentGameBinding
import com.alexproject.testapplication.objects.*
import com.alexproject.testapplication.viewModels.FragmentGameViewModel
import com.alexproject.testapplication.viewModels.ViewModelFactory
import com.alexproject.testapplication.views.TabItemClickListener
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentGame : Fragment(), GameClickListener, TabItemClickListener {

    private lateinit var binding: FragmentGameBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: FragmentGameViewModel

    private lateinit var game: Game
    private var h2hGames: List<Game> = listOf()
    private lateinit var menu: Menu

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        requireActivity().setTitle(R.string.fragmentGame)
        binding = FragmentGameBinding.inflate(
            inflater,
            container,
            false
        )
        context?.appComponent?.inject(this)

        viewModel = ViewModelProvider(this, viewModelFactory)[FragmentGameViewModel::class.java]

        lifecycleScope.launchWhenStarted {
            arguments?.getInt(GAME_ID)?.let { it ->
                viewModel.loadGameById(it).collectLatest { game ->
                    this@FragmentGame.game = game
                    updateUi(game)
                    if (game.isFavorite) {
                        menu.findItem(R.id.favorites).setIcon(R.drawable.favorites_enable)
                    }
                }
            }
        }

        binding.homeTeamFavorites.setOnClickListener {
            game.homeTeam.isFavorite = !game.homeTeam.isFavorite
            onButtonFavoriteClicked(game.homeTeam.id, game.homeTeam.isFavorite)
            setImage()
        }
        binding.awayTeamFavorites.setOnClickListener {
            game.awayTeam.isFavorite = !game.awayTeam.isFavorite
            onButtonFavoriteClicked(game.awayTeam.id, game.awayTeam.isFavorite)
            setImage()
        }

        binding.customTabView.setClickListener(this)
        binding.customTabView.setTabNames(
            listOf(
                getString(R.string.reviewTabMenu),
                getString(R.string.h2hTabMenu)
            )
        )

        binding.awayTeamLabelGame.setOnClickListener {
            findNavController().navigate(
                R.id.fragmentTeam,
                bundleOf(
                    TEAM_ID to game.awayTeam.id,
                    COUNTRY_ID to game.country.id,
                    LEAGUE_ID to game.league.id
                )
            )
        }
        binding.homeTeamLabelGame.setOnClickListener {
            findNavController().navigate(R.id.fragmentTeam, bundleOf(TEAM_ID to game.homeTeam.id))
        }
        return binding.root
    }

    private fun initH2HAdapter(listGames: List<Game>) =
        lifecycleScope.launch(Dispatchers.Main) {
            binding.matchInfo.isVisible = false
            binding.rcView.isVisible = true
            binding.rcView.layoutManager = LinearLayoutManager(context)
            val adapter = GamesAdapter(listGames, this@FragmentGame)
            binding.rcView.adapter = adapter
        }

    private fun onButtonFavoriteClicked(teamId: Int, isFavorite: Boolean) {
        if (isFavorite)
            viewModel.addTeamToFavorites(teamId)
        else
            viewModel.deleteTeamFromFavorites(teamId)
    }

    private fun setImage() {
        if (game.homeTeam.isFavorite)
            binding.homeTeamFavorites.setImageResource(R.drawable.favorites_enable)
        else
            binding.homeTeamFavorites.setImageResource(R.drawable.nav_menu_favorites)

        if (game.awayTeam.isFavorite)
            binding.awayTeamFavorites.setImageResource(R.drawable.favorites_enable)
        else
            binding.awayTeamFavorites.setImageResource(R.drawable.nav_menu_favorites)
    }

    @SuppressLint("SetTextI18n")
    private fun updateUi(game: Game) {
        binding.apply {
            homeTeamNameGame.text = game.homeTeam.name
            awayTeamNameGame.text = game.awayTeam.name
            timeGame.text = "${game.date} ${game.time}"
            setImage()

            if (game.status != Status.GAME_NOT_STARTED.get) {
                ScoreGame.text = "${game.homeScores}-${game.awayScores}"
                lifecycleScope.launchWhenStarted {
                    viewModel.loadGameEvents(game.id).collectLatest {
                        initEventsAdapter(it)
                    }
                }
            } else
                ScoreGame.text = GAME_NOT_STARTED

            Picasso.get().load(game.homeTeam.logo).into(homeTeamLabelGame)
            Picasso.get().load(game.awayTeam.logo).into(awayTeamLabelGame)
            Picasso.get().load(game.league.logo).into(leagueEmblem)

            leagueName.text = "$LEAGUE ${game.league.name}"
        }
    }

    private fun initEventsAdapter(gameEvents: List<EventsAdapterItem>) =
        lifecycleScope.launch(Dispatchers.Main) {
            binding.matchInfo.isVisible = false
            binding.rcView.isVisible = true
            binding.rcView.layoutManager = LinearLayoutManager(context)
            val adapter = GameEventsAdapter(game, gameEvents)

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


    override fun positionActiveTabChanged(activeTabIndex: Int) {
        lifecycleScope.launch(Dispatchers.IO) {
            if (activeTabIndex == Tab.EVENTS.index) {
                viewModel.loadGameEvents(game.id).collectLatest {
                    initEventsAdapter(it)
                }
            } else {
                if (h2hGames.isEmpty())
                    viewModel.loadH2HGames(game.homeTeam.id, game.awayTeam.id).collect { list ->
                        h2hGames =
                            list.filter { it.id != game.id && it.status != Status.GAME_NOT_STARTED.get }
                        initH2HAdapter(h2hGames)
                    }
                else
                    initH2HAdapter(h2hGames)
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
        if (item.itemId == R.id.favorites) {
            game.isFavorite = !game.isFavorite
            buttonGameFavoriteClicked(game.id, game.isFavorite)
            if (game.isFavorite)
                item.setIcon(R.drawable.favorites_enable)
            else
                item.setIcon(R.drawable.favorite_not_enable)
        } else
            findNavController().navigate(R.id.fragmentSettings)
        return super.onOptionsItemSelected(item)
    }
}
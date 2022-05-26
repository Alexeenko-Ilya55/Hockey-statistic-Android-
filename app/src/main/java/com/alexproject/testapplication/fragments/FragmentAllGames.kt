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
import com.alexproject.testapplication.R
import com.alexproject.testapplication.adapters.GamesAdapter
import com.alexproject.testapplication.app.appComponent
import com.alexproject.testapplication.contracts.GameClickListener
import com.alexproject.testapplication.databinding.FragmentAllGamesBinding
import com.alexproject.testapplication.objects.GAME_ID
import com.alexproject.testapplication.viewModels.FragmentAllGamesViewModel
import com.alexproject.testapplication.viewModels.ViewModelFactory
import kotlinx.coroutines.flow.collectLatest
import java.time.LocalDate
import javax.inject.Inject

class FragmentAllGames : Fragment(), GameClickListener {

    private lateinit var binding: FragmentAllGamesBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: FragmentAllGamesViewModel
    private lateinit var listGames: List<Game>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setHasOptionsMenu(true)
        requireActivity().setTitle(R.string.fragmentAllGames)
        binding = FragmentAllGamesBinding.inflate(inflater, container, false)
        context?.appComponent?.inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[FragmentAllGamesViewModel::class.java]
        lifecycleScope.launchWhenStarted {
            viewModel.loadGamesByDate(LocalDate.now().toString()).collectLatest {
                listGames = it
                initRecyclerAdapter(it)
            }
        }
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
                newText?.let {
                    initRecyclerAdapter(listGames.filter {
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
}
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
import com.alexproject.testapplication.R
import com.alexproject.testapplication.adapters.GamesAdapter
import com.alexproject.testapplication.app.appComponent
import com.alexproject.testapplication.contracts.GameClickListener
import com.alexproject.testapplication.databinding.FragmentLiveBinding
import com.alexproject.testapplication.objects.GAME_ID
import com.alexproject.testapplication.viewModels.FragmentLiveViewModel
import com.alexproject.testapplication.viewModels.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

class FragmentLive : Fragment(), GameClickListener {

    private lateinit var binding: FragmentLiveBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: FragmentLiveViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLiveBinding.inflate(inflater, container, false)
        context?.appComponent?.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[FragmentLiveViewModel::class.java]
        binding.swipeRefresh.setOnRefreshListener {
            lifecycleScope.launch(Dispatchers.IO) {
                viewModel.updateLiveGames(LocalDate.now().toString())
            }
            binding.swipeRefresh.isRefreshing = false
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.loadLiveGames(LocalDate.now().toString())
                .collectLatest { initRecyclerAdapter(it) }
        }
    }

    private fun initRecyclerAdapter(games: List<Game>) {
        lifecycleScope.launch(Dispatchers.Main) {
            binding.rcView.layoutManager = LinearLayoutManager(context)
            val adapter = GamesAdapter(games, this@FragmentLive)
            binding.rcView.adapter = adapter
        }
    }

    override fun buttonGameFavoriteClicked(gameId: Int, isFavorite: Boolean) {
        if (isFavorite)
            viewModel.addGameToFavorites(gameId)
        else
            viewModel.deleteGameFromFavorites(gameId)
    }

    override fun itemGameClicked(gameId: Int) =
        findNavController().navigate(R.id.fragmentGame, bundleOf(GAME_ID to gameId))
}
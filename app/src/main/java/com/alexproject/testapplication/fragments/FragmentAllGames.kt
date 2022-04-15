package com.alexproject.testapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexproject.domain.models.Games
import com.alexproject.domain.useCases.FavoritesUseCase
import com.alexproject.testapplication.adapters.RecyclerAdapterGames
import com.alexproject.testapplication.app.appComponent
import com.alexproject.testapplication.databinding.FragmentAllGamesBinding
import com.alexproject.testapplication.viewModels.FragmentAllGamesViewModel
import com.alexproject.testapplication.viewModels.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentAllGames : Fragment() {

    private lateinit var binding: FragmentAllGamesBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var favoritesUseCase: FavoritesUseCase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllGamesBinding.inflate(inflater, container, false)
        context?.appComponent?.inject(this)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel =
            ViewModelProvider(this, viewModelFactory)[FragmentAllGamesViewModel::class.java]
        lifecycleScope.launchWhenStarted {
            viewModel.loadGamesByDate("2022-04-15").collectLatest {
                initRecyclerAdapter(it)
            }
        }
    }

    private fun initRecyclerAdapter(games: Games) {
        lifecycleScope.launch(Dispatchers.Main) {
            binding.rcView.layoutManager = LinearLayoutManager(context)
            val adapter =
                RecyclerAdapterGames(games, lifecycleScope, findNavController(), favoritesUseCase)
            binding.rcView.adapter = adapter
        }

    }
}
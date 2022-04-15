package com.alexproject.testapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alexproject.testapplication.app.appComponent
import com.alexproject.testapplication.databinding.FragmentFavoritesBinding
import com.alexproject.testapplication.viewModels.FragmentFavoritesViewModel
import com.alexproject.testapplication.viewModels.ViewModelFactory
import javax.inject.Inject

class FragmentFavorites : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        context?.appComponent?.inject(this)

        val viewModel =
            ViewModelProvider(this, viewModelFactory)[FragmentFavoritesViewModel::class.java]
        return binding.root
    }


}
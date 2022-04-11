package com.alexproject.testapplication.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexproject.testapplication.R
import com.alexproject.testapplication.viewModels.FragmentFavoritesViewModel

class FragmentFavorites : Fragment() {

    private lateinit var viewModel: FragmentFavoritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FragmentFavoritesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
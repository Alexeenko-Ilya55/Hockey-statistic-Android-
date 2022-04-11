package com.alexproject.testapplication.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.alexproject.testapplication.R
import com.alexproject.testapplication.databinding.FragmentMainBinding
import com.alexproject.testapplication.viewModels.FragmentMainViewModel

class FragmentMain : Fragment() {

    private lateinit var viewModel: FragmentMainViewModel
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[FragmentMainViewModel::class.java]


        NavigationUI.setupWithNavController(
            binding.bottomNavigationView,
            view.findNavController()
        )
        val navController = view.findNavController()
        //binding.bottomNavigationView.setupWithNavController(navController)

    }
}

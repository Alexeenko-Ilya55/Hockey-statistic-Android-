package com.alexproject.testapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alexproject.testapplication.app.appComponent
import com.alexproject.testapplication.databinding.FragmentTeamBinding
import com.alexproject.testapplication.viewModels.FragmentTeamViewModel
import com.alexproject.testapplication.viewModels.ViewModelFactory
import javax.inject.Inject

class FragmentTeam : Fragment() {

    private lateinit var binding: FragmentTeamBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeamBinding.inflate(inflater, container, false)
        context?.appComponent?.inject(this)

        val viewModel =
            ViewModelProvider(this, viewModelFactory)[FragmentTeamViewModel::class.java]
        binding.leagueButton.setOnClickListener {

        }
        return binding.root
    }

}
package com.alexproject.testapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.alexproject.domain.models.Team
import com.alexproject.testapplication.app.appComponent
import com.alexproject.testapplication.databinding.FragmentTeamBinding
import com.alexproject.testapplication.objects.TEAM_ID
import com.alexproject.testapplication.viewModels.FragmentTeamViewModel
import com.alexproject.testapplication.viewModels.ViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class FragmentTeam : Fragment() {

    private lateinit var binding: FragmentTeamBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: FragmentTeamViewModel
    lateinit var team: Team

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeamBinding.inflate(inflater, container, false)
        context?.appComponent?.inject(this)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[FragmentTeamViewModel::class.java]

        val teamId = arguments?.getInt(TEAM_ID)
        lifecycleScope.launchWhenStarted {
            teamId?.let {
                viewModel.loadTeamById(it).collectLatest { team ->
                    this@FragmentTeam.team = team
                    updateUi(team)
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.loadAllGamesForTeam(team.id)
        }

        binding.tabItemResults.setOnClickListener {

        }

        binding.leagueButton.setOnClickListener {

        }
        return binding.root
    }

    private fun updateUi(team: Team) {
        binding.apply {
            teamName.text = team.name
            Picasso.get().load(team.logo).into(teamEmblem)
        }
    }

}
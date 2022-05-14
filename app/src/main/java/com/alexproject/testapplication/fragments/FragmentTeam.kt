package com.alexproject.testapplication.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.alexproject.testapplication.R
import com.alexproject.testapplication.app.appComponent
import com.alexproject.testapplication.databinding.FragmentTeamBinding
import com.alexproject.testapplication.objects.COUNTRY_ID
import com.alexproject.testapplication.objects.LEAGUE_ID
import com.alexproject.testapplication.objects.TEAM_ID
import com.alexproject.testapplication.viewModels.FragmentTeamViewModel
import com.alexproject.testapplication.viewModels.ViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class FragmentTeam : Fragment() {

    private lateinit var binding: FragmentTeamBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: FragmentTeamViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTeamBinding.inflate(inflater, container, false)
        context?.appComponent?.inject(this)
        viewModel =
            ViewModelProvider(this, viewModelFactory)[FragmentTeamViewModel::class.java]

        val teamId = arguments?.getInt(TEAM_ID)!!
        val countryId = arguments?.getInt(COUNTRY_ID)!!
        val leagueId = arguments?.getInt(LEAGUE_ID)!!

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.loadTeamById(teamId).collectLatest { team ->
                initUI(team.name, team.logo, binding.teamEmblem, binding.teamName)
            }
        }
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.loadLeagueById(leagueId).collectLatest {
                initUI(
                    getString(R.string.league_name, it.name),
                    it.logo,
                    binding.leagueLogo,
                    binding.leagueName
                )
            }
        }
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.loadCountryById(countryId).collectLatest {
                initUI(
                    getString(R.string.country_name, it.name),
                    it.flag,
                    binding.countryFlag,
                    binding.countryName
                )
            }
        }

        lifecycleScope.launchWhenStarted {
            //viewModel.loadAllGamesForTeam(teamId)
        }

        binding.leagueButton.setOnClickListener {

        }
        return binding.root
    }

    private fun initUI(name: String, imageUrl: String?, imageView: ImageView, textView: TextView) {
        lifecycleScope.launch(Dispatchers.Main) {
            Picasso.get().load(imageUrl).into(imageView)
            textView.text = name
        }
    }
}
package com.alexproject.testapplication.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alexproject.domain.models.Response
import com.alexproject.testapplication.app.appComponent
import com.alexproject.testapplication.databinding.FragmentGameBinding
import com.alexproject.testapplication.viewModels.FragmentGameViewModel
import com.alexproject.testapplication.viewModels.ViewModelFactory
import javax.inject.Inject

class FragmentGame(
    private val game: Response
) : Fragment() {

    private lateinit var binding: FragmentGameBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGameBinding.inflate(inflater, container, false)

        context?.appComponent?.inject(this)

        val viewModel =
            ViewModelProvider(this, viewModelFactory)[FragmentGameViewModel::class.java]

        binding.apply {
            homeTeamNameGame.text = game.teams.home.name
            awayTeamNameGame.text = game.teams.away.name
            timeGame.text = game.time

            if (game.status != "NS")
                ScoreGame.text = "${game.scores.home}-${game.scores.away}"
            else
                ScoreGame.text = "-"
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.leagueButton.setOnClickListener {

        }
    }


}
package com.alexproject.testapplication.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alexproject.domain.models.Game
import com.alexproject.testapplication.app.appComponent
import com.alexproject.testapplication.databinding.FragmentGameBinding
import com.alexproject.testapplication.viewModels.FragmentGameViewModel
import com.alexproject.testapplication.viewModels.ViewModelFactory
import javax.inject.Inject

class FragmentGame(
    private val game: Game
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
            homeTeamNameGame.text = game.homeTeam.name
            awayTeamNameGame.text = game.awayTeam.name
            timeGame.text = game.date

            if (game.status != "NS")
                ScoreGame.text = "${game.homeScores}-${game.awayScores}"
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
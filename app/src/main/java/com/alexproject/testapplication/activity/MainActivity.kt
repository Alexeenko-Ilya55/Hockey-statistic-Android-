package com.alexproject.testapplication.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.alexproject.testapplication.R
import com.alexproject.testapplication.app.appComponent
import com.alexproject.testapplication.databinding.ActivityMainBinding
import com.alexproject.testapplication.objects.ACTION
import com.alexproject.testapplication.objects.GAME_ID
import com.alexproject.testapplication.objects.MOVE_TO_FAVORITES_GAMES
import com.alexproject.testapplication.viewModels.MainActivityViewModel
import com.alexproject.testapplication.viewModels.ViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        appComponent.inject(this)
        val viewModel = ViewModelProvider(this, viewModelFactory)[MainActivityViewModel::class.java]
        applicationContext.appComponent.inject(this)
        val navController = findNavController(R.id.activity_nav_container)
        binding.bottomNavigationView.setupWithNavController(navController)
        viewModel.initLeagueDownloadWorker(applicationContext)
        viewModel.initNotificationWorker(applicationContext)
        val gameId = intent.getIntExtra(GAME_ID, 0)
        if (gameId != 0)
            findNavController(R.id.activity_nav_container).navigate(
                R.id.fragmentGame, bundleOf(GAME_ID to gameId)
            )
        if (intent.getStringExtra(ACTION) == MOVE_TO_FAVORITES_GAMES)
            findNavController(R.id.activity_nav_container).navigate(R.id.fragmentFavorites)
    }
}
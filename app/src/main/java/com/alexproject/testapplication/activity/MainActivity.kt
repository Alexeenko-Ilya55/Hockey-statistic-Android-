package com.alexproject.testapplication.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.alexproject.testapplication.R
import com.alexproject.testapplication.app.appComponent
import com.alexproject.testapplication.databinding.ActivityMainBinding
import com.alexproject.testapplication.viewModels.MainActivityViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        applicationContext.appComponent.inject(this)
        val navController = findNavController(R.id.activity_nav_container)
        binding.bottomNavigationView.setupWithNavController(navController)
    }

}
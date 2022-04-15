package com.alexproject.testapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alexproject.testapplication.app.appComponent
import com.alexproject.testapplication.databinding.FragmentStatisticsBinding
import com.alexproject.testapplication.viewModels.FragmentStatisticsViewModel
import com.alexproject.testapplication.viewModels.ViewModelFactory
import javax.inject.Inject

class FragmentStatistics : Fragment() {

    private lateinit var binding: FragmentStatisticsBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        context?.appComponent?.inject(this)

        val viewModel =
            ViewModelProvider(this, viewModelFactory)[FragmentStatisticsViewModel::class.java]
        return binding.root
    }
}
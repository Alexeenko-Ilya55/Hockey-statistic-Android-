package com.alexproject.testapplication.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexproject.testapplication.R
import com.alexproject.testapplication.viewModels.FragmentStatisticsViewModel

class FragmentStatistics : Fragment() {

    companion object {
        fun newInstance() = FragmentStatistics()
    }

    private lateinit var viewModel: FragmentStatisticsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_statistics, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FragmentStatisticsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
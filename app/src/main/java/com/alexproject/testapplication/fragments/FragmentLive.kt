package com.alexproject.testapplication.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexproject.testapplication.R
import com.alexproject.testapplication.viewModels.FragmentLiveViewModel

class FragmentLive : Fragment() {

    companion object {
        fun newInstance() = FragmentLive()
    }

    private lateinit var viewModel: FragmentLiveViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_live, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FragmentLiveViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
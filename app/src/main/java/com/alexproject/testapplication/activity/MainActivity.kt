package com.alexproject.testapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alexproject.testapplication.R
import com.alexproject.testapplication.app.App

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (applicationContext as App).appComponent.inject(this)
    }
}
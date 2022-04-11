package com.alexproject.testapplication.app

import android.app.Application
import android.content.Context
import com.alexproject.testapplication.di.AppComponent
import com.alexproject.testapplication.di.DaggerAppComponent

class App : Application(), AppDependencies {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .appDependencies(this)
            .build()
    }
    override val context = this
}

interface AppDependencies {
    val context: Context
}
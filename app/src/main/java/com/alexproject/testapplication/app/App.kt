package com.alexproject.testapplication.app

import android.app.Application
import com.alexproject.testapplication.di.AppComponent
import com.alexproject.testapplication.di.AppModule
import com.alexproject.testapplication.di.DaggerAppComponent

class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(context = this))
            .build()
    }
}
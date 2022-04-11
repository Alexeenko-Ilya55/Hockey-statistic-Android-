package com.alexproject.testapplication.di

import android.view.View
import androidx.navigation.Navigation
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideNavController(view: View) = Navigation.findNavController(view = view)

    @Provides
    @Singleton
    fun provideActivity(view: View) = view
}
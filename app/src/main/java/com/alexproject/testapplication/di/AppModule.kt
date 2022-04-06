package com.alexproject.testapplication.di

import android.content.Context
import dagger.Module

@Module
class AppModule(private val context: Context) {

    fun provideContext() = context
}
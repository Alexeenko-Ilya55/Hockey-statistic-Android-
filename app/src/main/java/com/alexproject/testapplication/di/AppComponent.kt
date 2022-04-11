package com.alexproject.testapplication.di

import com.alexproject.testapplication.activity.MainActivity
import com.alexproject.testapplication.app.AppDependencies
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [AppModule::class, DomainModule::class, DataModule::class],
    dependencies = [AppDependencies::class]
)
@Singleton
interface AppComponent {
    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {
        fun appDependencies(appDependencies: AppDependencies): Builder
        fun build(): AppComponent
    }
}
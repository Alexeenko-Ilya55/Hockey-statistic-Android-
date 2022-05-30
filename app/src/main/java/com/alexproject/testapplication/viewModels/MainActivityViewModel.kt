package com.alexproject.testapplication.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.alexproject.testapplication.objects.WORK_NAME
import com.alexproject.testapplication.workManager.LeagueDownloadWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivityViewModel @Inject constructor() : ViewModel() {

    fun initWorker(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        val request = PeriodicWorkRequest.Builder(
            LeagueDownloadWorker::class.java,
            7,
            TimeUnit.DAYS
        ).setConstraints(constraints).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}
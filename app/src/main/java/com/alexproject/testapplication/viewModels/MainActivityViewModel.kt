package com.alexproject.testapplication.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.work.*
import com.alexproject.testapplication.objects.LEAGUE_UPDATE_WORK_NAME
import com.alexproject.testapplication.objects.NOTIFICATIONS_WORK_NAME
import com.alexproject.testapplication.workManager.LeagueDownloadWorker
import com.alexproject.testapplication.workManager.NotificationWorker
import java.util.concurrent.TimeUnit

class MainActivityViewModel : ViewModel() {

    fun initLeagueDownloadWorker(context: Context) {
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
            LEAGUE_UPDATE_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }

    fun initNotificationWorker(context: Context) {
        val request = PeriodicWorkRequestBuilder<NotificationWorker>(
            15,
            TimeUnit.MINUTES,
            5,
            TimeUnit.SECONDS
        ).build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            NOTIFICATIONS_WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            request
        )
    }
}
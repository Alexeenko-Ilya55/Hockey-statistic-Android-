package com.alexproject.testapplication.workManager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.alexproject.domain.useCases.UpdateAllLeaguesUseCase
import com.alexproject.testapplication.app.appComponent
import javax.inject.Inject

class LeagueDownloadWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    @Inject
    lateinit var updateAllLeaguesUseCase: UpdateAllLeaguesUseCase

    override suspend fun doWork(): Result {
        applicationContext.appComponent.inject(this)
        updateAllLeaguesUseCase.updateAllLeagues()
        return Result.success()
    }
}
package com.alexproject.testapplication.workManager

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.alexproject.testapplication.notification.StartEndGameNotification

class NotificationWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        StartEndGameNotification(applicationContext).checkNeedNotification()
        return Result.success()
    }
}

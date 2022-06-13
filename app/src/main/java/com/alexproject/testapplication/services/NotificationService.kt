package com.alexproject.testapplication.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.alexproject.testapplication.app.appComponent
import com.alexproject.testapplication.notification.StartEndGameNotification
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class NotificationService : Service() {

    @Inject
    lateinit var context: Context
    lateinit var coroutineJob: Job

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        appComponent.inject(this)
        coroutineJob = CoroutineScope(context as CoroutineContext).launch {
            while (true) {
                StartEndGameNotification(context).checkNeedNotification()
                delay(TimeUnit.MINUTES.toMillis(10))
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        coroutineJob.cancel("Service destroy")
        super.onDestroy()
    }
}
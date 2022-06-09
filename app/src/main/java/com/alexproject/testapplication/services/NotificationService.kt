package com.alexproject.testapplication.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.alexproject.testapplication.app.appComponent
import com.alexproject.testapplication.notification.StartEndGameNotification
import com.alexproject.testapplication.objects.TEN_MINUTES
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class NotificationService : Service() {

    @Inject
    lateinit var context: Context

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        appComponent.inject(this)
        CoroutineScope(context as CoroutineContext).launch {
            while (true) {
                StartEndGameNotification(context).checkNeedNotification()
                delay(TEN_MINUTES)
            }
        }
        return START_STICKY
    }
}
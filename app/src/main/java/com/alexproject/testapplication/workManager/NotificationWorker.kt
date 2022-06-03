package com.alexproject.testapplication.workManager

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.provider.Settings
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.alexproject.domain.useCases.LoadFavoritesGamesUseCase
import com.alexproject.testapplication.R
import com.alexproject.testapplication.activity.MainActivity
import com.alexproject.testapplication.app.appComponent
import com.alexproject.testapplication.objects.*
import kotlinx.coroutines.flow.collectLatest
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class NotificationWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {

    @Inject
    lateinit var loadFavoritesGamesUseCase: LoadFavoritesGamesUseCase
    private lateinit var intentFragmentGame: Intent
    private lateinit var intentFragmentFavorites: Intent
    private lateinit var pendingIntentGame: PendingIntent
    private lateinit var pendingIntentFavorites: PendingIntent


    override suspend fun doWork(): Result {
        applicationContext.appComponent.inject(this)
        createNotificationChannel()
        loadFavoritesGamesUseCase.loadFavoritesGames().collectLatest { listFavoritesGames ->
            listFavoritesGames.forEach { game ->
                val needNotificationToGame =
                    game.date == LocalDate.now().toString() && ((LocalTime.now()
                        .plusMinutes(10).isAfter(LocalTime.parse(game.time)) && LocalTime.now()
                        .isBefore(LocalTime.parse(game.time))) || (LocalTime.now()
                        .isAfter(LocalTime.parse(game.time).plusHours(2).plusMinutes(30))
                            && LocalTime.now()
                        .isBefore(LocalTime.parse(game.time).plusHours(2).plusMinutes(40))))

                if (needNotificationToGame)
                    createNotification(game.id, game.time, game.homeTeam.name, game.awayTeam.name)
            }
        }
        return Result.success()
    }

    private fun createNotificationChannel() {
        val attr = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ALARM)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
        channel.description = CHANNEL_DESCRIPTION_TEXT
        channel.enableVibration(true)
        channel.enableLights(true)
        channel.lightColor = Color.RED
        channel.setSound(Settings.System.DEFAULT_NOTIFICATION_URI, attr)
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotification(
        gameId: Int,
        gameTime: String,
        homeTeamName: String,
        awayTeamName: String
    ) {
        if (LocalTime.now().isBefore(LocalTime.parse(gameTime)))
            createBeforeGameNotification(gameId, gameTime, homeTeamName, awayTeamName)
        else
            createAfterGameNotification(gameId, homeTeamName, awayTeamName)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun createBeforeGameNotification(
        gameId: Int,
        gameTime: String,
        homeTeamName: String,
        awayTeamName: String
    ) {
        createIntents(gameId)
        val notification = createNotification(
            applicationContext.resources.getString(R.string.beforeGameTitleNotification),
            applicationContext.resources.getString(
                R.string.beforeGameContentTextNotification,
                Duration.between(LocalTime.now(), LocalTime.parse(gameTime)).toMinutes().toString(),
                homeTeamName,
                awayTeamName
            )
        )
        NotificationManagerCompat.from(applicationContext).notify(gameId, notification.build())
    }

    private fun createAfterGameNotification(
        gameId: Int,
        homeTeamName: String,
        awayTeamName: String
    ) {
        createIntents(gameId)
        val notification = createNotification(
            applicationContext.resources.getString(R.string.AfterGameTitleNotification),
            applicationContext.resources.getString(
                R.string.AfterGameContentTextNotification,
                homeTeamName,
                awayTeamName
            )
        )
        NotificationManagerCompat.from(applicationContext).notify(gameId, notification.build())
    }

    private fun createNotification(
        titleText: String,
        contentText: String
    ): NotificationCompat.Builder {
        return NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle(titleText)
            .setContentText(contentText)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(
                R.drawable.notification_icon,
                applicationContext.getString(R.string.notificationButtonName),
                pendingIntentGame
            )
            .setAutoCancel(true)
            .setContentIntent(pendingIntentFavorites)
            .setStyle(NotificationCompat.BigTextStyle())
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
            .setLights(Color.RED, 3000, 3000)
    }

    private fun createIntents(gameId: Int) {
        intentFragmentGame = Intent(applicationContext, MainActivity::class.java)
        intentFragmentGame.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intentFragmentGame.putExtra(GAME_ID, gameId)

        intentFragmentFavorites = Intent(applicationContext, MainActivity::class.java)
        intentFragmentFavorites.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intentFragmentFavorites.putExtra(ACTION, MOVE_TO_FAVORITES_GAMES)

        pendingIntentFavorites = PendingIntent.getActivity(
            applicationContext, 0, intentFragmentFavorites, PendingIntent.FLAG_IMMUTABLE
        )
        pendingIntentGame = PendingIntent.getActivity(
            applicationContext, 0, intentFragmentGame, PendingIntent.FLAG_IMMUTABLE
        )
    }
}

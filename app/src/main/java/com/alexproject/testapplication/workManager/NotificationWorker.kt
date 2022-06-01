package com.alexproject.testapplication.workManager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.alexproject.api.GAMES
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

    override suspend fun doWork(): Result {
        applicationContext.appComponent.inject(this)
        createNotificationChannel()
        loadFavoritesGamesUseCase.loadFavoritesGames().collectLatest { listFavoritesGames ->
            listFavoritesGames.forEach { game ->
                val needNotificationToGame =
                    game.date == LocalDate.now().toString() && ((LocalTime.now()
                        .plusMinutes(15).isAfter(LocalTime.parse(game.time)) && LocalTime.now()
                        .isBefore(LocalTime.parse(game.time))) || (LocalTime.now()
                        .isAfter(LocalTime.parse(game.time).plusMinutes(150)) && LocalTime.now()
                        .isBefore(LocalTime.parse(game.time).plusMinutes(165))))

                if (needNotificationToGame)
                    createNotification(game.id, game.time, game.homeTeam.name, game.awayTeam.name)
            }
        }
        return Result.success()
    }

    private fun createNotificationChannel() {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
        channel.description = CHANNEL_DESCRIPTION_TEXT
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

    private fun createBeforeGameNotification(
        gameId: Int,
        gameTime: String,
        homeTeamName: String,
        awayTeamName: String
    ) {
        val intentGame = Intent(applicationContext, MainActivity::class.java)
        intentGame.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intentGame.putExtra(GAME_ID, gameId)

        val intentFragmentFavorites = Intent(applicationContext, MainActivity::class.java)
        intentFragmentFavorites.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intentFragmentFavorites.putExtra(GAMES, GAME_NOT_STARTED)

        val pendingIntentFavorites = PendingIntent.getActivity(
            applicationContext, 0, intentFragmentFavorites, PendingIntent.FLAG_IMMUTABLE
        )
        val pendingIntentGame = PendingIntent.getActivity(
            applicationContext, 0, intentGame, PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle(applicationContext.resources.getString(R.string.beforeGameTitleNotification))
            .setContentText(
                applicationContext.resources.getString(
                    R.string.beforeGameContentTextNotification,
                    Duration.between(LocalTime.now(), LocalTime.parse(gameTime)).toMinutes().toString(),
                    homeTeamName,
                    awayTeamName
                )
            )
            .addAction(
                R.drawable.notification_icon,
                applicationContext.getString(R.string.notificationButtonName),
                pendingIntentGame
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntentFavorites)

        NotificationManagerCompat.from(applicationContext).notify(gameId, notification.build())
    }

    private fun createAfterGameNotification(
        gameId: Int,
        homeTeamName: String,
        awayTeamName: String
    ) {
        val intentGame = Intent(applicationContext, MainActivity::class.java)
        intentGame.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intentGame.putExtra(GAME_ID, gameId)

        val intentFragmentFavorites = Intent(applicationContext, MainActivity::class.java)
        intentFragmentFavorites.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        intentFragmentFavorites.putExtra(GAMES, GAMES)

        val pendingIntentFavorites = PendingIntent.getActivity(
            applicationContext, 0, intentFragmentFavorites, PendingIntent.FLAG_IMMUTABLE
        )
        val pendingIntentGame = PendingIntent.getActivity(
            applicationContext, 0, intentGame, PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentTitle(applicationContext.resources.getString(R.string.AfterGameTitleNotification))
            .setContentText(
                applicationContext.resources.getString(
                    R.string.AfterGameContentTextNotification,
                    homeTeamName,
                    awayTeamName
                )
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .addAction(
                R.drawable.notification_icon,
                applicationContext.getString(R.string.notificationButtonName),
                pendingIntentGame
            )
            .setAutoCancel(true)
            .setContentIntent(pendingIntentFavorites)

        NotificationManagerCompat.from(applicationContext).notify(gameId, notification.build())
    }
}

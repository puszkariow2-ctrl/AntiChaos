package com.antichaos.app.core.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.work.*
import com.antichaos.app.R
import com.antichaos.app.presentation.MainActivity
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages reminder notifications using WorkManager for reliable scheduling.
 */
@Singleton
class ReminderNotificationManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val workManager: WorkManager
) {
    companion object {
        const val CHANNEL_ID_REMINDERS = "antichaos_reminders"
        const val CHANNEL_NAME_REMINDERS = "Нагадування AntiChaos"
        const val TAG_REMINDER = "ReminderNotification"
    }

    init {
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        val remindersChannel = NotificationChannel(
            CHANNEL_ID_REMINDERS,
            CHANNEL_NAME_REMINDERS,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Нагадування про задачі та важливі події"
            enableVibration(true)
        }

        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(remindersChannel)
    }

    /**
     * Schedule a one-time reminder notification.
     */
    fun scheduleReminder(reminderId: Long, text: String, triggerTimeMillis: Long) {
        val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(
                triggerTimeMillis - System.currentTimeMillis(),
                TimeUnit.MILLISECONDS
            )
            .setInputData(
                Data.Builder()
                    .putLong("reminder_id", reminderId)
                    .putString("reminder_text", text)
                    .build()
            )
            .addTag(TAG_REMINDER + reminderId)
            .build()

        workManager.enqueueUniqueWork(
            TAG_REMINDER + reminderId,
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }

    /**
     * Cancel a specific reminder.
     */
    fun cancelReminder(reminderId: Long) {
        workManager.cancelUniqueWork(TAG_REMINDER + reminderId)
    }

    /**
     * Show the actual notification (called from Worker).
     */
    fun showNotification(reminderId: Long, text: String) {
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("open_reminder", reminderId)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            reminderId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID_REMINDERS)
            .setSmallIcon(R.drawable.ic_notification) // TODO: add icon
            .setContentTitle("AntiChaos")
            .setContentText(text)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(reminderId.toInt(), notification)
    }
}

/**
 * Worker that triggers the reminder notification at scheduled time.
 */
class ReminderWorker @Inject constructor(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    @Inject lateinit var notificationManager: ReminderNotificationManager

    override suspend fun doWork(): Result {
        val reminderId = inputData.getLong("reminder_id", -1L)
        val text = inputData.getString("reminder_text") ?: "Нагадування"

        if (reminderId > 0) {
            notificationManager.showNotification(reminderId, text)
        }

        return Result.success()
    }
}

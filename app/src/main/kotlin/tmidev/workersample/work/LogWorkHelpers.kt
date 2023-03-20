package tmidev.workersample.work

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.content.getSystemService
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ForegroundInfo
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import tmidev.workersample.R
import tmidev.workersample.util.isAndroid8OrUp
import java.util.concurrent.TimeUnit

private const val WORK_NOTIFICATION_CHANNEL_ID = "log_work_notification_channel"
private const val WORK_NOTIFICATION_ID = 1

const val LOG_WORK_INTERVAL = 15L

private fun Context.workNotification(): Notification {
    if (isAndroid8OrUp()) {
        val channel = NotificationChannel(
            WORK_NOTIFICATION_CHANNEL_ID,
            getString(R.string.logWorkChannelName),
            NotificationManager.IMPORTANCE_LOW,
        ).apply {
            description = getString(R.string.logWorkChannelDescription)
        }

        val notificationManager = getSystemService<NotificationManager>()

        notificationManager?.createNotificationChannel(channel)
    }

    return NotificationCompat.Builder(this, WORK_NOTIFICATION_CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_notification)
        .setContentTitle(getString(R.string.logWorkNotificationTitle))
        .setContentText(getString(R.string.logWorkNotificationText))
        .setPriority(NotificationCompat.PRIORITY_LOW)
        .build()
}

fun Context.logWorkForegroundInfo(): ForegroundInfo = ForegroundInfo(
    WORK_NOTIFICATION_ID,
    workNotification()
)

private val workConstraints
    get() = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

private val logWorkRequest: PeriodicWorkRequest =
    PeriodicWorkRequestBuilder<LogWork>(LOG_WORK_INTERVAL, TimeUnit.MINUTES)
        .setConstraints(workConstraints)
        .build()

fun Context.enqueueLogWork() {
    WorkManager.getInstance(this).apply {
        enqueueUniquePeriodicWork(
            LogWork.workName,
            ExistingPeriodicWorkPolicy.KEEP,
            logWorkRequest
        )
    }
}
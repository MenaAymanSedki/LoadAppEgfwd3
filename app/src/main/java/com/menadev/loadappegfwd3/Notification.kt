package com.menadev.loadappegfwd3

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.menadev.loadappegfwd3.DetailActivity.Companion.bundleExtrasOf

private val DOWNLOAD_COMPLETED_ID=1
private val NOTIFICATION_REQUEST_CODE=1


fun NotificationManager.sendDownloadCompletedNotification(
    fileName:String,
    downloadStatus:DownloadStatus,
    context: Context
){
    val contentIntent =Intent(context,DetailActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        putExtras(bundleExtrasOf(fileName,downloadStatus))

    }
    val contentPendingIntent =PendingIntent.getActivity(
        context,
        NOTIFICATION_REQUEST_CODE,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    val checkStatusAction = NotificationCompat.Action.Builder(
        null,
        context.getString(R.string.notification_action_check_status),
        contentPendingIntent

    ).build()

    NotificationCompat.Builder(
        context,
        context.getString(R.string.notification_channel_id)
    )

        .setSmallIcon(R.drawable.ic_assistant_black_24dp)
        .setContentTitle("Android kotlin Nanodegree")
        .setContentText("The Project 3 repositroy is download ")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
        .addAction(checkStatusAction)
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        .apply {

        }.run {
            notify(DOWNLOAD_COMPLETED_ID,this.build())
        }

}
fun NotificationManager.createDownloadStatusChannel(context: Context){
    Build.VERSION.SDK_INT.takeIf { it >= Build.VERSION_CODES.O }?.run {
        NotificationChannel(
            "loading_status",
            "Download status",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "loading_status"
            setShowBadge(true)
        }.run {
            createNotificationChannel(this)
        }
    }

    fun NotificationManager.createDownloadStatusChannel(context: Context){
        Build.VERSION.SDK_INT.takeIf { it >= Build.VERSION_CODES.O }?.run {
            NotificationChannel(
                context.getString(R.string.notification_channel_id),
                context.getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_DEFAULT



            ).apply {
                description = context.getString(R.string.notification_channel_description)
                setShowBadge(true)
            }.run {
                createNotificationChannel(this)
            }
        }



    }
}
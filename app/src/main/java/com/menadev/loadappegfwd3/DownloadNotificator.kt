package com.menadev.loadappegfwd3

import android.app.DownloadManager
import android.content.Context
import android.telephony.mbms.DownloadStatusListener
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import timber.log.Timber

class DownloadNotificator(private val context: Context,private val lifecycle: Lifecycle):LifecycleObserver {

    init {
        lifecycle.addObserver(this).also {
     Timber.d("Notificator add")
        }
    }

    fun notify(
        fileName:String,
        downloadStatus:DownloadStatus
    ){
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)){
            Timber.d("Notify with a Toast")
            Toast.makeText(
                context,
                context.getString(R.string.download_completed),
                Toast.LENGTH_SHORT

            ).show()
        }
        with(context.applicationContext){
            getNotificationManager().run {
                createDownloadStatusChannel(applicationContext)
                sendDownloadCompletedNotification(
                    fileName,
                    downloadStatus,
                    applicationContext
                )
            }
        }




    }

    @Suppress("DEPRECATION")
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unregisterObserver()= lifecycle.removeObserver(this)
        .also {
            Timber.d("Notification removed from lifecycle")
        }




}
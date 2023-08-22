package com.nadev.module_26

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCM : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        zapros(this)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)

    }

    @SuppressLint("MissingPermission", "SuspiciousIndentation")
    private fun zapros(context: Context) {
        Log.d("time", "activity started")
        val name = "alarm manager"
        val descriptionText = "wake up"
        val priority = NotificationManager.IMPORTANCE_DEFAULT

        val channel = NotificationChannel("ID", name, priority).apply {
            description = descriptionText
        }

        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(context, "ID")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Время пришло").setContentText("Пора вставать")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()

        NotificationManagerCompat.from(context).notify(10, notification)

    }
}
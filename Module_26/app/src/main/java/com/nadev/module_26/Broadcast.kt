package com.nadev.module_26

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class Broadcast : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("RECEIVE", "MERGE")
        zapros(context!!, intent)
    }
    private fun zapros(context: Context, intent: Intent?){
        Log.d("time", "activity started")
        val name = "alarm manager"
        val descriptionText = "wake up"
        val priority = NotificationManager.IMPORTANCE_DEFAULT

        val channel = NotificationChannel("ID", name, priority).apply {
            description = descriptionText
        }

        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(context, "ID").setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Время пришло").setContentText("Пора вставать")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT).build()

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }else{
            NotificationManagerCompat.from(context).notify(10, notification)
        }
    }
}
package ict2105.team02.application.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ict2105.team02.application.R

class NotificationSpawner(private val context: Context) {
    private val CHANNEL_ID: String = "defaultChannel"
    private val mainActivityIntentSpec: String = context.getString(R.string.main_activity_intent_fragment)

    /**
     * Must be called on application startup to allow notifications to work properly.
     */
    fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.default_channel_name)
            val descriptionText = context.getString(R.string.default_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * Spawns a sample ready notification.
     */
    fun generateSampleReadyNotification() {
        // Create an explicit intent for an Activity in your app
        val intent = Intent(context, NotificationActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        intent.putExtra(mainActivityIntentSpec, "equipment")

        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ttsh_logo)
            .setContentTitle(context.getString(R.string.sample_ready_title))
//            .setStyle(
//                NotificationCompat.BigTextStyle()
//                    .bigText(context.getString(R.string.sample_ready_description))
//            )
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(context.resources.getInteger(R.integer.sample_ready_id), builder.build())
        }
    }
}
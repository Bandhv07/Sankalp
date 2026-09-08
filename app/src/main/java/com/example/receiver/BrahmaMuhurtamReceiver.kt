package com.example.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.MainActivity
import com.example.R
import com.example.util.AstronomicalCalculator
import com.example.util.ReminderScheduler

class BrahmaMuhurtamReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val isTest = intent.getBooleanExtra(ReminderScheduler.EXTRA_IS_TEST, false)
        val reminderType = intent.getStringExtra(ReminderScheduler.EXTRA_REMINDER_TYPE)
            ?: if (intent.action == ReminderScheduler.ACTION_SLEEP_REMINDER) ReminderScheduler.TYPE_SLEEP else ReminderScheduler.TYPE_BRAHMA_MUHURTAM

        val isSleep = reminderType == ReminderScheduler.TYPE_SLEEP

        val channelId = if (isSleep) "sleep_reminder_channel" else "brahma_muhurtam_channel"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager ?: return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = if (isSleep) "Night Sleep Reminder (7h Rest)" else "Brahma Muhurtam Alert"
            val descriptionText = if (isSleep)
                "Night reminder to sleep 7 hours before waking up for Brahma Muhurtam"
            else
                "Daily reminders during auspicious Brahma Muhurtam for Sri Hanuman Chalisa recitations"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
                enableVibration(true)
                vibrationPattern = if (isSleep) longArrayOf(0, 200, 150, 200) else longArrayOf(0, 300, 200, 300)
            }
            notificationManager.createNotificationChannel(channel)
        }

        // Verify POST_NOTIFICATIONS permission on Android 13+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
        }

        val launchIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            if (isSleep) 201 else 101,
            launchIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val prefs = context.getSharedPreferences("sankalpa_prefs", Context.MODE_PRIVATE)
        val wakeHour = prefs.getInt("pref_reminder_hour", 4)
        val wakeMin = prefs.getInt("pref_reminder_minute", 30)
        val wakeTimeStr = AstronomicalCalculator.formatTime(wakeHour, wakeMin)

        val title = when {
            isTest && isSleep -> "🌙 Test Alert: 7-Hour Bedtime Reminder"
            isTest -> "🕉️ Test Alert: Brahma Muhurtam Reminder"
            isSleep -> "🌙 Time for 7-Hour Rest • Bedtime Reminder"
            else -> "🕉️ Brahma Muhurtam: Sri Hanuman Chalisa"
        }

        val bodyText = when {
            isTest && isSleep -> "Bedtime reminder is working! Sleep now to wake up rejuvenated at $wakeTimeStr."
            isTest -> "Brahma Muhurtam reminder is verified! You will receive daily alerts for your 11 Chalisa recitations."
            isSleep -> "Wind down for sleep now to achieve your 7 hours of rest and wake up fresh at $wakeTimeStr for Brahma Muhurtam Hanuman Chalisa."
            else -> "The sacred Brahma Muhurtam dawn hours are here. Begin your 11 recitations of Sri Hanuman Chalisa. Jai Bajrangbali!"
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(bodyText)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(
                        if (isSleep) {
                            "$bodyText\n\n\"Shubh Ratri • Rest peacefully with Lord Hanuman's protection.\" 🪔"
                        } else {
                            "$bodyText\n\n\"Manojavam Maaruta Tulya Vegam, Jitendriyam Buddhimataam Varishtam\" 🙏"
                        }
                    )
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setCategory(if (isSleep) NotificationCompat.CATEGORY_ALARM else NotificationCompat.CATEGORY_REMINDER)
            .setColor(if (isSleep) 0xFF3F51B5.toInt() else 0xFFD84315.toInt())
            .build()

        val notificationId = when {
            isSleep && isTest -> 2002
            isSleep -> 2001
            isTest -> 1002
            else -> 1001
        }
        notificationManager.notify(notificationId, notification)

        // Reschedule for next day if this was an actual scheduled trigger
        if (!isTest) {
            if (isSleep) {
                val sleepHour = prefs.getInt("pref_sleep_hour", 21)
                val sleepMinute = prefs.getInt("pref_sleep_minute", 30)
                val sleepEnabled = prefs.getBoolean("pref_sleep_reminder_enabled", true)
                if (sleepEnabled) {
                    ReminderScheduler.scheduleSleepReminder(context, sleepHour, sleepMinute)
                }
            } else {
                val enabled = prefs.getBoolean("pref_reminder_enabled", true)
                if (enabled) {
                    ReminderScheduler.scheduleDailyReminder(context, wakeHour, wakeMin)
                }
            }
        }
    }
}

package com.example.util

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.receiver.BrahmaMuhurtamReceiver
import java.util.Calendar

object ReminderScheduler {
    private const val TAG = "ReminderScheduler"

    const val REQUEST_CODE_DAILY_ALARM = 1001
    const val REQUEST_CODE_SLEEP_ALARM = 2001

    const val ACTION_REMINDER = "com.aistudio.sankalpam.ACTION_REMINDER"
    const val ACTION_SLEEP_REMINDER = "com.aistudio.sankalpam.ACTION_SLEEP_REMINDER"

    const val EXTRA_IS_TEST = "extra_is_test"
    const val EXTRA_REMINDER_TYPE = "extra_reminder_type"
    const val TYPE_BRAHMA_MUHURTAM = "brahma_muhurtam"
    const val TYPE_SLEEP = "sleep_bedtime"

    fun scheduleDailyReminder(context: Context, hour: Int, minute: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return

        val intent = Intent(context, BrahmaMuhurtamReceiver::class.java).apply {
            action = ACTION_REMINDER
            putExtra(EXTRA_IS_TEST, false)
            putExtra(EXTRA_REMINDER_TYPE, TYPE_BRAHMA_MUHURTAM)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE_DAILY_ALARM,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            // If the time has already passed today, schedule for tomorrow
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        scheduleAlarm(alarmManager, calendar.timeInMillis, pendingIntent)
        Log.d(TAG, "Scheduled Brahma Muhurtam reminder for ${calendar.time}")
    }

    fun cancelReminder(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
        val intent = Intent(context, BrahmaMuhurtamReceiver::class.java).apply {
            action = ACTION_REMINDER
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE_DAILY_ALARM,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

    fun scheduleSleepReminder(context: Context, hour: Int, minute: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return

        val intent = Intent(context, BrahmaMuhurtamReceiver::class.java).apply {
            action = ACTION_SLEEP_REMINDER
            putExtra(EXTRA_IS_TEST, false)
            putExtra(EXTRA_REMINDER_TYPE, TYPE_SLEEP)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE_SLEEP_ALARM,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }

        scheduleAlarm(alarmManager, calendar.timeInMillis, pendingIntent)
        Log.d(TAG, "Scheduled Bedtime Sleep reminder for ${calendar.time}")
    }

    fun cancelSleepReminder(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
        val intent = Intent(context, BrahmaMuhurtamReceiver::class.java).apply {
            action = ACTION_SLEEP_REMINDER
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            REQUEST_CODE_SLEEP_ALARM,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

    private fun scheduleAlarm(alarmManager: AlarmManager, triggerAtMillis: Long, pendingIntent: PendingIntent) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (alarmManager.canScheduleExactAlarms()) {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        triggerAtMillis,
                        pendingIntent
                    )
                } else {
                    alarmManager.setAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP,
                        triggerAtMillis,
                        pendingIntent
                    )
                }
            } else {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerAtMillis,
                    pendingIntent
                )
            }
        } catch (e: SecurityException) {
            Log.e(TAG, "Failed to schedule exact alarm, falling back to inexact", e)
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
        }
    }

    fun triggerTestNotification(context: Context, isSleepReminder: Boolean = false) {
        val receiver = BrahmaMuhurtamReceiver()
        val intent = Intent(context, BrahmaMuhurtamReceiver::class.java).apply {
            action = if (isSleepReminder) ACTION_SLEEP_REMINDER else ACTION_REMINDER
            putExtra(EXTRA_IS_TEST, true)
            putExtra(EXTRA_REMINDER_TYPE, if (isSleepReminder) TYPE_SLEEP else TYPE_BRAHMA_MUHURTAM)
        }
        receiver.onReceive(context, intent)
    }
}

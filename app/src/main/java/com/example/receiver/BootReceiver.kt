package com.example.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.util.ReminderScheduler

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val prefs = context.getSharedPreferences("sankalpa_prefs", Context.MODE_PRIVATE)

            // Reschedule Brahma Muhurtam morning reminder
            val morningEnabled = prefs.getBoolean("pref_reminder_enabled", true)
            val morningHour = prefs.getInt("pref_reminder_hour", 4)
            val morningMinute = prefs.getInt("pref_reminder_minute", 30)
            if (morningEnabled) {
                ReminderScheduler.scheduleDailyReminder(context, morningHour, morningMinute)
            }

            // Reschedule 7h Night Bedtime sleep reminder
            val sleepEnabled = prefs.getBoolean("pref_sleep_reminder_enabled", true)
            val sleepHour = prefs.getInt("pref_sleep_hour", 21)
            val sleepMinute = prefs.getInt("pref_sleep_minute", 30)
            if (sleepEnabled) {
                ReminderScheduler.scheduleSleepReminder(context, sleepHour, sleepMinute)
            }
        }
    }
}

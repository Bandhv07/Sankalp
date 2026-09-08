package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.data.dao.SankalpaDao
import com.example.data.model.SankalpaDay
import com.example.util.AstronomicalCalculator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

data class UserSettings(
    val reminderHour: Int = 4,
    val reminderMinute: Int = 30,
    val reminderEnabled: Boolean = true,
    // Sleep reminder settings (7 hours sleep goal)
    val sleepReminderEnabled: Boolean = true,
    val targetSleepHours: Double = 7.0,
    val sleepReminderHour: Int = 21, // 09:30 PM for 04:30 AM wake-up
    val sleepReminderMinute: Int = 30,
    // Location-based Brahma Muhurtam
    val useLocationForBrahmaMuhurtam: Boolean = true,
    val latitude: Double = 17.3850, // Default Hyderabad, India
    val longitude: Double = 78.4867,
    val locationName: String = "Hyderabad, India",
    val calculatedSunriseHour: Int = 6,
    val calculatedSunriseMinute: Int = 6,
    val calculatedBrahmaStartHour: Int = 4,
    val calculatedBrahmaStartMinute: Int = 30,
    // App preferences
    val soundChimeEnabled: Boolean = true,
    val hapticEnabled: Boolean = true,
    val selectedLanguage: String = "Telugu"
)

class SankalpaRepository(
    private val dao: SankalpaDao,
    private val context: Context
) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("sankalpa_prefs", Context.MODE_PRIVATE)

    private val _settings = MutableStateFlow(loadSettings())
    val settings: StateFlow<UserSettings> = _settings.asStateFlow()

    private val _currentCycleId = MutableStateFlow(prefs.getInt(KEY_CYCLE_ID, 1))
    val currentCycleId: StateFlow<Int> = _currentCycleId.asStateFlow()

    fun getDays(cycleId: Int): Flow<List<SankalpaDay>> {
        return dao.getDaysForCycle(cycleId)
    }

    suspend fun ensureCycleInitialized(cycleId: Int = _currentCycleId.value) = withContext(Dispatchers.IO) {
        val count = dao.getDayCountForCycle(cycleId)
        if (count == 0) {
            val dateFormat = SimpleDateFormat("MMM dd", Locale.getDefault())
            val calendar = Calendar.getInstance()
            val initialDays = (1..11).map { dayNum ->
                val dateStr = "Day $dayNum • " + dateFormat.format(calendar.time)
                calendar.add(Calendar.DAY_OF_YEAR, 1)
                SankalpaDay(
                    cycleId = cycleId,
                    dayNumber = dayNum,
                    targetCount = 11,
                    completedCount = 0,
                    isCompleted = false,
                    dateString = dateStr,
                    completedAtMillis = null
                )
            }
            dao.insertDays(initialDays)
        }
    }

    suspend fun incrementCount(day: SankalpaDay): SankalpaDay = withContext(Dispatchers.IO) {
        val newCount = (day.completedCount + 1).coerceAtMost(day.targetCount)
        val isNowCompleted = newCount >= day.targetCount
        val completedTimestamp = if (isNowCompleted && day.completedAtMillis == null) {
            System.currentTimeMillis()
        } else day.completedAtMillis

        val updated = day.copy(
            completedCount = newCount,
            isCompleted = isNowCompleted,
            completedAtMillis = completedTimestamp
        )
        dao.updateDay(updated)
        updated
    }

    suspend fun decrementCount(day: SankalpaDay): SankalpaDay = withContext(Dispatchers.IO) {
        val newCount = (day.completedCount - 1).coerceAtLeast(0)
        val updated = day.copy(
            completedCount = newCount,
            isCompleted = newCount >= day.targetCount,
            completedAtMillis = if (newCount < day.targetCount) null else day.completedAtMillis
        )
        dao.updateDay(updated)
        updated
    }

    suspend fun resetDayCount(day: SankalpaDay) = withContext(Dispatchers.IO) {
        val updated = day.copy(
            completedCount = 0,
            isCompleted = false,
            completedAtMillis = null
        )
        dao.updateDay(updated)
    }

    suspend fun startNewCycle(): Int = withContext(Dispatchers.IO) {
        val maxCycle = dao.getMaxCycleId() ?: 1
        val newCycleId = maxCycle + 1
        _currentCycleId.value = newCycleId
        prefs.edit().putInt(KEY_CYCLE_ID, newCycleId).apply()
        ensureCycleInitialized(newCycleId)
        newCycleId
    }

    fun updateLocation(lat: Double, lon: Double, locationName: String) {
        val current = _settings.value
        val sunTimes = AstronomicalCalculator.calculateBrahmaMuhurtam(lat, lon)
        val (bedHour, bedMin) = AstronomicalCalculator.calculateBedtime(
            sunTimes.brahmaMuhurtaStartHour,
            sunTimes.brahmaMuhurtaStartMinute,
            current.targetSleepHours
        )

        val updated = current.copy(
            latitude = lat,
            longitude = lon,
            locationName = locationName,
            calculatedSunriseHour = sunTimes.sunriseHour,
            calculatedSunriseMinute = sunTimes.sunriseMinute,
            calculatedBrahmaStartHour = sunTimes.brahmaMuhurtaStartHour,
            calculatedBrahmaStartMinute = sunTimes.brahmaMuhurtaStartMinute,
            reminderHour = sunTimes.brahmaMuhurtaStartHour,
            reminderMinute = sunTimes.brahmaMuhurtaStartMinute,
            sleepReminderHour = bedHour,
            sleepReminderMinute = bedMin
        )
        updateSettings(updated)
    }

    fun updateSettings(newSettings: UserSettings) {
        _settings.value = newSettings
        prefs.edit().apply {
            putInt(KEY_REMINDER_HOUR, newSettings.reminderHour)
            putInt(KEY_REMINDER_MINUTE, newSettings.reminderMinute)
            putBoolean(KEY_REMINDER_ENABLED, newSettings.reminderEnabled)
            putBoolean(KEY_SLEEP_REMINDER_ENABLED, newSettings.sleepReminderEnabled)
            putFloat(KEY_TARGET_SLEEP_HOURS, newSettings.targetSleepHours.toFloat())
            putInt(KEY_SLEEP_HOUR, newSettings.sleepReminderHour)
            putInt(KEY_SLEEP_MINUTE, newSettings.sleepReminderMinute)
            putBoolean(KEY_USE_LOCATION, newSettings.useLocationForBrahmaMuhurtam)
            putFloat(KEY_LATITUDE, newSettings.latitude.toFloat())
            putFloat(KEY_LONGITUDE, newSettings.longitude.toFloat())
            putString(KEY_LOCATION_NAME, newSettings.locationName)
            putInt(KEY_SUNRISE_HOUR, newSettings.calculatedSunriseHour)
            putInt(KEY_SUNRISE_MIN, newSettings.calculatedSunriseMinute)
            putInt(KEY_BRAHMA_HOUR, newSettings.calculatedBrahmaStartHour)
            putInt(KEY_BRAHMA_MIN, newSettings.calculatedBrahmaStartMinute)
            putBoolean(KEY_SOUND_CHIME, newSettings.soundChimeEnabled)
            putBoolean(KEY_HAPTIC, newSettings.hapticEnabled)
            putString(KEY_LANGUAGE, newSettings.selectedLanguage)
            apply()
        }
    }

    private fun loadSettings(): UserSettings {
        val lat = prefs.getFloat(KEY_LATITUDE, 17.3850f).toDouble()
        val lon = prefs.getFloat(KEY_LONGITUDE, 78.4867f).toDouble()
        val locName = prefs.getString(KEY_LOCATION_NAME, "Hyderabad, India") ?: "Hyderabad, India"

        // Recalculate astronomical times based on current date
        val sunTimes = AstronomicalCalculator.calculateBrahmaMuhurtam(lat, lon)
        val targetSleep = prefs.getFloat(KEY_TARGET_SLEEP_HOURS, 7.0f).toDouble()

        val savedReminderHour = prefs.getInt(KEY_REMINDER_HOUR, sunTimes.brahmaMuhurtaStartHour)
        val savedReminderMin = prefs.getInt(KEY_REMINDER_MINUTE, sunTimes.brahmaMuhurtaStartMinute)

        val (calcBedHour, calcBedMin) = AstronomicalCalculator.calculateBedtime(
            savedReminderHour,
            savedReminderMin,
            targetSleep
        )

        return UserSettings(
            reminderHour = savedReminderHour,
            reminderMinute = savedReminderMin,
            reminderEnabled = prefs.getBoolean(KEY_REMINDER_ENABLED, true),
            sleepReminderEnabled = prefs.getBoolean(KEY_SLEEP_REMINDER_ENABLED, true),
            targetSleepHours = targetSleep,
            sleepReminderHour = prefs.getInt(KEY_SLEEP_HOUR, calcBedHour),
            sleepReminderMinute = prefs.getInt(KEY_SLEEP_MINUTE, calcBedMin),
            useLocationForBrahmaMuhurtam = prefs.getBoolean(KEY_USE_LOCATION, true),
            latitude = lat,
            longitude = lon,
            locationName = locName,
            calculatedSunriseHour = sunTimes.sunriseHour,
            calculatedSunriseMinute = sunTimes.sunriseMinute,
            calculatedBrahmaStartHour = sunTimes.brahmaMuhurtaStartHour,
            calculatedBrahmaStartMinute = sunTimes.brahmaMuhurtaStartMinute,
            soundChimeEnabled = prefs.getBoolean(KEY_SOUND_CHIME, true),
            hapticEnabled = prefs.getBoolean(KEY_HAPTIC, true),
            selectedLanguage = prefs.getString(KEY_LANGUAGE, "Telugu") ?: "Telugu"
        )
    }

    companion object {
        private const val KEY_CYCLE_ID = "pref_cycle_id"
        private const val KEY_REMINDER_HOUR = "pref_reminder_hour"
        private const val KEY_REMINDER_MINUTE = "pref_reminder_minute"
        private const val KEY_REMINDER_ENABLED = "pref_reminder_enabled"
        private const val KEY_SLEEP_REMINDER_ENABLED = "pref_sleep_reminder_enabled"
        private const val KEY_TARGET_SLEEP_HOURS = "pref_target_sleep_hours"
        private const val KEY_SLEEP_HOUR = "pref_sleep_hour"
        private const val KEY_SLEEP_MINUTE = "pref_sleep_minute"
        private const val KEY_USE_LOCATION = "pref_use_location"
        private const val KEY_LATITUDE = "pref_latitude"
        private const val KEY_LONGITUDE = "pref_longitude"
        private const val KEY_LOCATION_NAME = "pref_location_name"
        private const val KEY_SUNRISE_HOUR = "pref_sunrise_hour"
        private const val KEY_SUNRISE_MIN = "pref_sunrise_min"
        private const val KEY_BRAHMA_HOUR = "pref_brahma_hour"
        private const val KEY_BRAHMA_MIN = "pref_brahma_min"
        private const val KEY_SOUND_CHIME = "pref_sound_chime"
        private const val KEY_HAPTIC = "pref_haptic"
        private const val KEY_LANGUAGE = "pref_language"
    }
}

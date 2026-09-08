package com.example.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.data.model.SankalpaDay
import com.example.data.repository.SankalpaRepository
import com.example.data.repository.UserSettings
import com.example.util.AstronomicalCalculator
import com.example.util.ChimeHelper
import com.example.util.LocationHelper
import com.example.util.ReminderScheduler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class SankalpaUiState(
    val currentCycleId: Int = 1,
    val days: List<SankalpaDay> = emptyList(),
    val selectedDayNumber: Int = 1,
    val selectedDay: SankalpaDay? = null,
    val completedDaysCount: Int = 0,
    val totalRecitationsCompleted: Int = 0,
    val isCycleCompleted: Boolean = false,
    val userSettings: UserSettings = UserSettings(),
    val isDetectingLocation: Boolean = false,
    val showCelebrationDialog: Boolean = false,
    val showResetConfirmDialog: Boolean = false,
    val showSettingsDialog: Boolean = false,
    val showPrayerSheet: Boolean = false,
    val messageBanner: String? = null
)

class SankalpaViewModel(
    private val repository: SankalpaRepository
) : ViewModel() {

    private val _selectedDayNumber = MutableStateFlow(1)
    private val _showCelebrationDialog = MutableStateFlow(false)
    private val _showResetConfirmDialog = MutableStateFlow(false)
    private val _showSettingsDialog = MutableStateFlow(false)
    private val _showPrayerSheet = MutableStateFlow(false)
    private val _messageBanner = MutableStateFlow<String?>(null)
    private val _isDetectingLocation = MutableStateFlow(false)

    val currentCycleId = repository.currentCycleId
    private val _days = MutableStateFlow<List<SankalpaDay>>(emptyList())

    val uiState: StateFlow<SankalpaUiState> = combine(
        currentCycleId,
        _days,
        _selectedDayNumber,
        repository.settings,
        _showCelebrationDialog,
        _showResetConfirmDialog,
        _showSettingsDialog,
        _showPrayerSheet,
        _messageBanner,
        _isDetectingLocation
    ) { params ->
        val cycle = params[0] as Int
        @Suppress("UNCHECKED_CAST")
        val daysList = params[1] as List<SankalpaDay>
        val selectedNum = params[2] as Int
        val settings = params[3] as UserSettings
        val showCeleb = params[4] as Boolean
        val showReset = params[5] as Boolean
        val showSettings = params[6] as Boolean
        val showPrayer = params[7] as Boolean
        val msgBanner = params[8] as String?
        val detectingLoc = params[9] as Boolean

        val selectedDay = daysList.find { it.dayNumber == selectedNum }
            ?: daysList.firstOrNull()

        val completedDays = daysList.count { it.isCompleted }
        val totalRecitations = daysList.sumOf { it.completedCount }
        val allComplete = daysList.isNotEmpty() && daysList.all { it.isCompleted }

        SankalpaUiState(
            currentCycleId = cycle,
            days = daysList,
            selectedDayNumber = selectedDay?.dayNumber ?: selectedNum,
            selectedDay = selectedDay,
            completedDaysCount = completedDays,
            totalRecitationsCompleted = totalRecitations,
            isCycleCompleted = allComplete,
            userSettings = settings,
            isDetectingLocation = detectingLoc,
            showCelebrationDialog = showCeleb,
            showResetConfirmDialog = showReset,
            showSettingsDialog = showSettings,
            showPrayerSheet = showPrayer,
            messageBanner = msgBanner
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = SankalpaUiState()
    )

    init {
        viewModelScope.launch {
            repository.ensureCycleInitialized()
            // Observe days for the current cycle
            repository.currentCycleId.collect { cycleId ->
                repository.getDays(cycleId).collect { daysList ->
                    _days.value = daysList
                    // Auto select the first incomplete day if current selected is already completed or at start
                    if (daysList.isNotEmpty()) {
                        val firstIncomplete = daysList.find { !it.isCompleted }
                        if (firstIncomplete != null && _selectedDayNumber.value == 1 && daysList.firstOrNull()?.isCompleted == true) {
                            _selectedDayNumber.value = firstIncomplete.dayNumber
                        }
                    }
                }
            }
        }
    }

    fun incrementChant(context: Context) {
        val current = uiState.value.selectedDay ?: return
        if (current.completedCount >= current.targetCount) return

        val willComplete = current.completedCount + 1 >= current.targetCount
        val settings = uiState.value.userSettings

        viewModelScope.launch {
            repository.incrementCount(current)
            if (willComplete) {
                ChimeHelper.playCompletionChime(settings.soundChimeEnabled)
                ChimeHelper.vibrateCompletion(context, settings.hapticEnabled)
                _showCelebrationDialog.value = true
            } else {
                ChimeHelper.playChantTone(settings.soundChimeEnabled)
                ChimeHelper.vibrateBead(context, settings.hapticEnabled)
            }
        }
    }

    fun decrementChant(context: Context) {
        val current = uiState.value.selectedDay ?: return
        if (current.completedCount <= 0) return

        val settings = uiState.value.userSettings
        viewModelScope.launch {
            repository.decrementCount(current)
            ChimeHelper.vibrateBead(context, settings.hapticEnabled)
        }
    }

    fun resetTodayCount() {
        val current = uiState.value.selectedDay ?: return
        viewModelScope.launch {
            repository.resetDayCount(current)
            _showResetConfirmDialog.value = false
            _messageBanner.value = "Day ${current.dayNumber} count reset to 0."
        }
    }

    fun selectDay(dayNumber: Int) {
        _selectedDayNumber.value = dayNumber
    }

    fun startNewSankalpa() {
        viewModelScope.launch {
            repository.startNewCycle()
            _selectedDayNumber.value = 1
            _showCelebrationDialog.value = false
            _messageBanner.value = "New 11-Day Hanuman Sankalpam cycle started!"
        }
    }

    fun detectAndApplyLocation(context: Context) {
        viewModelScope.launch {
            _isDetectingLocation.value = true
            val locInfo = LocationHelper.getCurrentLocation(context)
            _isDetectingLocation.value = false

            if (locInfo != null) {
                repository.updateLocation(locInfo.latitude, locInfo.longitude, locInfo.cityName)
                val settings = repository.settings.value

                // If alarms are enabled, reschedule with the new astronomical times
                if (settings.reminderEnabled) {
                    ReminderScheduler.scheduleDailyReminder(
                        context,
                        settings.reminderHour,
                        settings.reminderMinute
                    )
                }
                if (settings.sleepReminderEnabled) {
                    ReminderScheduler.scheduleSleepReminder(
                        context,
                        settings.sleepReminderHour,
                        settings.sleepReminderMinute
                    )
                }

                _messageBanner.value = "Updated Brahma Muhurtam (${AstronomicalCalculator.formatTime(settings.reminderHour, settings.reminderMinute)}) & Bedtime (${AstronomicalCalculator.formatTime(settings.sleepReminderHour, settings.sleepReminderMinute)}) for ${locInfo.cityName}."
            } else {
                _messageBanner.value = "Could not fetch current GPS location. Using saved location."
            }
        }
    }

    fun setCustomLocation(context: Context, lat: Double, lon: Double, name: String) {
        repository.updateLocation(lat, lon, name)
        val settings = repository.settings.value
        if (settings.reminderEnabled) {
            ReminderScheduler.scheduleDailyReminder(context, settings.reminderHour, settings.reminderMinute)
        }
        if (settings.sleepReminderEnabled) {
            ReminderScheduler.scheduleSleepReminder(context, settings.sleepReminderHour, settings.sleepReminderMinute)
        }
        _messageBanner.value = "Location set to $name."
    }

    fun updateMorningReminder(context: Context, hour: Int, minute: Int, enabled: Boolean) {
        val current = uiState.value.userSettings
        val (bedH, bedM) = AstronomicalCalculator.calculateBedtime(hour, minute, current.targetSleepHours)

        val updated = current.copy(
            reminderHour = hour,
            reminderMinute = minute,
            reminderEnabled = enabled,
            sleepReminderHour = bedH,
            sleepReminderMinute = bedM
        )
        repository.updateSettings(updated)

        if (enabled) {
            ReminderScheduler.scheduleDailyReminder(context, hour, minute)
            _messageBanner.value = "Brahma Muhurtam reminder set for ${AstronomicalCalculator.formatTime(hour, minute)}."
        } else {
            ReminderScheduler.cancelReminder(context)
            _messageBanner.value = "Brahma Muhurtam reminder paused."
        }

        // Reschedule bedtime reminder to match new wake-up time if enabled
        if (current.sleepReminderEnabled) {
            ReminderScheduler.scheduleSleepReminder(context, bedH, bedM)
        }
    }

    fun updateSleepReminder(context: Context, enabled: Boolean, hour: Int? = null, minute: Int? = null) {
        val current = uiState.value.userSettings
        val h = hour ?: current.sleepReminderHour
        val m = minute ?: current.sleepReminderMinute

        val updated = current.copy(
            sleepReminderEnabled = enabled,
            sleepReminderHour = h,
            sleepReminderMinute = m
        )
        repository.updateSettings(updated)

        if (enabled) {
            ReminderScheduler.scheduleSleepReminder(context, h, m)
            _messageBanner.value = "7-Hour sleep reminder set for ${AstronomicalCalculator.formatTime(h, m)}."
        } else {
            ReminderScheduler.cancelSleepReminder(context)
            _messageBanner.value = "Sleep reminder disabled."
        }
    }

    fun toggleSound() {
        val current = uiState.value.userSettings
        val updated = current.copy(soundChimeEnabled = !current.soundChimeEnabled)
        repository.updateSettings(updated)
    }

    fun toggleHaptic() {
        val current = uiState.value.userSettings
        val updated = current.copy(hapticEnabled = !current.hapticEnabled)
        repository.updateSettings(updated)
    }

    fun setPrayerLanguage(lang: String) {
        val current = uiState.value.userSettings
        val updated = current.copy(selectedLanguage = lang)
        repository.updateSettings(updated)
    }

    fun triggerTestNotification(context: Context, isSleep: Boolean = false) {
        ReminderScheduler.triggerTestNotification(context, isSleep)
        if (isSleep) {
            _messageBanner.value = "Test 7-hour bedtime sleep reminder sent to notifications!"
        } else {
            _messageBanner.value = "Test Brahma Muhurtam dawn reminder sent to notifications!"
        }
    }

    fun clearBanner() {
        _messageBanner.value = null
    }

    fun setCelebrationDialog(show: Boolean) {
        _showCelebrationDialog.value = show
    }

    fun setResetConfirmDialog(show: Boolean) {
        _showResetConfirmDialog.value = show
    }

    fun setSettingsDialog(show: Boolean) {
        _showSettingsDialog.value = show
    }

    fun setPrayerSheet(show: Boolean) {
        _showPrayerSheet.value = show
    }

    companion object {
        fun provideFactory(
            repository: SankalpaRepository
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SankalpaViewModel(repository) as T
            }
        }
    }
}

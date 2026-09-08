package com.example.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.ui.SankalpaUiState
import com.example.ui.SankalpaViewModel
import com.example.ui.components.BrahmaMuhurtamSection
import com.example.ui.components.CelebrationDialog
import com.example.ui.components.ElevenDaysGrid
import com.example.ui.components.JapaCounter
import com.example.ui.components.PrayerDialog
import com.example.ui.components.ReminderTimeDialog
import com.example.ui.components.ResetConfirmDialog
import com.example.ui.components.SacredHeader

@Composable
fun SankalpaScreen(
    viewModel: SankalpaViewModel,
    uiState: SankalpaUiState,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    var showTimePicker by remember { mutableStateOf(false) }

    // Runtime Permission for Notifications (Android 13+)
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.updateMorningReminder(
                context,
                uiState.userSettings.reminderHour,
                uiState.userSettings.reminderMinute,
                true
            )
        }
    }

    // Runtime Permission for Location
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true
        val coarseGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (fineGranted || coarseGranted) {
            viewModel.detectAndApplyLocation(context)
        }
    }

    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    LaunchedEffect(uiState.messageBanner) {
        uiState.messageBanner?.let { message ->
            snackbarHostState.showSnackbar(message, duration = SnackbarDuration.Short)
            viewModel.clearBanner()
        }
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .testTag("sankalpa_main_scaffold"),
        contentWindowInsets = WindowInsets.safeDrawing,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .widthIn(max = 640.dp)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header section
                SacredHeader(
                    currentDayNumber = uiState.selectedDayNumber,
                    completedDaysCount = uiState.completedDaysCount,
                    totalRecitations = uiState.totalRecitationsCompleted,
                    isAllCompleted = uiState.isCycleCompleted,
                    settings = uiState.userSettings,
                    onOpenPrayer = { viewModel.setPrayerSheet(true) },
                    onOpenSettings = { showTimePicker = true }
                )

                // The 11-Chant Japa Counter (Primary user task: Hanuman Chalisa readings)
                uiState.selectedDay?.let { activeDay ->
                    JapaCounter(
                        day = activeDay,
                        soundEnabled = uiState.userSettings.soundChimeEnabled,
                        hapticEnabled = uiState.userSettings.hapticEnabled,
                        onIncrement = { viewModel.incrementChant(context) },
                        onDecrement = { viewModel.decrementChant(context) },
                        onResetRequest = { viewModel.setResetConfirmDialog(true) },
                        onToggleSound = { viewModel.toggleSound() },
                        onToggleHaptic = { viewModel.toggleHaptic() },
                        onOpenPrayer = { viewModel.setPrayerSheet(true) }
                    )
                }

                // 11-Day Journey Pathway
                if (uiState.days.isNotEmpty()) {
                    ElevenDaysGrid(
                        days = uiState.days,
                        selectedDayNumber = uiState.selectedDayNumber,
                        onSelectDay = { dayNum -> viewModel.selectDay(dayNum) }
                    )
                }

                // Brahma Muhurtam & 7-Hour Sleep Reminder Settings
                BrahmaMuhurtamSection(
                    settings = uiState.userSettings,
                    isDetectingLocation = uiState.isDetectingLocation,
                    onDetectLocation = {
                        val hasFine = ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                        val hasCoarse = ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED

                        if (hasFine || hasCoarse) {
                            viewModel.detectAndApplyLocation(context)
                        } else {
                            locationPermissionLauncher.launch(
                                arrayOf(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                )
                            )
                        }
                    },
                    onUpdateMorningReminder = { hour, min, enabled ->
                        if (enabled && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
                        ) {
                            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                        viewModel.updateMorningReminder(context, hour, min, enabled)
                    },
                    onUpdateSleepReminder = { enabled ->
                        if (enabled && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
                        ) {
                            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                        viewModel.updateSleepReminder(context, enabled)
                    },
                    onTestNotification = { isSleep ->
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
                        ) {
                            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        } else {
                            viewModel.triggerTestNotification(context, isSleep)
                        }
                    },
                    onOpenCustomMorningTime = { showTimePicker = true }
                )

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Dialogs
        if (uiState.showPrayerSheet) {
            PrayerDialog(
                currentLanguage = uiState.userSettings.selectedLanguage,
                onLanguageSelected = { lang -> viewModel.setPrayerLanguage(lang) },
                onDismiss = { viewModel.setPrayerSheet(false) }
            )
        }

        if (uiState.showCelebrationDialog) {
            CelebrationDialog(
                dayNumber = uiState.selectedDayNumber,
                isAllCompleted = uiState.isCycleCompleted,
                onDismiss = { viewModel.setCelebrationDialog(false) },
                onStartNewCycle = { viewModel.startNewSankalpa() }
            )
        }

        if (showTimePicker) {
            ReminderTimeDialog(
                initialHour = uiState.userSettings.reminderHour,
                initialMinute = uiState.userSettings.reminderMinute,
                onSave = { hour, minute ->
                    viewModel.updateMorningReminder(context, hour, minute, true)
                },
                onDismiss = { showTimePicker = false }
            )
        }

        if (uiState.showResetConfirmDialog) {
            ResetConfirmDialog(
                dayNumber = uiState.selectedDayNumber,
                onConfirm = { viewModel.resetTodayCount() },
                onDismiss = { viewModel.setResetConfirmDialog(false) }
            )
        }
    }
}

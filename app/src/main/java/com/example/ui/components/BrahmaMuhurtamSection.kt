package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.WbTwilight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.repository.UserSettings
import com.example.ui.theme.DiyaFlame
import com.example.util.AstronomicalCalculator

@Composable
fun BrahmaMuhurtamSection(
    settings: UserSettings,
    isDetectingLocation: Boolean,
    onDetectLocation: () -> Unit,
    onUpdateMorningReminder: (hour: Int, minute: Int, enabled: Boolean) -> Unit,
    onUpdateSleepReminder: (enabled: Boolean) -> Unit,
    onTestNotification: (isSleep: Boolean) -> Unit,
    onOpenCustomMorningTime: () -> Unit,
    modifier: Modifier = Modifier
) {
    val morningFormatted = AstronomicalCalculator.formatTime(settings.reminderHour, settings.reminderMinute)
    val bedtimeFormatted = AstronomicalCalculator.formatTime(settings.sleepReminderHour, settings.sleepReminderMinute)
    val sunriseFormatted = AstronomicalCalculator.formatTime(settings.calculatedSunriseHour, settings.calculatedSunriseMinute)

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("brahma_muhurtam_card"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Section 1: Location & Astronomical Sunrise
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
                        modifier = Modifier.size(34.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Location",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                    Column {
                        Text(
                            text = settings.locationName,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Sunrise: $sunriseFormatted • Brahma: $morningFormatted",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                OutlinedButton(
                    onClick = onDetectLocation,
                    enabled = !isDetectingLocation,
                    modifier = Modifier.testTag("detect_location_button"),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    if (isDetectingLocation) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(14.dp),
                            strokeWidth = 2.dp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.MyLocation,
                            contentDescription = "Detect GPS",
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Auto GPS", fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

            // Section 2: Morning Brahma Muhurtam Reminder (04:30 AM)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.WbTwilight,
                        contentDescription = "Dawn Icon",
                        tint = DiyaFlame,
                        modifier = Modifier.size(24.dp)
                    )
                    Column {
                        Text(
                            text = "Brahma Muhurtam Wake-Up",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = if (settings.reminderEnabled)
                                "Alert at $morningFormatted (1h 36m before sunrise)"
                            else "Morning reminder paused",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Switch(
                    checked = settings.reminderEnabled,
                    onCheckedChange = { isChecked ->
                        onUpdateMorningReminder(settings.reminderHour, settings.reminderMinute, isChecked)
                    },
                    modifier = Modifier.testTag("reminder_switch"),
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.primary,
                        checkedTrackColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
            }

            // Quick Presets
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                val presets = listOf(
                    Pair(settings.calculatedBrahmaStartHour, settings.calculatedBrahmaStartMinute) to "GPS Calc",
                    Pair(4, 0) to "04:00 AM",
                    Pair(4, 30) to "04:30 AM",
                    Pair(5, 0) to "05:00 AM"
                )

                presets.forEach { (time, label) ->
                    val isSelected = settings.reminderHour == time.first && settings.reminderMinute == time.second
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            onUpdateMorningReminder(time.first, time.second, true)
                        },
                        label = {
                            Text(
                                text = label,
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))

            // Section 3: Night 7-Hour Sleep Reminder (e.g. 09:30 PM)
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.35f)
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bedtime,
                                contentDescription = "Bedtime",
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(22.dp)
                            )
                            Column {
                                Text(
                                    text = "7-Hour Sleep Goal Reminder",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                                Text(
                                    text = if (settings.sleepReminderEnabled)
                                        "Bedtime alert at $bedtimeFormatted"
                                    else "Sleep reminder off",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Switch(
                            checked = settings.sleepReminderEnabled,
                            onCheckedChange = onUpdateSleepReminder,
                            modifier = Modifier.testTag("sleep_reminder_switch"),
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = MaterialTheme.colorScheme.secondary,
                                checkedTrackColor = MaterialTheme.colorScheme.secondaryContainer
                            )
                        )
                    }

                    Text(
                        text = "Sleeping at $bedtimeFormatted ensures you complete a full 7 hours of rest and wake up alert for your 11 Hanuman Chalisa recitations at $morningFormatted.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 18.sp
                    )
                }
            }

            // Section 4: Action Buttons (Custom Time & Test Alerts)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onOpenCustomMorningTime,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("custom_time_button"),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Alarm,
                        contentDescription = null,
                        modifier = Modifier.size(15.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Custom Dawn", fontSize = 11.sp)
                }

                OutlinedButton(
                    onClick = { onTestNotification(false) },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("test_alert_button"),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.NotificationsActive,
                        contentDescription = null,
                        modifier = Modifier.size(15.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Test Dawn",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                OutlinedButton(
                    onClick = { onTestNotification(true) },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("test_sleep_button"),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Bedtime,
                        contentDescription = null,
                        modifier = Modifier.size(15.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Test Sleep",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}

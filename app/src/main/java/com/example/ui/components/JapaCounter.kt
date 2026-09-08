package com.example.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.NotificationsOff
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material.icons.filled.VolumeMute
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SankalpaDay
import com.example.ui.theme.DiyaFlame
import com.example.ui.theme.DivineGold
import com.example.ui.theme.SacredBeadActive
import com.example.ui.theme.SacredBeadWood

@Composable
fun JapaCounter(
    day: SankalpaDay,
    soundEnabled: Boolean,
    hapticEnabled: Boolean,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onResetRequest: () -> Unit,
    onToggleSound: () -> Unit,
    onToggleHaptic: () -> Unit,
    onOpenPrayer: () -> Unit,
    modifier: Modifier = Modifier
) {
    val progress by animateFloatAsState(
        targetValue = (day.completedCount / 11f).coerceIn(0f, 1f),
        animationSpec = spring(),
        label = "progress"
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .testTag("japa_counter_card"),
        shape = RoundedCornerShape(28.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Day and status title
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Day ${day.dayNumber} Recitation",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = day.dateString.ifEmpty { "Daily 11 Times Goal" },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (day.isCompleted) Color(0xFF2E7D32).copy(alpha = 0.12f)
                    else MaterialTheme.colorScheme.primaryContainer
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        if (day.isCompleted) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = "Day Completed",
                                tint = Color(0xFF2E7D32),
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Day Fulfilled",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2E7D32)
                            )
                        } else {
                            Text(
                                text = "${11 - day.completedCount} remaining",
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }

            // 11 Sacred Mala Beads Visualizer
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "11 CHALISA RECITATIONS (MALA BEADS)",
                    style = MaterialTheme.typography.labelSmall,
                    letterSpacing = 1.2.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.SemiBold
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    for (i in 1..11) {
                        val isDone = i <= day.completedCount
                        val isCurrent = i == day.completedCount + 1 && !day.isCompleted
                        val beadColor by animateColorAsState(
                            targetValue = when {
                                isDone -> DiyaFlame
                                isCurrent -> DivineGold
                                else -> MaterialTheme.colorScheme.surfaceVariant
                            },
                            label = "bead_color"
                        )
                        val beadScale by animateFloatAsState(
                            targetValue = if (isCurrent) 1.25f else 1.0f,
                            label = "bead_scale"
                        )

                        Box(
                            modifier = Modifier
                                .scale(beadScale)
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(beadColor)
                                .border(
                                    width = if (isCurrent) 2.dp else 1.dp,
                                    color = if (isDone) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (isDone) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                            } else {
                                Text(
                                    text = "$i",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isCurrent) Color.Black else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }

            // Circular Count Display
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = { 1f },
                    modifier = Modifier.size(180.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    strokeWidth = 14.dp
                )

                CircularProgressIndicator(
                    progress = { progress },
                    modifier = Modifier.size(180.dp),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 14.dp
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "CHALISA READINGS",
                        style = MaterialTheme.typography.labelSmall,
                        letterSpacing = 1.5.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    AnimatedContent(
                        targetState = day.completedCount,
                        transitionSpec = { fadeIn() togetherWith fadeOut() },
                        label = "count_anim"
                    ) { count ->
                        Text(
                            text = "$count",
                            fontSize = 62.sp,
                            fontWeight = FontWeight.Black,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Text(
                        text = "of 11 times",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Primary Big Increment Button: "Chanted 1 Time (+1)"
            Button(
                onClick = onIncrement,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .testTag("chant_button"),
                enabled = day.completedCount < day.targetCount,
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "🪔",
                        fontSize = 24.sp
                    )
                    Text(
                        text = if (day.completedCount >= day.targetCount)
                            "11 Recitations Complete!"
                        else
                            "Read 1 Time  (+1)",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (day.completedCount >= day.targetCount)
                            MaterialTheme.colorScheme.onSurfaceVariant
                        else
                            MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            // Controls & Prayer row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Read Chalisa & Mantra button
                OutlinedButton(
                    onClick = onOpenPrayer,
                    modifier = Modifier.testTag("counter_read_prayer_button"),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Read Chalisa",
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                // Quick undo and reset actions
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedIconButton(
                        onClick = onDecrement,
                        enabled = day.completedCount > 0,
                        modifier = Modifier.testTag("undo_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Undo,
                            contentDescription = "Undo 1 chant (-1)"
                        )
                    }

                    OutlinedIconButton(
                        onClick = onResetRequest,
                        enabled = day.completedCount > 0,
                        modifier = Modifier.testTag("reset_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Reset Today Count"
                        )
                    }

                    FilledTonalIconButton(
                        onClick = onToggleSound,
                        modifier = Modifier.testTag("sound_toggle_button")
                    ) {
                        Icon(
                            imageVector = if (soundEnabled) Icons.Default.VolumeUp else Icons.Default.VolumeMute,
                            contentDescription = if (soundEnabled) "Mute Chime" else "Enable Chime",
                            tint = if (soundEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    FilledTonalIconButton(
                        onClick = onToggleHaptic,
                        modifier = Modifier.testTag("haptic_toggle_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Vibration,
                            contentDescription = if (hapticEnabled) "Disable Haptic" else "Enable Haptic",
                            tint = if (hapticEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

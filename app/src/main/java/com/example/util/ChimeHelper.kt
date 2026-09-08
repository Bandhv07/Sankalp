package com.example.util

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log

object ChimeHelper {
    private const val TAG = "ChimeHelper"
    private var toneGenerator: ToneGenerator? = null

    init {
        try {
            toneGenerator = ToneGenerator(AudioManager.STREAM_NOTIFICATION, 75)
        } catch (e: Exception) {
            Log.w(TAG, "Could not initialize ToneGenerator", e)
        }
    }

    fun playChantTone(isSoundEnabled: Boolean) {
        if (!isSoundEnabled) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP, 100)
        } catch (e: Exception) {
            Log.w(TAG, "Error playing tone", e)
        }
    }

    fun playCompletionChime(isSoundEnabled: Boolean) {
        if (!isSoundEnabled) return
        try {
            toneGenerator?.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 400)
        } catch (e: Exception) {
            Log.w(TAG, "Error playing completion chime", e)
        }
    }

    fun vibrateBead(context: Context, isHapticEnabled: Boolean) {
        if (!isHapticEnabled) return
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager =
                    context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vibratorManager?.defaultVibrator?.vibrate(
                    VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK)
                )
            } else {
                @Suppress("DEPRECATION")
                val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator?.vibrate(
                        VibrationEffect.createOneShot(35, VibrationEffect.DEFAULT_AMPLITUDE)
                    )
                } else {
                    @Suppress("DEPRECATION")
                    vibrator?.vibrate(35)
                }
            }
        } catch (e: Exception) {
            Log.w(TAG, "Vibration failed", e)
        }
    }

    fun vibrateCompletion(context: Context, isHapticEnabled: Boolean) {
        if (!isHapticEnabled) return
        try {
            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager =
                    context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vibratorManager?.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val timings = longArrayOf(0, 150, 100, 250)
                val amplitudes = intArrayOf(0, 180, 0, 255)
                vibrator?.vibrate(VibrationEffect.createWaveform(timings, amplitudes, -1))
            } else {
                @Suppress("DEPRECATION")
                vibrator?.vibrate(400)
            }
        } catch (e: Exception) {
            Log.w(TAG, "Completion vibration failed", e)
        }
    }
}

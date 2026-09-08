package com.example.util

import java.util.Calendar
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * Astronomical calculation of sunrise and Brahma Muhurtam based on geographic coordinates (Latitude, Longitude).
 *
 * Brahma Muhurtam is defined in Vedic tradition as the period starting 2 Muhurtas (96 minutes / 1h 36m)
 * before sunrise and ending 1 Muhurta (48 minutes) before sunrise (or extending until sunrise).
 */
object AstronomicalCalculator {

    data class SunTimes(
        val sunriseHour: Int,
        val sunriseMinute: Int,
        val brahmaMuhurtaStartHour: Int,
        val brahmaMuhurtaStartMinute: Int,
        val brahmaMuhurtaEndHour: Int,
        val brahmaMuhurtaEndMinute: Int
    )

    /**
     * Calculates sunrise and Brahma Muhurtam window for a given location and calendar date.
     * Uses the standard NOAA Solar Calculation algorithm.
     */
    fun calculateBrahmaMuhurtam(
        latitude: Double,
        longitude: Double,
        calendar: Calendar = Calendar.getInstance()
    ): SunTimes {
        val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
        val timeZoneOffsetHours = (calendar.timeZone.rawOffset + calendar.timeZone.dstSavings) / 3600000.0

        // Fractional year in radians (gamma)
        val gamma = (2 * Math.PI / 365.0) * (dayOfYear - 1)

        // Equation of time (in minutes)
        val eqTime = 229.18 * (
            0.000075 +
                0.001868 * cos(gamma) -
                0.032077 * sin(gamma) -
                0.014615 * cos(2 * gamma) -
                0.040849 * sin(2 * gamma)
            )

        // Solar declination angle (in radians)
        val decl = 0.006918 -
            0.399912 * cos(gamma) +
            0.070257 * sin(gamma) -
            0.006758 * cos(2 * gamma) +
            0.000907 * sin(2 * gamma) -
            0.002697 * cos(3 * gamma) +
            0.00148 * sin(3 * gamma)

        val latRad = Math.toRadians(latitude)
        val zenith = Math.toRadians(90.833) // Official sunrise/sunset zenith accounting for refraction

        // Hour angle (ha)
        val cosHa = (cos(zenith) / (cos(latRad) * cos(decl))) - (Math.tan(latRad) * Math.tan(decl))

        val haDeg = when {
            cosHa > 1.0 -> 0.0 // Polar night (no sunrise) -> fallback
            cosHa < -1.0 -> 180.0 // Midnight sun (no sunset) -> fallback
            else -> Math.toDegrees(Math.acos(cosHa))
        }

        // Sunrise in minutes from midnight UTC
        val sunriseUtcMinutes = 720 - (4 * (longitude + haDeg)) - eqTime
        // Local sunrise minutes
        var localSunriseMinutes = (sunriseUtcMinutes + (timeZoneOffsetHours * 60)).toInt()

        // Normalize within 0..1439
        while (localSunriseMinutes < 0) localSunriseMinutes += 1440
        while (localSunriseMinutes >= 1440) localSunriseMinutes -= 1440

        // In Vedic astronomy:
        // 1 Muhurta = 48 minutes
        // Brahma Muhurtam starts 2 Muhurtas (96 min) before sunrise and ends 1 Muhurta (48 min) before sunrise.
        var bmStartMinutes = localSunriseMinutes - 96
        while (bmStartMinutes < 0) bmStartMinutes += 1440

        var bmEndMinutes = localSunriseMinutes - 48
        while (bmEndMinutes < 0) bmEndMinutes += 1440

        val sunriseH = (localSunriseMinutes / 60) % 24
        val sunriseM = localSunriseMinutes % 60

        val bmStartH = (bmStartMinutes / 60) % 24
        val bmStartM = bmStartMinutes % 60

        val bmEndH = (bmEndMinutes / 60) % 24
        val bmEndM = bmEndMinutes % 60

        return SunTimes(
            sunriseHour = sunriseH,
            sunriseMinute = sunriseM,
            brahmaMuhurtaStartHour = bmStartH,
            brahmaMuhurtaStartMinute = bmStartM,
            brahmaMuhurtaEndHour = bmEndH,
            brahmaMuhurtaEndMinute = bmEndM
        )
    }

    /**
     * Given the desired wake-up time (e.g. Brahma Muhurtam start or user wake time)
     * and target sleep duration in hours (e.g. 7.0), calculates the bedtime hour & minute.
     */
    fun calculateBedtime(wakeHour: Int, wakeMinute: Int, sleepDurationHours: Double = 7.0): Pair<Int, Int> {
        val wakeTotalMinutes = wakeHour * 60 + wakeMinute
        val sleepMinutes = (sleepDurationHours * 60).toInt()
        var bedtimeMinutes = wakeTotalMinutes - sleepMinutes
        while (bedtimeMinutes < 0) {
            bedtimeMinutes += 1440
        }
        val bedHour = (bedtimeMinutes / 60) % 24
        val bedMinute = bedtimeMinutes % 60
        return Pair(bedHour, bedMinute)
    }

    /**
     * Calculates the time left until bedtime or wake-up in a readable format.
     */
    fun formatTime(hour: Int, minute: Int): String {
        val period = if (hour < 12) "AM" else "PM"
        val h12 = when (hour % 12) {
            0 -> 12
            else -> hour % 12
        }
        return String.format("%02d:%02d %s", h12, minute, period)
    }

    fun format24(hour: Int, minute: Int): String {
        return String.format("%02d:%02d", hour, minute)
    }
}

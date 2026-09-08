package com.example

import com.example.util.AstronomicalCalculator
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.Calendar
import java.util.TimeZone

class AstronomicalCalculatorTest {

    @Test
    fun testBedtimeCalculationFor7Hours() {
        // Wake-up at 04:30 AM -> Bedtime should be 09:30 PM (21:30)
        val (bedHour, bedMin) = AstronomicalCalculator.calculateBedtime(4, 30, 7.0)
        assertEquals(21, bedHour)
        assertEquals(30, bedMin)

        // Wake-up at 05:00 AM -> Bedtime should be 10:00 PM (22:00)
        val (bedHour2, bedMin2) = AstronomicalCalculator.calculateBedtime(5, 0, 7.0)
        assertEquals(22, bedHour2)
        assertEquals(0, bedMin2)

        // Wake-up at 04:00 AM -> Bedtime should be 09:00 PM (21:00)
        val (bedHour3, bedMin3) = AstronomicalCalculator.calculateBedtime(4, 0, 7.0)
        assertEquals(21, bedHour3)
        assertEquals(0, bedMin3)
    }

    @Test
    fun testBrahmaMuhurtamWindowCalculation() {
        // Test location: Hyderabad (17.3850 N, 78.4867 E)
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Kolkata"))
        val sunTimes = AstronomicalCalculator.calculateBrahmaMuhurtam(17.3850, 78.4867, calendar)

        // Sunrise is typically between 5:30 AM and 6:30 AM
        assertTrue("Sunrise hour should be plausible", sunTimes.sunriseHour in 5..7)

        // Brahma Muhurtam starts 96 minutes before sunrise, so roughly 1h 36m earlier (~4:00 to 5:00 AM)
        assertTrue("Brahma Muhurtam start hour should be plausible", sunTimes.brahmaMuhurtaStartHour in 3..5)
    }
}

package com.anadolstudio.chronos

import com.anadolstudio.chronos.util.totalDays
import com.anadolstudio.utils.util.data_time.Time
import com.anadolstudio.utils.util.data_time.remainingMinutes
import com.anadolstudio.utils.util.data_time.remainingSeconds
import com.anadolstudio.utils.util.data_time.toHours
import com.anadolstudio.utils.util.data_time.toMinutes
import com.anadolstudio.utils.util.data_time.toSeconds
import org.joda.time.DateTime
import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.concurrent.TimeUnit

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TimeTest {

    @Test
    fun convert_time_isCorrect() {
        var time = Time(TimeUnit.MINUTES.toMillis(16620.toLong()))
        assertEquals(277, time.hours)
        time = Time(TimeUnit.MINUTES.toMillis(23760.toLong()))
        assertEquals(396, time.hours)
        time = Time(TimeUnit.MINUTES.toMillis(16620 + 23760.toLong()))
        assertEquals(673, time.hours)

        var millis = 997200000L + 1426101000

        assertEquals(673, millis.toHours)
        assertEquals(40_388, millis.toMinutes)
        assertEquals(2_423_301, millis.toSeconds)

        time = Time(
                hours = 20,
                minutes = 15,
                seconds = 20
        )

        assertEquals(1215, time.totalMinutes)

        millis = TimeUnit.MINUTES.toMillis(time.totalMinutes.toLong())
        assertEquals(15, millis.remainingMinutes)
        assertEquals(0, millis.remainingSeconds)

        millis = TimeUnit.SECONDS.toMillis(time.totalSeconds.toLong())
        assertEquals(72920, time.totalSeconds)
        assertEquals(20, millis.remainingSeconds)

    }

    @Test
    fun test_count_total_days_from_date_success() {
        var dateTime = DateTime.parse("2024-07-15T03:00:00.000")
        var days = dateTime.totalDays
        assertEquals(19_919L, days)

        dateTime = DateTime.parse("2024-07-15T00:00:00.000+03:00") // 2024-07-14
        days = dateTime.totalDays

        assertEquals(19_918L, days)

        dateTime = DateTime.parse("2024-07-15T03:00:00.000-14:00") // 2024-07-15
        days = dateTime.totalDays

        assertEquals(19_919L, days)

        dateTime = DateTime.parse("2024-07-15T03:00:00.000+03:00") // 2024-07-15
        days = dateTime.totalDays

        assertEquals(19_919L, days)

        dateTime = DateTime.parse("2024-07-15T04:00:00.000+04:00") // 2024-07-15
        days = dateTime.totalDays

        assertEquals(19_919L, days)

        dateTime = DateTime.parse("2024-07-15T02:00:00.000+02:00") // 2024-07-15
        days = dateTime.totalDays

        assertEquals(19_919L, days)
    }
}

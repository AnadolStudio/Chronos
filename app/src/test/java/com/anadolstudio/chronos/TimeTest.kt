package com.anadolstudio.chronos

import com.anadolstudio.utils.util.data_time.Time
import com.anadolstudio.utils.util.data_time.remainingMinutes
import com.anadolstudio.utils.util.data_time.remainingSeconds
import com.anadolstudio.utils.util.data_time.toHours
import com.anadolstudio.utils.util.data_time.toMinutes
import com.anadolstudio.utils.util.data_time.toSeconds
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
}

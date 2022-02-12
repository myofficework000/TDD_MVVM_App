package com.sampleNote.work.util

import junit.framework.Assert.assertEquals
import org.junit.Test
import org.threeten.bp.LocalDateTime

class HelperTest {
    @Test
    fun testFormatDate(){
        //Sample date taken
        val dateTime = LocalDateTime.parse("2018-12-11T16:01:30")
        // expected data format by our helper method
        val formatted = Helper.formatDate(dateTime, "d MMMM, yyyy hh:mm a")
        //verifying that our expected format of date is same as the actual input item
        assertEquals("11 December, 2018 04:01 PM", formatted)
    }
}
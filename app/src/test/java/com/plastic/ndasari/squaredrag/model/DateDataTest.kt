package com.plastic.ndasari.squaredrag.model

import com.plastic.ndasari.squaredrag.exceptions.TimeDataException
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

/**
 * Created by ndasari on 20 Oct 2018
 */
class DateDataTest {

    private lateinit var dateData :DateData

    @Test
    fun test_secondsData() {
        dateData = DateData("2018-10-20 17:25:55.180004")
        assertEquals("25:55:18", dateData.secondsData())
    }

    @Test
    fun test_getDateTime() {
        dateData = DateData("2018-10-20 17:25:55.180004")
        assertEquals("2018-10-20 17:25:55.180004", dateData.dateTime)
    }

    @Test(expected = TimeDataException::class)
    fun test_unExpectedData() {
        dateData = DateData("")
        dateData.secondsData()
    }

    @Test(expected = TimeDataException::class)
    fun test_nullData() {
        dateData = DateData(null)
        dateData.secondsData()
    }
}
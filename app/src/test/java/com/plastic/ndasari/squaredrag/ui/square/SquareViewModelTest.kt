package com.plastic.ndasari.squaredrag.ui.square

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.plastic.ndasari.squaredrag.model.DateData
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.junit.MockitoJUnit

/**
 * Created by ndasari on 20 Oct 2018
 */
class SquareViewModelTest {

    @get:Rule
    val mockitoRule = MockitoJUnit.rule()!!

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @InjectMocks
    lateinit var classUnderTest: SquareViewModel

    private lateinit var data: DateData
    private var mockedData: DateData? = null

    @Before
    fun setup() {
        data = DateData("2018-10-20 17:25:55.180004")
        val liveDataUnderTest = classUnderTest.data
        liveDataUnderTest.value = data
        mockedData = classUnderTest.getDateData().value
    }

    @Test
    fun test_getDateData() {
        assertEquals(mockedData, data)
    }

    @Test
    fun test_getFormattedDateData() {
        assertEquals(mockedData?.secondsData(), "25:55:18")
    }
}
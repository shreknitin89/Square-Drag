package com.plastic.ndasari.squaredrag.customview

import com.plastic.ndasari.squaredrag.SquareActivity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.android.controller.ActivityController


/**
 * Created by ndasari on 20 Oct 2018
 */
@RunWith(RobolectricTestRunner::class)
class SquareViewTest {

    private lateinit var activityController: ActivityController<SquareActivity>

    private lateinit var view: SquareView
    @Before
    fun setup() {
        activityController = Robolectric.buildActivity(SquareActivity::class.java)
        view = SquareView(activityController.get())
    }

    @Test
    fun test_absoluteSize() {
        var size = view.getAbsoluteSize(15, 25)
        assertEquals(size, 25)

        size = view.getAbsoluteSize(35, 25)
        assertEquals(size, 35)

        size = view.getAbsoluteSize(45, 45)
        assertEquals(size, 45)
    }

    @Test
    fun test_measure() {
        assertEquals(view.measuredHeight, view.measuredWidth)
    }
}
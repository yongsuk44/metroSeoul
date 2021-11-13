package com.young.metro.stationdetail

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.young.metro.R
import com.young.metro.ui.MainActivity
import com.young.presentation.viewmodel.StationEntranceViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class EntranceGuideUiTest {

    @get:Rule
    private val rule = ActivityTestRule(MainActivity::class.java)

    private lateinit var viewModel : StationEntranceViewModel

    @Before
    fun setUp() {

    }

    @Test
    fun guideTest() {
        onView(withId(R.id.tv_station_entrance_title))
            .perform(click())
            .check(matches(isDisplayed()))
    }
}
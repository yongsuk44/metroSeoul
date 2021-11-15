package com.young.metro.home

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.young.metro.R
import com.young.metro.base.BaseViewHolder
import com.young.metro.databinding.ItemStationNameBinding
import com.young.metro.launchFragmentInHiltContainer
import com.young.metro.ui.HomeFragment
import com.young.presentation.model.ListRouteInformation
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@MediumTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class HomeUiTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun HomeToDetailFragmentMoveCheck() {
        launchFragmentInHiltContainer<HomeFragment>()

        Thread.sleep(1000L)

        Espresso.onView(ViewMatchers.withId(R.id.et_station_search))
            .perform(ViewActions.replaceText("서울역"))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Thread.sleep(1000L)

        Espresso.onView(ViewMatchers.withId(R.id.rv_popup_list))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<BaseViewHolder<ItemStationNameBinding>>(0,
                    ViewActions.click()
                ))

        Thread.sleep(2000L)
    }

    fun stubListRouteInformationData() = ListRouteInformation(
        "01", listOf("S1", "S1", "AR", "KR"), "1", "1호선",
        listOf("1", "4", "A1", "K4"),
        listOf("133", "426", "A01", "P313"),
        "34", "서울역"
    )
}
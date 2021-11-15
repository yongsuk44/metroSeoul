package com.young.metro.stationdetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.young.domain.usecase.AllStationCodeUseCase
import com.young.domain.usecase.FullRouteInformationUseCase
import com.young.metro.R
import com.young.metro.base.BaseViewHolder
import com.young.metro.databinding.BaseSpinnerListBinding
import com.young.metro.launchFragmentInHiltContainer
import com.young.metro.ui.HomeFragment
import com.young.presentation.model.ListRouteInformation
import com.young.presentation.modelfunction.FullRouteInformationCase
import com.young.presentation.viewmodel.FullRouteInformationViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class EntranceGuideUiTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    lateinit var fullRouteInformationViewModel: FullRouteInformationViewModel
    @Inject lateinit var fullRouteInformationUseCase: FullRouteInformationUseCase
    @Inject lateinit var allStationCodeUseCase: AllStationCodeUseCase

    @Before
    fun setUp() {
        hiltRule.inject()
        fullRouteInformationViewModel = FullRouteInformationViewModel(fullRouteInformationUseCase, allStationCodeUseCase)
    }

    @Test
    fun guideTest() {
        launchFragmentInHiltContainer<HomeFragment>()

        onView(withId(R.id.et_station_search))
            .perform(click())

        onView(withId(R.id.et_station_search))
            .perform(replaceText("서울역"))
            .check(matches(isDisplayed()))

        onView(withId(R.id.rv_popup_list))
            .check(matches(isDisplayed()))
            .perform(RecyclerViewActions.actionOnItemAtPosition<BaseViewHolder<BaseSpinnerListBinding>>(0, click()))


    }

//        assertThat(navController.currentDestination?.id , `is`(R.id.stationInformationDetailFragment))
//        onView(withId(R.id.tv_station_entrance_title))
//            .perform(click())
//            .check(matches(isDisplayed()))

    fun stubListRouteInformationData() = ListRouteInformation(
        "01", listOf("S1", "S1", "AR", "KR"), "1", "1호선",
        listOf("1", "4", "A1", "K4"),
        listOf("133", "426", "A01", "P313"),
        "34", "서울역"
    )
}
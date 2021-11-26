package com.young.metro.home

import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.annotation.UiThreadTest
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.young.metro.R
import com.young.metro.base.BaseViewHolder
import com.young.metro.databinding.ItemStationNameBinding
import com.young.metro.launchFragmentInHiltContainer
import com.young.metro.ui.HomeFragment
import com.young.metro.util.*
import com.young.presentation.viewmodel.FullRouteInformationViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.*
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@FlowPreview
@ExperimentalCoroutinesApi
@MediumTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class HomeUiTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    private val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
    lateinit var viewModel: FullRouteInformationViewModel


    @Before
    fun setUp() {
        launchFragmentInHiltContainer<HomeFragment> {
            navController.setGraph(R.navigation.main_graph)
            navController.setCurrentDestination(R.id.fragment_home)

            val com: FullRouteInformationViewModel by viewModels()
            viewModel = com

            Navigation.setViewNavController(requireView(), navController)
        }

        hiltRule.inject()
    }

    @Test
    fun HomeToDetailFragmentMoveCheck() {
        onView(withId(R.id.et_station_search))
            .perform(click())
            .perform(ViewActions.replaceText("서울"))

        Thread.sleep(1000L)

        onView(withId(R.id.rv_popup_list)).isViewVisible()

        onView(withId(R.id.rv_popup_list))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<BaseViewHolder<ItemStationNameBinding>>(
                    1,
                    childViewClick(R.id.lo_item_station_name)
                )
            )

        assertThat(
            navController.currentDestination?.id,
            `is`(R.id.stationInformationDetailFragment)
        )
    }

    @Test
    fun HomeToLocationFragmentMoveCheck() {

        onView(withId(R.id.rv_popup_list)).isViewGone()
        onView(withId(R.id.lo_home_background)).onDisplayCheck()
        onView(withId(R.id.tv_main_menu_location_txt)).onDisplayCheck()
        onView(withId(R.id.tv_main_menu_location_txt)).perFormClick()

        assertThat(navController.currentDestination?.id, `is`(R.id.locationListFragment))
    }
}
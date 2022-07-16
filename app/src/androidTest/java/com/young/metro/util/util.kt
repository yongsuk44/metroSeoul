package com.young.metro.util

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import kotlin.math.max
import kotlin.math.min

fun ViewInteraction.isViewGone() =
    matches(withEffectiveVisibility(Visibility.GONE))

fun ViewInteraction.isViewVisible() =
    matches(withEffectiveVisibility(Visibility.VISIBLE))

fun ViewInteraction.isViewInVisible() =
    matches(withEffectiveVisibility(Visibility.INVISIBLE))

fun ViewInteraction.onDisplayCheck() = matches(isDisplayed())
fun ViewInteraction.perFormClick() = perform(ViewActions.click())

fun childViewClick(id: Int) = object : ViewAction {
    override fun getConstraints(): Matcher<View> =
        allOf(isDisplayed(), isAssignableFrom(View::class.java))

    override fun getDescription(): String = "Ui Test : RecyclerView Child View 클릭"
    override fun perform(uiController: UiController?, view: View?) {
        val v = view?.findViewById(id) as View
        v.performClick()
    }
}

inline fun <reified T> List<T>.listMinOfIndex(): Int {
    val map = HashMap<T,Int>()
    var min = 0

    toHashSet().forEachIndexed { index, t ->
        min = min(t as Int, min)
        map[t] = index
    }

    return map.getOrDefault(min , -1)
}
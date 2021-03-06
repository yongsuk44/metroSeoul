package com.young.metro.util

import android.app.Activity
import android.content.res.Resources
import android.os.Build
import android.util.Patterns
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.young.metro.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalTime
import kotlin.math.abs

fun List<String>?.nowTimeNearList(): Int {
    val min = 1000
    val target = LocalTime.now().toSecondOfDay()

    return this?.find { s -> abs(min) > abs(LocalTime.parse(s).toSecondOfDay() - target) }
        .let { this@nowTimeNearList?.indexOf(it) ?: 0 }
}

fun RecyclerView.recyclerViewScrollPosition(scope: LifecycleCoroutineScope, position: Int) {
    if (position <= 0) return
    else {
        scope.launch(Dispatchers.Main) {
            delay(500)
            scrollToPosition(position)
        }
    }
}

fun Fragment.waitForTransition(view: View) {
    postponeEnterTransition()
    view.doOnPreDraw { startPostponedEnterTransition() }
}

fun View.toTransitionGroup() = this to transitionName

fun Int?.zeroOrNull() = this != null && this != 0
fun Double?.zeroOrNull() = this != null && this != 0.0
fun Int?.intNullCheck() = this != null
fun Double?.nullCheck() = this != null

fun String.equlesDataToInt(value: String): Int = if (this == value) 1 else 0
inline fun <reified T> equalsZeroCheck(value: T): T? = if (value == 0f) null else value

val Int.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.toDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

enum class StatusColor(val color: Int) {
    WHITE(R.color.white),
    BLACK(R.color.black)
}

fun setStatusBarColor(activity: Activity, statusColor: StatusColor) {
    activity.window.statusBarColor = activity.getColor(statusColor.color)

    activity.window.decorView.systemUiVisibility =
        when (statusColor.color) {
            StatusColor.WHITE.color -> View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            else -> 0
        }
}

fun Activity.showSystemUI() {
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
}

fun Activity.hideSystemUI() {
    window.decorView.systemUiVisibility =
            // ???????????? Mode
        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or

                // ???????????? set
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_FULLSCREEN

}

enum class RegexProfile(
    val regex: Regex
) {
    @RequiresApi(Build.VERSION_CODES.FROYO)
    EMAIL(
        Patterns.EMAIL_ADDRESS.toRegex()
    ),
    NAME(
        ("^[???-???|a-z|A-Z]{2,99}$").toRegex()
    ),
    HP(
        ("^[010][0-9]{10}$").toRegex()
    ),
    ONLY_NUMBER(
        ("^[0-9]{1,2}$").toRegex()
    ),
    BIRTH(
        ("^[0-9]{8}$").toRegex()
    ),
    TEXT_NUMBER(
        ("^[0-9A-Za-z???-???]{1,4}$").toRegex()
    )
}
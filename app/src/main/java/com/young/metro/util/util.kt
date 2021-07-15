package com.young.metro.util

import android.app.Activity
import android.content.res.Resources
import android.os.Build
import android.os.Parcelable
import android.util.Patterns
import android.view.View
import androidx.annotation.RequiresApi
import com.young.metro.R


fun Int?.ZeroOrNull() = this != null && this != 0
fun Int?.intNullCheck() = this != null
fun String.equlesDataToInt(value: String): Int = if (this == value) 1 else 0

val Int.toPx: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()

val Int.toDp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

enum class StatusColor(val color : Int) {
    WHITE(R.color.white) ,
    BLACK(R.color.black)
}

fun setStatusBarColor(activity: Activity, statusColor: StatusColor) {
    activity.window.statusBarColor = activity.getColor(statusColor.color)

    activity.window.decorView.systemUiVisibility =
        when(statusColor.color) {
            StatusColor.WHITE.color -> View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            else -> 0
        }
}

fun Activity.showSystemUI() {
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
}
fun Activity.hideSystemUI() {
    window.decorView.systemUiVisibility =
            // 전체화면 Mode
        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or

                // 전체화면 set
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
        ("^[가-힣|a-z|A-Z]{2,99}$").toRegex()
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
        ("^[0-9A-Za-z가-힣]{1,4}$").toRegex()
    )
}
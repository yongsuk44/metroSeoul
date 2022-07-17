package com.young.metro.viewbinding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.young.metro.R
import com.young.metro.util.STATION_LINE

@BindingAdapter("lineColor")
fun getTextLineColor(view: TextView, line: String) {
    view.setTextColor(
        view.resources.getColor(
            when(line) {
                STATION_LINE.LINE_1.lnCd -> STATION_LINE.LINE_1.getColor()
                STATION_LINE.LINE_2.lnCd -> STATION_LINE.LINE_2.getColor()
                STATION_LINE.LINE_3.lnCd -> STATION_LINE.LINE_3.getColor()
                STATION_LINE.LINE_4.lnCd -> STATION_LINE.LINE_4.getColor()
                STATION_LINE.LINE_5.lnCd -> STATION_LINE.LINE_5.getColor()
                STATION_LINE.LINE_6.lnCd -> STATION_LINE.LINE_6.getColor()
                STATION_LINE.LINE_7.lnCd -> STATION_LINE.LINE_7.getColor()
                STATION_LINE.LINE_8.lnCd -> STATION_LINE.LINE_8.getColor()
                STATION_LINE.LINE_9.lnCd -> STATION_LINE.LINE_9.getColor()
                STATION_LINE.LINE_AIRPORT.lnCd -> STATION_LINE.LINE_AIRPORT.getColor()
                STATION_LINE.LINE_BUNDANG.lnCd -> STATION_LINE.LINE_BUNDANG.getColor()
                STATION_LINE.LINE_EVER.lnCd -> STATION_LINE.LINE_EVER.getColor()
                STATION_LINE.LINE_GIMPO.lnCd -> STATION_LINE.LINE_GIMPO.getColor()
                STATION_LINE.LINE_GYEONGGANG.lnCd -> STATION_LINE.LINE_GYEONGGANG.getColor()
                STATION_LINE.LINE_GYONGCHUN.lnCd -> STATION_LINE.LINE_GYONGCHUN.getColor()
                STATION_LINE.LINE_GYONGCUI_CENTERAL.lnCd -> STATION_LINE.LINE_GYONGCUI_CENTERAL.getColor()
                STATION_LINE.LINE_INCEHON_1.lnCd -> STATION_LINE.LINE_INCEHON_1.getColor()
                STATION_LINE.LINE_INCEHON_2.lnCd -> STATION_LINE.LINE_INCEHON_2.getColor()
                STATION_LINE.LINE_INCHEON_MAGLEY.lnCd -> STATION_LINE.LINE_INCHEON_MAGLEY.getColor()
                STATION_LINE.LINE_NEW_BUNDANG.lnCd -> STATION_LINE.LINE_NEW_BUNDANG.getColor()
                STATION_LINE.LINE_SEOHEA.lnCd -> STATION_LINE.LINE_SEOHEA.getColor()
                STATION_LINE.LINE_UIJEONGBU.lnCd -> STATION_LINE.LINE_UIJEONGBU.getColor()
                STATION_LINE.LINE_UISENSEOL.lnCd -> STATION_LINE.LINE_UISENSEOL.getColor()
                else -> R.color.black
            }
            , null
        )
    )
}

@BindingAdapter("lineLogo")
fun getLogoImage(view : ImageView , line : String) {
    Glide.with(view)
        .load(
            when(line) {
                STATION_LINE.LINE_1.lnCd -> STATION_LINE.LINE_1.getLineLogo()
                STATION_LINE.LINE_2.lnCd -> STATION_LINE.LINE_2.getLineLogo()
                STATION_LINE.LINE_3.lnCd -> STATION_LINE.LINE_3.getLineLogo()
                STATION_LINE.LINE_4.lnCd -> STATION_LINE.LINE_4.getLineLogo()
                STATION_LINE.LINE_5.lnCd -> STATION_LINE.LINE_5.getLineLogo()
                STATION_LINE.LINE_6.lnCd -> STATION_LINE.LINE_6.getLineLogo()
                STATION_LINE.LINE_7.lnCd -> STATION_LINE.LINE_7.getLineLogo()
                STATION_LINE.LINE_8.lnCd -> STATION_LINE.LINE_8.getLineLogo()
                STATION_LINE.LINE_9.lnCd -> STATION_LINE.LINE_9.getLineLogo()
                STATION_LINE.LINE_AIRPORT.lnCd -> STATION_LINE.LINE_AIRPORT.getLineLogo()
                STATION_LINE.LINE_BUNDANG.lnCd -> STATION_LINE.LINE_BUNDANG.getLineLogo()
                STATION_LINE.LINE_EVER.lnCd -> STATION_LINE.LINE_EVER.getLineLogo()
                STATION_LINE.LINE_GIMPO.lnCd -> STATION_LINE.LINE_GIMPO.getLineLogo()
                STATION_LINE.LINE_GYEONGGANG.lnCd -> STATION_LINE.LINE_GYEONGGANG.getLineLogo()
                STATION_LINE.LINE_GYONGCHUN.lnCd -> STATION_LINE.LINE_GYONGCHUN.getLineLogo()
                STATION_LINE.LINE_GYONGCUI_CENTERAL.lnCd -> STATION_LINE.LINE_GYONGCUI_CENTERAL.getLineLogo()
                STATION_LINE.LINE_INCEHON_1.lnCd -> STATION_LINE.LINE_INCEHON_1.getLineLogo()
                STATION_LINE.LINE_INCEHON_2.lnCd -> STATION_LINE.LINE_INCEHON_2.getLineLogo()
                STATION_LINE.LINE_INCHEON_MAGLEY.lnCd -> STATION_LINE.LINE_INCHEON_MAGLEY.getLineLogo()
                STATION_LINE.LINE_NEW_BUNDANG.lnCd -> STATION_LINE.LINE_NEW_BUNDANG.getLineLogo()
                STATION_LINE.LINE_SEOHEA.lnCd -> STATION_LINE.LINE_SEOHEA.getLineLogo()
                STATION_LINE.LINE_UIJEONGBU.lnCd -> STATION_LINE.LINE_UIJEONGBU.getLineLogo()
                STATION_LINE.LINE_UISENSEOL.lnCd -> STATION_LINE.LINE_UISENSEOL.getLineLogo()
                else -> R.color.black
            }
        )
        .error(R.drawable.popup_background)
        .into(view)
}
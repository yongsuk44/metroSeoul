package com.young.metro.util

import com.young.metro.R

enum class STATION_LINE(val lnCd : String) {

    LINE_1("1") {
        override fun getColor(): Int = R.color.colorLine1
        override fun getLineLogo(): Int = R.drawable.line1_svg
    },
    LINE_2("2") {
        override fun getColor(): Int = R.color.colorLine2
        override fun getLineLogo(): Int = R.drawable.line2_svg
    },
    LINE_3("3") {
        override fun getColor(): Int = R.color.colorLine3
        override fun getLineLogo(): Int = R.drawable.line3_svg
    },
    LINE_4("4") {
        override fun getColor(): Int = R.color.colorLine4
        override fun getLineLogo(): Int = R.drawable.line4_svg
    },
    LINE_5("5") {
        override fun getColor(): Int = R.color.colorLine5
        override fun getLineLogo(): Int = R.drawable.line5_svg
    },
    LINE_6("6") {
        override fun getColor(): Int = R.color.colorLine6
        override fun getLineLogo(): Int = R.drawable.line6_svg
    },
    LINE_7("7") {
        override fun getColor(): Int = R.color.colorLine7
        override fun getLineLogo(): Int = R.drawable.line7_svg
    },
    LINE_8("8") {
        override fun getColor(): Int = R.color.colorLine8
        override fun getLineLogo(): Int = R.drawable.line8_svg
    },
    LINE_9("9") {
        override fun getColor(): Int = R.color.colorLine9
        override fun getLineLogo(): Int = R.drawable.line9_svg
    },
    LINE_AIRPORT("A1") {
        override fun getColor(): Int = R.color.colorLineAirportRailway
        override fun getLineLogo(): Int = R.drawable.line_arex_svg
    },
    LINE_NEW_BUNDANG("D1") {
        override fun getColor(): Int = R.color.colorLineNewBundang
        override fun getLineLogo(): Int = R.drawable.line_new_bundang_svg
    },
    LINE_EVER("E1") {
        override fun getColor(): Int = R.color.colorLineEver
        override fun getLineLogo(): Int = R.drawable.line_ever_svg
    },
    LINE_GIMPO("G1") {
        override fun getColor(): Int = R.color.colorLineGimpo
        override fun getLineLogo(): Int = R.drawable.line_gimpo_svg
    },
    LINE_INCEHON_1("I1") {
        override fun getColor(): Int = R.color.colorLineIncheon
        override fun getLineLogo(): Int = R.drawable.line_incheon_1_svg
    },
    LINE_INCEHON_2("I2") {
        override fun getColor(): Int = R.color.colorLineIncheon2
        override fun getLineLogo(): Int = R.drawable.line_incheon_2_svg
    },
    LINE_BUNDANG("K1") {
        override fun getColor(): Int = R.color.colorLineBundang
        override fun getLineLogo(): Int = R.drawable.line_bundang_svg
    },
    LINE_GYONGCHUN("K2") {
        override fun getColor(): Int = R.color.colorLineGyeongchun
        override fun getLineLogo(): Int = R.drawable.line_gcc_svg
    },
    LINE_GYONGCUI_CENTERAL("K4") {
        override fun getColor(): Int = R.color.colorLineGyeonguiCentral
        override fun getLineLogo(): Int = R.drawable.line_gc_svg
    },
    LINE_GYEONGGANG("K5") {
        override fun getColor(): Int = R.color.colorLineGyeonggang
        override fun getLineLogo(): Int = R.drawable.linegg_svg
    },
    LINE_INCHEON_MAGLEY("M1") {
        override fun getColor(): Int = R.color.colorLineIncheonMaglev
        override fun getLineLogo(): Int = R.drawable.line_incheon_ma_svg
    },
    LINE_UIJEONGBU("U1") {
        override fun getColor(): Int = R.color.colorLineUijeongbu
        override fun getLineLogo(): Int = R.drawable.line_uj_svg
    },
    LINE_UISENSEOL("UI") {
        override fun getColor(): Int = R.color.colorLineUiSinseol
        override fun getLineLogo(): Int = R.drawable.line_ui_svg
    },
    LINE_SEOHEA("WS") {
        override fun getColor(): Int = R.color.colorLineSeohea
        override fun getLineLogo(): Int = R.drawable.line_sh_svg
    };

    abstract fun getColor() : Int
    abstract fun getLineLogo() : Int
}
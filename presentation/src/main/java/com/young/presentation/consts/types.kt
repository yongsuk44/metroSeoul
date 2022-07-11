package com.young.presentation.consts

enum class DayType(val seoul : String , val public: String) {
    WEEK("1","8") , SAT("2" , "7") , SUN("3","9");
}

fun getApiPath(lincode : String): String? = when (lincode) {
    "I1" -> "15041410"
    "1" -> "15041414"
    "2" -> "15041415"
    "3" -> "15041416"
    "4" -> "15041417"
    "5" -> "15041418"
    "6" -> "15041419"
    "7" -> "15041420"
    "8" -> "15041421"
    "9" -> "15041422"
    else -> null
}
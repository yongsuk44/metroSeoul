package com.young.data.model

import androidx.room.Entity

@Entity(primaryKeys = arrayOf("railOprIsttCd" , "lnCd"))
class LocalTrailCodeAndLineCode(
    val railOprIsttCd : String,
    val lnCd : String
)
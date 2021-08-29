package com.young.data.model

import androidx.room.Entity

@Entity(primaryKeys = arrayOf("railOprIsttCd" , "lnCd"))
class CacheTrailCodeAndLineCode(
    val railOprIsttCd : String,
    val lnCd : String
)
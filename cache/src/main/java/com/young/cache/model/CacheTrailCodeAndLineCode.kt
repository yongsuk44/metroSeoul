package com.young.cache.model

import androidx.room.Entity

@Entity(primaryKeys = arrayOf("railOprIsttCd" , "lnCd"))
class CacheTrailCodeAndLineCode(
    val railOprIsttCd : String,
    val lnCd : String
)
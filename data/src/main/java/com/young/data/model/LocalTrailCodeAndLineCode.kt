package com.young.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = arrayOf("railOprIsttCd" , "lnCd"))
class LocalTrailCodeAndLineCode(
    val railOprIsttCd : String,
    val lnCd : String
)
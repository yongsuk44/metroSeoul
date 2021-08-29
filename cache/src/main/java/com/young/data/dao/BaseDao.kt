package com.young.data.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Transaction

interface BaseDao<T> {
    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg data : T)
}
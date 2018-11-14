package com.ddmeng.dribbbleclient.data.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.ddmeng.dribbbleclient.data.model.Shot

@Dao
interface ShotDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShots(shots: List<Shot>)

    @Query("DELETE from shots_table")
    fun deleteAll()

    @Query("SELECT * from shots_table")
    fun getAllShots(): LiveData<List<Shot>>
}
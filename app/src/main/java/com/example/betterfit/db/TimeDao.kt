package com.example.betterfit.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TimeDao {

    @Insert
 suspend fun insert(timeDao: TimeData)

 @Delete
 suspend fun delete(timeDao: TimeData)

 @Query("SELECT * FROM TimeData order by id DESC")
 suspend fun getTime():List<TimeData>

}
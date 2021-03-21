package com.example.betterfit.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class TimeData(val time:Long,
                    @PrimaryKey(autoGenerate = true)
                    val id:Int=0)
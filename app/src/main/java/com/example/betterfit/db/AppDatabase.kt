package com.example.betterfit.db

import android.content.Context
import androidx.room.*
import kotlinx.coroutines.CoroutineScope

@Database(entities = [TimeData::class],version=1,exportSchema = false)
abstract class AppDatabase:RoomDatabase() {

    abstract fun getTimeDao():TimeDao

    companion object{
        @Volatile private var instance:AppDatabase?=null
        private val LOCK=Any()
        operator fun invoke(context: Context)=instance?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance=it
            }
        }
        private fun buildDatabase(context:Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "betterfitdatabase"
        ).build()
    }

}
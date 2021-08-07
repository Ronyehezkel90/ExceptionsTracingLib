package com.ron.exceptionstracer.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ron.exceptionstracer.room.ExceptionDao
import com.ron.exceptionstracer.room.ExceptionEntity

@Database(entities = [ExceptionEntity::class], version = 1)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun exceptionDao(): ExceptionDao

    companion object {
        private const val DATABASE_NAME = "exceptionsDb"

        fun buildDatabase(context: Context) =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
    }
}
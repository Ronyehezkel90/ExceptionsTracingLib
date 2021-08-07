package com.ron.exceptionstracer.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
internal interface ExceptionDao {
    @Query("SELECT * FROM exceptions")
    fun getAll(): Single<List<ExceptionEntity>>

    @Insert
    fun insert(exception: ExceptionEntity): Completable
}
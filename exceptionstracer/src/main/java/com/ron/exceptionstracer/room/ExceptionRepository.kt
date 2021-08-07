package com.ron.exceptionstracer.room

import android.content.Context
import com.ron.exceptionstracer.database.AppDatabase
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

internal class ExceptionRepository(context: Context) {
    private var appDatabase: AppDatabase = AppDatabase.buildDatabase(context)

    fun getAllExceptions(): Single<List<ExceptionEntity>> {
        return appDatabase.exceptionDao().getAll()
            .subscribeOn(Schedulers.io())
    }

    fun insertException(exceptionEntity: ExceptionEntity): Completable {
        return appDatabase.exceptionDao().insert(exceptionEntity)
            .subscribeOn(Schedulers.io())
    }

    fun deleteAllExceptions() {
        appDatabase.clearAllTables()
    }
}
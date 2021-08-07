package com.ron.exceptionstracer.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exceptions")
internal data class ExceptionEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "message") var message: String,
    @ColumnInfo(name = "stackTrace") var stackTrace: String,
    @ColumnInfo(name = "description") var description: String
)
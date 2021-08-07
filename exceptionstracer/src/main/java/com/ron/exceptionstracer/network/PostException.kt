package com.ron.exceptionstracer.network

import com.ron.exceptionstracer.room.ExceptionEntity
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

internal interface PostException {

    @POST("api/exceptions")
    fun postException(@Body exceptionEntityList: List<ExceptionEntity>): Single<Boolean>
}
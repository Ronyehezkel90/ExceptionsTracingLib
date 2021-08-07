package com.ron.exceptionstracer

internal class UncaughtExceptionsHandlerSetter {
    private val androidDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler()

    init {
        Thread.setDefaultUncaughtExceptionHandler { thread, e ->
            Tracer.log(e, "UNHANDLED EXCEPTION")
            androidDefaultExceptionHandler.uncaughtException(thread, e)
        }
    }
}
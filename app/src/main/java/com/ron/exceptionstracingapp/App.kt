package com.ron.exceptionstracingapp

import android.app.Application
import com.ron.exceptionstracer.Tracer

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Tracer.createInstance(this)
    }
}
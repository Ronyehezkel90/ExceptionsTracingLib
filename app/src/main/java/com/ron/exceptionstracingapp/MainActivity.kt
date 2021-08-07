package com.ron.exceptionstracingapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ron.exceptionstracer.Tracer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        uncaughtExceptionButton.setOnClickListener {
            val a = 4 / 0
        }
        caughtExceptionButton.setOnClickListener {
            try {
                val a = 4 / 0
            } catch (e: Exception) {
                Tracer.log(e, "I just wanted divide by zero")
            }
        }
    }
}

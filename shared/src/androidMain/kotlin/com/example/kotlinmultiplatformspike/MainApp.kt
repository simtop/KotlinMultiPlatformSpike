package com.example.kotlinmultiplatformspike

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import org.koin.dsl.module

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(
            module {
                single<Context> { this@MainApp }
                single {
                    { Log.i("Startup", "Hello from Android/Kotlin!") }
                }
            }
        )
    }
}
package com.minhoi.pethotel

import android.app.Application
import com.minhoi.utils.PreferenceUtil

class MainApplication : Application() {
    companion object {
        lateinit var prefs : PreferenceUtil
    }

    override fun onCreate() {
        super.onCreate()
        prefs = PreferenceUtil(applicationContext)
    }
}
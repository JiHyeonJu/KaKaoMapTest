package com.kakao.android.kakaomaptest

import android.app.Application
import com.kakao.android.kakaomaptest.util.PreferenceUtil

class MyApplication : Application() {
    companion object {
        lateinit var prefs: PreferenceUtil
    }

    override fun onCreate() {
        prefs = PreferenceUtil(applicationContext)
        super.onCreate()
    }
}

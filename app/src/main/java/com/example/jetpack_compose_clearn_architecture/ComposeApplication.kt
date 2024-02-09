package com.example.jetpack_compose_clearn_architecture

import android.app.Application
import com.example.jetpack_compose_clearn_architecture.utils.PreferenceManager

class ComposeApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        PreferenceManager.with(this)
    }
}
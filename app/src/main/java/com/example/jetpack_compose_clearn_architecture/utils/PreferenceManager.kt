package com.example.jetpack_compose_clearn_architecture.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.GsonBuilder

object PreferenceManager {

    lateinit var preferenceInstance: SharedPreferences

    private const val preference_name = "COMPOSE_PREF"

    fun with(application: Application) {
        preferenceInstance = application.getSharedPreferences(preference_name, Context.MODE_PRIVATE)
    }

    fun <T> putModel(
        `object`: T,
        key: String
    ) {
        val jsonString = GsonBuilder().create().toJson(`object`)
        preferenceInstance.edit().putString(key, jsonString).apply()
    }

    inline fun <reified T> getModel(key:String): T{
        val value = preferenceInstance.getString(key,null)
        return GsonBuilder().create().fromJson(value, T::class.java)
    }

    fun getString(key:String): String? {
        return preferenceInstance.getString(key,null)
    }
}
package com.example.sharedpreferences

import android.content.SharedPreferences

object Saver {

    var prefs: SharedPreferences? = null
        set(value) {
            if (value != null) {
                field = value
            }
        }


//    fun set(p: SharedPreferences) {
//        prefs = p
//    }
//
//    fun get(): SharedPreferences {
//        return prefs
//    }



    fun bool(key: String, defaultValue: Boolean = false): Boolean {
        return prefs?.getBoolean(key, defaultValue) ?: defaultValue
    }

    fun int(key: String, defaultValue: Int = 0): Int {
        return prefs?.getInt(key, defaultValue) ?: defaultValue
    }

    fun str(key: String, defaultValue: String = ""): String {
        return prefs?.getString(key, defaultValue) ?: defaultValue
    }

    fun save(key: String, value: String) {
        prefs?.let {
            with(it.edit()) {
                putString(key, value)
                apply()
            }
        }
    }

    fun save(key: String, value: Int) {
        prefs?.let {
            with(it.edit()) {
                putInt(key, value)
                apply()
            }
        }
    }

}
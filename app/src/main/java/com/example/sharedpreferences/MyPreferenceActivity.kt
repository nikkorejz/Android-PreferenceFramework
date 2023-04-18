package com.example.sharedpreferences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate

class MyPreferenceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        if (Saver.bool(Constants.PREFS_NIGHT_MODE)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            setTheme(
                when (Saver.str(Constants.PREFS_THEME)) {
                    Constants.THEME_DEFAULT ->  R.style.Theme_SharedPreferences
                    Constants.THEME_GREEN  -> R.style.Theme_SharedPreferences_Green
                    else ->  R.style.Theme_SharedPreferences
                }
            )
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_preference)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.PreferenceLayout, MyPreferenceFragment())
            .commit()
    }
}
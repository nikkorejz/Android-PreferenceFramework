package com.example.sharedpreferences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MyPreferenceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_preference)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.PreferenceLayout, MyPreferenceFragment())
            .commit()
    }
}
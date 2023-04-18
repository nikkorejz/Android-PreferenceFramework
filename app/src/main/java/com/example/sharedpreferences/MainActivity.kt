package com.example.sharedpreferences

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText

const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.editText)

//        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
//        val prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
        // /data/data/com.example.sharedpreferences/shared_prefs/com.example.sharedpreferences_preferences.xml
//        Saver.prefs = getSharedPreferences("${packageName}_preferences", Context.MODE_PRIVATE)
        Saver.prefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)

//        if (prefs.contains(Constants.PREFS_TEXT)) {
//        val text = prefs.getString(Constants.PREFS_TEXT, "No content")
        editText.setText(Saver.str(Constants.PREFS_TEXT))
//        }

        findViewById<Button>(R.id.saveButton).setOnClickListener {
//            val editor = prefs.edit()
//            editor.putString(Constants.PREFS_TEXT, editText.text.toString())
//            editor.apply()

//            with(prefs.edit()) {
//                putString(Constants.PREFS_TEXT, editText.text.toString())
//                apply()
//            }
            Saver.save(Constants.PREFS_TEXT, editText.text.toString())
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: contains ${Saver.prefs?.contains(Constants.PREFS_EDIT_TEXT_DISABLED)}")
        Log.i(TAG, "onResume: ${Saver.bool(Constants.PREFS_EDIT_TEXT_DISABLED)}")
        editText.isEnabled = !Saver.bool(Constants.PREFS_EDIT_TEXT_DISABLED)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(
                    this, MyPreferenceActivity::class.java))
            }
        }
        return true
    }
}
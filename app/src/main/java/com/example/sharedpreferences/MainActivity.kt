package com.example.sharedpreferences

import android.content.Intent
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewAnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import kotlin.math.hypot


const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var editText: EditText
    private lateinit var imageView: ImageView
    private lateinit var container: ConstraintLayout
    private var isGreen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        Saver.prefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)

        if (Saver.bool(Constants.PREFS_NIGHT_MODE)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            setTheme(
                when (Saver.str(Constants.PREFS_THEME)) {
                    Constants.THEME_DEFAULT -> R.style.Theme_SharedPreferences
                    Constants.THEME_GREEN -> R.style.Theme_SharedPreferences_Green
                    else -> R.style.Theme_SharedPreferences
                }
            )
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editText = findViewById(R.id.editText)
        imageView = findViewById(R.id.imageView)
        container = findViewById(R.id.root)

//        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
//        val prefs = getSharedPreferences("settings", Context.MODE_PRIVATE)
        // /data/data/com.example.sharedpreferences/shared_prefs/com.example.sharedpreferences_preferences.xml
//        Saver.prefs = getSharedPreferences("${packageName}_preferences", Context.MODE_PRIVATE)

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

        findViewById<Button>(R.id.changeThemeBtn).setOnClickListener {
            if (!isGreen) {
                setMyTheme(
                    ContextCompat.getColor(this, R.color.teal_700)
                )
            } else {
                setMyTheme(
                    ContextCompat.getColor(this, R.color.purple_700)
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.i(
            TAG,
            "onResume: contains ${Saver.prefs?.contains(Constants.PREFS_EDIT_TEXT_DISABLED)}"
        )
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
                startActivity(
                    Intent(
                        this, MyPreferenceActivity::class.java
                    )
                )
            }
        }
        return true
    }

    private fun setMyTheme(color: Int) {
        if (imageView.isVisible) {
            return
        }


        val w = container.measuredWidth
        val h = container.measuredHeight
        Log.i(TAG, "Width: $w, Height: $h")
        val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        if (!isGreen) {
            val background = container.background
            var colorOld = 0
            if (background is ColorDrawable)
                colorOld = (background as ColorDrawable).color
            container.setBackgroundColor(color)
            container.draw(canvas)
            container.setBackgroundColor(colorOld)
        } else {
            container.draw(canvas)
            container.setBackgroundColor(color)
        }

//        imageView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
        imageView.setImageBitmap(bitmap)
        imageView.isVisible = true


//        container.setBackgroundColor(color)

        val finalRadius = hypot(w.toFloat(), h.toFloat())

        val anim = ViewAnimationUtils.createCircularReveal(
            imageView,
            w / 2,
            h / 2,
//            0f,
//            finalRadius,
            if (isGreen) finalRadius else 0f,
            if (isGreen) 0f else finalRadius
        )
        anim.duration = 400L
        anim.doOnEnd {
            imageView.setImageDrawable(null)
            imageView.isVisible = false
            if (!isGreen) {
                container.setBackgroundColor(color)
            }
            isGreen = !isGreen
//            imageView.visibility = View.GONE
        }
        anim.start()
    }
}
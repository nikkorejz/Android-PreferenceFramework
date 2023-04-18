package com.example.sharedpreferences

import android.os.Bundle
import androidx.preference.CheckBoxPreference
import androidx.preference.ListPreference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat

class MyPreferenceFragment : PreferenceFragmentCompat() {



    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.my_prefs, rootKey)

        context?.let {
            val simplePreference = CheckBoxPreference(it).apply {
                key = Constants.PREFS_EDIT_TEXT_DISABLED
                title = "Заблокировать EditText?"
            }
            preferenceScreen.addPreference(simplePreference)

            val preferenceCategory = PreferenceCategory(it).apply {
                title = "Внешний вид"
            }
            preferenceScreen.addPreference(preferenceCategory)

            val switchPreference = SwitchPreferenceCompat(it).apply {
                title = "Ночная тема"
                key = Constants.PREFS_NIGHT_MODE
            }
            preferenceCategory.addPreference(switchPreference)

            val listPreference = ListPreference(it).apply {
                title = "Тема"
                key = Constants.PREFS_THEME
                entries = arrayOf("Светлая тема", "Зеленая тема")
                entryValues = arrayOf(Constants.THEME_DEFAULT, Constants.THEME_GREEN)
            }
            preferenceCategory.addPreference(listPreference)

            listPreference.setOnPreferenceChangeListener { preference, newValue ->
                (activity as MyPreferenceActivity).recreate()
                true
            }
            listPreference.isEnabled = !Saver.bool(Constants.PREFS_NIGHT_MODE)
//            listPreference.dependency = switchPreference.key
            switchPreference.setOnPreferenceChangeListener { preference, newValue ->
                listPreference.isEnabled = !(newValue as Boolean)
                (activity as MyPreferenceActivity).recreate()
                true
            }
        }
    }

}

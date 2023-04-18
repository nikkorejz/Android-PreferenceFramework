package com.example.sharedpreferences

import android.os.Bundle
import androidx.preference.CheckBoxPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat

class MyPreferenceFragment : PreferenceFragmentCompat() {



    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.my_prefs, rootKey)

        context?.let {
            val simplePreference = CheckBoxPreference(it).apply {
                key = Constants.PREFS_EDIT_TEXT_DISABLED
                title = "Заблокировать EditText?"
            }
            preferenceScreen.addPreference(simplePreference)
        }
    }

}

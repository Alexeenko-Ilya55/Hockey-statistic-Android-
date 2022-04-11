package com.alexproject.testapplication.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.alexproject.testapplication.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}
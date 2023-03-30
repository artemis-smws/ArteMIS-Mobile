package com.example.project_artemis

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isDarkModeOn = sharedPrefs.getBoolean("isDarkModeOn", false)

        switchButton.isChecked = isDarkModeOn

        switchButton.setOnCheckedChangeListener { _, isChecked ->

            with(sharedPrefs.edit()){
                putBoolean("isDarkModeOn", isChecked)
                apply()
            }
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            recreate()
        }
    }

}
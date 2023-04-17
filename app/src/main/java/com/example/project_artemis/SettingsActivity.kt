package com.example.project_artemis

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.project_artemis.databinding.ActivitySettingsBinding
import java.util.*

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.changeLanguageButton.setOnClickListener{
            val languageSelectionDialog = LanguageSelectionDialog(this)
            languageSelectionDialog.show { 
                updateLanguage() 
            }
        }

        val sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val isDarkModeOn = sharedPrefs.getBoolean("isDarkModeOn", false)

        binding.switchButton.isChecked = isDarkModeOn
        binding.switchButton.setOnCheckedChangeListener { _, isChecked ->

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

    private fun updateLanguage() {
        recreate()
    }

}
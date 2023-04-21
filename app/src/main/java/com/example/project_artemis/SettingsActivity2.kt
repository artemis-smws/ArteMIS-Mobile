package com.example.project_artemis

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.example.project_artemis.databinding.ActivitySettings2Binding

class SettingsActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivitySettings2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySettings2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backSettings.setOnClickListener{
            onBackPressed()
        }

        binding.changeLanguageButton.setOnClickListener{
            val languageSelectionDialog = LanguageSelectionDialog(this)
            languageSelectionDialog.show {
                updateLanguage()
            }
        }

        binding.changeLanguageButton.text = getString(R.string.Language)

        val sharedPrefs = getSharedPreferences("MyThemePrefs", Context.MODE_PRIVATE)
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
        val savedState = Bundle()
        onSaveInstanceState(savedState)
        recreate()
        onRestoreInstanceState(savedState)
    }

    override fun onBackPressed() {
        val intent = Intent(this, GuestActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

}

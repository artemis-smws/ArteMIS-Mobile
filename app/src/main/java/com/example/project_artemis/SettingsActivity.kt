package com.example.project_artemis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    private var currentTheme = AppTheme.Light

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTheme(currentTheme.styleResId)
        setContentView(R.layout.activity_settings)

        toggleButton.isChecked = currentTheme == AppTheme.Dark

        toggleButton.setOnClickListener{
            currentTheme = if (toggleButton.isChecked) AppTheme.Dark else AppTheme.Light
            setTheme(currentTheme.styleResId)
            recreate()
        }
    }
    private enum class AppTheme(val styleResId: Int){
        Light(R.style.Theme_Project_arteMIS_Light),
        Dark(R.style.Theme_Project_arteMIS_Dark)
    }
}
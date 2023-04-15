package com.example.project_artemis

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog

class LanguageSelectionDialog(private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("language_pref", Context.MODE_PRIVATE)

    fun show() {
        val view = LayoutInflater.from(context).inflate(R.layout.language_selection_dialog,null)
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setView(view)

        val languageGroup = view.findViewById<RadioGroup>(R.id.language_group)
        val englishButton = view.findViewById<RadioButton>(R.id.englishButton)
        val tagalogButton = view.findViewById<RadioButton>(R.id.tagalogButton)

        when (sharedPreferences.getString("language", "en")) {
            "en" -> englishButton.isChecked = true
            "tag" -> tagalogButton.isChecked = true
        }

        languageGroup.setOnCheckedChangeListener { _, checkedId ->
            val language = when (checkedId) {
                R.id.englishButton -> "en"
                R.id.tagalogButton -> "es"
                else -> "en"
            }
            saveLanguage(language)
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun saveLanguage(language: String) {
        sharedPreferences.edit().putString("language", language).apply()
    }
}

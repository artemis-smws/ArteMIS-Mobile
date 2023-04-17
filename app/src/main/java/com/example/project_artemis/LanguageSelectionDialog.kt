package com.example.project_artemis

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.content.res.Configuration
import android.view.LayoutInflater
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import java.util.Locale
class LanguageSelectionDialog(private val context: Context) {

    private lateinit var editor: Editor
    private lateinit var sharedPreferences: SharedPreferences
    private var onConfirm: (() -> Unit)? = null

    fun show(function: () -> Unit) {
        onConfirm = function
        val view = LayoutInflater.from(context).inflate(R.layout.language_selection_dialog, null)
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setView(view)
        
        val englishButton = view.findViewById<RadioButton>(R.id.englishButton)
        val tagalogButton = view.findViewById<RadioButton>(R.id.tagalogButton)

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Confirm") { dialog, _ ->
            if (englishButton.isChecked) {
                setLocale("en")
            } else if (tagalogButton.isChecked) {
                setLocale("tag")
            } else {
                setLocale("en")
            }
            dialog.dismiss()
        }
        alertDialog.show()
    }

    private fun setLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val resources = context.resources
        val configuration = Configuration(resources.configuration)
        configuration.setLocale(locale)

        resources.updateConfiguration(configuration, resources.displayMetrics)

        sharedPreferences = context.getSharedPreferences("MyLanguagePrefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        editor.putString("selected_language", language)
        editor.apply()

        onConfirm?.invoke()
    }

}

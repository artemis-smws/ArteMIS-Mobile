package com.example.project_artemis

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.example.project_artemis.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = intent.getStringExtra("email")

        auth = FirebaseAuth.getInstance()

        val caller = intent.getStringExtra("caller")
        if (caller.equals("guest")) {
            binding.accountLayout.visibility = View.GONE
        }

        binding.account.text = email

        binding.logoutButton.setOnClickListener{
            val builder = AlertDialog.Builder(this)
        
            builder.setTitle("Sign Out")
            builder.setMessage("Are you sure you want to sign out?")
        
            builder.setPositiveButton("Yes") { dialog, which ->

                auth.signOut()
        
                val googleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN)
                googleSignInClient.signOut().addOnCompleteListener {

                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()

                }
            }
        
            builder.setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
            }
        
            val dialog = builder.create()
            dialog.show()
        }
        

        binding.backSettings.setOnClickListener{
            onBackPressed()
        }

        binding.changeLanguageButton.setOnClickListener{
            val languageSelectionDialog = LanguageSelectionDialog(this)
            languageSelectionDialog.show {
                recreateActivity()
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
            recreateActivity()
        }
    }

    private fun recreateActivity() {
        val savedState = Bundle()
        onSaveInstanceState(savedState)
        recreate()
        onRestoreInstanceState(savedState)
    }

    override fun onBackPressed() {

        val email = intent.getStringExtra("email")
        val name = intent.getStringExtra("name")
        val uid = intent.getStringExtra("uid")
        val caller = intent.getStringExtra("caller")
        
        if (caller.equals("home")) {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            intent.putExtra("email", email)
            intent.putExtra("name", name)
            intent.putExtra("uid", uid)
            startActivity(intent)
        } else {
            val intent = Intent(this, GuestActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        finish()
        
        onSaveInstanceState(Bundle())
        super.onBackPressed()
    }

}

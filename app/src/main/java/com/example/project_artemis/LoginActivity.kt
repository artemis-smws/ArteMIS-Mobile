package com.example.project_artemis

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.project_artemis.databinding.ActivityLoginBinding
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.appcompat.app.AlertDialog

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var backPressedTime: Long = 0
    private lateinit var sharedPreferences: SharedPreferences

    private var mIsShowPass = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.visibility.setOnClickListener {
            mIsShowPass = !mIsShowPass
            showPassword(mIsShowPass)
        }

        showPassword(mIsShowPass)

        binding.visibility.visibility = View.GONE

        binding.editTextPassword.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) {
                    binding.visibility.visibility = View.GONE
                } else {
                    binding.visibility.visibility = View.VISIBLE
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        sharedPreferences = getSharedPreferences("MySelectedLanguagePrefs", MODE_PRIVATE)
        val languageSelected = sharedPreferences.getBoolean("languageSelected", false)

        if (!languageSelected) {
            val languageSelectionDialog = LanguageSelectionDialog(this)
            languageSelectionDialog.show {
                updateLanguage()
            }

            sharedPreferences.edit().putBoolean("languageSelected", true).apply()
        }else {
            sharedPreferences.edit().putBoolean("languageSelected", false).apply()
        }

        if (!languageSelected) {
            val languageSelectionDialog = LanguageSelectionDialog(this)
            languageSelectionDialog.show {
                updateLanguage()
            }

            sharedPreferences.edit().putBoolean("languageSelected", true).apply()
        }    

        binding.textView2.text = getString(R.string.login_or)

        binding.textView3.text = getString(R.string.Login_to_your_account)

        binding.textView4.text = getString(R.string.welcome_back)

        binding.loginBtn.text = getString(R.string.sign_in)

        binding.viewBtn.text = getString(R.string.view_guest)

        binding.forgotPass.text = getString(R.string.forgot_password)

        binding.textView5.text = getString(R.string.continue_with)
        
        binding.loginBtn.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()

            val emailRegex = Regex("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
            val passwordRegex = Regex("^(?=.*[\\d@\$!%*?&])[\\w@\$!%*?&]{8,}$")

            if (!emailRegex.matches(email)) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Login Error")
                builder.setMessage("Please enter a valid email address")
                builder.setPositiveButton("OK") { dialog, which ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
            } else if (!passwordRegex.matches(password)) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Login Error")
                builder.setMessage("Your password must have:\n8 or more characters and contain at least 1 number and 1 special character.")
                builder.setPositiveButton("OK") { dialog, which ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
            } else {
                val intent = Intent(this, HomeActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("caller", "home")
                startActivity(intent)
            }
        }
        
        binding.viewBtn.setOnClickListener {
            val intent = Intent(this, GuestActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.putExtra("caller", "guest")
            startActivity(intent)
        }
        
    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            finishAffinity()
            finish()
        } else {
            Toast.makeText(this, "Press back again to exit the app.", Toast.LENGTH_LONG).show()
        }
        backPressedTime = System.currentTimeMillis()
    }

    private fun updateLanguage() {
        recreate()
    }

    private fun showPassword(isShow: Boolean){
        if (isShow) {
            binding.editTextPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.visibility.setImageResource(R.drawable.baseline_visibility_off_24)
        }else{
            binding.editTextPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.visibility.setImageResource(R.drawable.baseline_remove_red_eye_24)
        }
        binding.editTextPassword.setSelection(binding.editTextPassword.text.toString().length)
    }
}

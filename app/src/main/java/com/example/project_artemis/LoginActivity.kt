package com.example.project_artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.project_artemis.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout and initialize the binding object
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView2.text = getString(R.string.login_or)

        binding.textView3.text = getString(R.string.Login_to_your_account)

        binding.textView4.text = getString(R.string.welcome_back)

        binding.loginBtn.text = getString(R.string.sign_in)

        binding.viewBtn.text = getString(R.string.view_guest)

        binding.textView5.text = getString(R.string.continue_with)

        // Access views using the binding object
        binding.loginBtn.setOnClickListener {
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }
        binding.viewBtn.setOnClickListener {
            val intent = Intent(this,GuestActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            super.onBackPressed()
            finishAffinity()
            finish()
        }else{
            Toast.makeText(this, "Press back again to exit the app.", Toast.LENGTH_LONG).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}

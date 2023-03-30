package com.example.project_artemis

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    var backPressedTime: Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginBtn.setOnClickListener {
            val intent = Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }
        viewBtn.setOnClickListener {
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
package com.example.project_artemis

import android.app.UiModeManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import com.example.project_artemis.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val preferences_name: String = "isFirstTime"
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uiModeManager = getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
        if (uiModeManager.nightMode == UiModeManager.MODE_NIGHT_YES) {
            binding.logo.visibility = View.GONE
            binding.logoDark.visibility = View.VISIBLE
        }else{
            binding.logo.visibility = View.VISIBLE
            binding.logoDark.visibility = View.GONE
        }

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        val isConnected = networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

        if (isConnected) {
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                firstTime()
            }, 1500)

        }else{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("No Internet Connection")
            builder.setMessage("Please connect to the Internet to proceed")
            builder.setPositiveButton("Retry") {dialog, which ->
                val intent = Intent(this, this::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
            }
            val dialog = builder.create()
            dialog.show()
            //Toast.makeText(this,"You are not connected to the Internet", Toast.LENGTH_LONG).show()
            //finishAffinity()
            //finish()
        }
    }

    private fun firstTime() {
        val pref: SharedPreferences = getSharedPreferences(preferences_name, 0)
        if (pref.getBoolean("isFirstTime", true)) {
            Handler().postDelayed({
                val intent = Intent(this@MainActivity, IntroActivity::class.java)
                startActivity(intent)
            }, 1500)
            pref.edit().putBoolean("isFirstTime", false).commit()
        } else {
            Handler().postDelayed({
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
            }, 1500)
        }

    }
}
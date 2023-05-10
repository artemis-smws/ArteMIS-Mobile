package com.example.project_artemis

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.project_artemis.databinding.ActivityGuestBinding

class GuestActivity : AppCompatActivity() {

    private var backPressedTime: Long = 0
    private lateinit var binding : ActivityGuestBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.settings.setOnClickListener {
            val intent = Intent(this,SettingsActivity::class.java)
            intent.putExtra("caller", "guest")
            startActivity(intent)
        }

        binding.contactUs.setOnClickListener {
            if (binding.feedback.visibility == View.VISIBLE && binding.concerns.visibility == View.VISIBLE){
                binding.feedback.animate().alpha(0f).setDuration(300).withEndAction { binding.feedback.visibility = View.GONE }
                binding.concerns.animate().alpha(0f).setDuration(300).withEndAction { binding.concerns.visibility = View.GONE }
            }else{
                binding.feedback.visibility = View.VISIBLE
                binding.feedback.alpha = 0f
                binding.feedback.animate().alpha(1f).setDuration(300)
                binding.concerns.visibility = View.VISIBLE
                binding.concerns.alpha = 0f
                binding.concerns.animate().alpha(1f).setDuration(300)
            }
        }

        binding.concerns.setOnClickListener {
            val intent = Intent(this,ConcernActivity::class.java)
            startActivity(intent)
        }

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        val isConnected = networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

        if (isConnected) {
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
        }
        binding.bottomNav.selectedItemId = R.id.home
        binding.bottomNav.setOnNavigationItemSelectedListener {

            when(it.itemId){
                R.id.status -> replaceFragment(DataFragment())
                R.id.home -> replaceFragment(HomeFragment())
                R.id.maps -> replaceFragment(LocationFragment())

                else ->{
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
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
    
}

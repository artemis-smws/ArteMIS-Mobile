package com.example.project_artemis

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Handler
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.project_artemis.databinding.ActivityGuestBinding

class GuestActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var hideButtonRunnable: Runnable
    private var isButtonVisible: Boolean = true
    private var backPressedTime: Long = 0
    private lateinit var binding : ActivityGuestBinding

    @SuppressLint("ClickableViewAccessibility", "ResourceType")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())

        handler = Handler()
        hideButtonRunnable = Runnable { hideButton() }

        binding.frameLayout.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    showButton()
                    handler.removeCallbacks(hideButtonRunnable)
                }
                MotionEvent.ACTION_UP -> {
                    handler.removeCallbacks(hideButtonRunnable)
                    handler.postDelayed(hideButtonRunnable, 3000)
                }
            }
            false
        }

        binding.frameLayout.postDelayed(hideButtonRunnable, 3000)


        binding.settings.setOnClickListener {
            val intent = Intent(this,SettingsActivity::class.java).apply {
                putExtra("caller", "guest")
            }
            startActivity(intent)
        }

        binding.contactUs.setOnClickListener {
            val intent = Intent(this,ConcernActivity::class.java)
            startActivity(intent)
        }

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        val isConnected = networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true

        if (!isConnected) {
            showNoInternetDialog()
        }

        binding.bottomNav.selectedItemId = R.id.home
        binding.bottomNav.setOnNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.status -> replaceFragment(DataFragment())
                R.id.home -> replaceFragment(HomeFragment())
                R.id.maps -> replaceFragment(LocationFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment : Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout,fragment)
            .commit()
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

    private fun hideButton() {
        if (isButtonVisible) {
            binding.contactUs.animate().alpha(0f).setDuration(300).withEndAction { binding.contactUs.visibility = View.GONE }
            binding.contactIcon.animate().alpha(0f).setDuration(300).withEndAction { binding.contactIcon.visibility = View.GONE }
            isButtonVisible = false
        }
    }

    private fun showButton() {
        if (!isButtonVisible) {
            binding.contactUs.visibility = View.VISIBLE
            binding.contactUs.alpha = 0f
            binding.contactUs.animate().alpha(1f).setDuration(300)
            binding.contactIcon.visibility = View.VISIBLE
            binding.contactIcon.alpha = 0f
            binding.contactIcon.animate().alpha(1f).setDuration(300)
            isButtonVisible = true
        }
    }
    
    private fun showNoInternetDialog() {
        val builder = AlertDialog.Builder(this).apply {
            setTitle("No Internet Connection")
            setMessage("Please connect to the Internet to proceed")
            setPositiveButton("Retry") { dialog, which ->
                val intent = Intent(this@GuestActivity, this@GuestActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
                startActivity(intent)
                finish()
            }
        }
        val dialog = builder.create()
        dialog.show()
    }
}

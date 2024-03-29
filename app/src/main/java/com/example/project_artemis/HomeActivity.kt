package com.example.project_artemis

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.project_artemis.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private var backPressedTime: Long = 0
    private lateinit var binding : ActivityHomeBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val email = intent.getStringExtra("email")
        val name = intent.getStringExtra("name")
        val uid = intent.getStringExtra("uid")

        val bundle = Bundle()
        bundle.putString("name", name)

        val homeFragment = HomeFragment()
        homeFragment.arguments = bundle

        replaceFragment(homeFragment)

        val bundle2 = Bundle()
        bundle2.putString("uid", uid)

        val addFragment = AddFragment()
        addFragment.arguments = bundle2

        binding.settings.setOnClickListener {
            val intent = Intent(this,SettingsActivity::class.java)
            intent.putExtra("email", email)
            intent.putExtra("name", name)
            intent.putExtra("uid", uid)
            intent.putExtra("caller", "home")
            startActivity(intent)
        }

        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        val isConnected = networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        
        if (!isConnected) {
            showNoInternetDialog()
        }

        binding.bottomNav.selectedItemId = R.id.home
        binding.bottomNav.setOnNavigationItemSelectedListener {

            when(it.itemId){
                R.id.input -> replaceFragment(addFragment)
                R.id.status -> replaceFragment(DataFragment())
                R.id.home -> replaceFragment(homeFragment)
                R.id.maps -> replaceFragment(LocationFragment())
                R.id.logs -> replaceFragment(LogsFragment())

                else -> {
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

    private fun showNoInternetDialog() {
        val builder = AlertDialog.Builder(this).apply {
            setTitle("No Internet Connection")
            setMessage("Please connect to the Internet to proceed")
            setPositiveButton("Retry") { dialog, which ->
                val intent = Intent(this@HomeActivity, this@HomeActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
                startActivity(intent)
                finish()
            }
        }
        val dialog = builder.create()
        dialog.show()
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

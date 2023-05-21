package com.example.project_artemis

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.button.MaterialButton
import androidx.activity.result.contract.ActivityResultContracts

class IntroActivity : AppCompatActivity() {

    private lateinit var onboardingItemsAdapter: OnboardingItemsAdapter
    private lateinit var indicatorsContainer: LinearLayout
    private var backPressedTime: Long = 0

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            showToast("Notifications permission granted")
        } else {
            showToast("FCM can't post notifications without POST_NOTIFICATIONS permission")
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // FCM SDK (and your app) can post notifications.
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        setOnboardingItems()
        setupIndicators()
        setCurrentIndicator(0)

        askNotificationPermission()

        val buttonGettingStarted = findViewById<MaterialButton>(R.id.buttonGetStarted)

        buttonGettingStarted.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    private fun setOnboardingItems() {
        onboardingItemsAdapter = OnboardingItemsAdapter(
            listOf(
                OnboardingItem(
                    R.drawable.gettingstarted1,
                    "WELCOME TO WASTE MANAGEMENT APP",
                    "Our waste management app is available to assist you in your journey towards a more sustainable lifestyle. You can take a step further by exploring innovative ways to repurpose your waste materials, and our app will take care of the rest. We are here to support you in your efforts to reduce waste and make a positive impact on the environment."
                ),
                OnboardingItem(
                    R.drawable.gettingstarted2,
                    "LET’S GO!",
                    "The negative impacts of waste on the environment and human well-being are significant. Here comes our rule to help you have an environmental impact and feel good about it"
                ),
                OnboardingItem(
                    R.drawable.gettingstarted3,
                    "WELCOME ON BOARD!",
                    ""
                )
            )
        )
        val onboardingViewPager = findViewById<ViewPager2>(R.id.onboardingViewPager)
        onboardingViewPager.adapter = onboardingItemsAdapter
        onboardingViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })
        onboardingViewPager.getChildAt(0)?.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    private fun setupIndicators() {
        indicatorsContainer = findViewById(R.id.indicatorsContainer)
        val indicators = arrayOfNulls<ImageView>(onboardingItemsAdapter.itemCount)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(8, 0, 8, 0)
        }
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext).apply {
                setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive_background
                    )
                )
                this.layoutParams = layoutParams
                indicatorsContainer.addView(this)
            }
        }
    }

    private fun setCurrentIndicator(position: Int) {
        val childCount = indicatorsContainer.childCount
        for (i in 0 until childCount) {
            val imageView = indicatorsContainer.getChildAt(i) as ImageView
            val drawable = if (i == position) {
                R.drawable.indicator_active_background
            } else {
                R.drawable.indicator_inactive_background
            }
            imageView.setImageDrawable(ContextCompat.getDrawable(applicationContext, drawable))
        }
    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            finishAndRemoveTask()
        } else {
            showToast("Press back again to exit the app.")
        }
        backPressedTime = System.currentTimeMillis()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}






// package com.example.project_artemis

// import android.content.Intent
// import androidx.appcompat.app.AppCompatActivity
// import android.os.Bundle
// import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
// import android.widget.ImageView
// import android.widget.LinearLayout
// import android.widget.Toast
// import androidx.core.content.ContextCompat
// import androidx.recyclerview.widget.RecyclerView
// import androidx.viewpager2.widget.ViewPager2
// import com.google.android.material.button.MaterialButton
// import android.Manifest
// import android.content.pm.PackageManager
// import android.os.Build
// import androidx.activity.result.contract.ActivityResultContracts


// class IntroActivity : AppCompatActivity() {

//     private lateinit var onboardingItemsAdapter: OnboardingItemsAdapter
//     private lateinit var indicatorsContainer: LinearLayout
//     private var backPressedTime: Long = 0

//     private val requestPermissionLauncher = registerForActivityResult(
//         ActivityResultContracts.RequestPermission(),
//     ) { isGranted: Boolean ->
//         if (isGranted) {
//             Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT)
//                 .show()
//         } else {
//             Toast.makeText(
//                 this,
//                 "FCM can't post notifications without POST_NOTIFICATIONS permission",
//                 Toast.LENGTH_LONG,
//             ).show()
//         }
//     }

//     private fun askNotificationPermission() {
//         // This is only necessary for API Level > 33 (TIRAMISU)
//         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//             if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
//                 PackageManager.PERMISSION_GRANTED
//             ) {
//                 // FCM SDK (and your app) can post notifications.
//             } else {
//                 // Directly ask for the permission
//                 requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
//             }
//         }
//     }

//     override fun onCreate(savedInstanceState: Bundle?) {

//         super.onCreate(savedInstanceState)
//         setContentView(R.layout.activity_intro)
//         setOnboardingItems()
//         setupIndicators()
//         setCurrentIndicator(0)

//         askNotificationPermission()

//         val buttonGettingStarted = findViewById<MaterialButton>(R.id.buttonGetStarted)

//         buttonGettingStarted.setOnClickListener{
//             val intent = Intent(this, LoginActivity::class.java)
//             startActivity(intent)
//         }

//     }

//     private fun setOnboardingItems() {
//         onboardingItemsAdapter = OnboardingItemsAdapter(
//             listOf(
//                 OnboardingItem(
//                     onboardingImage = R.drawable.gettingstarted1,
//                     title = "WELCOME TO WASTE MANAGEMENT APP",
//                     description = "Our waste management app is available to assist you in your journey towards a more sustainable lifestyle. You can take a step further by exploring innovative ways to repurpose your waste materials, and our app will take care of the rest. We are here to support you in your efforts to reduce waste and make a positive impact on the environment."
//                 ),
//                 OnboardingItem(
//                     onboardingImage = R.drawable.gettingstarted2,
//                     title = "LET’S GO!",
//                     description = "The negative impacts of waste on the environment and human well-being are significant. Here comes our rule to help you have an environmental impact and feel good about it"
//                 ),
//                 OnboardingItem(
//                     onboardingImage = R.drawable.gettingstarted3,
//                     title = "WELCOME ON BOARD!",
//                     description = " "
//                 )
//             )
//         )
//         val onboardingViewPager = findViewById<ViewPager2>(R.id.onboardingViewPager)
//         onboardingViewPager.adapter = onboardingItemsAdapter
//         onboardingViewPager.registerOnPageChangeCallback(object :
//             ViewPager2.OnPageChangeCallback() {
//             override fun onPageSelected(position: Int) {
//                 super.onPageSelected(position)
//                 setCurrentIndicator(position)
//             }
//         })
//         (onboardingViewPager.getChildAt(0) as RecyclerView).overScrollMode =
//             RecyclerView.OVER_SCROLL_NEVER
//     }

//     private fun setupIndicators() {
//         indicatorsContainer = findViewById(R.id.indicatorsContainer)
//         val indicators = arrayOfNulls<ImageView>(onboardingItemsAdapter.itemCount)
//         val layoutParams: LinearLayout.LayoutParams =
//             LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
//         layoutParams.setMargins(8,0,8,0)
//         for (i in indicators.indices) {
//             indicators[i] = ImageView(applicationContext)
//             indicators[i]?.let {
//                 it.setImageDrawable(
//                     ContextCompat.getDrawable(
//                         applicationContext,
//                         R.drawable.indicator_inactive_background
//                     )
//                 )
//                 it.layoutParams = layoutParams
//                 indicatorsContainer.addView(it)
//             }
//         }
//     }

//     private fun setCurrentIndicator(position: Int) {
//         val childCount = indicatorsContainer.childCount
//         for (i in 0 until childCount) {
//             val imageView = indicatorsContainer.getChildAt(i) as ImageView
//             if (i == position) {
//                 imageView.setImageDrawable(
//                     ContextCompat.getDrawable(
//                         applicationContext,
//                         R.drawable.indicator_active_background
//                     )
//                 )
//             } else {
//                 imageView.setImageDrawable(
//                     ContextCompat.getDrawable(
//                         applicationContext,
//                         R.drawable.indicator_inactive_background
//                     )
//                 )
//             }
//         }
//     }

//     override fun onBackPressed() {
//         if (backPressedTime + 2000 > System.currentTimeMillis()) {
//             super.onBackPressed()
//             finishAndRemoveTask()
//         } else {
//             Toast.makeText(this, "Press back again to exit the app.", Toast.LENGTH_LONG).show()
//         }
//         backPressedTime = System.currentTimeMillis()
//     }
// }
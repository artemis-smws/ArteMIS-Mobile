package com.example.project_artemis

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.project_artemis.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import android.widget.*
import okhttp3.OkHttpClient
import okhttp3.Request
import android.widget.Toast
import okhttp3.logging.HttpLoggingInterceptor

class LoginActivity : AppCompatActivity() {

    private val signIn = BuildConfig.signin
    private lateinit var binding: ActivityLoginBinding
    private var backPressedTime: Long = 0
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    private var mIsShowPass = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        val currentUser = auth.currentUser
        if (currentUser != null) {
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("email",currentUser.email)
            intent.putExtra("name",currentUser.displayName)
            intent.putExtra("uid",currentUser.uid)
            startActivity(intent)
            finish()
        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.googleButton.setOnClickListener {
            signInGoogle()
            binding.progressBar2.visibility = View.GONE
        }

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

        binding.loginBtn.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()

            val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}\$")
            val passwordRegex = Regex("^.{8,}\$")

            if (!emailRegex.matches(email)) {
                showErrorDialog("Please enter a valid email address")
            } else if (!passwordRegex.matches(password)) {
                showErrorDialog("Your password must have 8 or more characters")
            } else {
                sendSignInRequest(email, password)
            }
        }

        binding.viewBtn.setOnClickListener {
            createIntentGuest()
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


    private fun showErrorDialog(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Login Error")
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    private fun sendSignInRequest(email: String, password: String) {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()

        val jsonBody = JSONObject().apply {
            put("email", email)
            put("password", password)
        }

        val requestBody =
            jsonBody.toString().toRequestBody("application/json; charset=utf-8".toMediaType())
        val request = Request.Builder()
            .url(signIn)
            .put(requestBody)
            .addHeader("Content-Type", "application/json")
            .build()


        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    showErrorDialog("Failed to sign in. Please try again.")
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if (response.isSuccessful && responseBody != null) {
                    createIntent()
                } else {
                    runOnUiThread {
                        showErrorDialog("Failed to sign in. Please check your credentials.")
                    }
                }
            }
        })
    }

    private fun createIntent() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun createIntentGuest() {
        val intent = Intent(this, GuestActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun signInGoogle(){
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }
    }

    private fun handleResults(task: Task<GoogleSignInAccount>){
        if (task.isSuccessful){
            val account : GoogleSignInAccount? = task.result
            binding.progressBar2.visibility = View.VISIBLE
            binding.overlay.visibility = View.VISIBLE
            if (account != null){
                updateUI(account)
            }
        }else{
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Signup Error")
            builder.setMessage("Please enter a valid email address")
            builder.setPositiveButton("OK") { dialog, which ->
                binding.progressBar2.visibility = View.GONE
                binding.overlay.visibility = View.GONE
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
        }
    }

    private fun updateUI(account: GoogleSignInAccount){
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener{
            if (it.isSuccessful){
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("email",account.email)
                intent.putExtra("name",account.displayName)
                intent.putExtra("uid",account.id)
                startActivity(intent)
            }else{
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Signup Error")
                builder.setMessage("Please enter a valid email address")
                builder.setPositiveButton("OK") { dialog, which ->
                    dialog.dismiss()
                }
                val dialog = builder.create()
                dialog.show()
            }
        }
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

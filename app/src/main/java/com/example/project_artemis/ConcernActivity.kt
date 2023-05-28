package com.example.project_artemis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.project_artemis.databinding.ActivityConcernBinding
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import java.io.IOException

class ConcernActivity : AppCompatActivity() {

    private var buildingName: String? = null
    private var campusName: String? = null
    private lateinit var binding: ActivityConcernBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConcernBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backConcern.setOnClickListener{
            onBackPressed()
        }

        val itemsLocation = listOf(
            "Batangas State University - Alangilan",
            "Batangas State University - Pablo Borbon",
            "Batangas State University - Malvar"
        )

        val adapterLocation = ArrayAdapter(
            this,
            R.layout.spinner_selected_item2,
            itemsLocation
        ).apply {
            setDropDownViewResource(R.layout.style_spinner)
        }

        binding.locationPickerInput.adapter = adapterLocation

        binding.locationPickerInput.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                    val selectedLoc = parent?.getItemAtPosition(position).toString()

                    val buildingSelectorAlangilan = listOf(
                        "CEAFA Building",
                        "CIT Building",
                        "CICS Building",
                        "RGR Building",
                        "Gymnasium",
                        "STEER Hub",
                        "Student Services Center"
                    )

                    val buildingSelectorMain = listOf(
                        "Audio Visual Building",
                        "CALABARZON Integrated Research & Training Center",
                        "College of Nursing",
                        "College of Teacher Education",
                        "CIT Building",
                        "COE Building",
                        "General Engineering Building",
                        "Gymnasium",
                        "Gymnasium 2",
                        "Higher Education Building",
                        "Sewage Treatment Plant",
                        "Student Services Center",
                        "Student Services Center II",
                        "University Wellness Center"
                    )

                    val buildingSelectorMalvar = listOf(
                        "CIT Building",
                        "CICS Building",
                        "COE Building",
                        "Gymnasium",
                        "Student Services Center"
                    )

                    val adapterBuilding = ArrayAdapter(
                        this@ConcernActivity,
                        R.layout.spinner_selected_item2,
                        buildingSelectorAlangilan
                    ).apply {
                        setDropDownViewResource(R.layout.style_spinner)
                    }

                    binding.buildingPickerInput.adapter = adapterBuilding

                    binding.buildingPickerInput.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {

                            val selectedBuilding = binding.buildingPickerInput.selectedItem.toString()
                            buildingName = when (selectedBuilding) {
                                "CEAFA Building" -> "CEAFA"
                                "CIT Building" -> "CIT"
                                "CICS Building" -> "CICS"
                                "RGR Building" -> "RGR"
                                "Gymnasium" -> "Gymnasium"
                                "STEER Hub" -> "STEER_Hub"
                                "Student Services Center" -> "SSC"
                                else -> ""
                            }
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {}
                    }

                    campusName = when (selectedLoc) {
                        "Batangas State University - Alangilan" -> "Alangilan"
                        "Batangas State University - Pablo Borbon" -> "Pablo Borbon"
                        "Batangas State University - Malvar" -> "Malvar"
                        else -> ""
                    }

                    when (selectedLoc) {
                        "Batangas State University - Alangilan" -> {
                            binding.buildingPickerInput.adapter = ArrayAdapter(
                                this@ConcernActivity,
                                R.layout.spinner_selected_item2,
                                buildingSelectorAlangilan
                            )
                        }

                        "Batangas State University - Pablo Borbon" -> {
                            binding.buildingPickerInput.adapter = ArrayAdapter(
                                this@ConcernActivity,
                                R.layout.spinner_selected_item2,
                                buildingSelectorMain
                            )
                        }

                        "Batangas State University - Malvar" -> {
                            binding.buildingPickerInput.adapter = ArrayAdapter(
                                this@ConcernActivity,
                                R.layout.spinner_selected_item2,
                                buildingSelectorMalvar
                            )
                        }
                    }

                    val itemsConcern = listOf(
                        "Trash Bin Full",
                        "Damaged Trash Bin",
                        "Odor Complaint Regarding Trash Bin",
                        "Problems with the App"
                    )

                    val adapterConcern = ArrayAdapter(
                        this@ConcernActivity,
                        R.layout.spinner_selected_item2,
                        itemsConcern
                    ).apply {
                        setDropDownViewResource(R.layout.style_spinner)
                    }

                    binding.concernTypeInput.adapter = adapterConcern

                    binding.concernTypeInput.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                val selectedItem = parent?.getItemAtPosition(position).toString()

                                binding.submitButton.isEnabled = false // Disable the button initially

                                // Add a TextWatcher to monitor changes in the EditText fields
                                val textWatcher = object : TextWatcher {
                                    override fun afterTextChanged(s: Editable?) {
                                        // Check if all EditText fields have non-empty values
                                        val amount = binding.descriptionConcernEditText.text.toString().trim()
                                        val allFieldsFilled = amount.isNotEmpty()
                                        // Enable/disable the button based on the result of the check
                                        runOnUiThread {
                                            binding.submitButton.isEnabled = allFieldsFilled
                                        }
                                    }

                                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                                        // No implementation needed
                                    }

                                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                        // No implementation needed
                                    }
                                }

                                binding.descriptionConcernEditText.addTextChangedListener(textWatcher)

                                binding.submitButton.setOnClickListener {
                                    val building = buildingName
                                    val campus = campusName
                                    val description = binding.descriptionConcernEditText.text.toString().trim()
                                    
                                    // Check if any EditText field is empty before proceeding
                                    if (description.isEmpty()) {
                                        showErrorMessage("Please enter the required data")
                                        return@setOnClickListener
                                    }
                                    
                                    binding.progressBar2.visibility = View.VISIBLE
                                    binding.overlay.visibility = View.VISIBLE
                                    binding.overlay.setOnTouchListener { _, _ -> true}
                                    
                                    val interceptor = HttpLoggingInterceptor()
                                    interceptor.level = HttpLoggingInterceptor.Level.BODY
                                    
                                    val client = OkHttpClient.Builder()
                                        .addInterceptor(interceptor)
                                        .build()
                                    
                                    val url = "https://us-central1-artemis-b18ae.cloudfunctions.net/server/reports"
                                    
                                    val jsonBody = JSONObject().apply {
                                        put("title", selectedItem)
                                        put("campus", campus)
                                        put("building", building)
                                        put("description", description)
                                    }
                                    
                                    val requestBody = jsonBody.toString().toRequestBody("application/json; charset=utf-8".toMediaType())
                                    val request = Request.Builder()
                                        .url(url)
                                        .post(requestBody)
                                        .addHeader("Content-Type", "application/json")
                                        .build()
                                    
                                    // Send the request and handle the response
                                    client.newCall(request).enqueue(object : Callback {
                                        override fun onFailure(call: Call, e: IOException) {
                                            // Handle the failure
                                            Toast.makeText(
                                                this@ConcernActivity,
                                                "An error occurred: ${e.message}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    
                                        override fun onResponse(call: Call, response: Response) {
                                            val responseBody = response.body?.string()
                                            if (response.isSuccessful && responseBody != null) {
                                                runOnUiThread {
                                                    binding.descriptionConcernEditText.setText("")
                                                    binding.progressBar2.visibility = View.GONE
                                                    binding.overlay.visibility = View.GONE
                                                    Toast.makeText(
                                                        this@ConcernActivity,
                                                        "Your concern has been sent",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            } else {
                                                runOnUiThread {
                                                    binding.progressBar2.visibility = View.GONE
                                                    binding.overlay.visibility = View.GONE
                                                    Toast.makeText(
                                                        this@ConcernActivity,
                                                        "Sending concern failed",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                }
                                            }
                                        }
                                    })
                                }

                            }

                            override fun onNothingSelected(p0: AdapterView<*>?) {
                                TODO("Not yet implemented")
                            }
                        }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
    }

    private fun showErrorMessage(message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, which ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }

    override fun onBackPressed(){
        
        finish()
        onSaveInstanceState(Bundle())
        super.onBackPressed()
    }

}





// package com.example.project_artemis

// import androidx.appcompat.app.AppCompatActivity
// import android.os.Bundle
// import android.text.Editable
// import android.text.TextWatcher
// import android.view.View
// import android.widget.AdapterView
// import android.widget.ArrayAdapter
// import android.widget.Toast
// import androidx.appcompat.app.AlertDialog
// import com.example.project_artemis.databinding.ActivityConcernBinding
// import okhttp3.*
// import okhttp3.MediaType.Companion.toMediaType
// import okhttp3.RequestBody.Companion.toRequestBody
// import okhttp3.logging.HttpLoggingInterceptor
// import org.json.JSONObject
// import java.io.IOException

// class ConcernActivity : AppCompatActivity() {

//     private var buildingName: String? = null
//     private var campusName: String? = null
//     private lateinit var binding: ActivityConcernBinding

//     override fun onCreate(savedInstanceState: Bundle?) {
//         super.onCreate(savedInstanceState)

//         binding = ActivityConcernBinding.inflate(layoutInflater)
//         setContentView(binding.root)

//         binding.backConcern.setOnClickListener {
//             onBackPressed()
//         }

//         val itemsLocation = listOf(
//             "Batangas State University - Alangilan",
//             "Batangas State University - Pablo Borbon",
//             "Batangas State University - Malvar"
//         )

//         val adapterLocation = ArrayAdapter(
//             this,
//             R.layout.spinner_selected_item2,
//             itemsLocation
//         ).apply {
//             setDropDownViewResource(R.layout.style_spinner)
//         }

//         binding.locationPickerInput.adapter = adapterLocation

//         val buildingSelectorAlangilan = listOf(
//             "CEAFA Building",
//             "CIT Building",
//             "CICS Building",
//             "RGR Building",
//             "Gymnasium",
//             "STEER Hub",
//             "Student Services Center"
//         )

//         val buildingSelectorMain = listOf(
//             "Audio Visual Building",
//             "CALABARZON Integrated Research & Training Center",
//             "College of Nursing",
//             "College of Teacher Education",
//             "CIT Building",
//             "COE Building",
//             "General Engineering Building",
//             "Gymnasium",
//             "Gymnasium 2",
//             "Higher Education Building",
//             "Sewage Treatment Plant",
//             "Student Services Center",
//             "Student Services Center II",
//             "University Wellness Center"
//         )

//         val buildingSelectorMalvar = listOf(
//             "CIT Building",
//             "CICS Building",
//             "COE Building",
//             "Gymnasium",
//             "Student Services Center"
//         )

//         val adapterBuilding = ArrayAdapter(
//             this,
//             R.layout.spinner_selected_item2,
//             buildingSelectorAlangilan
//         ).apply {
//             setDropDownViewResource(R.layout.style_spinner)
//         }

//         binding.buildingPickerInput.adapter = adapterBuilding

//         binding.locationPickerInput.onItemSelectedListener =
//             object : AdapterView.OnItemSelectedListener {
//                 override fun onItemSelected(
//                     parent: AdapterView<*>?,
//                     view: View?,
//                     position: Int,
//                     id: Long
//                 ) {
//                     val selectedLoc = parent?.getItemAtPosition(position).toString()

//                     binding.buildingPickerInput.adapter = when (selectedLoc) {
//                         "Batangas State University - Alangilan" -> {
//                             ArrayAdapter(
//                                 this@ConcernActivity,
//                                 R.layout.spinner_selected_item2,
//                                 buildingSelectorAlangilan
//                             )
//                         }
//                         "Batangas State University - Pablo Borbon" -> {
//                             ArrayAdapter(
//                                 this@ConcernActivity,
//                                 R.layout.spinner_selected_item2,
//                                 buildingSelectorMain
//                             )
//                         }
//                         "Batangas State University - Malvar" -> {
//                             ArrayAdapter(
//                                 this@ConcernActivity,
//                                 R.layout.spinner_selected_item2,
//                                 buildingSelectorMalvar
//                             )
//                         }
//                         else -> null
//                     }

//                     campusName = when (selectedLoc) {
//                         "Batangas State University - Alangilan" -> "Alangilan"
//                         "Batangas State University - Pablo Borbon" -> "Pablo Borbon"
//                         "Batangas State University - Malvar" -> "Malvar"
//                         else -> null
//                     }
//                 }

//                 override fun onNothingSelected(parent: AdapterView<*>?) {
//                     // No implementation needed
//                 }
//             }

//         val itemsConcern = listOf(
//             "Trash Bin Full",
//             "Damaged Trash Bin",
//             "Odor Complaint Regarding Trash Bin",
//             "Problems with the App"
//         )

//         val adapterConcern = ArrayAdapter(
//             this,
//             R.layout.spinner_selected_item2,
//             itemsConcern
//         ).apply {
//             setDropDownViewResource(R.layout.style_spinner)
//         }

//         binding.concernTypeInput.adapter = adapterConcern

//         val textWatcher = object : TextWatcher {
//             override fun afterTextChanged(s: Editable?) {
//                 val amount = binding.descriptionConcernEditText.text.toString().trim()
//                 val allFieldsFilled = amount.isNotEmpty()
//                 binding.submitButton.isEnabled = allFieldsFilled
//             }

//             override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//                 // No implementation needed
//             }

//             override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                 // No implementation needed
//             }
//         }

//         binding.descriptionConcernEditText.addTextChangedListener(textWatcher)

//         binding.submitButton.setOnClickListener {
//             val description = binding.descriptionConcernEditText.text.toString().trim()

//             if (description.isEmpty()) {
//                 showErrorMessage("Please enter the required data")
//                 return@setOnClickListener
//             }

//             binding.progressBar2.visibility = View.VISIBLE

//             val url = "https://us-central1-artemis-b18ae.cloudfunctions.net/server/reports"

//             val requestBody = createJsonBody(description)

//             val request = Request.Builder()
//                 .url(url)
//                 .post(requestBody)
//                 .addHeader("Content-Type", "application/json")
//                 .build()

//             OkHttpClient.Builder()
//                 .addInterceptor(HttpLoggingInterceptor().apply {
//                     level = HttpLoggingInterceptor.Level.BODY
//                 })
//                 .build()
//                 .newCall(request)
//                 .enqueue(object : Callback {
//                     override fun onFailure(call: Call, e: IOException) {
//                         runOnUiThread {
//                             showErrorMessage("An error occurred: ${e.message}")
//                         }
//                     }

//                     override fun onResponse(call: Call, response: Response) {
//                         val responseBody = response.body?.string()
//                         if (response.isSuccessful && responseBody != null) {
//                             runOnUiThread {
//                                 clearInputFields()
//                                 binding.progressBar2.visibility = View.GONE
//                                 Toast.makeText(
//                                     this@ConcernActivity,
//                                     "Your concern has been sent",
//                                     Toast.LENGTH_SHORT
//                                 ).show()
//                             }
//                         } else {
//                             runOnUiThread {
//                                 binding.progressBar2.visibility = View.GONE
//                                 Toast.makeText(
//                                     this@ConcernActivity,
//                                     "Sending concern failed",
//                                     Toast.LENGTH_SHORT
//                                 ).show()
//                             }
//                         }
//                     }
//                 })
//         }
//     }

//     private fun createJsonBody(description: String): RequestBody {
//         val building = buildingName ?: ""
//         val campus = campusName ?: ""

//         val jsonBody = JSONObject().apply {
//             put("title", binding.concernTypeInput.selectedItem)
//             put("campus", campus)
//             put("building", building)
//             put("description", description)
//         }

//         val mediaType = "application/json; charset=utf-8".toMediaType()
//         return jsonBody.toString().toRequestBody(mediaType)
//     }

//     private fun showErrorMessage(message: String) {
//         val builder = AlertDialog.Builder(this)
//         builder.setTitle("Error")
//         builder.setMessage(message)
//         builder.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
//         val dialog = builder.create()
//         dialog.show()
//     }

//     private fun clearInputFields() {
//         binding.descriptionConcernEditText.setText("")
//     }

//     override fun onBackPressed() {
//         finish()
//         onSaveInstanceState(Bundle())
//         super.onBackPressed()
//     }
// }
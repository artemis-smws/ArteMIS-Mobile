package com.example.project_artemis

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import android.widget.*
import android.text.Editable
import android.text.TextWatcher
import com.example.project_artemis.databinding.FragmentAddBinding
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import kotlinx.coroutines.DelicateCoroutinesApi
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONException
import okhttp3.*
import java.io.IOException
import okhttp3.logging.HttpLoggingInterceptor

@Suppress("NAME_SHADOWING")
class AddFragment : Fragment() {

    private val waste = BuildConfig.waste
    private val wasteLatest = BuildConfig.waste_latest
    private var buildingName: String? = null
    private var campusName: String? = null
    private var selectedName: String? = null
    // private var todayId: String? = null
    private var residualweight: Double? = null
    private var infectiousweight: Double? = null
    private var recyclableweight: Double? = null
    private var biodegradableweight: Double? = null
    private var totalweight: Double? = null
    private var recyclablebottle: Double? = null
    private var recyclablecardboard: Double? = null
    private var recyclablepaper: Double? = null

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAddBinding.inflate(inflater, container, false)

        val itemsLocation = listOf(
            "Batangas State University - Alangilan",
            "Batangas State University - Pablo Borbon",
            "Batangas State University - Malvar"
        )

        val adapterLocation = ArrayAdapter(
            requireContext(),
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
                        requireContext(),
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
                                requireContext(),
                                R.layout.spinner_selected_item2,
                                buildingSelectorAlangilan
                            )
                        }
                        "Batangas State University - Pablo Borbon" -> {
                            binding.buildingPickerInput.adapter = ArrayAdapter(
                                requireContext(),
                                R.layout.spinner_selected_item2,
                                buildingSelectorMain
                            )
                        }
                        "Batangas State University - Malvar" -> {
                            binding.buildingPickerInput.adapter = ArrayAdapter(
                                requireContext(),
                                R.layout.spinner_selected_item2,
                                buildingSelectorMalvar
                            )
                        }
                    }

                    val wasteType = listOf(
                        "Residual Waste",
                        "Recyclable Waste",
                        "Biodegradable Waste",
                        "Infectious Waste"
                    )

                    val adapterWaste = ArrayAdapter(
                        requireContext(),
                        R.layout.spinner_selected_item2,
                        wasteType
                    ).apply {
                        setDropDownViewResource(R.layout.style_spinner)
                    }

                    binding.wastePickerInput.adapter = adapterWaste

                    binding.wastePickerInput.onItemSelectedListener =
                        object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View?,
                                position: Int,
                                id: Long
                            ) {
                                val recyclableName = listOf(
                                    "bottle",
                                    "paper",
                                    "cardboard"
                                )
                                val adapterName = ArrayAdapter(
                                    requireContext(),
                                    R.layout.spinner_selected_item2,
                                    recyclableName
                                ).apply {
                                    setDropDownViewResource(R.layout.style_spinner)
                                }

                                binding.namePickerInput.adapter = adapterName

                                binding.namePickerInput.onItemSelectedListener =
                                    object : AdapterView.OnItemSelectedListener {
                                        override fun onItemSelected(
                                            parent: AdapterView<*>?,
                                            view: View?,
                                            position: Int,
                                            id: Long
                                        ) {
                                            selectedName = parent?.getItemAtPosition(position).toString()
                                        }
                                        override fun onNothingSelected(parent: AdapterView<*>?) {
                                            // do nothing
                                        }
                                    }

                                val selectedItem = parent?.getItemAtPosition(position).toString()
                                when (selectedItem) {
                                    "Residual Waste" -> {

                                        binding.wasteQuantity.visibility = View.GONE
                                        binding.nameOfWaste.visibility = View.GONE
                                        binding.amountOfWaste.visibility = View.VISIBLE

                                        binding.inputButton.isEnabled = false // Disable the button initially

                                        // Add a TextWatcher to monitor changes in the EditText fields
                                        val textWatcher = object : TextWatcher {
                                            override fun afterTextChanged(s: Editable?) {
                                                // Check if all EditText fields have non-empty values
                                                val amount = binding.amountEditText.text.toString().trim()

                                                val allFieldsFilled = amount.isNotEmpty()

                                                // Enable/disable the button based on the result of the check
                                                binding.inputButton.isEnabled = allFieldsFilled
                                            }

                                            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                                                // No implementation needed
                                            }

                                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                                // No implementation needed
                                            }
                                        }

                                        binding.amountEditText.addTextChangedListener(textWatcher)

                                        binding.inputButton.setOnClickListener {

                                            binding.progressBar2.visibility = View.VISIBLE
                                            binding.overlay.visibility = View.VISIBLE
                                            binding.overlay.setOnTouchListener { _, _ -> true}

                                            val getclient = OkHttpClient()
                                            val getData = Request.Builder()
                                                .url(wasteLatest)
                                                .build()
                                        
                                            getclient.newCall(getData).enqueue(object : Callback {
                                                override fun onFailure(call: Call, e: IOException) {
                                                    requireActivity().runOnUiThread {
                                                        showErrorMessage("Please check your Internet Connection")
                                                    }
                                                }

                                                override fun onResponse(call: Call, response: Response) {
                                                    val responseString = response.body?.string()
                                                    val jsonArray = JSONArray(responseString)
                                                    val jsonObject = jsonArray.getJSONObject(0)
                                                    val todayId = jsonObject.getString("id")

                                                    if (jsonArray.length() > 0) {
                                                        val jsonObject = jsonArray.getJSONObject(0)
                                                        val buildingObject = jsonObject.optJSONObject(buildingName)

                                                        if (buildingObject != null) {
                                                            val weightObject = buildingObject.optJSONObject("weight")
                                                            residualweight = weightObject?.optDouble("residual")
                                                            infectiousweight = weightObject?.optDouble("infectious")

                                                            val recyclableObject = weightObject?.optJSONObject("recyclable")
                                                            recyclableweight = recyclableObject?.optDouble("total")
                                                            recyclablecardboard = recyclableObject?.optDouble("cardboard") ?: 0.0
                                                            recyclablebottle = recyclableObject?.optDouble("bottle") ?: 0.0
                                                            recyclablepaper = recyclableObject?.optDouble("paper") ?: 0.0

                                                            biodegradableweight = weightObject?.optDouble("biodegradable_waste")
                                                            totalweight = weightObject?.optDouble("total")
                                                        }
                                                    }
                                                    val building = buildingName
                                                    val campus = campusName
                                                    
                                                    if (recyclablecardboard == null || recyclablecardboard!!.isNaN()) {
                                                        recyclablecardboard = 0.0
                                                    }
                                                    if (recyclablepaper == null || recyclablepaper!!.isNaN()) {
                                                        recyclablepaper = 0.0
                                                    }
                                                    if (recyclablebottle == null || recyclablebottle!!.isNaN()) {
                                                        recyclablebottle = 0.0
                                                    }

                                                    val residual = residualweight ?: 0.0
                                                    val infectious = infectiousweight ?: 0.0
                                                    val recyclable = recyclableweight ?: 0.0
                                                    val cardboard = recyclablecardboard ?: 0.0
                                                    val bottle = recyclablebottle ?: 0.0
                                                    val paper = recyclablepaper ?: 0.0
                                                    val biodegradablewaste = biodegradableweight ?: 0.0
                                                    val totalWeight = totalweight ?: 0.0
                                                    val weight = binding.amountEditText.text.toString().trim().toDouble()

                                                    // Check if any EditText field is empty before proceeding
                                                    if (weight == 0.0) {
                                                        requireActivity().runOnUiThread {
                                                            showErrorMessage("Please enter the required data")
                                                        }
                                                    }

                                                    val interceptor = HttpLoggingInterceptor()
                                                    interceptor.level = HttpLoggingInterceptor.Level.BODY
                                        
                                                    val client = OkHttpClient.Builder()
                                                        .addInterceptor(interceptor)
                                                        .build()
                                        
                                                    val jsonBody = JSONObject().apply {
                                                        put("$building", JSONObject().apply {
                                                            put("weight", JSONObject().apply {
                                                                put("residual", residual + weight)
                                                                put("infectious", infectious)
                                                                put("recyclable", JSONObject().apply {
                                                                    put("total", recyclable)
                                                                    put("cardboard", cardboard)
                                                                    put("bottle", bottle)
                                                                    put("paper", paper)
                                                                })
                                                                put("biodegradable_waste", biodegradablewaste)
                                                                put("total", totalWeight + weight)
                                                            })
                                                            put("campus", campus)
                                                        })
                                                    }
                                        
                                                    val requestBody =
                                                        jsonBody.toString().toRequestBody("application/json; charset=utf-8".toMediaType())
                                                    val request = Request.Builder()
                                                        .url("$waste/$todayId")
                                                        .put(requestBody)
                                                        .addHeader("Content-Type", "application/json")
                                                        .build()
                                        
                                                    // Send the request and handle the response
                                                    client.newCall(request).enqueue(object : Callback {
                                                        override fun onFailure(call: Call, e: IOException) {
                                                            // Handle the failure
                                                            Toast.makeText(
                                                                requireContext(),
                                                                "An error occurred: ${e.message}",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        }
                                        
                                                        override fun onResponse(call: Call, response: Response) {
                                                            val responseBody = response.body?.string()
                                                            if (response.isSuccessful && responseBody != null) {
                                                                requireActivity().runOnUiThread {
                                                                    clearInputFields()
                                                                    binding.progressBar2.visibility = View.GONE
                                                                    binding.overlay.visibility = View.GONE
                                                                    Toast.makeText(
                                                                        requireContext(),
                                                                        "Input Successful: ${response.code}",
                                                                        Toast.LENGTH_SHORT
                                                                    ).show()
                                                                }
                                                            } else {
                                                                requireActivity().runOnUiThread {
                                                                    binding.progressBar2.visibility = View.GONE
                                                                    binding.overlay.visibility = View.GONE
                                                                    Toast.makeText(
                                                                        requireContext(),
                                                                        "Input Failed: ${response.code}",
                                                                        Toast.LENGTH_SHORT
                                                                    ).show()
                                                                }
                                                            }
                                                        }
                                                    })
                                        
                                                }
                                            })
                                        
                                        }
                                    }
                                    "Recyclable Waste" -> {

                                        binding.wasteQuantity.visibility = View.GONE
                                        binding.nameOfWaste.visibility = View.VISIBLE
                                        binding.amountOfWaste.visibility = View.VISIBLE

                                        binding.inputButton.isEnabled = false // Disable the button initially

                                        // Add a TextWatcher to monitor changes in the EditText fields
                                        val textWatcher = object : TextWatcher {
                                            override fun afterTextChanged(s: Editable?) {
                                                // Check if all EditText fields have non-empty values
                                                val amount = binding.amountEditText.text.toString().trim()
                                                // name.isNotEmpty() &&
                                                val allFieldsFilled = amount.isNotEmpty()

                                                // Enable/disable the button based on the result of the check
                                                binding.inputButton.isEnabled = allFieldsFilled
                                            }

                                            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                                                // No implementation needed
                                            }

                                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                                // No implementation needed
                                            }
                                        }

                                        binding.amountEditText.addTextChangedListener(textWatcher)

                                        binding.inputButton.setOnClickListener {

                                            binding.progressBar2.visibility = View.VISIBLE
                                            binding.overlay.visibility = View.VISIBLE
                                            binding.overlay.setOnTouchListener { _, _ -> true}

                                            val getclient = OkHttpClient()
                                            val getData = Request.Builder()
                                                .url(wasteLatest)
                                                .build()
                                        
                                            getclient.newCall(getData).enqueue(object : Callback {
                                                override fun onFailure(call: Call, e: IOException) {
                                                    requireActivity().runOnUiThread {
                                                        showErrorMessage("Please check your Internet Connection")
                                                    }
                                                }

                                                override fun onResponse(call: Call, response: Response) {
                                                    val responseString = response.body?.string()
                                                    val jsonArray = JSONArray(responseString)
                                                    val jsonObject = jsonArray.getJSONObject(0)
                                                    val todayId = jsonObject.getString("id")

                                                    if (jsonArray.length() > 0) {
                                                        val jsonObject = jsonArray.getJSONObject(0)
                                                        val buildingObject = jsonObject.optJSONObject(buildingName)

                                                        if (buildingObject != null) {
                                                            val weightObject = buildingObject.optJSONObject("weight")
                                                            residualweight = weightObject?.optDouble("residual")
                                                            infectiousweight = weightObject?.optDouble("infectious")

                                                            val recyclableObject = weightObject?.optJSONObject("recyclable")
                                                            recyclableweight = recyclableObject?.optDouble("total")
                                                            recyclablecardboard = recyclableObject?.optDouble("cardboard") ?: 0.0
                                                            recyclablebottle = recyclableObject?.optDouble("bottle") ?: 0.0
                                                            recyclablepaper = recyclableObject?.optDouble("paper") ?: 0.0

                                                            biodegradableweight = weightObject?.optDouble("biodegradable_waste")
                                                            totalweight = weightObject?.optDouble("total")
                                                        }
                                                    }
                                                    val building = buildingName
                                                    val campus = campusName
                                                    val name = selectedName

                                                    if (recyclablecardboard == null || recyclablecardboard!!.isNaN()) {
                                                        recyclablecardboard = 0.0
                                                    }
                                                    if (recyclablepaper == null || recyclablepaper!!.isNaN()) {
                                                        recyclablepaper = 0.0
                                                    }
                                                    if (recyclablebottle == null || recyclablebottle!!.isNaN()) {
                                                        recyclablebottle = 0.0
                                                    }

                                                    val residual = residualweight ?: 0.0
                                                    val infectious = infectiousweight ?: 0.0
                                                    val recyclable = recyclableweight ?: 0.0
                                                    val cardboard = recyclablecardboard ?: 0.0
                                                    val bottle = recyclablebottle ?: 0.0
                                                    val paper = recyclablepaper ?: 0.0
                                                    val biodegradablewaste = biodegradableweight ?: 0.0
                                                    val totalWeight = totalweight ?: 0.0
                                                    val weight = binding.amountEditText.text.toString().trim().toDouble()
                                        
                                                    // Check if any EditText field is empty before proceeding
                                                    if (weight == 0.0) {
                                                        requireActivity().runOnUiThread {
                                                            showErrorMessage("Please enter the required data")
                                                        }
                                                    }
                                        
                                                    val interceptor = HttpLoggingInterceptor()
                                                    interceptor.level = HttpLoggingInterceptor.Level.BODY
                                        
                                                    val client = OkHttpClient.Builder()
                                                        .addInterceptor(interceptor)
                                                        .build()
                                        
                                                    val jsonBody = JSONObject().apply {
                                                        put("$building", JSONObject().apply {
                                                            put("weight", JSONObject().apply {
                                                                put("residual", residual)
                                                                put("infectious", infectious)
                                                                put("recyclable", JSONObject().apply {
                                                                    when (name) {
                                                                        "cardboard" -> {
                                                                            put("total", recyclable + weight)
                                                                            put("cardboard", cardboard!! + weight)
                                                                            put("bottle", bottle)
                                                                            put("paper", paper)
                                                                        }
                                                                        "bottle" -> {
                                                                            put("total", recyclable + weight)
                                                                            put("cardboard", cardboard)
                                                                            put("bottle", bottle!! + weight)
                                                                            put("paper", paper)
                                                                        }
                                                                        "paper" -> {
                                                                            put("total", recyclable + weight)
                                                                            put("cardboard", cardboard)
                                                                            put("bottle", bottle)
                                                                            put("paper", paper!! + weight)
                                                                        }
                                                                    }
                                                                })
                                                                put("biodegradablewaste", biodegradablewaste)
                                                                put("total", totalWeight + weight)
                                                            })
                                                            put("campus", campus)
                                                        })
                                                    }
                                        
                                                    val requestBody =
                                                        jsonBody.toString().toRequestBody("application/json; charset=utf-8".toMediaType())
                                                    val request = Request.Builder()
                                                        .url("$waste/$todayId")
                                                        .put(requestBody)
                                                        .addHeader("Content-Type", "application/json")
                                                        .build()
                                        
                                                    // Send the request and handle the response
                                                    client.newCall(request).enqueue(object : Callback {
                                                        override fun onFailure(call: Call, e: IOException) {
                                                            // Handle the failure
                                                            Toast.makeText(
                                                                requireContext(),
                                                                "An error occurred: ${e.message}",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        }
                                        
                                                        override fun onResponse(call: Call, response: Response) {
                                                            val responseBody = response.body?.string()
                                                            if (response.isSuccessful && responseBody != null) {
                                                                requireActivity().runOnUiThread {
                                                                    clearInputFields()
                                                                    binding.progressBar2.visibility = View.GONE
                                                                    binding.overlay.visibility = View.GONE
                                                                    Toast.makeText(
                                                                        requireContext(),
                                                                        "Input Successful: ${response.code}",
                                                                        Toast.LENGTH_SHORT
                                                                    ).show()
                                                                }
                                                            } else {
                                                                requireActivity().runOnUiThread {
                                                                    binding.progressBar2.visibility = View.GONE
                                                                    binding.overlay.visibility = View.GONE
                                                                    Toast.makeText(
                                                                        requireContext(),
                                                                        "Input Failed: ${response.code}",
                                                                        Toast.LENGTH_SHORT
                                                                    ).show()
                                                                }
                                                            }
                                                        }
                                                    })
                                        
                                                }
                                            })
                                        
                                        }

                                    }
                                    "Biodegradable Waste" -> {

                                        binding.wasteQuantity.visibility = View.GONE
                                        binding.nameOfWaste.visibility = View.GONE
                                        binding.amountOfWaste.visibility = View.VISIBLE

                                        binding.inputButton.isEnabled = false // Disable the button initially

                                        // Add a TextWatcher to monitor changes in the EditText fields
                                        val textWatcher = object : TextWatcher {
                                            override fun afterTextChanged(s: Editable?) {
                                                // Check if all EditText fields have non-empty values
                                                val amount = binding.amountEditText.text.toString().trim()

                                                val allFieldsFilled = amount.isNotEmpty()

                                                // Enable/disable the button based on the result of the check
                                                binding.inputButton.isEnabled = allFieldsFilled
                                            }

                                            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                                                // No implementation needed
                                            }

                                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                                // No implementation needed
                                            }
                                        }

                                        binding.amountEditText.addTextChangedListener(textWatcher)


                                        binding.inputButton.setOnClickListener {

                                            binding.progressBar2.visibility = View.VISIBLE
                                            binding.overlay.visibility = View.VISIBLE
                                            binding.overlay.setOnTouchListener { _, _ -> true}

                                            val getclient = OkHttpClient()
                                            val getData = Request.Builder()
                                                .url(wasteLatest)
                                                .build()
                                        
                                            getclient.newCall(getData).enqueue(object : Callback {
                                                override fun onFailure(call: Call, e: IOException) {
                                                    requireActivity().runOnUiThread {
                                                        showErrorMessage("Please check your Internet Connection")
                                                    }
                                                }

                                                override fun onResponse(call: Call, response: Response) {
                                                    val responseString = response.body?.string()
                                                    val jsonArray = JSONArray(responseString)
                                                    val jsonObject = jsonArray.getJSONObject(0)
                                                    val todayId = jsonObject.getString("id")

                                                    if (jsonArray.length() > 0) {
                                                        val jsonObject = jsonArray.getJSONObject(0)
                                                        val buildingObject = jsonObject.optJSONObject(buildingName)

                                                        if (buildingObject != null) {
                                                            val weightObject = buildingObject.optJSONObject("weight")
                                                            residualweight = weightObject?.optDouble("residual")
                                                            infectiousweight = weightObject?.optDouble("infectious")

                                                            val recyclableObject = weightObject?.optJSONObject("recyclable")
                                                            recyclableweight = recyclableObject?.optDouble("total")
                                                            recyclablecardboard = recyclableObject?.optDouble("cardboard") ?: 0.0
                                                            recyclablebottle = recyclableObject?.optDouble("bottle") ?: 0.0
                                                            recyclablepaper = recyclableObject?.optDouble("paper") ?: 0.0

                                                            biodegradableweight = weightObject?.optDouble("biodegradable_waste")
                                                            totalweight = weightObject?.optDouble("total")
                                                        }
                                                    }
                                                    val building = buildingName
                                                    val campus = campusName

                                                    if (recyclablecardboard == null || recyclablecardboard!!.isNaN()) {
                                                        recyclablecardboard = 0.0
                                                    }
                                                    if (recyclablepaper == null || recyclablepaper!!.isNaN()) {
                                                        recyclablepaper = 0.0
                                                    }
                                                    if (recyclablebottle == null || recyclablebottle!!.isNaN()) {
                                                        recyclablebottle = 0.0
                                                    }

                                                    val residual = residualweight ?: 0.0
                                                    val infectious = infectiousweight ?: 0.0
                                                    val recyclable = recyclableweight ?: 0.0
                                                    val cardboard = recyclablecardboard ?: 0.0
                                                    val bottle = recyclablebottle ?: 0.0
                                                    val paper = recyclablepaper ?: 0.0
                                                    val biodegradablewaste = biodegradableweight ?: 0.0
                                                    val totalWeight = totalweight ?: 0.0
                                                    val weight = binding.amountEditText.text.toString().trim().toDouble()

                                                    // Check if any EditText field is empty before proceeding
                                                    if (weight == 0.0) {
                                                        requireActivity().runOnUiThread {
                                                            showErrorMessage("Please enter the required data")
                                                        }
                                                    }
                                        
                                                    val interceptor = HttpLoggingInterceptor()
                                                    interceptor.level = HttpLoggingInterceptor.Level.BODY
                                        
                                                    val client = OkHttpClient.Builder()
                                                        .addInterceptor(interceptor)
                                                        .build()

                                                    val jsonBody = JSONObject().apply {
                                                        put("$building", JSONObject().apply {
                                                            put("weight", JSONObject().apply {
                                                                put("residual", residual)
                                                                put("infectious", infectious)
                                                                put("recyclable", JSONObject().apply {
                                                                    put("total", recyclable)
                                                                    put("cardboard", cardboard)
                                                                    put("bottle", bottle)
                                                                    put("paper", paper)
                                                                })
                                                                put("biodegradable_waste", biodegradablewaste + weight)
                                                                put("total", totalWeight + weight)
                                                            })
                                                            put("campus", campus)
                                                        })
                                                    }
                                        
                                                    val requestBody =
                                                        jsonBody.toString().toRequestBody("application/json; charset=utf-8".toMediaType())
                                                    val request = Request.Builder()
                                                        .url("$waste/$todayId")
                                                        .put(requestBody)
                                                        .addHeader("Content-Type", "application/json")
                                                        .build()
                                        
                                                    // Send the request and handle the response
                                                    client.newCall(request).enqueue(object : Callback {
                                                        override fun onFailure(call: Call, e: IOException) {
                                                            // Handle the failure
                                                            Toast.makeText(
                                                                requireContext(),
                                                                "An error occurred: ${e.message}",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        }
                                        
                                                        override fun onResponse(call: Call, response: Response) {
                                                            val responseBody = response.body?.string()
                                                            if (response.isSuccessful && responseBody != null) {
                                                                requireActivity().runOnUiThread {
                                                                    clearInputFields()
                                                                    binding.progressBar2.visibility = View.GONE
                                                                    binding.overlay.visibility = View.GONE
                                                                    Toast.makeText(
                                                                        requireContext(),
                                                                        "Input Successful: ${response.code}",
                                                                        Toast.LENGTH_SHORT
                                                                    ).show()
                                                                }
                                                            } else {
                                                                requireActivity().runOnUiThread {
                                                                    binding.progressBar2.visibility = View.GONE
                                                                    binding.overlay.visibility = View.GONE
                                                                    Toast.makeText(
                                                                        requireContext(),
                                                                        "Input Failed: ${response.code}",
                                                                        Toast.LENGTH_SHORT
                                                                    ).show()
                                                                }
                                                            }
                                                        }
                                                    })
                                        
                                                }
                                            })
                                        
                                        }
                                        
                                    }
                                    "Infectious Waste" -> {

                                        binding.wasteQuantity.visibility = View.GONE
                                        binding.nameOfWaste.visibility = View.GONE
                                        binding.amountOfWaste.visibility = View.VISIBLE

                                        binding.inputButton.isEnabled = false // Disable the button initially

                                        // Add a TextWatcher to monitor changes in the EditText fields
                                        val textWatcher = object : TextWatcher {
                                            override fun afterTextChanged(s: Editable?) {
                                                // Check if all EditText fields have non-empty values
                                                val amount = binding.amountEditText.text.toString().trim()

                                                val allFieldsFilled = amount.isNotEmpty()

                                                // Enable/disable the button based on the result of the check
                                                binding.inputButton.isEnabled = allFieldsFilled
                                            }

                                            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                                                // No implementation needed
                                            }

                                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                                // No implementation needed
                                            }
                                        }

                                        binding.amountEditText.addTextChangedListener(textWatcher)

                                        binding.inputButton.setOnClickListener {

                                            binding.progressBar2.visibility = View.VISIBLE
                                            binding.overlay.visibility = View.VISIBLE
                                            binding.overlay.setOnTouchListener { _, _ -> true}

                                            val getclient = OkHttpClient()
                                            val getData = Request.Builder()
                                                .url(wasteLatest)
                                                .build()

                                            getclient.newCall(getData).enqueue(object : Callback {
                                                override fun onFailure(call: Call, e: IOException) {
                                                    requireActivity().runOnUiThread {
                                                        showErrorMessage("Please check your Internet Connection")
                                                    }
                                                }

                                                override fun onResponse(call: Call, response: Response) {
                                                    val responseString = response.body?.string()
                                                    val jsonArray = JSONArray(responseString)
                                                    val jsonObject = jsonArray.getJSONObject(0)
                                                    val todayId = jsonObject.getString("id")

                                                    if (jsonArray.length() > 0) {
                                                        val jsonObject = jsonArray.getJSONObject(0)
                                                        val buildingObject = jsonObject.optJSONObject(buildingName)

                                                        if (buildingObject != null) {
                                                            val weightObject = buildingObject.optJSONObject("weight")
                                                            residualweight = weightObject?.optDouble("residual")
                                                            infectiousweight = weightObject?.optDouble("infectious")

                                                            val recyclableObject = weightObject?.optJSONObject("recyclable")
                                                            recyclableweight = recyclableObject?.optDouble("total")
                                                            recyclablecardboard = recyclableObject?.optDouble("cardboard") ?: 0.0
                                                            recyclablebottle = recyclableObject?.optDouble("bottle") ?: 0.0
                                                            recyclablepaper = recyclableObject?.optDouble("paper") ?: 0.0

                                                            biodegradableweight = weightObject?.optDouble("biodegradable_waste")
                                                            totalweight = weightObject?.optDouble("total")
                                                        }
                                                    }
                                                    val building = buildingName
                                                    val campus = campusName

                                                    if (recyclablecardboard == null || recyclablecardboard!!.isNaN()) {
                                                        recyclablecardboard = 0.0
                                                    }
                                                    if (recyclablepaper == null || recyclablepaper!!.isNaN()) {
                                                        recyclablepaper = 0.0
                                                    }
                                                    if (recyclablebottle == null || recyclablebottle!!.isNaN()) {
                                                        recyclablebottle = 0.0
                                                    }

                                                    val residual = residualweight ?: 0.0
                                                    val infectious = infectiousweight ?: 0.0
                                                    val recyclable = recyclableweight ?: 0.0
                                                    val cardboard = recyclablecardboard ?: 0.0
                                                    val bottle = recyclablebottle ?: 0.0
                                                    val paper = recyclablepaper ?: 0.0
                                                    val biodegradablewaste = biodegradableweight ?: 0.0
                                                    val totalWeight = totalweight ?: 0.0
                                                    val weight = binding.amountEditText.text.toString().trim().toDouble()

                                                    // Check if any EditText field is empty before proceeding
                                                    if (weight == 0.0) {
                                                        requireActivity().runOnUiThread {
                                                            showErrorMessage("Please enter the required data")
                                                        }
                                                    }

                                                    val interceptor = HttpLoggingInterceptor()
                                                    interceptor.level = HttpLoggingInterceptor.Level.BODY

                                                    val client = OkHttpClient.Builder()
                                                        .addInterceptor(interceptor)
                                                        .build()

                                                    val jsonBody = JSONObject().apply {
                                                        put("$building", JSONObject().apply {
                                                            put("weight", JSONObject().apply {
                                                                put("residual", residual)
                                                                put("infectious", infectious + weight)
                                                                put("recyclable", JSONObject().apply {
                                                                    put("total", recyclable)
                                                                    put("cardboard", cardboard)
                                                                    put("bottle", bottle)
                                                                    put("paper", paper)
                                                                })
                                                                put("biodegradable_waste", biodegradablewaste)
                                                                put("total", totalWeight + weight)
                                                            })
                                                            put("campus", campus)
                                                        })
                                                    }

                                                    val requestBody =
                                                        jsonBody.toString().toRequestBody("application/json; charset=utf-8".toMediaType())
                                                    val request = Request.Builder()
                                                        .url("$waste/$todayId")
                                                        .put(requestBody)
                                                        .addHeader("Content-Type", "application/json")
                                                        .build()

                                                    // Send the request and handle the response
                                                    client.newCall(request).enqueue(object : Callback {
                                                        override fun onFailure(call: Call, e: IOException) {
                                                            // Handle the failure
                                                            Toast.makeText(
                                                                requireContext(),
                                                                "An error occurred: ${e.message}",
                                                                Toast.LENGTH_SHORT
                                                            ).show()
                                                        }

                                                        override fun onResponse(call: Call, response: Response) {
                                                            val responseBody = response.body?.string()
                                                            if (response.isSuccessful && responseBody != null) {
                                                                requireActivity().runOnUiThread {
                                                                    clearInputFields()
                                                                    binding.progressBar2.visibility = View.GONE
                                                                    binding.overlay.visibility = View.GONE
                                                                    Toast.makeText(
                                                                        requireContext(),
                                                                        "Input Successful: ${response.code}",
                                                                        Toast.LENGTH_SHORT
                                                                    ).show()
                                                                }
                                                            } else {
                                                                requireActivity().runOnUiThread {
                                                                    binding.progressBar2.visibility = View.GONE
                                                                    binding.overlay.visibility = View.GONE
                                                                    Toast.makeText(
                                                                        requireContext(),
                                                                        "Input Failed: ${response.code}",
                                                                        Toast.LENGTH_SHORT
                                                                    ).show()
                                                                }
                                                            }
                                                        }
                                                    })

                                                }
                                            })

                                        }
                                    }
                                }
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                // do nothing
                            }
                        }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

                private fun showErrorMessage(message: String) {
                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Error")
                    builder.setMessage(message)
                    builder.setPositiveButton("OK") { dialog, which ->
                        dialog.dismiss()
                    }
                    val dialog = builder.create()
                    dialog.show()
                }

                private fun clearInputFields() {
                    binding.quantityEditText.setText("")
                    binding.amountEditText.setText("")
                }

            }


        return binding.root

    }

}

/*
Copyright 2023 arteMIS

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/


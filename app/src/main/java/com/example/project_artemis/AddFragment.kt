package com.example.project_artemis

import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import android.widget.*
import android.text.Editable
import android.text.TextWatcher
import com.example.project_artemis.databinding.FragmentAddBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import kotlinx.coroutines.DelicateCoroutinesApi
import okhttp3.*
import org.json.JSONArray
import java.io.IOException
import org.json.JSONObject

@Suppress("NAME_SHADOWING")
class AddFragment : Fragment() {

    private var id: String? = null

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAddBinding.inflate(inflater, container, false)

        val uid = arguments?.getString("uid")

        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://us-central1-artemis-b18ae.cloudfunctions.net/server/waste")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Toast.makeText(requireContext(), "Request unsuccessful", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call, response: Response) {
                val responseString = response.body?.string()
                val jsonArray = JSONArray(responseString)
                val jsonObject = jsonArray.getJSONObject(0)
                val id = jsonObject.getString("id")
                this@AddFragment.id = id
                if (isAdded) {
                    requireActivity().runOnUiThread {
                        Toast.makeText(requireContext(), "Retrieved id: $id", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        })

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
                        "COE Building",
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

                    when (selectedLoc) {
                        "Batangas State University - Alangilan" -> binding.buildingPickerInput.adapter = ArrayAdapter(
                            requireContext(),
                            R.layout.spinner_selected_item2,
                            buildingSelectorAlangilan
                        )
                        "Batangas State University - Pablo Borbon" -> binding.buildingPickerInput.adapter = ArrayAdapter(
                            requireContext(),
                            R.layout.spinner_selected_item2,
                            buildingSelectorMain
                        )
                        "Batangas State University - Malvar" -> binding.buildingPickerInput.adapter = ArrayAdapter(
                            requireContext(),
                            R.layout.spinner_selected_item2,
                            buildingSelectorMalvar
                        )

                    }

                    val wasteType = listOf(
                        "Hazardous Waste",
                        "Residual Waste",
                        "Recyclable Waste",
                        "Food Waste"
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
                                val selectedItem = parent?.getItemAtPosition(position).toString()
                                when (selectedItem) {
                                    "Hazardous Waste" -> {
                                        binding.wasteQuantity.visibility = View.VISIBLE
                                        binding.nameOfWaste.visibility = View.VISIBLE
                                        binding.amountOfWaste.visibility = View.VISIBLE

                                        binding.inputButton.isEnabled = false // Disable the button initially

                                        // Add a TextWatcher to monitor changes in the EditText fields
                                        val textWatcher = object : TextWatcher {
                                            override fun afterTextChanged(s: Editable?) {
                                                // Check if all EditText fields have non-empty values
                                                val name = binding.nameEditText.text.toString().trim()
                                                val quantity = binding.quantityEditText.text.toString().trim()
                                                val amount = binding.amountEditText.text.toString().trim()

                                                val allFieldsFilled = name.isNotEmpty() && quantity.isNotEmpty() && amount.isNotEmpty()

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

                                        binding.nameEditText.addTextChangedListener(textWatcher)
                                        binding.quantityEditText.addTextChangedListener(textWatcher)
                                        binding.amountEditText.addTextChangedListener(textWatcher)

                                        binding.inputButton.setOnClickListener {
                                            val wastetype = "hazardous_waste"
                                            val name = binding.nameEditText.text.toString().trim()
                                            val quantity = binding.quantityEditText.text.toString().trim().toInt()
                                            val weight = binding.amountEditText.text.toString().trim().toInt()

                                            // Check if any EditText field is empty before proceeding
                                            if (name.isEmpty() || quantity == 0 || weight == 0) {
                                                showErrorMessage("Please enter the required data")
                                                return@setOnClickListener
                                            }

                                            val itemObject = JSONObject()
                                            itemObject.put("name", name)
                                            itemObject.put("quantity", quantity)

                                            val postDataEditObject = JSONObject()
                                            postDataEditObject.put("items", JSONArray().put(itemObject))
                                            postDataEditObject.put("weight", weight)

                                            val postData = JSONObject()
                                            postData.put(wastetype, postDataEditObject)
                                            postData.put("location", selectedLoc)

                                            binding.progressBar2.visibility = View.VISIBLE
                                            updateWasteData(id, postData)
                                        }
                                    }
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

                                            val wastetype = "residual"
                                            val weight = binding.amountEditText.text.toString().trim().toInt()

                                            // Check if any EditText field is empty before proceeding
                                            if (weight == 0) {
                                                showErrorMessage("Please enter the required data")
                                                return@setOnClickListener
                                            }

                                            val itemArray = JSONArray()
                                            val itemObject = JSONObject()

                                            itemArray.put(itemObject)

                                            val postData = JSONObject()
                                            val postDataEditObject = JSONObject()

                                            postDataEditObject.put("items", itemArray)
                                            postDataEditObject.put("weight", weight)

                                            postData.put(wastetype, postDataEditObject)
                                            postData.put("location", selectedLoc)

                                            binding.progressBar2.visibility = View.VISIBLE
                                            updateWasteData(id, postData)

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
                                                val name = binding.nameEditText.text.toString().trim()
                                                val amount = binding.amountEditText.text.toString().trim()

                                                val allFieldsFilled = name.isNotEmpty() && amount.isNotEmpty()

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

                                        binding.nameEditText.addTextChangedListener(textWatcher)
                                        binding.amountEditText.addTextChangedListener(textWatcher)

                                        binding.inputButton.setOnClickListener {

                                            val wastetype = "recyclable"
                                            val name = binding.nameEditText.text.toString().trim()
                                            val weight = binding.amountEditText.text.toString().trim().toInt()

                                            // Check if any EditText field is empty before proceeding
                                            if (name.isEmpty() || weight == 0) {
                                                showErrorMessage("Please enter the required data")
                                                return@setOnClickListener
                                            }

                                            var totalWeight = 0
                                            val itemArray = JSONArray()
                                            val itemObject = JSONObject()

                                            itemObject.put("name", name)

                                            itemArray.put(itemObject)

                                            val postData = JSONObject()
                                            val postDataEditObject = JSONObject()

                                            postDataEditObject.put("items", itemArray)
                                            postDataEditObject.put("weight", weight)

                                            postData.put(wastetype, postDataEditObject)
                                            postData.put("location", selectedLoc)

                                            binding.progressBar2.visibility = View.VISIBLE
                                            updateWasteData(id, postData)

                                        }


                                    }
                                    "Food Waste" -> {
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

                                            val wastetype = "food_waste"
                                            val weight = binding.amountEditText.text.toString().trim().toInt()

                                            // Check if any EditText field is empty before proceeding
                                            if (weight == 0) {
                                                showErrorMessage("Please enter the required data")
                                                return@setOnClickListener
                                            }

                                            var totalWeight = 0
                                            val itemArray = JSONArray()
                                            val itemObject = JSONObject()

                                            itemArray.put(itemObject)

                                            val postData = JSONObject()
                                            val postDataEditObject = JSONObject()

                                            postDataEditObject.put("items", itemArray)
                                            postDataEditObject.put("weight", weight)

                                            postData.put(wastetype, postDataEditObject)
                                            postData.put("location", selectedLoc)

                                            binding.progressBar2.visibility = View.VISIBLE
                                            updateWasteData(id, postData)

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

                private fun updateWasteData(id: Long, postData: JSONObject) {
                    GlobalScope.launch(Dispatchers.IO) {
                        try {
                            val client = OkHttpClient()
                            val url = "https://us-central1-artemis-b18ae.cloudfunctions.net/server/waste/$id"
                            val mediaType = "application/json".toMediaType()
                            val request = Request.Builder()
                                .url(url)
                                .patch(postData.toString().toRequestBody(mediaType))
                                .addHeader("Content-Type", "application/json")
                                .build()
                            val response = client.newCall(request).execute()
                            if (response.isSuccessful) {
                                withContext(Dispatchers.Main) {
                                    clearInputFields()
                                    Toast.makeText(requireContext(), "Input Successful: ${response.code}", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                withContext(Dispatchers.Main) {
                                    Toast.makeText(requireContext(), "Error: ${response.code}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(requireContext(), "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        } finally {
                            withContext(Dispatchers.Main) {
                                binding.progressBar2.visibility = View.GONE
                            }
                        }
                    }
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
                    binding.nameEditText.setText("")
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




//https://us-central1-artemis-b18ae.cloudfunctions.net/server/waste/uTMqAN6LRw641OPeg4yE



//        wastePickerInput.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
//                val selectedItem = parent?.getItemAtPosition(position).toString()
//                if (selectedItem == "Hazardous Waste") {

//                }
//            }

//            override fun onNothingSelected(parent: AdapterView<*>?) {
//                TODO("Not yet implemented")
//            }
//        }

// Calculate the total weight of all items in the array
// var totalWeight = 0
// val itemArray = JSONArray()
// for (i in 0 until weight) {
//     val itemObject = JSONObject()
//     itemObject.put("weight", weight)
//     itemObject.put("name", name)
//     itemObject.put("quantity", quantity)
//     itemArray.put(itemObject)
//     totalWeight += weight
// }
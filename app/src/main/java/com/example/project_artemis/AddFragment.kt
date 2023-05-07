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
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject

class AddFragment : Fragment() {

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAddBinding.inflate(inflater, container, false)

        val uid = arguments?.getString("uid")

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

        val wasteType = listOf( //name ng string na iseset sa val wastetype
            "Hazardous Waste",  //hazwaste 
            "Residual Waste",   //residual
            "Recyclable Waste", //recyclable
            "Food Waste"        //foodwaste
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
                                val wastetype = "hazwaste"
                                val name = binding.nameEditText.text.toString().trim()
                                val quantity = binding.quantityEditText.text.toString().trim().toInt()
                                val weight = binding.amountEditText.text.toString().trim().toInt()

                                // Check if any EditText field is empty before proceeding
                                if (name.isEmpty() || quantity == 0 || weight == 0) {
                                    val builder = AlertDialog.Builder(requireContext())
                                    builder.setTitle("Error")
                                    builder.setMessage("Please enter the following data needed")
                                    builder.setPositiveButton("OK") { dialog, which ->
                                        dialog.dismiss()
                                    }
                                    val dialog = builder.create()
                                    dialog.show()
                                    return@setOnClickListener
                                }

                                // Calculate the total weight of all items in the array
                                var totalWeight = 0
                                val itemArray = JSONArray()
                                for (i in 0 until weight) {
                                    val itemObject = JSONObject()
                                    itemObject.put("weight", weight)
                                    itemObject.put("name", name)
                                    itemObject.put("quantity", quantity)
                                    itemArray.put(itemObject)
                                    totalWeight += weight
                                }

                                val postData = JSONObject()
                                val postDataEditObject = JSONObject()

                                postDataEditObject.put("items", itemArray)
                                postDataEditObject.put("total weight", totalWeight)

                                postData.put(wastetype, postDataEditObject)

                                GlobalScope.launch(Dispatchers.IO) {
                                    try {
                                        val client = OkHttpClient()
                                        val mediaType = "application/json; charset=utf-8".toMediaType()
                                        val request = Request.Builder()
                                            .url("https://us-central1-artemis-b18ae.cloudfunctions.net/server/waste")
                                            .post(postData.toString().toRequestBody(mediaType))
                                            .build()
                                        val response = client.newCall(request).execute()
                                        if (response.isSuccessful) {
                                            val responseBody = response.body?.string()
                                            withContext(Dispatchers.Main) {
                                                // Update UI or show success message
                                                // binding.typeText.text = ""
                                                binding.nameEditText.setText("")
                                                binding.quantityEditText.setText("")
                                                binding.amountEditText.setText("")
                                                Toast.makeText(requireContext(), "Input Successful: ${response.code}", Toast.LENGTH_SHORT).show()
                                            }
                                        } else {
                                            // handle the error here
                                            withContext(Dispatchers.Main) {
                                                Toast.makeText(requireContext(), "Error: ${response.code}", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    } catch (e: Exception) {
                                        withContext(Dispatchers.Main) {
                                            Toast.makeText(requireContext(), "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
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
                                    val builder = AlertDialog.Builder(requireContext())
                                    builder.setTitle("Error")
                                    builder.setMessage("Please enter the following data needed")
                                    builder.setPositiveButton("OK") { dialog, which ->
                                        dialog.dismiss()
                                    }
                                    val dialog = builder.create()
                                    dialog.show()
                                    return@setOnClickListener
                                }

                                 // Calculate the total weight of all items in the array
                                var totalWeight = 0
                                val itemArray = JSONArray()
                                for (i in 0 until weight) {
                                    val itemObject = JSONObject()
                                    itemObject.put("weight", weight)
                                    itemArray.put(itemObject)
                                    totalWeight += weight
                                }
                 
                                val postData = JSONObject()
                                val postDataEditObject = JSONObject()
                 
                                postDataEditObject.put("items", itemArray)
                                postDataEditObject.put("totalweight", totalWeight)
                 
                                postData.put(wastetype, postDataEditObject)
                 
                                GlobalScope.launch(Dispatchers.IO) {
                                    try {
                                        val client = OkHttpClient()
                                        val mediaType = "application/json; charset=utf-8".toMediaType()
                                        val request = Request.Builder()
                                            .url("https://us-central1-artemis-b18ae.cloudfunctions.net/server/waste")
                                            .post(postData.toString().toRequestBody(mediaType))
                                            .build()
                                        val response = client.newCall(request).execute()
                                        if (response.isSuccessful) {
                                            val responseBody = response.body?.string()
                                            withContext(Dispatchers.Main) {
                                                // Update UI or show success message
                                                // binding.typeEditText.setText("")
                                                binding.nameEditText.setText("")
                                                binding.quantityEditText.setText("")
                                                binding.amountEditText.setText("")
                                                Toast.makeText(requireContext(), "Input Successful: ${response.code}", Toast.LENGTH_SHORT).show()
                                            }
                                        } else {
                                            // handle the error here
                                            withContext(Dispatchers.Main) {
                                                Toast.makeText(requireContext(), "Error: ${response.code}", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    } catch (e: Exception) {
                                        withContext(Dispatchers.Main) {
                                            Toast.makeText(requireContext(), "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                 
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
                                    val builder = AlertDialog.Builder(requireContext())
                                    builder.setTitle("Error")
                                    builder.setMessage("Please enter the following data needed")
                                    builder.setPositiveButton("OK") { dialog, which ->
                                        dialog.dismiss()
                                    }
                                    val dialog = builder.create()
                                    dialog.show()
                                    return@setOnClickListener
                                }

                                // Calculate the total weight of all items in the array
                                var totalWeight = 0
                                val itemArray = JSONArray()
                                for (i in 0 until weight) {
                                    val itemObject = JSONObject()
                                    itemObject.put("name", name)
                                    itemObject.put("weight", weight)
                                    itemArray.put(itemObject)
                                    totalWeight += weight
                                }
                 
                                val postData = JSONObject()
                                val postDataEditObject = JSONObject()
                 
                                postDataEditObject.put("items", itemArray)
                                postDataEditObject.put("total weight", totalWeight)
                 
                                postData.put(wastetype, postDataEditObject)
                 
                                GlobalScope.launch(Dispatchers.IO) {
                                    try {
                                        val client = OkHttpClient()
                                        val mediaType = "application/json; charset=utf-8".toMediaType()
                                        val request = Request.Builder()
                                            .url("https://us-central1-artemis-b18ae.cloudfunctions.net/server/waste")
                                            .post(postData.toString().toRequestBody(mediaType))
                                            .build()
                                        val response = client.newCall(request).execute()
                                        if (response.isSuccessful) {
                                            val responseBody = response.body?.string()
                                            withContext(Dispatchers.Main) {
                                                // Update UI or show success message
                                                // binding.typeeditText.setText("")
                                                binding.nameEditText.setText("")
                                                binding.quantityEditText.setText("")
                                                binding.amountEditText.setText("")
                                                Toast.makeText(requireContext(), "Input Successful: ${response.code}", Toast.LENGTH_SHORT).show()
                                            }
                                        } else {
                                            // handle the error here
                                            withContext(Dispatchers.Main) {
                                                Toast.makeText(requireContext(), "Error: ${response.code}", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    } catch (e: Exception) {
                                        withContext(Dispatchers.Main) {
                                            Toast.makeText(requireContext(), "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                 
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

                                val wastetype = "foodwaste"
                                val weight = binding.amountEditText.text.toString().trim().toInt()
                 
                                // Check if any EditText field is empty before proceeding
                                if (weight == 0) {
                                    val builder = AlertDialog.Builder(requireContext())
                                    builder.setTitle("Error")
                                    builder.setMessage("Please enter the following data needed")
                                    builder.setPositiveButton("OK") { dialog, which ->
                                        dialog.dismiss()
                                    }
                                    val dialog = builder.create()
                                    dialog.show()
                                    return@setOnClickListener
                                }

                                 // Calculate the total weight of all items in the array
                                var totalWeight = 0
                                val itemArray = JSONArray()
                                for (i in 0 until weight) {
                                    val itemObject = JSONObject()
                                    itemObject.put("weight", weight)
                                    itemArray.put(itemObject)
                                    totalWeight += weight
                                }
                 
                                val postData = JSONObject()
                                val postDataEditObject = JSONObject()
                 
                                postDataEditObject.put("items", itemArray)
                                postDataEditObject.put("total weight", totalWeight)
                 
                                postData.put(wastetype, postDataEditObject)
                 
                                GlobalScope.launch(Dispatchers.IO) {
                                    try {
                                        val client = OkHttpClient()
                                        val mediaType = "application/json; charset=utf-8".toMediaType()
                                        val request = Request.Builder()
                                            .url("https://us-central1-artemis-b18ae.cloudfunctions.net/server/waste")
                                            .post(postData.toString().toRequestBody(mediaType))
                                            .build()
                                        val response = client.newCall(request).execute()
                                        if (response.isSuccessful) {
                                            val responseBody = response.body?.string()
                                            withContext(Dispatchers.Main) {
                                                // Update UI or show success message
                                                // binding.typeeditText.setText("")
                                                binding.nameEditText.setText("")
                                                binding.quantityEditText.setText("")
                                                binding.amountEditText.setText("")
                                                Toast.makeText(requireContext(), "Input Successful: ${response.code}", Toast.LENGTH_SHORT).show()
                                            }
                                        } else {
                                            // handle the error here
                                            withContext(Dispatchers.Main) {
                                                Toast.makeText(requireContext(), "Error: ${response.code}", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    } catch (e: Exception) {
                                        withContext(Dispatchers.Main) {
                                            Toast.makeText(requireContext(), "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                 
                            }
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // do nothing
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


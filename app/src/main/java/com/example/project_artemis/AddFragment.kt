package com.example.project_artemis

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import android.view.ViewGroup
import android.widget.ArrayAdapter
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
        
        binding.addTitle.text = getString(R.string.data_input)
        binding.locationText.text = getString(R.string.locationText)
        binding.amountText.text = getString(R.string.amountText)
        binding.typeText.text = getString(R.string.typeText)
        binding.quantityText.text = getString(R.string.quantityText)
        binding.NameText.text = getString(R.string.nameText)

        val itemsLocation = listOf(
            "Batangas State University - Alangilan",
            "Batangas State University - Pablo Borbon",
            "Batangas State University - Malvar"
        )

        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.spinner_selected_item2,
            itemsLocation
        ).apply {
            setDropDownViewResource(R.layout.style_spinner)
        }

        binding.locationPickerInput.adapter = adapter

        binding.inputButton.setOnClickListener {

            val wastetype = binding.typeeditText.text.toString().trim()
            val name = binding.nameeditText.text.toString().trim()
            val quantity = binding.quantityeditText.text.toString().trim().toInt()
            val weight = binding.amountedditText.text.toString().trim().toInt()

            val itemArray = JSONArray()
            val itemObject = JSONObject()

            itemObject.put("name", name)
            itemObject.put("quantity", quantity)

            itemArray.put(itemObject)

            val postData = JSONObject()
            val postDataEditObject = JSONObject()

            postDataEditObject.put("items", itemArray)
            postDataEditObject.put("weight", weight)

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
                            binding.typeeditText.setText("")
                            binding.nameeditText.setText("")
                            binding.quantityeditText.setText("")
                            binding.amountedditText.setText("")
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

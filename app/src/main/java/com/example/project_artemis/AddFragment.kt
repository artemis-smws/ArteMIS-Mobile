package com.example.project_artemis

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import com.example.project_artemis.databinding.FragmentAddBinding
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
import java.net.HttpURLConnection
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.InputStreamReader
import java.io.OutputStreamWriter


class AddFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentAddBinding.inflate(inflater, container, false)

        binding.addTitle.text = getString(R.string.data_input)
        binding.locationText.text = getString(R.string.locationText)
        binding.amountText.text = getString(R.string.amountText)
        binding.typeText.text = getString(R.string.typeText)
        binding.quantityText.text = getString(R.string.quantityText)

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
    
    val waste_type = binding.typeeditText.text.toString().trim()
    val name = binding.nameeditText.text.toString().trim()
    val quantity = binding.quantityeditText.text.toString().toInt().trim()
    val weight = binding.amountedditText.text.toString().toInt().trim()

    val requestBody = JSONObject()
    val item = JSONObject()
    val itemsArray = JSONArray()

    item.put("name", name)
    item.put("quantity", quantity)

    itemsArray.put(item)

    val editTextObject = JSONObject()
    editTextObject.put("items", itemsArray)
    editTextObject.put("weight", weight)

    requestBody.put(waste_type, editTextObject)

    Thread {
        try {
            val url = URL("https://us-central1-artemis-b18ae.cloudfunctions.net/server/waste")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json; utf-8")
            connection.setRequestProperty("Accept", "application/json")
            connection.doOutput = true

            val outputStream = connection.outputStream
            val input = BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))
            input.write(requestBody.toString())
            input.close()
            outputStream.close()

            val responseCode = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val responseStream = connection.inputStream
                val response = BufferedReader(InputStreamReader(responseStream)).readText()
                responseStream.close()
                //if it work
            } else {
                //if it doesnt
            }
            connection.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }.start()
}

        return binding.root
    }

}

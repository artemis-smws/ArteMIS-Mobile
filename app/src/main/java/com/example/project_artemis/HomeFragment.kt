package com.example.project_artemis

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import android.content.Context
import android.util.TypedValue
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.formatter.PercentFormatter
import com.example.project_artemis.databinding.FragmentHomeBinding
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private var buildingObject: String? = null
    private var current_average: Int? = null
    private var overallWeight: Double? = null
    private var overallWeight1: Double? = null
    private var overallWeight2: Double? = null
    private var overallWeight3: Double? = null
    private var overallWeight4: Double? = null
    private var overallWeight5: Double? = null
    private var overallWeight6: Double? = null


    private var cicsresidualWeight: Double? = null
    private var cicsresidualWeight1: Double? = null
    private var cicsresidualWeight2: Double? = null
    private var cicsresidualWeight3: Double? = null
    private var cicsresidualWeight4: Double? = null
    private var cicsresidualWeight5: Double? = null
    private var cicsresidualWeight6: Double? = null

    private var cicsrecyclableWeight: Double? = null
    private var cicsrecyclableWeight1: Double? = null
    private var cicsrecyclableWeight2: Double? = null
    private var cicsrecyclableWeight3: Double? = null
    private var cicsrecyclableWeight4: Double? = null
    private var cicsrecyclableWeight5: Double? = null
    private var cicsrecyclableWeight6: Double? = null

    private var cicsfoodWeight: Double? = null
    private var cicsfoodWeight1: Double? = null
    private var cicsfoodWeight2: Double? = null
    private var cicsfoodWeight3: Double? = null
    private var cicsfoodWeight4: Double? = null
    private var cicsfoodWeight5: Double? = null
    private var cicsfoodWeight6: Double? = null
    

    private var citresidualWeight: Double? = null
    private var citresidualWeight1: Double? = null
    private var citresidualWeight2: Double? = null
    private var citresidualWeight3: Double? = null
    private var citresidualWeight4: Double? = null
    private var citresidualWeight5: Double? = null
    private var citresidualWeight6: Double? = null

    private var citrecyclableWeight: Double? = null
    private var citrecyclableWeight1: Double? = null
    private var citrecyclableWeight2: Double? = null
    private var citrecyclableWeight3: Double? = null
    private var citrecyclableWeight4: Double? = null
    private var citrecyclableWeight5: Double? = null
    private var citrecyclableWeight6: Double? = null

    private var citfoodWeight: Double? = null
    private var citfoodWeight1: Double? = null
    private var citfoodWeight2: Double? = null
    private var citfoodWeight3: Double? = null
    private var citfoodWeight4: Double? = null
    private var citfoodWeight5: Double? = null
    private var citfoodWeight6: Double? = null


    private var rgrresidualWeight: Double? = null
    private var rgrresidualWeight1: Double? = null
    private var rgrresidualWeight2: Double? = null
    private var rgrresidualWeight3: Double? = null
    private var rgrresidualWeight4: Double? = null
    private var rgrresidualWeight5: Double? = null
    private var rgrresidualWeight6: Double? = null

    private var rgrrecyclableWeight: Double? = null
    private var rgrrecyclableWeight1: Double? = null
    private var rgrrecyclableWeight2: Double? = null
    private var rgrrecyclableWeight3: Double? = null
    private var rgrrecyclableWeight4: Double? = null
    private var rgrrecyclableWeight5: Double? = null
    private var rgrrecyclableWeight6: Double? = null

    private var rgrfoodWeight: Double? = null
    private var rgrfoodWeight1: Double? = null
    private var rgrfoodWeight2: Double? = null
    private var rgrfoodWeight3: Double? = null
    private var rgrfoodWeight4: Double? = null
    private var rgrfoodWeight5: Double? = null
    private var rgrfoodWeight6: Double? = null


    private var sscresidualWeight: Double? = null
    private var sscresidualWeight1: Double? = null
    private var sscresidualWeight2: Double? = null
    private var sscresidualWeight3: Double? = null
    private var sscresidualWeight4: Double? = null
    private var sscresidualWeight5: Double? = null
    private var sscresidualWeight6: Double? = null

    private var sscrecyclableWeight: Double? = null
    private var sscrecyclableWeight1: Double? = null
    private var sscrecyclableWeight2: Double? = null
    private var sscrecyclableWeight3: Double? = null
    private var sscrecyclableWeight4: Double? = null
    private var sscrecyclableWeight5: Double? = null
    private var sscrecyclableWeight6: Double? = null

    private var sscfoodWeight: Double? = null
    private var sscfoodWeight1: Double? = null
    private var sscfoodWeight2: Double? = null
    private var sscfoodWeight3: Double? = null
    private var sscfoodWeight4: Double? = null
    private var sscfoodWeight5: Double? = null
    private var sscfoodWeight6: Double? = null


    private var steerHubresidualWeight: Double? = null
    private var steerHubresidualWeight1: Double? = null
    private var steerHubresidualWeight2: Double? = null
    private var steerHubresidualWeight3: Double? = null
    private var steerHubresidualWeight4: Double? = null
    private var steerHubresidualWeight5: Double? = null
    private var steerHubresidualWeight6: Double? = null

    private var steerHubrecyclableWeight: Double? = null
    private var steerHubrecyclableWeight1: Double? = null
    private var steerHubrecyclableWeight2: Double? = null
    private var steerHubrecyclableWeight3: Double? = null
    private var steerHubrecyclableWeight4: Double? = null
    private var steerHubrecyclableWeight5: Double? = null
    private var steerHubrecyclableWeight6: Double? = null

    private var steerHubfoodWeight: Double? = null
    private var steerHubfoodWeight1: Double? = null
    private var steerHubfoodWeight2: Double? = null
    private var steerHubfoodWeight3: Double? = null
    private var steerHubfoodWeight4: Double? = null
    private var steerHubfoodWeight5: Double? = null
    private var steerHubfoodWeight6: Double? = null


    private var gymnasiumresidualWeight: Double? = null
    private var gymnasiumresidualWeight1: Double? = null
    private var gymnasiumresidualWeight2: Double? = null
    private var gymnasiumresidualWeight3: Double? = null
    private var gymnasiumresidualWeight4: Double? = null
    private var gymnasiumresidualWeight5: Double? = null
    private var gymnasiumresidualWeight6: Double? = null

    private var gymnasiumrecyclableWeight: Double? = null
    private var gymnasiumrecyclableWeight1: Double? = null
    private var gymnasiumrecyclableWeight2: Double? = null
    private var gymnasiumrecyclableWeight3: Double? = null
    private var gymnasiumrecyclableWeight4: Double? = null
    private var gymnasiumrecyclableWeight5: Double? = null
    private var gymnasiumrecyclableWeight6: Double? = null

    private var gymnasiumfoodWeight: Double? = null
    private var gymnasiumfoodWeight1: Double? = null
    private var gymnasiumfoodWeight2: Double? = null
    private var gymnasiumfoodWeight3: Double? = null
    private var gymnasiumfoodWeight4: Double? = null
    private var gymnasiumfoodWeight5: Double? = null
    private var gymnasiumfoodWeight6: Double? = null


    private var ceafaresidualWeight: Double? = null
    private var ceafaresidualWeight1: Double? = null
    private var ceafaresidualWeight2: Double? = null
    private var ceafaresidualWeight3: Double? = null
    private var ceafaresidualWeight4: Double? = null
    private var ceafaresidualWeight5: Double? = null
    private var ceafaresidualWeight6: Double? = null

    private var ceafarecyclableWeight: Double? = null
    private var ceafarecyclableWeight1: Double? = null
    private var ceafarecyclableWeight2: Double? = null
    private var ceafarecyclableWeight3: Double? = null
    private var ceafarecyclableWeight4: Double? = null
    private var ceafarecyclableWeight5: Double? = null
    private var ceafarecyclableWeight6: Double? = null

    private var ceafafoodWeight: Double? = null
    private var ceafafoodWeight2: Double? = null
    private var ceafafoodWeight1: Double? = null
    private var ceafafoodWeight3: Double? = null
    private var ceafafoodWeight4: Double? = null
    private var ceafafoodWeight5: Double? = null
    private var ceafafoodWeight6: Double? = null



    private var ceafaTotalWeight: Double? = null
    private var citTotalWeight: Double? = null
    private var cicsTotalWeight: Double? = null
    private var rgrTotalWeight: Double? = null
    private var steerHubTotalWeight: Double? = null
    private var gymnasiumTotalWeight: Double? = null
    private var sscTotalWeight: Double? = null

    private var ceafaTotalWeight1: Double? = null
    private var citTotalWeight1: Double? = null
    private var cicsTotalWeight1: Double? = null
    private var rgrTotalWeight1: Double? = null
    private var steerHubTotalWeight1: Double? = null
    private var gymnasiumTotalWeight1: Double? = null
    private var sscTotalWeight1: Double? = null

    private var ceafaTotalWeight2: Double? = null
    private var citTotalWeight2: Double? = null
    private var cicsTotalWeight2: Double? = null
    private var rgrTotalWeight2: Double? = null
    private var steerHubTotalWeight2: Double? = null
    private var gymnasiumTotalWeight2: Double? = null
    private var sscTotalWeight2: Double? = null

    private var ceafaTotalWeight3: Double? = null
    private var citTotalWeight3: Double? = null
    private var cicsTotalWeight3: Double? = null
    private var rgrTotalWeight3: Double? = null
    private var steerHubTotalWeight3: Double? = null
    private var gymnasiumTotalWeight3: Double? = null
    private var sscTotalWeight3: Double? = null

    private var ceafaTotalWeight4: Double? = null
    private var citTotalWeight4: Double? = null
    private var cicsTotalWeight4: Double? = null
    private var rgrTotalWeight4: Double? = null
    private var steerHubTotalWeight4: Double? = null
    private var gymnasiumTotalWeight4: Double? = null
    private var sscTotalWeight4: Double? = null

    private var ceafaTotalWeight5: Double? = null
    private var citTotalWeight5: Double? = null
    private var cicsTotalWeight5: Double? = null
    private var rgrTotalWeight5: Double? = null
    private var steerHubTotalWeight5: Double? = null
    private var gymnasiumTotalWeight5: Double? = null
    private var sscTotalWeight5: Double? = null

    private var ceafaTotalWeight6: Double? = null
    private var citTotalWeight6: Double? = null
    private var cicsTotalWeight6: Double? = null
    private var rgrTotalWeight6: Double? = null
    private var steerHubTotalWeight6: Double? = null
    private var gymnasiumTotalWeight6: Double? = null
    private var sscTotalWeight6: Double? = null

    private var cicsPercentage: Double? = null
    private var citPercentage: Double? = null
    private var ceafaPercentage: Double? = null
    private var steerPercentage: Double? = null
    private var rgrPercentage: Double? = null
    private var sscPercentage: Double? = null
    private var gymPercentage: Double? = null
    private var residualPercentage: Double? = null
    private var foodWastePercentage: Double? = null
    private var recyclablePercentage: Double? = null
    private lateinit var itemsBuilding: List<String>
    private lateinit var itemsTime: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val binding = FragmentHomeBinding.inflate(inflater, container, false)

        val decimalFormat = DecimalFormat("#.##")

        val getHighest = OkHttpClient()
        val highrequest = Request.Builder()
            .url("https://us-central1-artemis-b18ae.cloudfunctions.net/server/waste/highest")
            .build()

        getHighest.newCall(highrequest).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle request failure
            }
        
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val jsonData = response.body?.string()
                    // Parse the JSON data and extract the required values
                    val jsonArray = JSONArray(jsonData)
                    if (jsonArray.length() > 0) {
                        val jsonObject = jsonArray.getJSONObject(0)
                        val overallWeight = jsonObject.getInt("overall_weight")
                        val createdAtSeconds = jsonObject.getJSONObject("createdAt").getLong("seconds")
        
                        // Convert createdAtSeconds to a Date object
                        val createdAtDate = Date(createdAtSeconds * 1000)
        
                        // Format the date as MMM-d
                        val dateFormat = SimpleDateFormat("MMM d", Locale.getDefault())
                        val formattedDate = dateFormat.format(createdAtDate)
        
                        if (isAdded) {
                            requireActivity().runOnUiThread {
                                binding.highestDate.text = formattedDate
                                binding.highestWeight.text = overallWeight.let { decimalFormat.format(it) + " kg" }
                            }
                        }
                    }
                }
            }
        })

        val getLowest = OkHttpClient()
        val lowrequest = Request.Builder()
            .url("https://us-central1-artemis-b18ae.cloudfunctions.net/server/waste/lowest")
            .build()
        
        getLowest.newCall(lowrequest).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle request failure
            }
        
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val jsonData = response.body?.string()
                    // Parse the JSON data and extract the required values
                    val jsonArray = JSONArray(jsonData)
                    if (jsonArray.length() > 0) {
                        val jsonObject = jsonArray.getJSONObject(0)
                        val overallWeight = jsonObject.getInt("overall_weight")
                        val createdAtSeconds = jsonObject.getJSONObject("createdAt").getLong("seconds")

                        // Convert createdAtSeconds to a Date object
                        val createdAtDate = Date(createdAtSeconds * 1000)
        
                        // Format the date as MMM-d
                        val dateFormat = SimpleDateFormat("MMM d", Locale.getDefault())
                        val formattedDate = dateFormat.format(createdAtDate)
        
                        if (isAdded) {
                            requireActivity().runOnUiThread {
                                binding.lowestDate.text = formattedDate
                                binding.lowestWeight.text = overallWeight.let { decimalFormat.format(it) + " kg" }
                            }
                        }
                    }
                }
            }
        })

        val getAverage = OkHttpClient()
        val averequest = Request.Builder()
            .url("https://us-central1-artemis-b18ae.cloudfunctions.net/server/status/latest")
            .build()

        getAverage.newCall(averequest).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Handle request failure
            }
        
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val jsonData = response.body?.string()
                    // Parse the JSON data and extract the required values
                    val jsonArray = JSONArray(jsonData)
                    if (jsonArray.length() > 0) {
                        val jsonObject = jsonArray.getJSONObject(0)
                        val overallWeight: Int? = try {
                            jsonObject.getInt("average_per_building")
                        } catch (e: JSONException) {
                            null
                        }
                        if (overallWeight != null) {
                            current_average = overallWeight
                        }
        
                        if (isAdded) {
                            requireActivity().runOnUiThread {
                                binding.averageWeight.text = current_average?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                            }
                        }
                    }
                }
            }
        })

        itemsTime = listOf("7 days", "30 days", "Last Year")
        itemsBuilding = listOf(
            "CEAFA Building",
            "CIT Building",
            "CICS Building",
            "RGR Building",
            "Gymnasium",
            "STEER Hub",
            "Student Services Center"
        )

        val adapterTime = ArrayAdapter(
            requireContext(),
            R.layout.spinner_selected_item,
            itemsTime
        ).apply{
            setDropDownViewResource(R.layout.style_spinner)
        }


        val adapterBuilding = ArrayAdapter(
            requireContext(),
            R.layout.spinner_selected_item,
            itemsBuilding
        ).apply {
            setDropDownViewResource(R.layout.style_spinner)
        }

        // Waste Generated Graph

        val dateFormat = SimpleDateFormat("MMM d", Locale.getDefault())

        val calendar = Calendar.getInstance()

        val dayString = mutableListOf<String>()
        for (i in 0 until 7) {
            val date = calendar.time
            val formattedDate = dateFormat.format(date)
            dayString.add(formattedDate)

            calendar.add(Calendar.DAY_OF_YEAR, -1)
        }

        dayString.reverse()

        val currentDate = dateFormat.format(Date())
        dayString.add(currentDate) // Add the current date to the list

        // Print the dayString to verify the result
        for (day in dayString) {
            println(day)
        }

        val themeColor = resolveThemeColor(requireContext(), com.google.android.material.R.attr.colorOnSecondary)

        val wasteGeneratedChart = binding.wasteGenChart

       val buildingLineData: List<List<Entry>> = listOf(
           listOf(Entry(0f, ceafaTotalWeight6?.toFloat() ?: 0f), Entry(1f, ceafaTotalWeight5?.toFloat() ?: 0f), Entry(2f, ceafaTotalWeight4?.toFloat() ?: 0f), Entry(3f, ceafaTotalWeight3?.toFloat() ?: 0f), Entry(4f, ceafaTotalWeight2?.toFloat() ?: 0f), Entry(5f, ceafaTotalWeight1?.toFloat() ?: 0f), Entry(6f, ceafaTotalWeight?.toFloat() ?: 0f)),  // CEAFA
           listOf(Entry(0f, citTotalWeight6?.toFloat() ?: 0f), Entry(1f, citTotalWeight5?.toFloat() ?: 0f), Entry(2f, citTotalWeight4?.toFloat() ?: 0f), Entry(3f, citTotalWeight3?.toFloat() ?: 0f), Entry(4f, citTotalWeight2?.toFloat() ?: 0f), Entry(5f, citTotalWeight1?.toFloat() ?: 0f), Entry(6f, citTotalWeight?.toFloat() ?: 0f)), //CIT
           listOf(Entry(0f, cicsTotalWeight6?.toFloat() ?: 0f), Entry(1f, cicsTotalWeight5?.toFloat() ?: 0f), Entry(2f, cicsTotalWeight4?.toFloat() ?: 0f), Entry(3f, cicsTotalWeight3?.toFloat() ?: 0f), Entry(4f, cicsTotalWeight2?.toFloat() ?: 0f), Entry(5f, cicsTotalWeight1?.toFloat() ?: 0f), Entry(6f, cicsTotalWeight?.toFloat() ?: 0f)),  // CICS
           listOf(Entry(0f, rgrTotalWeight6?.toFloat() ?: 0f), Entry(1f, rgrTotalWeight5?.toFloat() ?: 0f), Entry(2f, rgrTotalWeight4?.toFloat() ?: 0f), Entry(3f, rgrTotalWeight3?.toFloat() ?: 0f), Entry(4f, rgrTotalWeight2?.toFloat() ?: 0f), Entry(5f, rgrTotalWeight1?.toFloat() ?: 0f), Entry(6f, rgrTotalWeight?.toFloat() ?: 0f)),  // RGR
           listOf(Entry(0f, gymnasiumTotalWeight6?.toFloat() ?: 0f), Entry(1f, gymnasiumTotalWeight5?.toFloat() ?: 0f), Entry(2f, gymnasiumTotalWeight4?.toFloat() ?: 0f), Entry(3f, gymnasiumTotalWeight3?.toFloat() ?: 0f), Entry(4f, gymnasiumTotalWeight2?.toFloat() ?: 0f), Entry(5f, gymnasiumTotalWeight1?.toFloat() ?: 0f), Entry(6f, gymnasiumTotalWeight?.toFloat() ?: 0f)),   // Gym
           listOf(Entry(0f, steerHubTotalWeight6?.toFloat() ?: 0f), Entry(1f, steerHubTotalWeight5?.toFloat() ?: 0f), Entry(2f, steerHubTotalWeight4?.toFloat() ?: 0f), Entry(3f, steerHubTotalWeight3?.toFloat() ?: 0f), Entry(4f, steerHubTotalWeight2?.toFloat() ?: 0f), Entry(5f, steerHubTotalWeight1?.toFloat() ?: 0f), Entry(6f, steerHubTotalWeight?.toFloat() ?: 0f)),  // STEER Hub
           listOf(Entry(0f, sscTotalWeight6?.toFloat() ?: 0f), Entry(1f, sscTotalWeight5?.toFloat() ?: 0f), Entry(2f, sscTotalWeight4?.toFloat() ?: 0f), Entry(3f, sscTotalWeight3?.toFloat() ?: 0f), Entry(4f, sscTotalWeight2?.toFloat() ?: 0f), Entry(5f, sscTotalWeight1?.toFloat() ?: 0f), Entry(6f, sscTotalWeight?.toFloat() ?: 0f))  // SSC
       )

       val residualLineData: List<List<Entry>> = listOf(
            listOf(Entry(0f, ceafaresidualWeight6?.toFloat() ?: 0f), Entry(1f, ceafaresidualWeight5?.toFloat() ?: 0f), Entry(2f, ceafaresidualWeight4?.toFloat() ?: 0f), Entry(3f, ceafaresidualWeight3?.toFloat() ?: 0f), Entry(4f, ceafaresidualWeight2?.toFloat() ?: 0f), Entry(5f, ceafaresidualWeight1?.toFloat() ?: 0f), Entry(6f, ceafaresidualWeight?.toFloat() ?: 0f)),  // CEAFA
            listOf(Entry(0f, citresidualWeight6?.toFloat() ?: 0f), Entry(1f, citresidualWeight5?.toFloat() ?: 0f), Entry(2f, citresidualWeight4?.toFloat() ?: 0f), Entry(3f, citresidualWeight3?.toFloat() ?: 0f), Entry(4f, citresidualWeight2?.toFloat() ?: 0f), Entry(5f, citresidualWeight1?.toFloat() ?: 0f), Entry(6f, citresidualWeight?.toFloat() ?: 0f)), //CIT
            listOf(Entry(0f, cicsresidualWeight6?.toFloat() ?: 0f), Entry(1f, cicsresidualWeight5?.toFloat() ?: 0f), Entry(2f, cicsresidualWeight4?.toFloat() ?: 0f), Entry(3f, cicsresidualWeight3?.toFloat() ?: 0f), Entry(4f, cicsresidualWeight2?.toFloat() ?: 0f), Entry(5f, cicsresidualWeight1?.toFloat() ?: 0f), Entry(6f, cicsresidualWeight?.toFloat() ?: 0f)),  // CICS
            listOf(Entry(0f, rgrresidualWeight6?.toFloat() ?: 0f), Entry(1f, rgrresidualWeight5?.toFloat() ?: 0f), Entry(2f, rgrresidualWeight4?.toFloat() ?: 0f), Entry(3f, rgrresidualWeight3?.toFloat() ?: 0f), Entry(4f, rgrresidualWeight2?.toFloat() ?: 0f), Entry(5f, rgrresidualWeight1?.toFloat() ?: 0f), Entry(6f, rgrresidualWeight?.toFloat() ?: 0f)),  // RGR
            listOf(Entry(0f, gymnasiumresidualWeight6?.toFloat() ?: 0f), Entry(1f, gymnasiumresidualWeight5?.toFloat() ?: 0f), Entry(2f, gymnasiumresidualWeight4?.toFloat() ?: 0f), Entry(3f, gymnasiumresidualWeight3?.toFloat() ?: 0f), Entry(4f, gymnasiumresidualWeight2?.toFloat() ?: 0f), Entry(5f, gymnasiumresidualWeight1?.toFloat() ?: 0f), Entry(6f, gymnasiumresidualWeight?.toFloat() ?: 0f)),   // Gym
            listOf(Entry(0f, steerHubresidualWeight6?.toFloat() ?: 0f), Entry(1f, steerHubresidualWeight5?.toFloat() ?: 0f), Entry(2f, steerHubresidualWeight4?.toFloat() ?: 0f), Entry(3f, steerHubresidualWeight3?.toFloat() ?: 0f), Entry(4f, steerHubresidualWeight2?.toFloat() ?: 0f), Entry(5f, steerHubresidualWeight1?.toFloat() ?: 0f), Entry(6f, steerHubresidualWeight?.toFloat() ?: 0f)),  // STEER Hub
            listOf(Entry(0f, sscresidualWeight6?.toFloat() ?: 0f), Entry(1f, sscresidualWeight5?.toFloat() ?: 0f), Entry(2f, sscresidualWeight4?.toFloat() ?: 0f), Entry(3f, sscresidualWeight3?.toFloat() ?: 0f), Entry(4f, sscresidualWeight2?.toFloat() ?: 0f), Entry(5f, sscresidualWeight1?.toFloat() ?: 0f), Entry(6f, sscresidualWeight?.toFloat() ?: 0f))  // SSC
        )

        val recyclableLineData: List<List<Entry>> = listOf(
            listOf(Entry(0f, ceafarecyclableWeight6?.toFloat() ?: 0f), Entry(1f, ceafarecyclableWeight5?.toFloat() ?: 0f), Entry(2f, ceafarecyclableWeight4?.toFloat() ?: 0f), Entry(3f, ceafarecyclableWeight3?.toFloat() ?: 0f), Entry(4f, ceafarecyclableWeight2?.toFloat() ?: 0f), Entry(5f, ceafarecyclableWeight1?.toFloat() ?: 0f), Entry(6f, ceafarecyclableWeight?.toFloat() ?: 0f)),  // CEAFA
            listOf(Entry(0f, citrecyclableWeight6?.toFloat() ?: 0f), Entry(1f, citrecyclableWeight5?.toFloat() ?: 0f), Entry(2f, citrecyclableWeight4?.toFloat() ?: 0f), Entry(3f, citrecyclableWeight3?.toFloat() ?: 0f), Entry(4f, citrecyclableWeight2?.toFloat() ?: 0f), Entry(5f, citrecyclableWeight1?.toFloat() ?: 0f), Entry(6f, citrecyclableWeight?.toFloat() ?: 0f)), // CIT
            listOf(Entry(0f, cicsrecyclableWeight6?.toFloat() ?: 0f), Entry(1f, cicsrecyclableWeight5?.toFloat() ?: 0f), Entry(2f, cicsrecyclableWeight4?.toFloat() ?: 0f), Entry(3f, cicsrecyclableWeight3?.toFloat() ?: 0f), Entry(4f, cicsrecyclableWeight2?.toFloat() ?: 0f), Entry(5f, cicsrecyclableWeight1?.toFloat() ?: 0f), Entry(6f, cicsrecyclableWeight?.toFloat() ?: 0f)),  // CICS
            listOf(Entry(0f, rgrrecyclableWeight6?.toFloat() ?: 0f), Entry(1f, rgrrecyclableWeight5?.toFloat() ?: 0f), Entry(2f, rgrrecyclableWeight4?.toFloat() ?: 0f), Entry(3f, rgrrecyclableWeight3?.toFloat() ?: 0f), Entry(4f, rgrrecyclableWeight2?.toFloat() ?: 0f), Entry(5f, rgrrecyclableWeight1?.toFloat() ?: 0f), Entry(6f, rgrrecyclableWeight?.toFloat() ?: 0f)),  // RGR
            listOf(Entry(0f, gymnasiumrecyclableWeight6?.toFloat() ?: 0f), Entry(1f, gymnasiumrecyclableWeight5?.toFloat() ?: 0f), Entry(2f, gymnasiumrecyclableWeight4?.toFloat() ?: 0f), Entry(3f, gymnasiumrecyclableWeight3?.toFloat() ?: 0f), Entry(4f, gymnasiumrecyclableWeight2?.toFloat() ?: 0f), Entry(5f, gymnasiumrecyclableWeight1?.toFloat() ?: 0f), Entry(6f, gymnasiumrecyclableWeight?.toFloat() ?: 0f)),   // Gym
            listOf(Entry(0f, steerHubrecyclableWeight6?.toFloat() ?: 0f), Entry(1f, steerHubrecyclableWeight5?.toFloat() ?: 0f), Entry(2f, steerHubrecyclableWeight4?.toFloat() ?: 0f), Entry(3f, steerHubrecyclableWeight3?.toFloat() ?: 0f), Entry(4f, steerHubrecyclableWeight2?.toFloat() ?: 0f), Entry(5f, steerHubrecyclableWeight1?.toFloat() ?: 0f), Entry(6f, steerHubrecyclableWeight?.toFloat() ?: 0f)),  // STEER Hub
            listOf(Entry(0f, sscrecyclableWeight6?.toFloat() ?: 0f), Entry(1f, sscrecyclableWeight5?.toFloat() ?: 0f), Entry(2f, sscrecyclableWeight4?.toFloat() ?: 0f), Entry(3f, sscrecyclableWeight3?.toFloat() ?: 0f), Entry(4f, sscrecyclableWeight2?.toFloat() ?: 0f), Entry(5f, sscrecyclableWeight1?.toFloat() ?: 0f), Entry(6f, sscrecyclableWeight?.toFloat() ?: 0f))  // SSC
        )

        val foodLineData: List<List<Entry>> = listOf(
            listOf(Entry(0f, ceafafoodWeight6?.toFloat() ?: 0f), Entry(1f, ceafafoodWeight5?.toFloat() ?: 0f), Entry(2f, ceafafoodWeight4?.toFloat() ?: 0f), Entry(3f, ceafafoodWeight3?.toFloat() ?: 0f), Entry(4f, ceafafoodWeight2?.toFloat() ?: 0f), Entry(5f, ceafafoodWeight1?.toFloat() ?: 0f), Entry(6f, ceafafoodWeight?.toFloat() ?: 0f)),  // CEAFA
            listOf(Entry(0f, citfoodWeight6?.toFloat() ?: 0f), Entry(1f, citfoodWeight5?.toFloat() ?: 0f), Entry(2f, citfoodWeight4?.toFloat() ?: 0f), Entry(3f, citfoodWeight3?.toFloat() ?: 0f), Entry(4f, citfoodWeight2?.toFloat() ?: 0f), Entry(5f, citfoodWeight1?.toFloat() ?: 0f), Entry(6f, citfoodWeight?.toFloat() ?: 0f)), // CIT
            listOf(Entry(0f, cicsfoodWeight6?.toFloat() ?: 0f), Entry(1f, cicsfoodWeight5?.toFloat() ?: 0f), Entry(2f, cicsfoodWeight4?.toFloat() ?: 0f), Entry(3f, cicsfoodWeight3?.toFloat() ?: 0f), Entry(4f, cicsfoodWeight2?.toFloat() ?: 0f), Entry(5f, cicsfoodWeight1?.toFloat() ?: 0f), Entry(6f, cicsfoodWeight?.toFloat() ?: 0f)),  // CICS
            listOf(Entry(0f, rgrfoodWeight6?.toFloat() ?: 0f), Entry(1f, rgrfoodWeight5?.toFloat() ?: 0f), Entry(2f, rgrfoodWeight4?.toFloat() ?: 0f), Entry(3f, rgrfoodWeight3?.toFloat() ?: 0f), Entry(4f, rgrfoodWeight2?.toFloat() ?: 0f), Entry(5f, rgrfoodWeight1?.toFloat() ?: 0f), Entry(6f, rgrfoodWeight?.toFloat() ?: 0f)),  // RGR
            listOf(Entry(0f, gymnasiumfoodWeight6?.toFloat() ?: 0f), Entry(1f, gymnasiumfoodWeight5?.toFloat() ?: 0f), Entry(2f, gymnasiumfoodWeight4?.toFloat() ?: 0f), Entry(3f, gymnasiumfoodWeight3?.toFloat() ?: 0f), Entry(4f, gymnasiumfoodWeight2?.toFloat() ?: 0f), Entry(5f, gymnasiumfoodWeight1?.toFloat() ?: 0f), Entry(6f, gymnasiumfoodWeight?.toFloat() ?: 0f)),   // Gym
            listOf(Entry(0f, steerHubfoodWeight6?.toFloat() ?: 0f), Entry(1f, steerHubfoodWeight5?.toFloat() ?: 0f), Entry(2f, steerHubfoodWeight4?.toFloat() ?: 0f), Entry(3f, steerHubfoodWeight3?.toFloat() ?: 0f), Entry(4f, steerHubfoodWeight2?.toFloat() ?: 0f), Entry(5f, steerHubfoodWeight1?.toFloat() ?: 0f), Entry(6f, steerHubfoodWeight?.toFloat() ?: 0f)),  // STEER Hub
            listOf(Entry(0f, sscfoodWeight6?.toFloat() ?: 0f), Entry(1f, sscfoodWeight5?.toFloat() ?: 0f), Entry(2f, sscfoodWeight4?.toFloat() ?: 0f), Entry(3f, sscfoodWeight3?.toFloat() ?: 0f), Entry(4f, sscfoodWeight2?.toFloat() ?: 0f), Entry(5f, sscfoodWeight1?.toFloat() ?: 0f), Entry(6f, sscfoodWeight?.toFloat() ?: 0f))  // SSC
        )
        

        val wasteGeneratedFormatter: ValueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase): String {
                return dayString[value.toInt()]
            }
        }

        val xAxisWasteGenerated: XAxis = wasteGeneratedChart.xAxis
        
        xAxisWasteGenerated.granularity = 1f
        // Set the text and label colors
        xAxisWasteGenerated.textColor = themeColor

        // Set the label color
        xAxisWasteGenerated.axisLineColor = themeColor

        xAxisWasteGenerated.valueFormatter = wasteGeneratedFormatter

        // Waste Composition Chart

        val wasteCompPieChart = binding.wasteCompChart

        // setupPieChart(wasteCompPieChart, itemsBuilding[0])

        // Waste Composition per Building Chart

        val wasteCompPieChartperBuilding = binding.wasteCompChartperBuilding

        buildingPieChart(wasteCompPieChartperBuilding) // Refresh the pie chart


        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://us-central1-artemis-b18ae.cloudfunctions.net/server/waste/latest/7days") 
            .build()
    
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                requireActivity().runOnUiThread {
                    showErrorMessage("Please check your Internet Connection")
                }
            }
    
            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                val jsonArray = JSONArray(responseData)
    
                // Sort the array based on the "createdAt" timestamp in descending order
                val sortedArray = (0 until jsonArray.length()).map { jsonArray.getJSONObject(it) }
                    .sortedByDescending { it.getJSONObject("createdAt").getLong("seconds") }
    
                // Get the current date in the same format as "createdAt" field
                val calendar = Calendar.getInstance()
                calendar.add(Calendar.DAY_OF_YEAR, -1)
                val calendar2 = Calendar.getInstance()
                calendar2.add(Calendar.DAY_OF_YEAR, -2)
                val calendar3 = Calendar.getInstance()
                calendar3.add(Calendar.DAY_OF_YEAR, -3)
                val calendar4 = Calendar.getInstance()
                calendar4.add(Calendar.DAY_OF_YEAR, -4)
                val calendar5 = Calendar.getInstance()
                calendar5.add(Calendar.DAY_OF_YEAR, -5)
                val calendar6 = Calendar.getInstance()
                calendar6.add(Calendar.DAY_OF_YEAR, -6)
                val previousDate6 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar6.time)
                val previousDate5 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar5.time)
                val previousDate4 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar4.time)
                val previousDate3 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar3.time)
                val previousDate2 = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar2.time)
                val previousDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
                val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
    
                // Find the latest data with the current date
                for (i in sortedArray.indices) {
                    val item = sortedArray[i]
                    val createdAt = item.getJSONObject("createdAt")
                    val createdAtDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        .format(Date(createdAt.getLong("seconds") * 1000))
    
                    if (createdAtDate == currentDate) {
                        overallWeight = item.getDouble("overall_weight")
                        val citData = item.optJSONObject("CIT")
                        val ceafaData = item.optJSONObject("CEAFA")
                        val cicsData = item.optJSONObject("CICS")
                        val steerHubData = item.optJSONObject("STEER_Hub")
                        val gymnasiumData = item.optJSONObject("Gymnasium")
                        val sscData = item.optJSONObject("SSC")
                        val rgrData = item.optJSONObject("RGR")
    

                        if (citData != null) {
                            citTotalWeight = citData.getJSONObject("weight").getDouble("total")
                            citresidualWeight = citData.getJSONObject("weight").getDouble("residual")
                            citrecyclableWeight = citData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            citfoodWeight = citData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (ceafaData != null) {
                            ceafaTotalWeight = ceafaData.getJSONObject("weight").getDouble("total")
                            ceafaresidualWeight = ceafaData.getJSONObject("weight").getDouble("residual")
                            ceafarecyclableWeight = ceafaData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            ceafafoodWeight = ceafaData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (cicsData != null) {
                            cicsTotalWeight = cicsData.getJSONObject("weight").getDouble("total")
                            cicsresidualWeight = cicsData.getJSONObject("weight").getDouble("residual")
                            cicsrecyclableWeight = cicsData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            cicsfoodWeight = cicsData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (steerHubData != null) {
                            steerHubTotalWeight = steerHubData.getJSONObject("weight").getDouble("total")
                            steerHubresidualWeight = steerHubData.getJSONObject("weight").getDouble("residual")
                            steerHubrecyclableWeight = steerHubData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            steerHubfoodWeight = steerHubData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (gymnasiumData != null) {
                            gymnasiumTotalWeight = gymnasiumData.getJSONObject("weight").getDouble("total")
                            gymnasiumresidualWeight = gymnasiumData.getJSONObject("weight").getDouble("residual")
                            gymnasiumrecyclableWeight = gymnasiumData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            gymnasiumfoodWeight = gymnasiumData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (sscData != null) {
                            sscTotalWeight = sscData.getJSONObject("weight").getDouble("total")
                            sscresidualWeight = sscData.getJSONObject("weight").getDouble("residual")
                            sscrecyclableWeight = sscData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            sscfoodWeight = sscData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (rgrData != null) {
                            rgrTotalWeight = rgrData.getJSONObject("weight").getDouble("total")
                            rgrresidualWeight = rgrData.getJSONObject("weight").getDouble("residual")
                            rgrrecyclableWeight = rgrData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            rgrfoodWeight = rgrData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                    }
                }
                for (i in sortedArray.indices) {
                    val item = sortedArray[i]
                    val createdAt = item.getJSONObject("createdAt")
                    val createdAtDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        .format(Date(createdAt.getLong("seconds") * 1000))
                
                    if (createdAtDate == previousDate) {
                        overallWeight1 = item.getDouble("overall_weight")
                        val citData = item.optJSONObject("CIT")
                        val ceafaData = item.optJSONObject("CEAFA")
                        val cicsData = item.optJSONObject("CICS")
                        val steerHubData = item.optJSONObject("STEER_Hub")
                        val gymnasiumData = item.optJSONObject("Gymnasium")
                        val sscData = item.optJSONObject("SSC")
                        val rgrData = item.optJSONObject("RGR")
    

                        if (citData != null) {
                            citTotalWeight1 = citData.getJSONObject("weight").getDouble("total")
                            citresidualWeight1 = citData.getJSONObject("weight").getDouble("residual")
                            citrecyclableWeight1 = citData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            citfoodWeight1 = citData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (ceafaData != null) {
                            ceafaTotalWeight1 = ceafaData.getJSONObject("weight").getDouble("total")
                            ceafaresidualWeight1 = ceafaData.getJSONObject("weight").getDouble("residual")
                            ceafarecyclableWeight1 = ceafaData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            ceafafoodWeight1 = ceafaData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (cicsData != null) {
                            cicsTotalWeight1 = cicsData.getJSONObject("weight").getDouble("total")
                            cicsresidualWeight1 = cicsData.getJSONObject("weight").getDouble("residual")
                            cicsrecyclableWeight1 = cicsData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            cicsfoodWeight1 = cicsData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (steerHubData != null) {
                            steerHubTotalWeight1 = steerHubData.getJSONObject("weight").getDouble("total")
                            steerHubresidualWeight1 = steerHubData.getJSONObject("weight").getDouble("residual")
                            steerHubrecyclableWeight1 = steerHubData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            steerHubfoodWeight1 = steerHubData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (gymnasiumData != null) {
                            gymnasiumTotalWeight1 = gymnasiumData.getJSONObject("weight").getDouble("total")
                            gymnasiumresidualWeight1 = gymnasiumData.getJSONObject("weight").getDouble("residual")
                            gymnasiumrecyclableWeight1 = gymnasiumData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            gymnasiumfoodWeight1 = gymnasiumData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (sscData != null) {
                            sscTotalWeight1 = sscData.getJSONObject("weight").getDouble("total")
                            sscresidualWeight1 = sscData.getJSONObject("weight").getDouble("residual")
                            sscrecyclableWeight1 = sscData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            sscfoodWeight1 = sscData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (rgrData != null) {
                            rgrTotalWeight1 = rgrData.getJSONObject("weight").getDouble("total")
                            rgrresidualWeight1 = rgrData.getJSONObject("weight").getDouble("residual")
                            rgrrecyclableWeight1 = rgrData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            rgrfoodWeight1 = rgrData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                    }
                }
                for (i in sortedArray.indices) {
                    val item = sortedArray[i]
                    val createdAt = item.getJSONObject("createdAt")
                    val createdAtDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        .format(Date(createdAt.getLong("seconds") * 1000))
                
                    if (createdAtDate == previousDate2) {
                        overallWeight2 = item.getDouble("overall_weight")
                        val citData = item.optJSONObject("CIT")
                        val ceafaData = item.optJSONObject("CEAFA")
                        val cicsData = item.optJSONObject("CICS")
                        val steerHubData = item.optJSONObject("STEER_Hub")
                        val gymnasiumData = item.optJSONObject("Gymnasium")
                        val sscData = item.optJSONObject("SSC")
                        val rgrData = item.optJSONObject("RGR")
    

                        if (citData != null) {
                            citTotalWeight2 = citData.getJSONObject("weight").getDouble("total")
                            citresidualWeight2 = citData.getJSONObject("weight").getDouble("residual")
                            citrecyclableWeight2 = citData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            citfoodWeight2 = citData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (ceafaData != null) {
                            ceafaTotalWeight2 = ceafaData.getJSONObject("weight").getDouble("total")
                            ceafaresidualWeight2 = ceafaData.getJSONObject("weight").getDouble("residual")
                            ceafarecyclableWeight2 = ceafaData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            ceafafoodWeight2 = ceafaData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (cicsData != null) {
                            cicsTotalWeight2 = cicsData.getJSONObject("weight").getDouble("total")
                            cicsresidualWeight2 = cicsData.getJSONObject("weight").getDouble("residual")
                            cicsrecyclableWeight2 = cicsData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            cicsfoodWeight2 = cicsData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (steerHubData != null) {
                            steerHubTotalWeight2 = steerHubData.getJSONObject("weight").getDouble("total")
                            steerHubresidualWeight2 = steerHubData.getJSONObject("weight").getDouble("residual")
                            steerHubrecyclableWeight2 = steerHubData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            steerHubfoodWeight2 = steerHubData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (gymnasiumData != null) {
                            gymnasiumTotalWeight2 = gymnasiumData.getJSONObject("weight").getDouble("total")
                            gymnasiumresidualWeight2 = gymnasiumData.getJSONObject("weight").getDouble("residual")
                            gymnasiumrecyclableWeight2 = gymnasiumData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            gymnasiumfoodWeight2 = gymnasiumData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (sscData != null) {
                            sscTotalWeight2 = sscData.getJSONObject("weight").getDouble("total")
                            sscresidualWeight2 = sscData.getJSONObject("weight").getDouble("residual")
                            sscrecyclableWeight2 = sscData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            sscfoodWeight2 = sscData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (rgrData != null) {
                            rgrTotalWeight2 = rgrData.getJSONObject("weight").getDouble("total")
                            rgrresidualWeight2 = rgrData.getJSONObject("weight").getDouble("residual")
                            rgrrecyclableWeight2 = rgrData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            rgrfoodWeight2 = rgrData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                    }
                }
                for (i in sortedArray.indices) {
                    val item = sortedArray[i]
                    val createdAt = item.getJSONObject("createdAt")
                    val createdAtDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        .format(Date(createdAt.getLong("seconds") * 1000))
                
                    if (createdAtDate == previousDate3) {
                        overallWeight3 = item.getDouble("overall_weight")
                        val citData = item.optJSONObject("CIT")
                        val ceafaData = item.optJSONObject("CEAFA")
                        val cicsData = item.optJSONObject("CICS")
                        val steerHubData = item.optJSONObject("STEER_Hub")
                        val gymnasiumData = item.optJSONObject("Gymnasium")
                        val sscData = item.optJSONObject("SSC")
                        val rgrData = item.optJSONObject("RGR")
    

                        if (citData != null) {
                            citTotalWeight3 = citData.getJSONObject("weight").getDouble("total")
                            citresidualWeight3 = citData.getJSONObject("weight").getDouble("residual")
                            citrecyclableWeight3 = citData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            citfoodWeight3 = citData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (ceafaData != null) {
                            ceafaTotalWeight3 = ceafaData.getJSONObject("weight").getDouble("total")
                            ceafaresidualWeight3 = ceafaData.getJSONObject("weight").getDouble("residual")
                            ceafarecyclableWeight3 = ceafaData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            ceafafoodWeight3 = ceafaData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (cicsData != null) {
                            cicsTotalWeight3 = cicsData.getJSONObject("weight").getDouble("total")
                            cicsresidualWeight3 = cicsData.getJSONObject("weight").getDouble("residual")
                            cicsrecyclableWeight3 = cicsData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            cicsfoodWeight3 = cicsData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (steerHubData != null) {
                            steerHubTotalWeight3 = steerHubData.getJSONObject("weight").getDouble("total")
                            steerHubresidualWeight3 = steerHubData.getJSONObject("weight").getDouble("residual")
                            steerHubrecyclableWeight3 = steerHubData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            steerHubfoodWeight3 = steerHubData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (gymnasiumData != null) {
                            gymnasiumTotalWeight3 = gymnasiumData.getJSONObject("weight").getDouble("total")
                            gymnasiumresidualWeight3 = gymnasiumData.getJSONObject("weight").getDouble("residual")
                            gymnasiumrecyclableWeight3 = gymnasiumData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            gymnasiumfoodWeight3 = gymnasiumData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (sscData != null) {
                            sscTotalWeight3 = sscData.getJSONObject("weight").getDouble("total")
                            sscresidualWeight3 = sscData.getJSONObject("weight").getDouble("residual")
                            sscrecyclableWeight3 = sscData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            sscfoodWeight3 = sscData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (rgrData != null) {
                            rgrTotalWeight3 = rgrData.getJSONObject("weight").getDouble("total")
                            rgrresidualWeight3 = rgrData.getJSONObject("weight").getDouble("residual")
                            rgrrecyclableWeight3 = rgrData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            rgrfoodWeight3 = rgrData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                    }
                }
                for (i in sortedArray.indices) {
                    val item = sortedArray[i]
                    val createdAt = item.getJSONObject("createdAt")
                    val createdAtDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        .format(Date(createdAt.getLong("seconds") * 1000))
                
                    if (createdAtDate == previousDate4) {
                        overallWeight4 = item.getDouble("overall_weight")
                        val citData = item.optJSONObject("CIT")
                        val ceafaData = item.optJSONObject("CEAFA")
                        val cicsData = item.optJSONObject("CICS")
                        val steerHubData = item.optJSONObject("STEER_Hub")
                        val gymnasiumData = item.optJSONObject("Gymnasium")
                        val sscData = item.optJSONObject("SSC")
                        val rgrData = item.optJSONObject("RGR")
    

                        if (citData != null) {
                            citTotalWeight4 = citData.getJSONObject("weight").getDouble("total")
                            citresidualWeight4 = citData.getJSONObject("weight").getDouble("residual")
                            citrecyclableWeight4 = citData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            citfoodWeight4 = citData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (ceafaData != null) {
                            ceafaTotalWeight4 = ceafaData.getJSONObject("weight").getDouble("total")
                            ceafaresidualWeight4 = ceafaData.getJSONObject("weight").getDouble("residual")
                            ceafarecyclableWeight4 = ceafaData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            ceafafoodWeight4 = ceafaData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (cicsData != null) {
                            cicsTotalWeight4 = cicsData.getJSONObject("weight").getDouble("total")
                            cicsresidualWeight4 = cicsData.getJSONObject("weight").getDouble("residual")
                            cicsrecyclableWeight4 = cicsData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            cicsfoodWeight4 = cicsData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (steerHubData != null) {
                            steerHubTotalWeight4 = steerHubData.getJSONObject("weight").getDouble("total")
                            steerHubresidualWeight4 = steerHubData.getJSONObject("weight").getDouble("residual")
                            steerHubrecyclableWeight4 = steerHubData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            steerHubfoodWeight4 = steerHubData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (gymnasiumData != null) {
                            gymnasiumTotalWeight4 = gymnasiumData.getJSONObject("weight").getDouble("total")
                            gymnasiumresidualWeight4 = gymnasiumData.getJSONObject("weight").getDouble("residual")
                            gymnasiumrecyclableWeight4 = gymnasiumData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            gymnasiumfoodWeight4 = gymnasiumData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (sscData != null) {
                            sscTotalWeight4 = sscData.getJSONObject("weight").getDouble("total")
                            sscresidualWeight4 = sscData.getJSONObject("weight").getDouble("residual")
                            sscrecyclableWeight4 = sscData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            sscfoodWeight4 = sscData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (rgrData != null) {
                            rgrTotalWeight4 = rgrData.getJSONObject("weight").getDouble("total")
                            rgrresidualWeight4 = rgrData.getJSONObject("weight").getDouble("residual")
                            rgrrecyclableWeight4 = rgrData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            rgrfoodWeight4 = rgrData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                    }
                }
                for (i in sortedArray.indices) {
                    val item = sortedArray[i]
                    val createdAt = item.getJSONObject("createdAt")
                    val createdAtDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        .format(Date(createdAt.getLong("seconds") * 1000))
                
                    if (createdAtDate == previousDate5) {
                        overallWeight5 = item.getDouble("overall_weight")
                        val citData = item.optJSONObject("CIT")
                        val ceafaData = item.optJSONObject("CEAFA")
                        val cicsData = item.optJSONObject("CICS")
                        val steerHubData = item.optJSONObject("STEER_Hub")
                        val gymnasiumData = item.optJSONObject("Gymnasium")
                        val sscData = item.optJSONObject("SSC")
                        val rgrData = item.optJSONObject("RGR")
    

                        if (citData != null) {
                            citTotalWeight5 = citData.getJSONObject("weight").getDouble("total")
                            citresidualWeight5 = citData.getJSONObject("weight").getDouble("residual")
                            citrecyclableWeight5 = citData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            citfoodWeight5 = citData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (ceafaData != null) {
                            ceafaTotalWeight5 = ceafaData.getJSONObject("weight").getDouble("total")
                            ceafaresidualWeight5 = ceafaData.getJSONObject("weight").getDouble("residual")
                            ceafarecyclableWeight5 = ceafaData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            ceafafoodWeight5 = ceafaData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (cicsData != null) {
                            cicsTotalWeight5 = cicsData.getJSONObject("weight").getDouble("total")
                            cicsresidualWeight5 = cicsData.getJSONObject("weight").getDouble("residual")
                            cicsrecyclableWeight5 = cicsData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            cicsfoodWeight5 = cicsData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (steerHubData != null) {
                            steerHubTotalWeight5 = steerHubData.getJSONObject("weight").getDouble("total")
                            steerHubresidualWeight5 = steerHubData.getJSONObject("weight").getDouble("residual")
                            steerHubrecyclableWeight5 = steerHubData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            steerHubfoodWeight5 = steerHubData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (gymnasiumData != null) {
                            gymnasiumTotalWeight5 = gymnasiumData.getJSONObject("weight").getDouble("total")
                            gymnasiumresidualWeight5 = gymnasiumData.getJSONObject("weight").getDouble("residual")
                            gymnasiumrecyclableWeight5 = gymnasiumData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            gymnasiumfoodWeight5 = gymnasiumData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (sscData != null) {
                            sscTotalWeight5 = sscData.getJSONObject("weight").getDouble("total")
                            sscresidualWeight5 = sscData.getJSONObject("weight").getDouble("residual")
                            sscrecyclableWeight5 = sscData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            sscfoodWeight5 = sscData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (rgrData != null) {
                            rgrTotalWeight5 = rgrData.getJSONObject("weight").getDouble("total")
                            rgrresidualWeight5 = rgrData.getJSONObject("weight").getDouble("residual")
                            rgrrecyclableWeight5 = rgrData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            rgrfoodWeight5 = rgrData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                    }
                }
                for (i in sortedArray.indices) {
                    val item = sortedArray[i]
                    val createdAt = item.getJSONObject("createdAt")
                    val createdAtDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                        .format(Date(createdAt.getLong("seconds") * 1000))
                
                    if (createdAtDate == previousDate6) {
                        overallWeight6 = item.getDouble("overall_weight")
                        val citData = item.optJSONObject("CIT")
                        val ceafaData = item.optJSONObject("CEAFA")
                        val cicsData = item.optJSONObject("CICS")
                        val steerHubData = item.optJSONObject("STEER_Hub")
                        val gymnasiumData = item.optJSONObject("Gymnasium")
                        val sscData = item.optJSONObject("SSC")
                        val rgrData = item.optJSONObject("RGR")
    

                        if (citData != null) {
                            citTotalWeight6 = citData.getJSONObject("weight").getDouble("total")
                            citresidualWeight6 = citData.getJSONObject("weight").getDouble("residual")
                            citrecyclableWeight6 = citData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            citfoodWeight6 = citData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (ceafaData != null) {
                            ceafaTotalWeight6 = ceafaData.getJSONObject("weight").getDouble("total")
                            ceafaresidualWeight6 = ceafaData.getJSONObject("weight").getDouble("residual")
                            ceafarecyclableWeight6 = ceafaData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            ceafafoodWeight6 = ceafaData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (cicsData != null) {
                            cicsTotalWeight6 = cicsData.getJSONObject("weight").getDouble("total")
                            cicsresidualWeight6 = cicsData.getJSONObject("weight").getDouble("residual")
                            cicsrecyclableWeight6 = cicsData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            cicsfoodWeight6 = cicsData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (steerHubData != null) {
                            steerHubTotalWeight6 = steerHubData.getJSONObject("weight").getDouble("total")
                            steerHubresidualWeight6 = steerHubData.getJSONObject("weight").getDouble("residual")
                            steerHubrecyclableWeight6 = steerHubData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            steerHubfoodWeight6 = steerHubData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (gymnasiumData != null) {
                            gymnasiumTotalWeight6 = gymnasiumData.getJSONObject("weight").getDouble("total")
                            gymnasiumresidualWeight6 = gymnasiumData.getJSONObject("weight").getDouble("residual")
                            gymnasiumrecyclableWeight6 = gymnasiumData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            gymnasiumfoodWeight6 = gymnasiumData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (sscData != null) {
                            sscTotalWeight6 = sscData.getJSONObject("weight").getDouble("total")
                            sscresidualWeight6 = sscData.getJSONObject("weight").getDouble("residual")
                            sscrecyclableWeight6 = sscData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            sscfoodWeight6 = sscData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                        if (rgrData != null) {
                            rgrTotalWeight6 = rgrData.getJSONObject("weight").getDouble("total")
                            rgrresidualWeight6 = rgrData.getJSONObject("weight").getDouble("residual")
                            rgrrecyclableWeight6 = rgrData.getJSONObject("weight").getJSONObject("recyclable").getDouble("total")
                            rgrfoodWeight6 = rgrData.getJSONObject("weight").getDouble("food_waste")
                        }
    
                    }
                }
            }
        })

        
        val client3 = OkHttpClient()
        val url = "https://us-central1-artemis-b18ae.cloudfunctions.net/server/waste/latest"
        val request3 = Request.Builder()
            .url(url)
            .build()

        client3.newCall(request3).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                requireActivity().runOnUiThread {
                    showErrorMessage("Please check your Internet Connection")
                }
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call, response: Response) {
                val responseString = response.body?.string()
                try {
                    val jsonArray = JSONArray(responseString)
                    val jsonObject = jsonArray.getJSONObject(0)
                    val cicsObject: JSONObject? = try {
                        jsonObject.getJSONObject("CICS")
                    } catch (e: JSONException) {
                        null
                    }
                    if (cicsObject != null) {
                        val weightObject = cicsObject.getJSONObject("weight")
                        val cicstotal: Double? = try {
                            weightObject.getDouble("total")
                        } catch (e: JSONException) {
                            null
                        }
                        if (cicstotal != null) {
                            cicsPercentage = cicstotal
                        }
                    }
                    val citObject: JSONObject? = try {
                        jsonObject.getJSONObject("CIT")
                    } catch (e: JSONException) {
                        null
                    }
                    if (citObject != null) {
                        val weightObject = citObject.getJSONObject("weight")
                        val cittotal: Double? = try {
                            weightObject.getDouble("total")
                        } catch (e: JSONException) {
                            null
                        }
                        if (cittotal != null) {
                            citPercentage = cittotal
                        }
                    }
                    val ceaObject: JSONObject? = try {
                        jsonObject.getJSONObject("CEAFA")
                    } catch (e: JSONException) {
                        null
                    }
                    if (ceaObject != null) {
                        val weightObject = ceaObject.getJSONObject("weight")
                        val ceatotal: Double? = try {
                            weightObject.getDouble("total")
                        } catch (e: JSONException) {
                            null
                        }
                        if (ceatotal != null) {
                            ceafaPercentage = ceatotal
                        }
                    }
                    val steerObject: JSONObject? = try {
                        jsonObject.getJSONObject("STEER_Hub")
                    } catch (e: JSONException) {
                        null
                    }
                    if (steerObject != null) {
                        val weightObject = steerObject.getJSONObject("weight")
                        val steertotal: Double? = try {
                            weightObject.getDouble("total")
                        } catch (e: JSONException) {
                            null
                        }
                        if (steertotal != null) {
                            steerPercentage = steertotal
                        }
                    }
                    val gymObject: JSONObject? = try {
                        jsonObject.getJSONObject("Gymnasium")
                    } catch (e: JSONException) {
                        null
                    }
                    if (gymObject != null) {
                        val weightObject = gymObject.getJSONObject("weight")
                        val gymtotal: Double? = try {
                            weightObject.getDouble("total")
                        } catch (e: JSONException) {
                            null
                        }
                        if (gymtotal != null) {
                            gymPercentage = gymtotal
                        }
                    }
                    val sscObject: JSONObject? = try {
                        jsonObject.getJSONObject("SSC")
                    } catch (e: JSONException) {
                        null
                    }
                    if (sscObject != null) {
                        val weightObject = sscObject.getJSONObject("weight")
                        val ssctotal: Double? = try {
                            weightObject.getDouble("total")
                        } catch (e: JSONException) {
                            null
                        }
                        if (ssctotal != null) {
                            sscPercentage = ssctotal
                        }
                    }
                    val rgrObject: JSONObject? = try {
                        jsonObject.getJSONObject("RGR")
                    } catch (e: JSONException) {
                        null
                    }
                    if (rgrObject != null) {
                        val weightObject = rgrObject.getJSONObject("weight")
                        val rgrtotal: Double? = try {
                            weightObject.getDouble("total")
                        } catch (e: JSONException) {
                            null
                        }
                        if (rgrtotal != null) {
                            rgrPercentage = rgrtotal
                        }
                    }
                    if (isAdded) {
                        requireActivity().runOnUiThread {
                            binding.gymTotal.text = gymPercentage?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                            binding.ceafaTotal.text = ceafaPercentage?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                            binding.cicsTotal.text = cicsPercentage?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                            binding.sscTotal.text = sscPercentage?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                            binding.citTotal.text = citPercentage?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                            binding.rgrTotal.text = rgrPercentage?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                            binding.steerTotal.text = steerPercentage?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                        }
                    }
                } catch (e: JSONException) {
                    // Handle the JSON parsing error
                    if (isAdded) {
                        requireActivity().runOnUiThread {
                            showErrorMessage("The app is on maintenance, Please comeback later.")
                        }
                    }
                }
                if (isAdded) {
                    requireActivity().runOnUiThread {
                        buildingPieChart(wasteCompPieChartperBuilding) // Refresh the pie chart
                    }
                }
            }
        })

        binding.buildingSpinner.adapter = adapterBuilding

        binding.buildingSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedBuilding = binding.buildingSpinner.selectedItem.toString()
                buildingObject = when (selectedBuilding) {
                    "CEAFA Building" -> "CEAFA"
                    "CIT Building" -> "CIT"
                    "CICS Building" -> "CICS"
                    "RGR Building" -> "RGR"
                    "Gymnasium" -> "Gymnasium"
                    "STEER Hub" -> "STEER_Hub"
                    "Student Services Center" -> "SSC"
                    else -> ""
                }

                val buildingIndex = when (buildingObject) {
                    "CEAFA" -> 0
                    "CIT" -> 1
                    "CICS" -> 2
                    "RGR" -> 3
                    "Gymnasium" -> 4
                    "STEER_Hub" -> 5
                    "SSC" -> 6
                    else -> 0
                }

                val buildingthemeColor = resolveThemeColor(requireContext(), com.google.android.material.R.attr.colorOnSecondary)

                val buildingDataSet = LineDataSet(buildingLineData[buildingIndex], "Building Waste Generated")
                buildingDataSet.setDrawValues(true)
                buildingDataSet.valueTextColor = buildingthemeColor
                buildingDataSet.color = buildingthemeColor
                buildingDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

                val residualDataSet = LineDataSet(residualLineData[buildingIndex], "Residual")
                residualDataSet.setDrawValues(true)
                residualDataSet.valueTextColor = buildingthemeColor
                residualDataSet.color = Color.RED
                residualDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

                val recyclableDataSet = LineDataSet(recyclableLineData[buildingIndex], "Recyclable")
                recyclableDataSet.setDrawValues(true)
                recyclableDataSet.valueTextColor = buildingthemeColor
                recyclableDataSet.color = Color.GREEN
                recyclableDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

                val foodDataSet = LineDataSet(foodLineData[buildingIndex], "Food Waste")
                foodDataSet.setDrawValues(true)
                foodDataSet.valueTextColor = buildingthemeColor
                foodDataSet.color = Color.YELLOW
                foodDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

                val wasteGeneratedDataSets = listOf(buildingDataSet, residualDataSet, recyclableDataSet, foodDataSet)
                val wasteGeneratedData = LineData(wasteGeneratedDataSets)

                wasteGeneratedChart.animateX(1000)
                wasteGeneratedChart.animateY(1000)
                wasteGeneratedChart.animate().alpha(1f).duration = 1000

                wasteGeneratedChart.axisLeft.textColor = themeColor
                wasteGeneratedChart.axisRight.textColor = themeColor
                wasteGeneratedChart.legend.textColor = themeColor

                wasteGeneratedChart.data = wasteGeneratedData
                wasteGeneratedChart.invalidate()


                setupPieChartL7days(wasteCompPieChart, itemsBuilding[buildingIndex])

                val decimalFormat = DecimalFormat("#.##")


                // val client2 = OkHttpClient()
                // val url = "https://us-central1-artemis-b18ae.cloudfunctions.net/server/waste/latest"
                // val request2 = Request.Builder()
                //     .url(url)
                //     .build()

                // client2.newCall(request2).enqueue(object : Callback {
                //     override fun onFailure(call: Call, e: IOException) {
                //         requireActivity().runOnUiThread {
                //             showErrorMessage("Please check your Internet Connection")
                //         }
                //     }

                //     @SuppressLint("SetTextI18n")
                //     override fun onResponse(call: Call, response: Response) {
                //         val responseString = response.body?.string()
                //         try {
                //             val jsonArray = JSONArray(responseString)
                //             val jsonObject = jsonArray.getJSONObject(0)
                //             val buildingObject: JSONObject? = try {
                //                 jsonObject.getJSONObject("$buildingObject")
                //             } catch (e: JSONException) {
                //                 null
                //             }
                //             if (buildingObject != null) {
                //                 val weightObject = buildingObject.getJSONObject("weight")
                //                 val residualWasteWeight: Double? = try {
                //                     weightObject.getDouble("residual")
                //                 } catch (e: JSONException) {
                //                     null
                //                 }
                //                 val foodWasteWeight: Double? = try {
                //                     weightObject.getDouble("food_waste")
                //                 } catch (e: JSONException) {
                //                     null
                //                 }
                //                 val recyclableWasteWeight: Double? = try {
                //                     weightObject.getDouble("recyclable")
                //                 } catch (e: JSONException) {
                //                     null
                //                 }
                //                 if (residualWasteWeight != null) {
                //                     residualPercentage = residualWasteWeight
                //                 }
                //                 if (foodWasteWeight != null) {
                //                     foodWastePercentage = foodWasteWeight
                //                 }

                //                 if (recyclableWasteWeight != null) {
                //                     recyclablePercentage = recyclableWasteWeight
                //                 }
                    
                //                 if (isAdded) {
                //                     requireActivity().runOnUiThread {
                //                         binding.displayres.text = residualWasteWeight?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                //                         binding.displayfood.text = foodWasteWeight?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                //                         binding.displayrec.text = recyclableWasteWeight?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                //                     }
                //                 }
                //             } else {
                //                 if (isAdded) {
                //                     requireActivity().runOnUiThread {
                //                         binding.displayres.text = "0 kg"
                //                         binding.displayfood.text = "0 kg"
                //                         binding.displayrec.text = "0 kg"
                //                     }
                //                 }
                //             }
                //         } catch (e: JSONException) {
                //             // Handle the JSON parsing error
                //             if (isAdded) {
                //                 requireActivity().runOnUiThread {
                //                     showErrorMessage("The app is on maintenance, Please comeback later.")
                //                 }
                //             }
                //         }
                    
                //         if (isAdded) {
                //             requireActivity().runOnUiThread {
                //                 setupPieChartL7days(wasteCompPieChart, selectedBuilding) // Refresh the pie chart
                //             }
                //         }
                //     }
                    
                // })

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.timeSpinner.adapter = adapterTime

        binding.timeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedBuilding = binding.buildingSpinner.selectedItem.toString()
                val selectedTime = binding.timeSpinner.selectedItem.toString()

                val ceafa_recyclabletotal = calculateTotalWeight(ceafarecyclableWeight, ceafarecyclableWeight1, ceafarecyclableWeight2, ceafarecyclableWeight3, ceafarecyclableWeight4, ceafarecyclableWeight5, ceafarecyclableWeight6)
                val ceafa_residualtotal = calculateTotalWeight(ceafaresidualWeight, ceafaresidualWeight1, ceafaresidualWeight2, ceafaresidualWeight3, ceafaresidualWeight4, ceafaresidualWeight5, ceafaresidualWeight6)
                val ceafa_foodtotal = calculateTotalWeight(ceafafoodWeight, ceafafoodWeight1, ceafafoodWeight2, ceafafoodWeight3, ceafafoodWeight4, ceafafoodWeight5, ceafafoodWeight6)
                val cit_recyclabletotal = calculateTotalWeight(citrecyclableWeight, citrecyclableWeight1, citrecyclableWeight2, citrecyclableWeight3, citrecyclableWeight4, citrecyclableWeight5, citrecyclableWeight6)
                val cit_residualtotal = calculateTotalWeight(citresidualWeight, citresidualWeight1, citresidualWeight2, citresidualWeight3, citresidualWeight4, citresidualWeight5, citresidualWeight6)
                val cit_foodtotal = calculateTotalWeight(citfoodWeight, citfoodWeight1, citfoodWeight2, citfoodWeight3, citfoodWeight4, citfoodWeight5, citfoodWeight6)
                val cics_recyclabletotal = calculateTotalWeight(cicsrecyclableWeight, cicsrecyclableWeight1, cicsrecyclableWeight2, cicsrecyclableWeight3, cicsrecyclableWeight4, cicsrecyclableWeight5, cicsrecyclableWeight6)
                val cics_residualtotal = calculateTotalWeight(cicsresidualWeight, cicsresidualWeight1, cicsresidualWeight2, cicsresidualWeight3, cicsresidualWeight4, cicsresidualWeight5, cicsresidualWeight6)
                val cics_foodtotal = calculateTotalWeight(cicsfoodWeight, cicsfoodWeight1, cicsfoodWeight2, cicsfoodWeight3, cicsfoodWeight4, cicsfoodWeight5, cicsfoodWeight6)
                val rgr_recyclabletotal = calculateTotalWeight(rgrrecyclableWeight, rgrrecyclableWeight1, rgrrecyclableWeight2, rgrrecyclableWeight3, rgrrecyclableWeight4, rgrrecyclableWeight5, rgrrecyclableWeight6)
                val rgr_residualtotal = calculateTotalWeight(rgrresidualWeight, rgrresidualWeight1, rgrresidualWeight2, rgrresidualWeight3, rgrresidualWeight4, rgrresidualWeight5, rgrresidualWeight6)
                val rgr_foodtotal = calculateTotalWeight(rgrfoodWeight, rgrfoodWeight1, rgrfoodWeight2, rgrfoodWeight3, rgrfoodWeight4, rgrfoodWeight5, rgrfoodWeight6)
                val steerHub_recyclabletotal = calculateTotalWeight(steerHubrecyclableWeight, steerHubrecyclableWeight1, steerHubrecyclableWeight2, steerHubrecyclableWeight3, steerHubrecyclableWeight4, steerHubrecyclableWeight5, steerHubrecyclableWeight6)
                val steerHub_residualtotal = calculateTotalWeight(steerHubresidualWeight, steerHubresidualWeight1, steerHubresidualWeight2, steerHubresidualWeight3, steerHubresidualWeight4, steerHubresidualWeight5, steerHubresidualWeight6)
                val steerHub_foodtotal = calculateTotalWeight(steerHubfoodWeight, steerHubfoodWeight1, steerHubfoodWeight2, steerHubfoodWeight3, steerHubfoodWeight4, steerHubfoodWeight5, steerHubfoodWeight6)
                val ssc_recyclabletotal = calculateTotalWeight(sscrecyclableWeight, sscrecyclableWeight1, sscrecyclableWeight2, sscrecyclableWeight3, sscrecyclableWeight4, sscrecyclableWeight5, sscrecyclableWeight6)
                val ssc_residualtotal = calculateTotalWeight(sscresidualWeight, sscresidualWeight1, sscresidualWeight2, sscresidualWeight3, sscresidualWeight4, sscresidualWeight5, sscresidualWeight6)
                val ssc_foodtotal = calculateTotalWeight(sscfoodWeight, sscfoodWeight1, sscfoodWeight2, sscfoodWeight3, sscfoodWeight4, sscfoodWeight5, sscfoodWeight6)
                val gymnasium_recyclabletotal = calculateTotalWeight(gymnasiumrecyclableWeight, gymnasiumrecyclableWeight1, gymnasiumrecyclableWeight2, gymnasiumrecyclableWeight3, gymnasiumrecyclableWeight4, gymnasiumrecyclableWeight5, gymnasiumrecyclableWeight6)
                val gymnasium_residualtotal = calculateTotalWeight(gymnasiumresidualWeight, gymnasiumresidualWeight1, gymnasiumresidualWeight2, gymnasiumresidualWeight3, gymnasiumresidualWeight4, gymnasiumresidualWeight5, gymnasiumresidualWeight6)
                val gymnasium_foodtotal = calculateTotalWeight(gymnasiumfoodWeight, gymnasiumfoodWeight1, gymnasiumfoodWeight2, gymnasiumfoodWeight3, gymnasiumfoodWeight4, gymnasiumfoodWeight5, gymnasiumfoodWeight6)

                when (selectedBuilding) {
                    "CEAFA Building" -> {
                        requireActivity().runOnUiThread {
                            binding.displayres.text = ceafa_residualtotal?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                            binding.displayfood.text = ceafa_foodtotal?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                            binding.displayrec.text = ceafa_recyclabletotal?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                        }
                    }
                    "CIT Building" -> {
                        requireActivity().runOnUiThread {
                            binding.displayres.text = cit_residualtotal?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                            binding.displayfood.text = cit_foodtotal?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                            binding.displayrec.text = cit_recyclabletotal?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                        }
                    }
                    "CICS Building" -> {
                        requireActivity().runOnUiThread {
                            binding.displayres.text = cics_residualtotal?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                            binding.displayfood.text = cics_foodtotal?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                            binding.displayrec.text = cics_recyclabletotal?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                        }
                    }
                    "RGR Building" -> {
                        requireActivity().runOnUiThread {
                            binding.displayres.text = rgr_residualtotal?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                            binding.displayfood.text = rgr_foodtotal?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                            binding.displayrec.text = rgr_recyclabletotal?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                        }
                    }
                    "Gymnasium" -> {
                        requireActivity().runOnUiThread {
                            binding.displayres.text = gymnasium_residualtotal?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                            binding.displayfood.text = gymnasium_foodtotal?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                            binding.displayrec.text = gymnasium_recyclabletotal?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                        }
                    }
                    "STEER Hub" -> {
                        requireActivity().runOnUiThread {
                            binding.displayres.text = steerHub_residualtotal?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                            binding.displayfood.text = steerHub_foodtotal?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                            binding.displayrec.text = steerHub_recyclabletotal?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                        }
                    }
                    "Student Services Center" -> {
                        requireActivity().runOnUiThread {
                            binding.displayres.text = ssc_residualtotal?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                            binding.displayfood.text = ssc_foodtotal?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                            binding.displayrec.text = ssc_recyclabletotal?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                        }
                    }
                }
                when (selectedTime) {
                    "7 days" -> {
                        requireActivity().runOnUiThread {
                            setupPieChartL7days(wasteCompPieChart, selectedBuilding) // Refresh the pie chart
                        }
                    }
                    "30 days" -> {
                        requireActivity().runOnUiThread {
                            setupPieChartL30days(wasteCompPieChart, selectedBuilding) // Refresh the pie chart
                        }
                    }
                    "Last year" -> {
                        requireActivity().runOnUiThread {
                            buildingPieChart(wasteCompPieChartperBuilding) // Refresh the pie chart
                        }
                    }

                }


            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }


        val name = arguments?.getString("name")

        if (name != null) {
            val words = name.split("\\s+".toRegex())
            if (words.size > 1) {
                requireActivity().runOnUiThread {
                    binding.name.text = words[0]
                }
            } else {
                requireActivity().runOnUiThread {
                    binding.name.text = name
                }
            }
        }

        val swipeRefreshLayout = binding.swipeRefreshLayout

        swipeRefreshLayout.setOnRefreshListener {

            val buildingIndex = when (buildingObject) {
                "CEAFA" -> 0
                "CIT" -> 1
                "CICS" -> 2
                "RGR" -> 3
                "Gymnasium" -> 4
                "STEER_Hub" -> 5
                "SSC" -> 6
                else -> 0
            }

            val buildingthemeColor = resolveThemeColor(requireContext(), com.google.android.material.R.attr.colorOnSecondary)


            val buildingDataSet = LineDataSet(buildingLineData[buildingIndex], "Building Waste Generated")
            buildingDataSet.setDrawValues(true)
            buildingDataSet.valueTextColor = buildingthemeColor
            buildingDataSet.color = buildingthemeColor
            buildingDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

            val residualDataSet = LineDataSet(residualLineData[buildingIndex], "Residual")
            residualDataSet.setDrawValues(true)
            residualDataSet.valueTextColor = buildingthemeColor
            residualDataSet.color = Color.RED
            residualDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

            val recyclableDataSet = LineDataSet(recyclableLineData[buildingIndex], "Recyclable")
            recyclableDataSet.setDrawValues(true)
            recyclableDataSet.valueTextColor = buildingthemeColor
            recyclableDataSet.color = Color.GREEN
            recyclableDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

            val foodDataSet = LineDataSet(foodLineData[buildingIndex], "Food Waste")
            foodDataSet.setDrawValues(true)
            foodDataSet.valueTextColor = buildingthemeColor
            foodDataSet.color = Color.YELLOW
            foodDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

            val wasteGeneratedDataSets = listOf(buildingDataSet, residualDataSet, recyclableDataSet, foodDataSet)
            val wasteGeneratedData = LineData(wasteGeneratedDataSets)

            wasteGeneratedChart.animateX(1000)
            wasteGeneratedChart.animateY(1000)
            wasteGeneratedChart.animate().alpha(1f).duration = 1000

            wasteGeneratedChart.axisLeft.textColor = themeColor
            wasteGeneratedChart.axisRight.textColor = themeColor
            wasteGeneratedChart.legend.textColor = themeColor

            wasteGeneratedChart.data = wasteGeneratedData
            wasteGeneratedChart.invalidate()


            swipeRefreshLayout.isRefreshing = false
        }

        return binding.root
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

    private fun buildingPieChart(wasteCompPieChartperBuilding: PieChart){

        val wasteComperBuildingColors = listOf(Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.LTGRAY, Color.MAGENTA, Color.CYAN)
        val entries: MutableList<PieEntry> = ArrayList()

        entries.add(PieEntry(cicsPercentage?.toFloat() ?: 0f, "CICS"))
        entries.add(PieEntry(ceafaPercentage?.toFloat() ?: 0f, "CEAFA"))
        entries.add(PieEntry(citPercentage?.toFloat() ?: 0f, "CIT"))
        entries.add(PieEntry(sscPercentage?.toFloat() ?: 0f, "SSC"))
        entries.add(PieEntry(gymPercentage?.toFloat() ?: 0f, "Gym"))
        entries.add(PieEntry(rgrPercentage?.toFloat() ?: 0f, "RGR"))
        entries.add(PieEntry(steerPercentage?.toFloat() ?: 0f, "STEER Hub"))

        val themeColor1 = resolveThemeColor(requireContext(), com.google.android.material.R.attr.colorOnSecondary)
        val themeColor2 = resolveThemeColor(requireContext(), com.google.android.material.R.attr.colorOnPrimary)


        val dataSet = PieDataSet(entries, "").apply {
            colors = wasteComperBuildingColors
            setDrawIcons(false)
            sliceSpace = 3f
            valueLinePart1OffsetPercentage = 80f
            valueLinePart1Length = 0.4f
            valueLinePart2Length = 0.5f
            yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
            valueTextColor = themeColor1
        }

        val data = PieData(dataSet).apply {
            setValueFormatter(PercentFormatter(wasteCompPieChartperBuilding))
            setValueTextSize(11f)
            setValueTextColor(themeColor1)
        }

        wasteCompPieChartperBuilding.apply {
            setUsePercentValues(true)
            description.isEnabled = true
            description.text = "gg"
            description.textColor = themeColor1
            legend.isEnabled = true
            legend.textColor = themeColor1
            setHoleColor(themeColor2)
            setExtraOffsets(5f, 10f, 5f, 5f)
            dragDecelerationFrictionCoef = 0.95f
            isDrawHoleEnabled = true
            holeRadius = 40f
            transparentCircleRadius = 45f
            setEntryLabelColor(themeColor1)
            setEntryLabelTextSize(12f)
            setDrawEntryLabels(false)
            rotationAngle = 0f
            animateY(1000)
            setData(data)
        }

        wasteCompPieChartperBuilding.invalidate()

    }

    private fun setupPieChartL7days(pieChart: PieChart?, building: String?) {
        if (pieChart == null || building == null) {
            // Handle the case when either pieChart or building is null
            return
        }

        val wasteCompColors = listOf(Color.GREEN, Color.RED, Color.YELLOW)

        val entries: MutableList<PieEntry> = ArrayList()

        val ceafa_recyclabletotal = calculateTotalWeight(ceafarecyclableWeight, ceafarecyclableWeight1, ceafarecyclableWeight2, ceafarecyclableWeight3, ceafarecyclableWeight4, ceafarecyclableWeight5, ceafarecyclableWeight6)
        val ceafa_residualtotal = calculateTotalWeight(ceafaresidualWeight, ceafaresidualWeight1, ceafaresidualWeight2, ceafaresidualWeight3, ceafaresidualWeight4, ceafaresidualWeight5, ceafaresidualWeight6)
        val ceafa_foodtotal = calculateTotalWeight(ceafafoodWeight, ceafafoodWeight1, ceafafoodWeight2, ceafafoodWeight3, ceafafoodWeight4, ceafafoodWeight5, ceafafoodWeight6)
        val cit_recyclabletotal = calculateTotalWeight(citrecyclableWeight, citrecyclableWeight1, citrecyclableWeight2, citrecyclableWeight3, citrecyclableWeight4, citrecyclableWeight5, citrecyclableWeight6)
        val cit_residualtotal = calculateTotalWeight(citresidualWeight, citresidualWeight1, citresidualWeight2, citresidualWeight3, citresidualWeight4, citresidualWeight5, citresidualWeight6)
        val cit_foodtotal = calculateTotalWeight(citfoodWeight, citfoodWeight1, citfoodWeight2, citfoodWeight3, citfoodWeight4, citfoodWeight5, citfoodWeight6)
        val cics_recyclabletotal = calculateTotalWeight(cicsrecyclableWeight, cicsrecyclableWeight1, cicsrecyclableWeight2, cicsrecyclableWeight3, cicsrecyclableWeight4, cicsrecyclableWeight5, cicsrecyclableWeight6)
        val cics_residualtotal = calculateTotalWeight(cicsresidualWeight, cicsresidualWeight1, cicsresidualWeight2, cicsresidualWeight3, cicsresidualWeight4, cicsresidualWeight5, cicsresidualWeight6)
        val cics_foodtotal = calculateTotalWeight(cicsfoodWeight, cicsfoodWeight1, cicsfoodWeight2, cicsfoodWeight3, cicsfoodWeight4, cicsfoodWeight5, cicsfoodWeight6)
        val rgr_recyclabletotal = calculateTotalWeight(rgrrecyclableWeight, rgrrecyclableWeight1, rgrrecyclableWeight2, rgrrecyclableWeight3, rgrrecyclableWeight4, rgrrecyclableWeight5, rgrrecyclableWeight6)
        val rgr_residualtotal = calculateTotalWeight(rgrresidualWeight, rgrresidualWeight1, rgrresidualWeight2, rgrresidualWeight3, rgrresidualWeight4, rgrresidualWeight5, rgrresidualWeight6)
        val rgr_foodtotal = calculateTotalWeight(rgrfoodWeight, rgrfoodWeight1, rgrfoodWeight2, rgrfoodWeight3, rgrfoodWeight4, rgrfoodWeight5, rgrfoodWeight6)
        val steerHub_recyclabletotal = calculateTotalWeight(steerHubrecyclableWeight, steerHubrecyclableWeight1, steerHubrecyclableWeight2, steerHubrecyclableWeight3, steerHubrecyclableWeight4, steerHubrecyclableWeight5, steerHubrecyclableWeight6)
        val steerHub_residualtotal = calculateTotalWeight(steerHubresidualWeight, steerHubresidualWeight1, steerHubresidualWeight2, steerHubresidualWeight3, steerHubresidualWeight4, steerHubresidualWeight5, steerHubresidualWeight6)
        val steerHub_foodtotal = calculateTotalWeight(steerHubfoodWeight, steerHubfoodWeight1, steerHubfoodWeight2, steerHubfoodWeight3, steerHubfoodWeight4, steerHubfoodWeight5, steerHubfoodWeight6)
        val ssc_recyclabletotal = calculateTotalWeight(sscrecyclableWeight, sscrecyclableWeight1, sscrecyclableWeight2, sscrecyclableWeight3, sscrecyclableWeight4, sscrecyclableWeight5, sscrecyclableWeight6)
        val ssc_residualtotal = calculateTotalWeight(sscresidualWeight, sscresidualWeight1, sscresidualWeight2, sscresidualWeight3, sscresidualWeight4, sscresidualWeight5, sscresidualWeight6)
        val ssc_foodtotal = calculateTotalWeight(sscfoodWeight, sscfoodWeight1, sscfoodWeight2, sscfoodWeight3, sscfoodWeight4, sscfoodWeight5, sscfoodWeight6)
        val gymnasium_recyclabletotal = calculateTotalWeight(gymnasiumrecyclableWeight, gymnasiumrecyclableWeight1, gymnasiumrecyclableWeight2, gymnasiumrecyclableWeight3, gymnasiumrecyclableWeight4, gymnasiumrecyclableWeight5, gymnasiumrecyclableWeight6)
        val gymnasium_residualtotal = calculateTotalWeight(gymnasiumresidualWeight, gymnasiumresidualWeight1, gymnasiumresidualWeight2, gymnasiumresidualWeight3, gymnasiumresidualWeight4, gymnasiumresidualWeight5, gymnasiumresidualWeight6)
        val gymnasium_foodtotal = calculateTotalWeight(gymnasiumfoodWeight, gymnasiumfoodWeight1, gymnasiumfoodWeight2, gymnasiumfoodWeight3, gymnasiumfoodWeight4, gymnasiumfoodWeight5, gymnasiumfoodWeight6)

        when (building) {
            "CEAFA Building" -> {
                entries.add(PieEntry(ceafa_recyclabletotal?.toFloat() ?: 0f, "Recyclable"))
                entries.add(PieEntry(ceafa_residualtotal?.toFloat() ?: 0f, "Residual"))
                entries.add(PieEntry(ceafa_foodtotal?.toFloat() ?: 0f, "Food Waste"))
                pieChart.invalidate()
            }
            "CIT Building" -> {
                entries.add(PieEntry(cit_recyclabletotal?.toFloat() ?: 0f, "Recyclable"))
                entries.add(PieEntry(cit_residualtotal?.toFloat() ?: 0f, "Residual"))
                entries.add(PieEntry(cit_foodtotal?.toFloat() ?: 0f, "Food Waste"))
                pieChart.invalidate()
            }
            "CICS Building" -> {
                entries.add(PieEntry(cics_recyclabletotal?.toFloat() ?: 0f, "Recyclable"))
                entries.add(PieEntry(cics_residualtotal?.toFloat() ?: 0f, "Residual"))
                entries.add(PieEntry(cics_foodtotal?.toFloat() ?: 0f, "Food Waste"))
                pieChart.invalidate()
            }
            "RGR Building" -> {
                entries.add(PieEntry(rgr_recyclabletotal?.toFloat() ?: 0f, "Recyclable"))
                entries.add(PieEntry(rgr_residualtotal?.toFloat() ?: 0f, "Residual"))
                entries.add(PieEntry(rgr_foodtotal?.toFloat() ?: 0f, "Food Waste"))
                pieChart.invalidate()
            }
            "Gymnasium" -> {
                entries.add(PieEntry(gymnasium_recyclabletotal?.toFloat() ?: 0f, "Recyclable"))
                entries.add(PieEntry(gymnasium_residualtotal?.toFloat() ?: 0f, "Residual"))
                entries.add(PieEntry(gymnasium_foodtotal?.toFloat() ?: 0f, "Food Waste"))
                pieChart.invalidate()
            }
            "STEER Hub" -> {
                entries.add(PieEntry(steerHub_recyclabletotal?.toFloat() ?: 0f, "Recyclable"))
                entries.add(PieEntry(steerHub_residualtotal?.toFloat() ?: 0f, "Residual"))
                entries.add(PieEntry(steerHub_foodtotal?.toFloat() ?: 0f, "Food Waste"))
                pieChart.invalidate()
            }
            "Student Services Center" -> {
                entries.add(PieEntry(ssc_recyclabletotal?.toFloat() ?: 0f, "Recyclable"))
                entries.add(PieEntry(ssc_residualtotal?.toFloat() ?: 0f, "Residual"))
                entries.add(PieEntry(ssc_foodtotal?.toFloat() ?: 0f, "Food Waste"))
                pieChart.invalidate()
            }
        }

        val themeColor = resolveThemeColor(requireContext(), com.google.android.material.R.attr.colorOnSecondary)
        val themeColor2 = resolveThemeColor(requireContext(), com.google.android.material.R.attr.colorOnPrimary)

        val dataSet = PieDataSet(entries, "").apply {
            colors = wasteCompColors
            setDrawIcons(false)
            sliceSpace = 3f
            valueLinePart1OffsetPercentage = 80f
            valueLinePart1Length = 0.4f
            valueLinePart2Length = 0.5f
            yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
            valueTextColor = themeColor
        }

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(11f)
        data.setValueTextColor(themeColor)

        pieChart.apply {
            setUsePercentValues(true)
            description.isEnabled = true
            description.text = "gg"
            description.textColor = themeColor
            legend.isEnabled = true
            setHoleColor(themeColor2)
            legend.textColor = themeColor
            setExtraOffsets(5f, 10f, 5f, 5f)
            dragDecelerationFrictionCoef = 0.95f
            isDrawHoleEnabled = true
            holeRadius = 40f
            transparentCircleRadius = 45f
            setEntryLabelColor(themeColor)
            setEntryLabelTextSize(12f)
            setDrawEntryLabels(false)
            rotationAngle = 0f
            animateY(1000)
        }

        pieChart.data = data
        pieChart.invalidate()
    }

    private fun calculateTotalWeight(vararg weights: Double?): Double? {
        var totalWeight: Double? = null
        for (weight in weights) {
            if (weight != null) {
                totalWeight = (totalWeight ?: 0.0) + weight
            }
        }
        return totalWeight
    }



    private fun setupPieChartL30days(pieChart: PieChart, building: String) {
        val wasteCompColors = listOf(Color.GREEN, Color.RED, Color.YELLOW)

        val entries: MutableList<PieEntry> = ArrayList()
        when (building) {
            "CEAFA Building" -> {
                entries.add(PieEntry(recyclablePercentage?.toFloat() ?: 0f, "Recyclable"))
                entries.add(PieEntry(residualPercentage?.toFloat() ?: 0f, "Residual"))
                entries.add(PieEntry(foodWastePercentage?.toFloat() ?: 0f, "Food Waste"))
                pieChart.invalidate()
            }
            "CIT Building" -> {
                entries.add(PieEntry(recyclablePercentage?.toFloat() ?: 0f, "Recyclable"))
                entries.add(PieEntry(residualPercentage?.toFloat() ?: 0f, "Residual"))
                entries.add(PieEntry(foodWastePercentage?.toFloat() ?: 0f, "Food Waste"))
                pieChart.invalidate()
            }
            "CICS Building" -> {
                entries.add(PieEntry(recyclablePercentage?.toFloat() ?: 0f, "Recyclable"))
                entries.add(PieEntry(residualPercentage?.toFloat() ?: 0f, "Residual"))
                entries.add(PieEntry(foodWastePercentage?.toFloat() ?: 0f, "Food Waste"))
                pieChart.invalidate()
            }
            "RGR Building" -> {
                entries.add(PieEntry(recyclablePercentage?.toFloat() ?: 0f, "Recyclable"))
                entries.add(PieEntry(residualPercentage?.toFloat() ?: 0f, "Residual"))
                entries.add(PieEntry(foodWastePercentage?.toFloat() ?: 0f, "Food Waste"))
                pieChart.invalidate()
            }
            "Gymnasium" -> {
                entries.add(PieEntry(recyclablePercentage?.toFloat() ?: 0f, "Recyclable"))
                entries.add(PieEntry(residualPercentage?.toFloat() ?: 0f, "Residual"))
                entries.add(PieEntry(foodWastePercentage?.toFloat() ?: 0f, "Food Waste"))
                pieChart.invalidate()
            }
            "STEER Hub" -> {
                entries.add(PieEntry(recyclablePercentage?.toFloat() ?: 0f, "Recyclable"))
                entries.add(PieEntry(residualPercentage?.toFloat() ?: 0f, "Residual"))
                entries.add(PieEntry(foodWastePercentage?.toFloat() ?: 0f, "Food Waste"))
                pieChart.invalidate()
            }
            "Student Services Center" -> {
                entries.add(PieEntry(recyclablePercentage?.toFloat() ?: 0f, "Recyclable"))
                entries.add(PieEntry(residualPercentage?.toFloat() ?: 0f, "Residual"))
                entries.add(PieEntry(foodWastePercentage?.toFloat() ?: 0f, "Food Waste"))
                pieChart.invalidate()
            }
        }

        val themeColor = resolveThemeColor(requireContext(), com.google.android.material.R.attr.colorOnSecondary)
        val themeColor2 = resolveThemeColor(requireContext(), com.google.android.material.R.attr.colorOnPrimary)

        val dataSet = PieDataSet(entries, "").apply {
            colors = wasteCompColors
            setDrawIcons(false)
            sliceSpace = 3f
            valueLinePart1OffsetPercentage = 80f
            valueLinePart1Length = 0.4f
            valueLinePart2Length = 0.5f
            yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
            valueTextColor = themeColor
        }

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(11f)
        data.setValueTextColor(themeColor)

        pieChart.apply {
            setUsePercentValues(true)
            description.isEnabled = true
            description.text = "gg"
            description.textColor = themeColor
            legend.isEnabled = true
            setHoleColor(themeColor2)
            legend.textColor = themeColor
            setExtraOffsets(5f, 10f, 5f, 5f)
            dragDecelerationFrictionCoef = 0.95f
            isDrawHoleEnabled = true
            holeRadius = 40f
            transparentCircleRadius = 45f
            setEntryLabelColor(themeColor)
            setEntryLabelTextSize(12f)
            setDrawEntryLabels(false)
            rotationAngle = 0f
            animateY(1000)
        }

        pieChart.data = data
        pieChart.invalidate()

    }
    private fun resolveThemeColor(context: Context, attr: Int): Int {
        val typedValue = TypedValue()
        context.theme.resolveAttribute(attr, typedValue, true)
        return typedValue.data
    }



}


        // Waste Generation per Building BarChart
//
//        val buildingString = arrayOf(
//            "CICS", "CEAFA", "CIT", "SSC", "Gym", "RGR", "STEER Hub"
//        )
//
//        val wasteGenPerBuildingChart: BarChart = binding.wasteGenPerBuildingChart
//
//        val cicsLineData: List<BarEntry> = listOf(
//            BarEntry(0f, 11f)
//        )
//
//        val ceafaLineData: List<BarEntry> = listOf(
//            BarEntry(1f, 2f)
//        )
//
//        val citLineData: List<BarEntry> = listOf(
//            BarEntry(2f, 2f)
//        )
//
//        val sscLineData: List<BarEntry> = listOf(
//            BarEntry(3f, 1f)
//        )
//
//        val gymLineData: List<BarEntry> = listOf(
//            BarEntry(4f, 14f)
//        )
//
//        val rgrLineData: List<BarEntry> = listOf(
//            BarEntry(5f, 4f)
//        )
//
//        val steerLineData: List<BarEntry> = listOf(
//            BarEntry(6f, 12f)
//        )
//
//        val cicsDataSet = BarDataSet(cicsLineData, "CICS")
//        cicsDataSet.color = Color.parseColor("#2d59eb")
//        val ceafaDataSet = BarDataSet(ceafaLineData, "CEAFA")
//        ceafaDataSet.color = Color.parseColor("#eb4034")
//        val citDataSet = BarDataSet(citLineData, "CIT")
//        citDataSet.color = Color.parseColor("#189e18")
//        val sscDataSet = BarDataSet(sscLineData, "SSC")
//        sscDataSet.color = Color.parseColor("#d9cb30")
//        val gymDataSet = BarDataSet(gymLineData, "Gym")
//        gymDataSet.color = Color.parseColor("#ba7a30")
//        val rgrDataSet = BarDataSet(rgrLineData, "RGR")
//        rgrDataSet.color = Color.parseColor("#872982")
//        val steerDataSet = BarDataSet(steerLineData, "STEER Hub")
//        steerDataSet.color = Color.parseColor("#329582")
//
//        val wasteGenPerBuildingDataSets: MutableList<IBarDataSet> = ArrayList()
//        wasteGenPerBuildingDataSets.add(cicsDataSet)
//        wasteGenPerBuildingDataSets.add(ceafaDataSet)
//        wasteGenPerBuildingDataSets.add(citDataSet)
//        wasteGenPerBuildingDataSets.add(sscDataSet)
//        wasteGenPerBuildingDataSets.add(gymDataSet)
//        wasteGenPerBuildingDataSets.add(rgrDataSet)
//        wasteGenPerBuildingDataSets.add(steerDataSet)
//
//        val wasteGenPerBuildingData = BarData(wasteGenPerBuildingDataSets)
//        wasteGenPerBuildingChart.data = wasteGenPerBuildingData
//
//        val wasteGenPerBuildingDataFormatter: ValueFormatter = object : ValueFormatter() {
//            override fun getAxisLabel(value: Float, axis: AxisBase): String {
//                return buildingString[value.toInt()]
//            }
//        }
//
//        val xAxisWasteGenPerBuilding: XAxis = wasteGenPerBuildingChart.xAxis
//        xAxisWasteGenPerBuilding.granularity = 1f
//        xAxisWasteGenPerBuilding.valueFormatter = wasteGenPerBuildingDataFormatter
//
//        wasteGenPerBuildingChart.invalidate() // refresh

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
import android.widget.Spinner
import androidx.fragment.app.Fragment
import android.content.Context
import android.util.TypedValue
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.formatter.PercentFormatter
import com.example.project_artemis.databinding.FragmentHomeBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
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

    private var ceafaTotalWeight: Double? = null
    private var citTotalWeight: Double? = null
    private var cicsTotalWeight: Double? = null
    private var rgrTotalWeight: Double? = null
    private var steerHubTotalWeight: Double? = null
    private var gymnasiumTotalWeight: Double? = null
    private var sscTotalWeight: Double? = null

    private var ceafaTotalWeight1: Double? = null
    private var citTotalWeight1: Double? = null
    private var cicsTotalWeigh1: Double? = null
    private var rgrTotalWeight1: Double? = null
    private var steerHubTotalWeight1: Double? = null
    private var gymnasiumTotalWeight1: Double? = null
    private var sscTotalWeight1: Double? = null

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
    ): View? {
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
                                binding.highestWeight.text = overallWeight?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
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
                                binding.lowestWeight.text = overallWeight?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
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
                            jsonObject.getInt("current_average")
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

        val overallLineData: List<List<Entry>> = listOf(
            listOf(Entry(0f, 3f), Entry(1f, 6f), Entry(2f, 7f), Entry(3f, 8f), Entry(4f, 7f), Entry(5f, overallWeight?.toFloat() ?: 0f), Entry(6f, overallWeight?.toFloat() ?: 0f)),  // CEAFA
            listOf(Entry(0f, 7f), Entry(1f, 9f), Entry(2f, 2f), Entry(3f, 3f), Entry(4f, 6f), Entry(5f, overallWeight?.toFloat() ?: 0f), Entry(6f, overallWeight?.toFloat() ?: 0f)),  // CIT
            listOf(Entry(0f, 7f), Entry(1f, 9f), Entry(2f, 12f), Entry(3f, 3f), Entry(4f, 9f), Entry(5f, overallWeight?.toFloat() ?: 0f), Entry(6f, overallWeight?.toFloat() ?: 0f)),  // CICS
            listOf(Entry(0f, 3f), Entry(1f, 6f), Entry(2f, 6f), Entry(3f, 4f), Entry(4f, 7f), Entry(5f, overallWeight?.toFloat() ?: 0f), Entry(6f, overallWeight?.toFloat() ?: 0f)),  // RGR
            listOf(Entry(0f, 7f), Entry(1f, 9f), Entry(2f, 2f), Entry(3f, 3f), Entry(4f, 6f), Entry(5f, overallWeight?.toFloat() ?: 0f), Entry(6f, overallWeight?.toFloat() ?: 0f)),  // Gym
            listOf(Entry(0f, 7f), Entry(1f, 9f), Entry(2f, 12f), Entry(3f, 3f), Entry(4f, 9f), Entry(5f, overallWeight?.toFloat() ?: 0f), Entry(6f, overallWeight?.toFloat() ?: 0f)),  // STEER Hub
            listOf(Entry(0f, 4f), Entry(1f, 4f), Entry(2f, 12f), Entry(3f, 3f), Entry(4f, 6f), Entry(5f, overallWeight?.toFloat() ?: 0f), Entry(6f, overallWeight?.toFloat() ?: 0f))  // SSC
        )

       val buildingLineData: List<List<Entry>> = listOf(
           listOf(Entry(0f, 2f), Entry(1f, 4f), Entry(2f, 6f), Entry(3f, 5f), Entry(4f, 3f), Entry(5f, ceafaTotalWeight1?.toFloat() ?: 0f), Entry(6f, ceafaTotalWeight?.toFloat() ?: 0f)),  // CEAFA
           listOf(Entry(0f, 3f), Entry(1f, 6f), Entry(2f, 6f), Entry(3f, 4f), Entry(4f, 7f), Entry(5f, citTotalWeight1?.toFloat() ?: 0f), Entry(6f, citTotalWeight?.toFloat() ?: 0f)),  // CIT
           listOf(Entry(0f, 2f), Entry(1f, 4f), Entry(2f, 8f), Entry(3f, 6f), Entry(4f, 2f), Entry(5f, cicsTotalWeigh1?.toFloat() ?: 0f), Entry(6f, cicsTotalWeight?.toFloat() ?: 0f)),  // CICS
           listOf(Entry(0f, 5f), Entry(1f, 9f), Entry(2f, 6f), Entry(3f, 4f), Entry(4f, 7f), Entry(5f, rgrTotalWeight1?.toFloat() ?: 0f), Entry(6f, rgrTotalWeight?.toFloat() ?: 0f)),  // RGR
           listOf(Entry(0f, 4f), Entry(1f, 8f), Entry(2f, 2f), Entry(3f, 3f), Entry(4f, 6f), Entry(5f, gymnasiumTotalWeight1?.toFloat() ?: 0f), Entry(6f, gymnasiumTotalWeight?.toFloat() ?: 0f)),  // Gym
           listOf(Entry(0f, 6f), Entry(1f, 7f), Entry(2f, 12f), Entry(3f, 3f), Entry(4f, 9f), Entry(5f, steerHubTotalWeight1?.toFloat() ?: 0f), Entry(6f, steerHubTotalWeight?.toFloat() ?: 0f)),  // STEER Hub
           listOf(Entry(0f, 8f), Entry(1f, 6f), Entry(2f, 12f), Entry(3f, 3f), Entry(4f, 6f), Entry(5f, sscTotalWeight1?.toFloat() ?: 0f), Entry(6f, sscTotalWeight?.toFloat() ?: 0f))  // SSC
       )
//
//        val recyclableLineData: List<List<Entry>> = listOf(
//            listOf(Entry(0f, 7f), Entry(1f, 6f), Entry(2f, 8f), Entry(3f, 5f), Entry(4f, 6f), Entry(5f, 7f), Entry(6f, 9f)),  // CEAFA
//            listOf(Entry(0f, 2f), Entry(1f, 4f), Entry(2f, 8f), Entry(3f, 6f), Entry(4f, 2f), Entry(5f, 4f), Entry(6f, 8f)),  // CIT
//            listOf(Entry(0f, 3f), Entry(1f, 6f), Entry(2f, 6f), Entry(3f, 4f), Entry(4f, 7f), Entry(5f, 8f), Entry(6f, 3f)),  // CICS
//            listOf(Entry(0f, 8f), Entry(1f, 7f), Entry(2f, 6f), Entry(3f, 4f), Entry(4f, 7f), Entry(5f, 8f), Entry(6f, 3f)),  // RGR
//            listOf(Entry(0f, 8f), Entry(1f, 7f), Entry(2f, 2f), Entry(3f, 3f), Entry(4f, 6f), Entry(5f, 7f), Entry(6f, 9f)),  // Gym
//            listOf(Entry(0f, 9f), Entry(1f, 3f), Entry(2f, 12f), Entry(3f, 3f), Entry(4f, 9f), Entry(5f, 7f), Entry(6f, 4f)),  // STEER Hub
//            listOf(Entry(0f, 5f), Entry(1f, 4f), Entry(2f, 12f), Entry(3f, 3f), Entry(4f, 6f), Entry(5f, 4f), Entry(6f, 7f))  // SSC
//        )

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

        // Waste Composition per Building Chart

        val wasteCompPieChartperBuilding = binding.wasteCompChartperBuilding

        buildingPieChart(wasteCompPieChartperBuilding) // Refresh the pie chart
        
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

                val oversllDataSet = LineDataSet(overallLineData[buildingIndex], "Overall Generated Weight")
                oversllDataSet.setDrawValues(true)
                oversllDataSet.color = Color.parseColor("#FF0000")
                oversllDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

               val buildingDataSet = LineDataSet(buildingLineData[buildingIndex], "Building Generated Weight")
               buildingDataSet.setDrawValues(true)
               buildingDataSet.color = buildingthemeColor
               buildingDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

//                val recyclableDataSet = LineDataSet(recyclableLineData[buildingIndex], "Recyclable Waste")
//                recyclableDataSet.setDrawValues(false)
//                recyclableDataSet.color = Color.parseColor("#22990b")
//                recyclableDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

                val wasteGeneratedDataSets = listOf(oversllDataSet, buildingDataSet)
                val wasteGeneratedData = LineData(wasteGeneratedDataSets)

                wasteGeneratedChart.animateX(1000)
                wasteGeneratedChart.animateY(1000)
                wasteGeneratedChart.animate().alpha(1f).setDuration(1000)

                wasteGeneratedChart.axisLeft.textColor = themeColor
                wasteGeneratedChart.axisRight.textColor = themeColor
                wasteGeneratedChart.legend.textColor = themeColor

                wasteGeneratedChart.data = wasteGeneratedData
                wasteGeneratedChart.invalidate()


                setupPieChartL7days(wasteCompPieChart, itemsBuilding[buildingIndex])

                val decimalFormat = DecimalFormat("#.##")


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
                                }
            
                                if (ceafaData != null) {
                                    ceafaTotalWeight = ceafaData.getJSONObject("weight").getDouble("total")
                                }
            
                                if (cicsData != null) {
                                    cicsTotalWeight = cicsData.getJSONObject("weight").getDouble("total")
                                }
            
                                if (steerHubData != null) {
                                    steerHubTotalWeight = steerHubData.getJSONObject("weight").getDouble("total")
                                }
            
                                if (gymnasiumData != null) {
                                    gymnasiumTotalWeight = gymnasiumData.getJSONObject("weight").getDouble("total")
                                }
            
                                if (sscData != null) {
                                    sscTotalWeight = sscData.getJSONObject("weight").getDouble("total")
                                }
            
                                if (rgrData != null) {
                                    rgrTotalWeight = rgrData.getJSONObject("weight").getDouble("total")
                                }
            
                            }
                        }
                        for (i in sortedArray.indices) {
                            val item = sortedArray[i]
                            val createdAt = item.getJSONObject("createdAt")
                            val createdAtDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                .format(Date(createdAt.getLong("seconds") * 1000))
                        
                            if (createdAtDate == previousDate) {
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
                                }
            
                                if (ceafaData != null) {
                                    ceafaTotalWeight1 = ceafaData.getJSONObject("weight").getDouble("total")
                                }
            
                                if (cicsData != null) {
                                    cicsTotalWeigh1 = cicsData.getJSONObject("weight").getDouble("total")
                                }
            
                                if (steerHubData != null) {
                                    steerHubTotalWeight1 = steerHubData.getJSONObject("weight").getDouble("total")
                                }
            
                                if (gymnasiumData != null) {
                                    gymnasiumTotalWeight1 = gymnasiumData.getJSONObject("weight").getDouble("total")
                                }
            
                                if (sscData != null) {
                                    sscTotalWeight1 = sscData.getJSONObject("weight").getDouble("total")
                                }
            
                                if (rgrData != null) {
                                    rgrTotalWeight1 = rgrData.getJSONObject("weight").getDouble("total")
                                }
            
                            }
                        }
                    }
                })



                val client2 = OkHttpClient()
                val url = "https://us-central1-artemis-b18ae.cloudfunctions.net/server/waste/latest"
                val request2 = Request.Builder()
                    .url(url)
                    .build()

                client2.newCall(request2).enqueue(object : Callback {
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
                            val buildingObject: JSONObject? = try {
                                jsonObject.getJSONObject("$buildingObject")
                            } catch (e: JSONException) {
                                null
                            }
                            if (buildingObject != null) {
                                val weightObject = buildingObject.getJSONObject("weight")
                                val residualWasteWeight: Double? = try {
                                    weightObject.getDouble("residual")
                                } catch (e: JSONException) {
                                    null
                                }
                                val foodWasteWeight: Double? = try {
                                    weightObject.getDouble("food_waste")
                                } catch (e: JSONException) {
                                    null
                                }
                                val recyclableWasteWeight: Double? = try {
                                    weightObject.getDouble("recyclable")
                                } catch (e: JSONException) {
                                    null
                                }
                                if (residualWasteWeight != null) {
                                    residualPercentage = residualWasteWeight
                                }
                                if (foodWasteWeight != null) {
                                    foodWastePercentage = foodWasteWeight
                                }

                                if (recyclableWasteWeight != null) {
                                    recyclablePercentage = recyclableWasteWeight
                                }
                    
                                if (isAdded) {
                                    requireActivity().runOnUiThread {
                                        binding.displayres.text = residualWasteWeight?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                                        binding.displayfood.text = foodWasteWeight?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                                        binding.displayrec.text = recyclableWasteWeight?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                                    }
                                }
                            } else {
                                if (isAdded) {
                                    requireActivity().runOnUiThread {
                                        binding.displayres.text = "0 kg"
                                        binding.displayfood.text = "0 kg"
                                        binding.displayrec.text = "0 kg"
                                    }
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
                                setupPieChartL7days(wasteCompPieChart, selectedBuilding) // Refresh the pie chart
                            }
                        }
                    }
                    
                    // override fun onResponse(call: Call, response: Response) {
                    //     val responseString = response.body?.string()
                    //     val jsonArray = JSONArray(responseString)
                    //     val jsonObject = jsonArray.getJSONObject(0)
                    //     val overall_weight: Double? = try {
                    //         jsonObject.getDouble("overall_weight")
                    //     } catch (e: JSONException) {
                    //         null
                    //     }
                    //     val buildingObject: JSONObject? = try {
                    //         jsonObject.getJSONObject("$buildingObject")
                    //     } catch (e: JSONException) {
                    //         null
                    //     }
                    //     if (buildingObject != null) {
                    //         val weightObject = buildingObject.getJSONObject("weight")
                    //         val residualWasteWeight: Double? = try {
                    //             weightObject.getDouble("residual")
                    //         } catch (e: JSONException) {
                    //             null
                    //         }
                    //         val foodWasteWeight: Double? = try {
                    //             weightObject.getDouble("food_waste")
                    //         } catch (e: JSONException) {
                    //             null
                    //         }
                    //         val recyclableWasteWeight: Double? = try {
                    //             weightObject.getDouble("recyclable")
                    //         } catch (e: JSONException) {
                    //             null
                    //         }
                    //         val totalweight: Double? = try {
                    //             weightObject.getDouble("total")
                    //         } catch (e: JSONException) {
                    //             null
                    //         }
                    //         if (totalweight != null && residualWasteWeight != null) {
                    //             residualPercentage = (residualWasteWeight / totalweight) * 100
                    //         }
                            
                    //         if (totalweight != null && foodWasteWeight != null) {
                    //             foodWastePercentage = (foodWasteWeight / totalweight) * 100
                    //         }
                            
                    //         if (totalweight != null && recyclableWasteWeight != null) {
                    //             recyclablePercentage = (recyclableWasteWeight / totalweight) * 100
                    //         }
                            
                    //         if (isAdded) {
                    //             requireActivity().runOnUiThread {
                    //                 binding.residualwastepercent.text = residualPercentage?.let { decimalFormat.format(it)+ "%" } ?: "0%"
                    //                 binding.foodwastepercent.text = foodWastePercentage?.let { decimalFormat.format(it)+ "%" } ?: "0%"
                    //                 binding.recyclablewastepercent.text = recyclablePercentage?.let { decimalFormat.format(it)+ "%" } ?: "0%"
                    //                 binding.alangilanTotal.text = overall_weight?.let { decimalFormat.format(it)+ " kg" } ?: "0 kg"
                    //                 binding.displayres.text = residualWasteWeight?.let { decimalFormat.format(it)+ " kg" } ?: "0 kg"
                    //                 binding.displayfood.text = foodWasteWeight?.let { decimalFormat.format(it)+ " kg" } ?: "0 kg"
                    //                 binding.displayrec.text = recyclableWasteWeight?.let { decimalFormat.format(it)+ " kg" } ?: "0 kg"
                    //             }
                    //         }
                    //     }else{
                    //         if (this@HomeFragment.isAdded) {
                    //             requireActivity().runOnUiThread {
                    //                 binding.residualwastepercent.text = "0%"
                    //                 binding.foodwastepercent.text = "0%"
                    //                 binding.recyclablewastepercent.text = "0%"
                    //                 binding.alangilanTotal.text = "0 kg"
                    //                 binding.displayres.text = "0 kg"
                    //                 binding.displayfood.text = "0 kg"
                    //                 binding.displayrec.text = "0 kg"
                    //             }
                    //         }
                    //     }
                    //     if (isAdded){
                    //         requireActivity().runOnUiThread {
                    //             setupPieChart(wasteCompPieChart, selectedBuilding) // Refresh the pie chart
                    //         }
                    //     }
                    // }
                })

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

            val oversllDataSet = LineDataSet(overallLineData[buildingIndex], "Overall Generated Weight")
            oversllDataSet.setDrawValues(true)
            oversllDataSet.color = Color.parseColor("#FF0000")
            oversllDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

            val buildingDataSet = LineDataSet(buildingLineData[buildingIndex], "Building Generated Weight")
            buildingDataSet.setDrawValues(true)
            buildingDataSet.color = buildingthemeColor
            buildingDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

//
//            val recyclableDataSet = LineDataSet(recyclableLineData[buildingIndex], "Recyclable Waste")
//            recyclableDataSet.setDrawValues(false)
//            recyclableDataSet.color = Color.parseColor("#22990b")
//            recyclableDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

            val wasteGeneratedDataSets = listOf(oversllDataSet, buildingDataSet)
            val wasteGeneratedData = LineData(wasteGeneratedDataSets)

            wasteGeneratedChart.animateX(1000)
            wasteGeneratedChart.animateY(1000)
            wasteGeneratedChart.animate().alpha(1f).setDuration(1000)

            wasteGeneratedChart.axisLeft.textColor = themeColor
            wasteGeneratedChart.axisRight.textColor = themeColor
            wasteGeneratedChart.legend.textColor = themeColor

            wasteGeneratedChart.data = wasteGeneratedData
            wasteGeneratedChart.invalidate()


            swipeRefreshLayout.isRefreshing = false
        }

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    private fun setupPieChartL7days(pieChart: PieChart, building: String) {
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

        var data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(11f)
        data.setValueTextColor(themeColor)

        pieChart.apply {
            setUsePercentValues(true)
            description.isEnabled = true
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
            animateY(1000) //line 519
            data = data
        }

        pieChart.data = data
        pieChart.invalidate()

    }

    private fun setupPieChartL30days(pieChart: PieChart, building: String) {
        val wasteCompColors = listOf(Color.GREEN, Color.RED, Color.YELLOW)

        val entries: MutableList<PieEntry> = ArrayList()
        when (building) {
            "CEAFA Building" -> {
                entries.add(PieEntry(2f, "Recyclable"))
                entries.add(PieEntry(3f, "Residual"))
                entries.add(PieEntry(4f, "Food Waste"))
                pieChart.invalidate()
            }
            "CIT Building" -> {
                entries.add(PieEntry(5f, "Recyclable"))
                entries.add(PieEntry(6f, "Residual"))
                entries.add(PieEntry(7f, "Food Waste"))
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

        var data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(11f)
        data.setValueTextColor(themeColor)

        pieChart.apply {
            setUsePercentValues(true)
            description.isEnabled = true
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
            animateY(1000) //line 519
            data = data
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
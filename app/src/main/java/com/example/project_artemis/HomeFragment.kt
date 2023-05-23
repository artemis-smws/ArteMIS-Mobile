package com.example.project_artemis

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.project_artemis.databinding.FragmentHomeBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.text.DecimalFormat

class HomeFragment : Fragment() {

    private var buildingObject: String? = null
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

        itemsTime = listOf("Week", "Month", "Year", "All Time")
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

        binding.timeSpinner.adapter = adapterTime

        val adapterBuilding = ArrayAdapter(
            requireContext(),
            R.layout.spinner_selected_item,
            itemsBuilding
        ).apply {
            setDropDownViewResource(R.layout.style_spinner)
        }

        // Waste Generated Graph
        val dayString = arrayOf(
            "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"
        )

        val wasteGeneratedChart = binding.wasteGenChart

        val foodLineData: List<List<Entry>> = listOf(
            listOf(Entry(0f, 3f), Entry(1f, 6f), Entry(2f, 7f), Entry(3f, 8f), Entry(4f, 7f), Entry(5f, 9f), Entry(6f, 6f)),  // CEAFA
            listOf(Entry(0f, 7f), Entry(1f, 9f), Entry(2f, 2f), Entry(3f, 3f), Entry(4f, 6f), Entry(5f, 7f), Entry(6f, 9f)),  // CIT
            listOf(Entry(0f, 7f), Entry(1f, 9f), Entry(2f, 12f), Entry(3f, 3f), Entry(4f, 9f), Entry(5f, 7f), Entry(6f, 4f)),  // CICS
            listOf(Entry(0f, 3f), Entry(1f, 6f), Entry(2f, 6f), Entry(3f, 4f), Entry(4f, 7f), Entry(5f, 8f), Entry(6f, 3f)),  // RGR
            listOf(Entry(0f, 7f), Entry(1f, 9f), Entry(2f, 2f), Entry(3f, 3f), Entry(4f, 6f), Entry(5f, 7f), Entry(6f, 9f)),  // Gym
            listOf(Entry(0f, 7f), Entry(1f, 9f), Entry(2f, 12f), Entry(3f, 3f), Entry(4f, 9f), Entry(5f, 7f), Entry(6f, 4f)),  // STEER Hub
            listOf(Entry(0f, 4f), Entry(1f, 4f), Entry(2f, 12f), Entry(3f, 3f), Entry(4f, 6f), Entry(5f, 4f), Entry(6f, 7f))  // SSC
        )

        val residualLineData: List<List<Entry>> = listOf(
            listOf(Entry(0f, 2f), Entry(1f, 4f), Entry(2f, 6f), Entry(3f, 5f), Entry(4f, 3f), Entry(5f, 4f), Entry(6f, 6f)),  // CEAFA
            listOf(Entry(0f, 3f), Entry(1f, 6f), Entry(2f, 6f), Entry(3f, 4f), Entry(4f, 7f), Entry(5f, 8f), Entry(6f, 3f)),  // CIT
            listOf(Entry(0f, 2f), Entry(1f, 4f), Entry(2f, 8f), Entry(3f, 6f), Entry(4f, 2f), Entry(5f, 4f), Entry(6f, 8f)),  // CICS
            listOf(Entry(0f, 3f), Entry(1f, 6f), Entry(2f, 6f), Entry(3f, 4f), Entry(4f, 7f), Entry(5f, 8f), Entry(6f, 3f)),  // RGR
            listOf(Entry(0f, 7f), Entry(1f, 9f), Entry(2f, 2f), Entry(3f, 3f), Entry(4f, 6f), Entry(5f, 7f), Entry(6f, 9f)),  // Gym
            listOf(Entry(0f, 7f), Entry(1f, 9f), Entry(2f, 12f), Entry(3f, 3f), Entry(4f, 9f), Entry(5f, 7f), Entry(6f, 4f)),  // STEER Hub
            listOf(Entry(0f, 4f), Entry(1f, 4f), Entry(2f, 12f), Entry(3f, 3f), Entry(4f, 6f), Entry(5f, 4f), Entry(6f, 7f))  // SSC
        )

        val recyclableLineData: List<List<Entry>> = listOf(
            listOf(Entry(0f, 7f), Entry(1f, 6f), Entry(2f, 8f), Entry(3f, 5f), Entry(4f, 6f), Entry(5f, 7f), Entry(6f, 9f)),  // CEAFA
            listOf(Entry(0f, 2f), Entry(1f, 4f), Entry(2f, 8f), Entry(3f, 6f), Entry(4f, 2f), Entry(5f, 4f), Entry(6f, 8f)),  // CIT
            listOf(Entry(0f, 3f), Entry(1f, 6f), Entry(2f, 6f), Entry(3f, 4f), Entry(4f, 7f), Entry(5f, 8f), Entry(6f, 3f)),  // CICS
            listOf(Entry(0f, 3f), Entry(1f, 6f), Entry(2f, 6f), Entry(3f, 4f), Entry(4f, 7f), Entry(5f, 8f), Entry(6f, 3f)),  // RGR
            listOf(Entry(0f, 7f), Entry(1f, 9f), Entry(2f, 2f), Entry(3f, 3f), Entry(4f, 6f), Entry(5f, 7f), Entry(6f, 9f)),  // Gym
            listOf(Entry(0f, 7f), Entry(1f, 9f), Entry(2f, 12f), Entry(3f, 3f), Entry(4f, 9f), Entry(5f, 7f), Entry(6f, 4f)),  // STEER Hub
            listOf(Entry(0f, 4f), Entry(1f, 4f), Entry(2f, 12f), Entry(3f, 3f), Entry(4f, 6f), Entry(5f, 4f), Entry(6f, 7f))  // SSC
        )



        val wasteGeneratedFormatter: ValueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase): String {
                return dayString[value.toInt()]
            }
        }

        val xAxisWasteGenerated: XAxis = wasteGeneratedChart.xAxis
        
        xAxisWasteGenerated.granularity = 1f
        xAxisWasteGenerated.valueFormatter = wasteGeneratedFormatter

        // Waste Composition Chart

        val wasteCompPieChart = binding.wasteCompChart

        setupPieChart(wasteCompPieChart, itemsBuilding[0])

        // Waste Generation per Building Chart

        val wasteGenPerBuildingChart = binding.wasteGenPerBuildingChart

        val cicsLineData: List<Entry> = listOf(
            Entry(0f, 11f),
            Entry(1f, 7f),
            Entry(2f, 8f),
            Entry(3f, 5f),
            Entry(4f, 3f),
            Entry(5f, 1f),
            Entry(6f, 2f)
        )

        val ceafaLineData: List<Entry> = listOf(
            Entry(0f, 2f),
            Entry(1f, 3f),
            Entry(2f, 11f),
            Entry(3f, 16f),
            Entry(4f, 4f),
            Entry(5f, 7f),
            Entry(6f, 7f)
        )

        val citLineData: List<Entry> = listOf(
            Entry(0f, 12f),
            Entry(1f, 15f),
            Entry(2f, 2f),
            Entry(3f, 12f),
            Entry(4f, 17f),
            Entry(5f, 7f),
            Entry(6f, 8f)
        )

        val sscLineData: List<Entry> = listOf(
            Entry(0f, 12f),
            Entry(1f, 6f),
            Entry(2f, 12f),
            Entry(3f, 8f),
            Entry(4f, 1f),
            Entry(5f, 2f),
            Entry(6f, 3f)
        )

        val gymLineData: List<Entry> = listOf(
            Entry(0f, 3f),
            Entry(1f, 4f),
            Entry(2f, 12f),
            Entry(3f, 10f),
            Entry(4f, 14f),
            Entry(5f, 13f),
            Entry(6f, 2f)
        )

        val rgrLineData: List<Entry> = listOf(
            Entry(0f, 18f),
            Entry(1f, 15f),
            Entry(2f, 17f),
            Entry(3f, 14f),
            Entry(4f, 3f),
            Entry(5f, 4f),
            Entry(6f, 12f)
        )

        val steerLineData: List<Entry> = listOf(
            Entry(0f, 18f),
            Entry(1f, 15f),
            Entry(2f, 17f),
            Entry(3f, 14f),
            Entry(4f, 3f),
            Entry(5f, 4f),
            Entry(6f, 12f)
        )

        val cicsDataSet = LineDataSet(cicsLineData, "CICS")
        cicsDataSet.color = Color.parseColor("#2d59eb")
        val ceafaDataSet = LineDataSet(ceafaLineData, "CEAFA")
        ceafaDataSet.color = Color.parseColor("#eb4034")
        val citDataSet = LineDataSet(citLineData, "CIT")
        citDataSet.color = Color.parseColor("#189e18")
        val sscDataSet = LineDataSet(sscLineData, "SSC")
        sscDataSet.color = Color.parseColor("#d9cb30")
        val gymDataSet = LineDataSet(gymLineData, "Gym")
        gymDataSet.color = Color.parseColor("#ba7a30")
        val rgrDataSet = LineDataSet(rgrLineData, "RGR")
        rgrDataSet.color = Color.parseColor("#872982")
        val steerDataSet = LineDataSet(steerLineData, "STEER Hub")
        steerDataSet.color = Color.parseColor("#329582")

        val wasteGenPerBuildingDataSets: MutableList<ILineDataSet> = ArrayList()
        wasteGenPerBuildingDataSets.add(cicsDataSet)
        wasteGenPerBuildingDataSets.add(ceafaDataSet)
        wasteGenPerBuildingDataSets.add(citDataSet)
        wasteGenPerBuildingDataSets.add(sscDataSet)
        wasteGenPerBuildingDataSets.add(gymDataSet)
        wasteGenPerBuildingDataSets.add(rgrDataSet)
        wasteGenPerBuildingDataSets.add(steerDataSet)

        val wasteGenPerBuildingData = LineData(wasteGenPerBuildingDataSets)
        wasteGenPerBuildingChart.data = wasteGenPerBuildingData
        wasteGenPerBuildingChart.invalidate() // refresh

        val wasteGenPerBuildingDataFormatter: ValueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase): String {
                return dayString[value.toInt()]
            }
        }

        val xAxisWasteGenPerBuilding: XAxis = wasteGenPerBuildingChart.xAxis
        xAxisWasteGenPerBuilding.granularity = 1f
        xAxisWasteGenPerBuilding.valueFormatter = wasteGenPerBuildingDataFormatter

        binding.buildingSpinner.adapter = adapterBuilding

        binding.buildingSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedBuilding = binding.buildingSpinner.selectedItem.toString()
                setupPieChart(wasteCompPieChart, selectedBuilding) // Refresh the pie chart
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

                val foodDataSet = LineDataSet(foodLineData[buildingIndex], "Food Waste")
                foodDataSet.color = Color.parseColor("#d7e605")

                val residualDataSet = LineDataSet(residualLineData[buildingIndex], "Residual Waste")
                residualDataSet.color = Color.parseColor("#e60505")

                val recyclableDataSet = LineDataSet(recyclableLineData[buildingIndex], "Recyclable Waste")
                recyclableDataSet.color = Color.parseColor("#22990b")

                val wasteGeneratedDataSets = listOf(foodDataSet, residualDataSet, recyclableDataSet)
                val wasteGeneratedData = LineData(wasteGeneratedDataSets)
                wasteGeneratedChart.animateX(1000)
                wasteGeneratedChart.animateY(1000)
                wasteGeneratedChart.animate().alpha(1f).setDuration(1000)

                wasteGeneratedChart.data = wasteGeneratedData
                wasteGeneratedChart.invalidate()

                setupPieChart(wasteCompPieChart, itemsBuilding[buildingIndex])

                val decimalFormat = DecimalFormat("#.##")

                val client2 = OkHttpClient()
                val url = "https://us-central1-artemis-b18ae.cloudfunctions.net/server/waste/latest"
                val request2 = Request.Builder()
                    .url(url)
                    .build()

                client2.newCall(request2).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        // Handle network errors here
                    }

                    @SuppressLint("SetTextI18n")
                    override fun onResponse(call: Call, response: Response) {
                        val responseString = response.body?.string()
                        val jsonArray = JSONArray(responseString)
                        val jsonObject = jsonArray.getJSONObject(0)
                        val overall_weight: Double? = try {
                            jsonObject.getDouble("overall_weight")
                        } catch (e: JSONException) {
                            null
                        }
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
                            val totalweight: Double? = try {
                                weightObject.getDouble("total")
                            } catch (e: JSONException) {
                                null
                            }
                            if (totalweight != null && residualWasteWeight != null) {
                                residualPercentage = (residualWasteWeight / totalweight) * 100
                            }
                            
                            if (totalweight != null && foodWasteWeight != null) {
                                foodWastePercentage = (foodWasteWeight / totalweight) * 100
                            }
                            
                            if (totalweight != null && recyclableWasteWeight != null) {
                                recyclablePercentage = (recyclableWasteWeight / totalweight) * 100
                            }
                            
                            if (isAdded) {
                                requireActivity().runOnUiThread {
                                    binding.residualwastepercent.text = residualPercentage?.let { decimalFormat.format(it)+ "%" } ?: "%"
                                    binding.foodwastepercent.text = foodWastePercentage?.let { decimalFormat.format(it)+ "%" } ?: "%"
                                    binding.recyclablewastepercent.text = recyclablePercentage?.let { decimalFormat.format(it)+ "%" } ?: "%"
                                    binding.alangilanTotal.text = overall_weight?.let { decimalFormat.format(it)+ " kg" } ?: "0kg"
                                    binding.displayres.text = residualWasteWeight?.let { decimalFormat.format(it)+ " kg" } ?: "0kg"
                                    binding.displayfood.text = foodWasteWeight?.let { decimalFormat.format(it)+ " kg" } ?: "0 kg"
                                    binding.displayrec.text = recyclableWasteWeight?.let { decimalFormat.format(it)+ " kg" } ?: "0 kg"
                                }
                            }
                        }else{
                            if (this@HomeFragment.isAdded) {
                                requireActivity().runOnUiThread {
                                    binding.residualwastepercent.text = "0%"
                                    binding.foodwastepercent.text = "0%"
                                    binding.recyclablewastepercent.text = "0%"
                                    binding.alangilanTotal.text = "0 kg"
                                    binding.displayres.text = "0 kg"
                                    binding.displayfood.text = "0 kg"
                                    binding.displayrec.text = "0 kg"
                                }
                            }
                        }
                    }
                })

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

        val foodDataSet = LineDataSet(foodLineData[buildingIndex], "Food Waste")
        foodDataSet.color = Color.parseColor("#d7e605")

        val residualDataSet = LineDataSet(residualLineData[buildingIndex], "Residual Waste")
        residualDataSet.color = Color.parseColor("#e60505")

        val recyclableDataSet = LineDataSet(recyclableLineData[buildingIndex], "Recyclable Waste")
        recyclableDataSet.color = Color.parseColor("#22990b")

        val wasteGeneratedDataSets = listOf(foodDataSet, residualDataSet, recyclableDataSet)
        val wasteGeneratedData = LineData(wasteGeneratedDataSets)
        wasteGeneratedChart.data = wasteGeneratedData
        wasteGeneratedChart.invalidate()

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun setupPieChart(pieChart: PieChart, building: String) {
        val wasteCompColors = listOf(Color.GREEN, Color.YELLOW, Color.RED)

        val entries: MutableList<PieEntry> = ArrayList()
        when (building) {
            "CEAFA Building" -> {
                entries.add(PieEntry(recyclablePercentage?.toFloat() ?: 0f, "Recyclable"))
                entries.add(PieEntry(foodWastePercentage?.toFloat() ?: 0f, "Food Waste"))
                entries.add(PieEntry(residualPercentage?.toFloat() ?: 0f, "Residual"))
                pieChart.invalidate()
            }
            "CIT Building" -> {
                entries.add(PieEntry(recyclablePercentage?.toFloat() ?: 0f, "Recyclable"))
                entries.add(PieEntry(foodWastePercentage?.toFloat() ?: 0f, "Food Waste"))
                entries.add(PieEntry(residualPercentage?.toFloat() ?: 0f, "Residual"))
                pieChart.invalidate()
            }
            "CICS Building" -> {
                entries.add(PieEntry(recyclablePercentage?.toFloat() ?: 0f, "Recyclable"))
                entries.add(PieEntry(foodWastePercentage?.toFloat() ?: 0f, "Food Waste"))
                entries.add(PieEntry(residualPercentage?.toFloat() ?: 0f, "Residual"))
                pieChart.invalidate()
            }
            "RGR Building" -> {
                entries.add(PieEntry(recyclablePercentage?.toFloat() ?: 0f, "Recyclable"))
                entries.add(PieEntry(foodWastePercentage?.toFloat() ?: 0f, "Food Waste"))
                entries.add(PieEntry(residualPercentage?.toFloat() ?: 0f, "Residual"))
                pieChart.invalidate()
            }
            "Gymnasium" -> {
                entries.add(PieEntry(recyclablePercentage?.toFloat() ?: 0f, "Recyclable"))
                entries.add(PieEntry(foodWastePercentage?.toFloat() ?: 0f, "Food Waste"))
                entries.add(PieEntry(residualPercentage?.toFloat() ?: 0f, "Residual"))
                pieChart.invalidate()
            }
            "STEER Hub" -> {
                entries.add(PieEntry(recyclablePercentage?.toFloat() ?: 0f, "Recyclable"))
                entries.add(PieEntry(foodWastePercentage?.toFloat() ?: 0f, "Food Waste"))
                entries.add(PieEntry(residualPercentage?.toFloat() ?: 0f, "Residual"))
                pieChart.invalidate()
            }
            "Student Services Center" -> {
                entries.add(PieEntry(recyclablePercentage?.toFloat() ?: 0f, "Recyclable"))
                entries.add(PieEntry(foodWastePercentage?.toFloat() ?: 0f, "Food Waste"))
                entries.add(PieEntry(residualPercentage?.toFloat() ?: 0f, "Residual"))
                pieChart.invalidate()
            }
        }

        val dataSet = PieDataSet(entries, "Waste Composition").apply {
            colors = wasteCompColors
            setDrawIcons(false)
            sliceSpace = 3f
            valueLinePart1OffsetPercentage = 80f
            valueLinePart1Length = 0.4f
            valueLinePart2Length = 0.5f
            yValuePosition = PieDataSet.ValuePosition.OUTSIDE_SLICE
            valueTextColor = Color.BLACK
        }

        var data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.BLACK)

        pieChart.apply {
            setUsePercentValues(true)
            description.isEnabled = true
            legend.isEnabled = true
            setExtraOffsets(5f, 10f, 5f, 5f)
            dragDecelerationFrictionCoef = 0.95f
            isDrawHoleEnabled = true
            holeRadius = 30f
            transparentCircleRadius = 45f
            setEntryLabelColor(Color.BLACK)
            setEntryLabelTextSize(12f)
            setDrawEntryLabels(false)
            rotationAngle = 0f
            animateY(1000)
            data = data
        }

        pieChart.data = data
        pieChart.invalidate()

    }

}
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.project_artemis.databinding.FragmentHomeBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.PercentFormatter
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

        itemsTime = listOf("7 days", "30 days", "Last Year", "All Time")
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

        // Print the dayString to verify the result
        for (day in dayString) {
            println(day)
        }
//        val dayString = arrayOf(
//            "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"
//        )

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

        // setupPieChart(wasteCompPieChart, itemsBuilding[0])

        // Waste Generation per Building BarChart

        val buildingString = arrayOf(
            "CICS", "CEAFA", "CIT", "SSC", "Gym", "RGR", "STEER Hub"
        )

        val wasteGenPerBuildingChart: BarChart = binding.wasteGenPerBuildingChart

        val cicsLineData: List<BarEntry> = listOf(
            BarEntry(0f, 11f)
        )

        val ceafaLineData: List<BarEntry> = listOf(
            BarEntry(1f, 2f)
        )

        val citLineData: List<BarEntry> = listOf(
            BarEntry(2f, 2f)
        )

        val sscLineData: List<BarEntry> = listOf(
            BarEntry(3f, 1f)
        )

        val gymLineData: List<BarEntry> = listOf(
            BarEntry(4f, 14f)
        )

        val rgrLineData: List<BarEntry> = listOf(
            BarEntry(5f, 4f)
        )

        val steerLineData: List<BarEntry> = listOf(
            BarEntry(6f, 12f)
        )

        val cicsDataSet = BarDataSet(cicsLineData, "CICS")
        cicsDataSet.color = Color.parseColor("#2d59eb")
        val ceafaDataSet = BarDataSet(ceafaLineData, "CEAFA")
        ceafaDataSet.color = Color.parseColor("#eb4034")
        val citDataSet = BarDataSet(citLineData, "CIT")
        citDataSet.color = Color.parseColor("#189e18")
        val sscDataSet = BarDataSet(sscLineData, "SSC")
        sscDataSet.color = Color.parseColor("#d9cb30")
        val gymDataSet = BarDataSet(gymLineData, "Gym")
        gymDataSet.color = Color.parseColor("#ba7a30")
        val rgrDataSet = BarDataSet(rgrLineData, "RGR")
        rgrDataSet.color = Color.parseColor("#872982")
        val steerDataSet = BarDataSet(steerLineData, "STEER Hub")
        steerDataSet.color = Color.parseColor("#329582")

        val wasteGenPerBuildingDataSets: MutableList<IBarDataSet> = ArrayList()
        wasteGenPerBuildingDataSets.add(cicsDataSet)
        wasteGenPerBuildingDataSets.add(ceafaDataSet)
        wasteGenPerBuildingDataSets.add(citDataSet)
        wasteGenPerBuildingDataSets.add(sscDataSet)
        wasteGenPerBuildingDataSets.add(gymDataSet)
        wasteGenPerBuildingDataSets.add(rgrDataSet)
        wasteGenPerBuildingDataSets.add(steerDataSet)

        val wasteGenPerBuildingData = BarData(wasteGenPerBuildingDataSets)
        wasteGenPerBuildingChart.data = wasteGenPerBuildingData

        val wasteGenPerBuildingDataFormatter: ValueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase): String {
                return buildingString[value.toInt()]
            }
        }

        val xAxisWasteGenPerBuilding: XAxis = wasteGenPerBuildingChart.xAxis
        xAxisWasteGenPerBuilding.granularity = 1f
        xAxisWasteGenPerBuilding.valueFormatter = wasteGenPerBuildingDataFormatter

        wasteGenPerBuildingChart.invalidate() // refresh

        // Waste Composition per Building Chart

        val wasteCompPieChartperBuilding = binding.wasteCompChartperBuilding


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

                val foodDataSet = LineDataSet(foodLineData[buildingIndex], "Food Waste")
                foodDataSet.setDrawValues(false)
                foodDataSet.color = Color.parseColor("#d7e605")
                foodDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

                val residualDataSet = LineDataSet(residualLineData[buildingIndex], "Residual Waste")
                residualDataSet.setDrawValues(false)
                residualDataSet.color = Color.parseColor("#e60505")
                residualDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

                val recyclableDataSet = LineDataSet(recyclableLineData[buildingIndex], "Recyclable Waste")
                recyclableDataSet.setDrawValues(false)
                recyclableDataSet.color = Color.parseColor("#22990b")
                recyclableDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

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
                        showErrorMessage("Please check your Internet Connection")
                    }

                    @SuppressLint("SetTextI18n")
                    override fun onResponse(call: Call, response: Response) {
                        val responseString = response.body?.string()
                        try {
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
                                        binding.alangilanTotal.text = overall_weight?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                                        binding.displayres.text = residualWasteWeight?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                                        binding.displayfood.text = foodWasteWeight?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                                        binding.displayrec.text = recyclableWasteWeight?.let { decimalFormat.format(it) + " kg" } ?: "0 kg"
                                    }
                                }
                            } else {
                                if (isAdded) {
                                    requireActivity().runOnUiThread {
                                        binding.alangilanTotal.text = "0 kg"
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
                                setupPieChart(wasteCompPieChart, selectedBuilding) // Refresh the pie chart
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
                val selectedTime = binding.buildingSpinner.selectedItem.toString()

                setupPieChartperBuilding(wasteCompPieChartperBuilding, selectedTime) // Refresh the pie chart

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

            val foodDataSet = LineDataSet(foodLineData[buildingIndex], "Food Waste")
            foodDataSet.setDrawValues(false)
            foodDataSet.color = Color.parseColor("#d7e605")
            foodDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

            val residualDataSet = LineDataSet(residualLineData[buildingIndex], "Residual Waste")
            residualDataSet.setDrawValues(false)
            residualDataSet.color = Color.parseColor("#e60505")
            residualDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

            val recyclableDataSet = LineDataSet(recyclableLineData[buildingIndex], "Recyclable Waste")
            recyclableDataSet.setDrawValues(false)
            recyclableDataSet.color = Color.parseColor("#22990b")
            recyclableDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER

            val wasteGeneratedDataSets = listOf(foodDataSet, residualDataSet, recyclableDataSet)
            val wasteGeneratedData = LineData(wasteGeneratedDataSets)

            wasteGeneratedChart.animateX(1000)
            wasteGeneratedChart.animateY(1000)
            wasteGeneratedChart.animate().alpha(1f).setDuration(1000)

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

    private fun setupPieChart(pieChart: PieChart, building: String) {
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
            holeRadius = 50f
            transparentCircleRadius = 45f
            setEntryLabelColor(Color.BLACK)
            setEntryLabelTextSize(12f)
            setDrawEntryLabels(false)
            rotationAngle = 0f
            animateY(1000) //line 519
            data = data
        }

        pieChart.data = data
        pieChart.invalidate()

    }

    private fun setupPieChartperBuilding(pieChart: PieChart, time: String) {
        val wasteCompperBuildingColors = listOf(Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW, Color.LTGRAY, Color.MAGENTA, Color.CYAN)

        val entries: MutableList<PieEntry> = ArrayList()
        when (time) {
            "Week" -> {
                entries.add(PieEntry(16f, "CICS"))
                entries.add(PieEntry(13f, "CEAFA"))
                entries.add(PieEntry(11f, "CIT"))
                entries.add(PieEntry(15f, "SSC"))
                entries.add(PieEntry(17f, "Gym"))
                entries.add(PieEntry(10f, "RGR"))
                entries.add(PieEntry(18f, "STEER Hub"))
                pieChart.invalidate()
            }
            "Month" -> {
                entries.add(PieEntry(13f, "CICS"))
                entries.add(PieEntry(11f, "CEAFA"))
                entries.add(PieEntry(16f, "CIT"))
                entries.add(PieEntry(10f, "SSC"))
                entries.add(PieEntry(15f, "Gym"))
                entries.add(PieEntry(18f, "RGR"))
                entries.add(PieEntry(17f, "STEER Hub"))
                pieChart.invalidate()
            }
            "Year" -> {
                entries.add(PieEntry(recyclablePercentage?.toFloat() ?: 0f, "CICS"))
                entries.add(PieEntry(residualPercentage?.toFloat() ?: 0f, "CEAFA"))
                entries.add(PieEntry(foodWastePercentage?.toFloat() ?: 0f, "CIT"))
                entries.add(PieEntry(recyclablePercentage?.toFloat() ?: 0f, "SSC"))
                entries.add(PieEntry(residualPercentage?.toFloat() ?: 0f, "Gym"))
                entries.add(PieEntry(foodWastePercentage?.toFloat() ?: 0f, "RGR"))
                entries.add(PieEntry(foodWastePercentage?.toFloat() ?: 0f, "STEER Hub"))
                pieChart.invalidate()
            }
            "All Time" -> {
                entries.add(PieEntry(recyclablePercentage?.toFloat() ?: 0f, "CICS"))
                entries.add(PieEntry(residualPercentage?.toFloat() ?: 0f, "CEAFA"))
                entries.add(PieEntry(foodWastePercentage?.toFloat() ?: 0f, "CIT"))
                entries.add(PieEntry(recyclablePercentage?.toFloat() ?: 0f, "SSC"))
                entries.add(PieEntry(residualPercentage?.toFloat() ?: 0f, "Gym"))
                entries.add(PieEntry(foodWastePercentage?.toFloat() ?: 0f, "RGR"))
                entries.add(PieEntry(foodWastePercentage?.toFloat() ?: 0f, "STEER Hub"))
                pieChart.invalidate()
            }
        }

        val dataSet = PieDataSet(entries, "Waste Composition").apply {
            colors = wasteCompperBuildingColors
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
            holeRadius = 50f
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
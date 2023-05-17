package com.example.project_artemis

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.project_artemis.databinding.FragmentHomeBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import okhttp3.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException


class HomeFragment : Fragment() {

    private var buildingObject: String? = null
    private lateinit var itemsBuilding: List<String>
    private lateinit var itemsTime: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentHomeBinding.inflate(inflater, container, false)








        itemsTime = listOf("Today", "Week", "Month", "Year", "All Time")
        itemsBuilding = listOf(
            "CEAFA Building",
            "CIT Building",
            "CICS Building",
            "COE Building",
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
                    "COE Building" -> "COE"
                    "Gymnasium" -> "Gym"
                    "STEER Hub" -> "STEER Hub"
                    "Student Services Center" -> "SSC"
                    else -> ""
                }

                val client2 = OkHttpClient()
                val url = "https://us-central1-artemis-b18ae.cloudfunctions.net/server/waste/latest"
                val request2 = Request.Builder()
                    .url(url)
                    .build()

                client2.newCall(request2).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        // Handle network errors here
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val responseString = response.body?.string()
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

                            if (isAdded) {
                                requireActivity().runOnUiThread {
                                    if (residualWasteWeight == null) {
                                        binding.displayres.text = "0"
                                    } else {
                                        binding.displayres.text = residualWasteWeight.toString()
                                    }
                                    if (foodWasteWeight == null) {
                                        binding.displayfood.text = "0"
                                    } else {
                                        binding.displayfood.text = foodWasteWeight.toString()
                                    }
                                    if (recyclableWasteWeight == null) {
                                        binding.displayrec.text = "0"
                                    } else {
                                        binding.displayrec.text = recyclableWasteWeight.toString()
                                    }
                                }
                            }
                        }else{
                            if (this@HomeFragment.isAdded) {
                                requireActivity().runOnUiThread {
                                    binding.displayres.text = "0"
                                    binding.displayfood.text = "0"
                                    binding.displayrec.text = "0"
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

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Waste Generated Chart

        val dayString = arrayOf(
            "1h",
            "2h",
            "3h",
            "4h",
            "5h",
            "6h",
            "7h",
            "8h",
            "9h",
            "10h",
            "11h",
            "12h",
            "13h",
            "14h",
            "15h",
            "16h",
            "17h",
            "18h",
            "19h",
            "20h",
            "21h",
            "22h",
            "23h",
            "24h",
        )

        val wasteGeneratedChart = requireView().findViewById<LineChart>(R.id.wasteGenChart)

        val foodLineData: List<Entry> = listOf(
            Entry(0f, 81f),
            Entry(1f, 70f),
            Entry(2f, 23f),
            Entry(3f, 64f),
            Entry(4f, 17f),
            Entry(5f, 82f),
            Entry(6f, 23f)
        )

        val residualLineData: List<Entry> = listOf(
            Entry(0f, 21f),
            Entry(1f, 14f),
            Entry(2f, 18f),
            Entry(3f, 16f),
            Entry(4f, 2f),
            Entry(5f, 24f),
            Entry(6f, 38f)
        )

        val recyclableLineData: List<Entry> = listOf(
            Entry(0f, 76f),
            Entry(1f, 96f),
            Entry(2f, 12f),
            Entry(3f, 32f),
            Entry(4f, 16f),
            Entry(5f, 77f),
            Entry(6f, 49f)
        )

        val foodDataSet = LineDataSet(foodLineData, "Food Waste")
        foodDataSet.color = Color.parseColor("#FF5858")
        val residualDataSet = LineDataSet(residualLineData, "Residual Waste")
        residualDataSet.color = Color.parseColor("#4F231F")
        val recyclableDataSet = LineDataSet(recyclableLineData, "Recyclable Waste")
        recyclableDataSet.color = Color.parseColor("#845F23")

        val wasteGeneratedDataSets: MutableList<ILineDataSet> = ArrayList()
        wasteGeneratedDataSets.add(foodDataSet)
        wasteGeneratedDataSets.add(residualDataSet)
        wasteGeneratedDataSets.add(recyclableDataSet)

        val wasteGeneratedData = LineData(wasteGeneratedDataSets)
        wasteGeneratedChart.data = wasteGeneratedData
        wasteGeneratedChart.invalidate() // refresh

        val wasteGeneratedFormatter: ValueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase): String {
                return dayString.get(value.toInt())
            }
        }

        val xAxisWasteGenerated: XAxis = wasteGeneratedChart.xAxis
        xAxisWasteGenerated.granularity = 1f
        xAxisWasteGenerated.valueFormatter = wasteGeneratedFormatter

        // Waste Composition Chart

        val wasteCompPieChart = requireView().findViewById<PieChart>(R.id.wasteCompChart)

        val wasteCompColors = listOf(Color.RED, Color.BLUE, Color.GREEN)
        val entries: MutableList<PieEntry> = ArrayList()
        entries.add(PieEntry(50f, "Recyclable Waste"))
        entries.add(PieEntry(30f, "Food Waste"))
        entries.add(PieEntry(20f, "Residual Waste"))

        val wasteCompDataSet = PieDataSet(entries, "Waste Composition")
        wasteCompDataSet.colors = wasteCompColors

        val data = PieData(wasteCompDataSet)
        wasteCompPieChart.data = data
        wasteCompPieChart.invalidate()

        // Waste Generation per Building Chart

        val wasteGenPerBuildingChart = requireView().findViewById<LineChart>(R.id.wasteGenPerBuildingChart)

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

        val acesLineData: List<Entry> = listOf(
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
        val acesDataSet = LineDataSet(acesLineData, "ACES")
        acesDataSet.color = Color.parseColor("#872982")

        val wasteGenPerBuildingDataSets: MutableList<ILineDataSet> = ArrayList()
        wasteGenPerBuildingDataSets.add(cicsDataSet)
        wasteGenPerBuildingDataSets.add(ceafaDataSet)
        wasteGenPerBuildingDataSets.add(citDataSet)
        wasteGenPerBuildingDataSets.add(sscDataSet)
        wasteGenPerBuildingDataSets.add(gymDataSet)
        wasteGenPerBuildingDataSets.add(citDataSet)

        val wasteGenPerBuildingData = LineData(wasteGenPerBuildingDataSets)
        wasteGenPerBuildingChart.data = wasteGenPerBuildingData
        wasteGenPerBuildingChart.invalidate() // refresh

        val wasteGenPerBuildingDataFormatter: ValueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase): String {
                return dayString.get(value.toInt())
            }
        }

        val xAxisWasteGenPerBuilding: XAxis = wasteGenPerBuildingChart.xAxis
        xAxisWasteGenPerBuilding.granularity = 1f
        xAxisWasteGenPerBuilding.valueFormatter = wasteGenPerBuildingDataFormatter



    }
    }
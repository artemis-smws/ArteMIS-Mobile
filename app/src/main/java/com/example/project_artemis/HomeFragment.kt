package com.example.project_artemis

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.*
import org.json.JSONObject
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException
import com.example.project_artemis.databinding.FragmentHomeBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

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

        itemsTime = listOf("24hr", "7d", "1m", "1y", "2y", "3y", "4y", "5y", "All Time")
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

        binding.dashboardTitle.text = getString(R.string.Dashboard)
        binding.thisyourdashboard.text = getString(R.string.this_is_you_dashboard)
        binding.Hello.text = getString(R.string.HELLO)
        binding.mostGatheredWaste.text = getString(R.string.Mostgatheredwaste)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lineData = listOf(
            Entry(1f, 4f),
            Entry(2f, 7f),
            Entry(3f, 2f)
        )

        val dataSet = LineDataSet(lineData, "Waste Chart")
        val lineDatas = LineData(dataSet)

        val lineChart = requireView().findViewById<LineChart>(R.id.chart)
        lineChart.data = lineDatas
        lineChart.invalidate()

    }
    }
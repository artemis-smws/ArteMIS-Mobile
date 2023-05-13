package com.example.project_artemis

// import okhttp3.OkHttpClient
// import okhttp3.Request
// import okhttp3.*
// import org.json.JSONArray
// import java.io.IOException
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.project_artemis.databinding.FragmentHomeBinding
import com.github.mikephil.charting.*
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class HomeFragment : Fragment() {

    private lateinit var spinnerBuilding: Spinner
    private lateinit var spinnerTime: Spinner
    private lateinit var itemsBuilding: List<String>
    private lateinit var itemsTime: List<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentHomeBinding.inflate(inflater, container, false)

        // val client = OkHttpClient()
        // val request = Request.Builder()
        //     .url("https://us-central1-artemis-b18ae.cloudfunctions.net/server/waste/latest")
        //     .build()
        
        // client.newCall(request).enqueue(object : Callback {
        //     override fun onFailure(call: Call, e: IOException) {
        //         // Handle network errors here
        //     }
        
        //     override fun onResponse(call: Call, response: Response) {
        //         val responseString = response.body?.string()
        //         val jsonArray = JSONArray(responseString)
        //         val wasteObject = jsonArray.getJSONObject(0)
        //         val foodWaste = wasteObject.getJSONObject("food_waste")
        //         val foodWasteWeight = hazardousWaste.getInt("weight")
        //         val residualWaste = wasteObject.getJSONObject("residual")
        //         val residualWasteWeight = residualWaste.getInt("weight")
        //         val recyclableWaste = wasteObject.getJSONObject("recyclable")
        //         val recyclableWasteWeight = recyclableWaste.getInt("weight")
        
        //         if (isAdded) {
        //             requireActivity().runOnUiThread {
        //                 binding.displayfood.text = foodWasteWeight.toString()
        //                 binding.displayres.text = residualWasteWeight.toString()
        //                 binding.displayrec.text = recyclableWasteWeight.toString()
        //             }
        //         }
        //     }
        // })

        val name = arguments?.getString("name")

        if (name != null) {
            val words = name.split("\\s+".toRegex())
            if (words.size > 1) {
                binding.name.text = "${words[0]} ${words[1]}"
            } else {
                binding.name.text = name
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

        spinnerBuilding = view.findViewById(R.id.buildingSpinner)
        spinnerTime = view.findViewById(R.id.timeSpinner)

        itemsTime = listOf("24hr", "7d", "1m", "1y", "2y", "3y", "4y", "5y", "All Time")
        itemsBuilding = listOf("CEAFA Building",
            "CIT Building",
            "CICS Building",
            "COE Building",
            "Gymnasium",
            "STEER Hub",
            "Student Services Center"
        )

        val adapterBuilding: ArrayAdapter<*> = ArrayAdapter<Any?>(
            requireContext().applicationContext,
            R.layout.spinner_selected_item,
            itemsBuilding
        )

        adapterBuilding.setDropDownViewResource(R.layout.style_spinner)
        spinnerBuilding.adapter = adapterBuilding

        val adapterTime: ArrayAdapter<*> = ArrayAdapter<Any?>(
            requireContext().applicationContext,
            R.layout.spinner_selected_item,
            itemsTime
        )

        adapterTime.setDropDownViewResource(R.layout.style_spinner)
        spinnerTime.adapter = adapterTime

        val lineData = listOf(
            Entry(1f, 4f),
            Entry(2f, 7f),
            Entry(3f, 2f)
        )

        val dataSet = LineDataSet(lineData, "Waste Chart")
        val lineDatas = LineData(dataSet)

        val lineChart = view.findViewById<LineChart>(R.id.chart)
        lineChart.data = lineDatas
        lineChart.invalidate()

    }


}
package com.example.project_artemis

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.project_artemis.databinding.FragmentHomeBinding

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

    }


}
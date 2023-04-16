package com.example.project_artemis

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.project_artemis.databinding.FragmentLocationBinding
import java.util.*

class LocationFragment : Fragment() {

    @SuppressLint("StaticFieldLeak")
    private lateinit var spinnerLocation: Spinner
    private lateinit var itemsLocation: List<String>

    private lateinit var datePickerDialog: DatePickerDialog
    @SuppressLint("StaticFieldLeak")
    private lateinit var datePickerButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val binding = FragmentLocationBinding.inflate(inflater, container, false)

        binding.wasteMappingTitle.text = getString(R.string.wasteMappingTitle)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinnerLocation = view.findViewById(R.id.locationPickerMapping)!!

        itemsLocation = listOf("Batangas State University - Alangilan", "Batangas State University - Pablo Borbon", "Batangas State University - Malvar")

        val adapter1: ArrayAdapter<*> = ArrayAdapter<Any?>(
            requireContext().applicationContext,
            R.layout.spinner_selected_item,
            itemsLocation
        )

        adapter1.setDropDownViewResource(R.layout.style_spinner)

        spinnerLocation.adapter = adapter1

        initDatePicker()
        datePickerButton = view.findViewById(R.id.datePicker)
        datePickerButton.text = getTodaysDate()

        datePickerButton.setOnClickListener {
            datePickerDialog.show()
        }
    }

    private fun initDatePicker() {
        val dateSetListener =
            OnDateSetListener { datePicker, year, month, day ->
                var month = month
                month = month + 1
                val date: String = makeDateString(day, month, year)
                datePickerButton.setText(date)
            }

        val calendar: Calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        val month: Int = calendar.get(Calendar.MONTH)
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

        val style: Int = AlertDialog.THEME_HOLO_LIGHT

        datePickerDialog = DatePickerDialog(view?.context!!, style, dateSetListener, year, month, day)

    }

    private fun makeDateString(day: Int, month: Int, year: Int): String {
        return getMonthFormat(month) + " " + day + " " + year
    }

    private fun getMonthFormat(month: Int): String {
        if (month == 1) {
            return "JAN"
        }
        if (month == 2) {
            return "FEB"
        }
        if (month == 3) {
            return "MAR"
        }
        if (month == 4) {
            return "APR"
        }
        if (month == 5) {
            return "MAY"
        }
        if (month == 6) {
            return "JUN"
        }
        if (month == 7) {
            return "JUL"
        }
        if (month == 8) {
            return "AUG"
        }
        if (month == 9) {
            return "SEP"
        }
        if (month == 10) {
            return "OCT"
        }
        if (month == 11) {
            return "NOV"
        }
        if (month == 12) {
            return "DEC"
        }

        return "JAN"
    }

    private fun getTodaysDate(): String {
        val calendar: Calendar = Calendar.getInstance()
        val year: Int = calendar.get(Calendar.YEAR)
        var month: Int = calendar.get(Calendar.MONTH)
        month = month + 1
        val day: Int = calendar.get(Calendar.DAY_OF_MONTH)

        return makeDateString(day, month, year)
    }

}
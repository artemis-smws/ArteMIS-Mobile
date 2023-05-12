package com.example.project_artemis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.project_artemis.databinding.ActivityConcernBinding

class ConcernActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConcernBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConcernBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backConcern.setOnClickListener{
            onBackPressed()
        }

        val itemsLocation = listOf(
            "Batangas State University - Alangilan",
            "Batangas State University - Pablo Borbon",
            "Batangas State University - Malvar"
        )

        val adapterLocation = ArrayAdapter(
            this,
            R.layout.spinner_selected_item2,
            itemsLocation
        ).apply {
            setDropDownViewResource(R.layout.style_spinner)
        }

        binding.locationPickerConcern.adapter = adapterLocation

        val itemsConcern = listOf(
            "General",
            "Report",
            "Problems with the App"
        )

        val adapterConcern = ArrayAdapter(
            this,
            R.layout.spinner_selected_item2,
            itemsConcern
        ).apply {
            setDropDownViewResource(R.layout.style_spinner)
        }

        binding.concernTypeInput.adapter = adapterConcern

    }
    override fun onBackPressed(){
        
        finish()
        onSaveInstanceState(Bundle())
        super.onBackPressed()
    }

}
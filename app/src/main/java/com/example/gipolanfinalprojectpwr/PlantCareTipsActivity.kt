package com.example.gipolanfinalprojectpwr


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gipolanfinalprojectpwr.databinding.ActivityPlantCareTipsBinding


class PlantCareTipsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlantCareTipsBinding
    private val plants = mutableListOf<Plant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantCareTipsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val plantNameTextView = binding.plantNameTextView
        val wateringIntervalTextView = binding.wateringIntervalTextView


        // Retrieve data from the intent
        val plantName = intent.getStringExtra("plantName")
        val wateringInterval = intent.getIntExtra("wateringInterval", 0)

        // Set the data to the views
        plantNameTextView.text = plantName
        wateringIntervalTextView.text = "Watering Interval: $wateringInterval days"
    }
}

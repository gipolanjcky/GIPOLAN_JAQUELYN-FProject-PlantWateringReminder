package com.example.gipolanfinalprojectpwr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gipolanfinalprojectpwr.databinding.ActivityPlantHealthTrackerBinding

class PlantHealthTrackerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlantHealthTrackerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantHealthTrackerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Hide the title bar
        supportActionBar?.hide()
    }

    override fun onResume() {
        super.onResume()

        val plantNameTextView = binding.plantNameTextView
        val healthStatusMessageTextView = binding.healthStatusMessageTextView
        val healthStatusTextView = binding.healthStatusTextView

        // Retrieve data from the updated intent
        val plantName = intent.getStringExtra("plantName")
        val isTaskDone = intent.getBooleanExtra("isTaskDone", true)

        // Set plant name
        plantNameTextView.text = plantName

        // Set health status based on whether the task is marked as done or not
        if (isTaskDone) {
            //if the user clicked the Done button, it means the plant is watered.
            healthStatusMessageTextView.text = "Watered the plant today!"
            val healthStatus = "Your plant is Hydrated. Water it accordingly to keep it healthy."
            healthStatusTextView.text = "Health Status: $healthStatus"
        } else {
            //if the user did not clicked the Done button, it means the plant is not watered.
            healthStatusMessageTextView.text = "Did not water the plant today!"
            val healthStatus = "Your plant is Dehydrated. Keep watering it to avoid dying."
            healthStatusTextView.text = "Health Status: $healthStatus"
        }
    }
}

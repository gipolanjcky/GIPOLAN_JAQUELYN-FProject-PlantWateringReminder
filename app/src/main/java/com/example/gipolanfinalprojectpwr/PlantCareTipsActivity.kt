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


        // Hide the title bar
        supportActionBar?.hide()

        val plantNameTextView = binding.plantNameTextView

        // Retrieve data from the intent
        val plantName = intent.getStringExtra("plantName")

        // Set plant name
        plantNameTextView.text = plantName

    }
}

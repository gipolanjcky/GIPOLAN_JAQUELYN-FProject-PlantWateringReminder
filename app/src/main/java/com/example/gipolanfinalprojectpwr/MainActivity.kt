package com.example.gipolanfinalprojectpwr

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var plantNameEditText: EditText
    private lateinit var wateringIntervalEditText: EditText
    private lateinit var addPlantButton: Button
    private lateinit var plantListView: ListView
    private val plants = mutableListOf<Plant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        plantNameEditText = findViewById(R.id.plantNameEditText)
        wateringIntervalEditText = findViewById(R.id.wateringIntervalEditText)
        addPlantButton = findViewById(R.id.addPlantButton)
        plantListView = findViewById(R.id.plantListView)

        setUpButtonListeners()

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, plants)
        plantListView.adapter = adapter


        addPlantButton.setOnClickListener {
            val plantName = plantNameEditText.text.toString()
            val wateringInterval = wateringIntervalEditText.text.toString().toInt()
            val plant = Plant(plantName, wateringInterval)
            plants.add(plant)
            adapter.notifyDataSetChanged()
            plantNameEditText.text.clear()
            wateringIntervalEditText.text.clear()

        }

    }

private fun setUpButtonListeners() {

        // Add a click listener for the plantListView items
        plantListView.setOnItemClickListener { _, _, position, _ ->
            val selectedPlant = plants[position]
            openPlantDetailsActivity(selectedPlant)
        }
    }

    private fun openPlantDetailsActivity(plant: Plant) {
        val intent = Intent(this, PlantDetailsActivity::class.java).apply {
            putExtra("plantName", plant.name)
            putExtra("wateringInterval", plant.wateringInterval)
        }
        startActivity(intent)
    }

    private fun putExtra(s: String, name: Any) {

    }

}

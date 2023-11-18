package com.example.gipolanfinalprojectpwr

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gipolanfinalprojectpwr.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val plants = mutableListOf<Plant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val plantNameEditText = binding.plantNameEditText
        val wateringIntervalEditText = binding.wateringIntervalEditText
        val addPlantButton = binding.addPlantButton
        val plantListView = binding.plantListView

        setUpButtonListeners()

        val adapter = PlantAdapter(this, plants)
        plantListView.adapter = adapter


        addPlantButton.setOnClickListener {
            val plantName = plantNameEditText.text.toString()
            val wateringIntervalText = wateringIntervalEditText.text.toString()

            if (plantName.isEmpty() || wateringIntervalText.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
            } else {
                try {
                    val wateringInterval = wateringIntervalText.toInt()
                    val plant = Plant(plantName, wateringInterval)
                    plants.add(plant)

                    // Use custom adapter
                    adapter.notifyDataSetChanged()

                    Toast.makeText(this, "Plant added successfully!", Toast.LENGTH_SHORT).show()

                    plantNameEditText.text.clear()
                    wateringIntervalEditText.text.clear()
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Invalid watering interval. Enter a number only.", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()  // Log the exception
                } catch (e: Exception) {
                    Toast.makeText(this, "An error occurred: ${e.message}", Toast.LENGTH_SHORT).show()
                    e.printStackTrace()  // Log the exception
                }
            }
        }
    }
        private fun setUpButtonListeners() {

        // Add a click listener for the plantListView items
        binding.plantListView.setOnItemClickListener { _, _, position, _ ->
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

}

package com.example.gipolanfinalprojectpwr

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class PlantHealthTrackerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.plant_health_tracker)
        supportActionBar?.title = "Plant Health Tracker" // Optional, set the action bar title
    }
}

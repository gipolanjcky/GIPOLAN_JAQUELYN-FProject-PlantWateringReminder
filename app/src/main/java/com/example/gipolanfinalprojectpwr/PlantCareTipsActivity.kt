package com.example.gipolanfinalprojectpwr


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class PlantCareTipsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.plant_care_tips)
        supportActionBar?.title = "Plant Care Tips" // Optional, set the action bar title
    }
}

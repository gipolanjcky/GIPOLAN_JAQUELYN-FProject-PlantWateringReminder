package com.example.gipolanfinalprojectpwr

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.gipolanfinalprojectpwr.databinding.ActivityViewNotificationBinding


class ViewNotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityViewNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewNotificationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Hide the title bar
        supportActionBar?.hide()


        val notificationMessageTextView: TextView = binding.notificationMessageTextView
        val doneButton: Button = binding.doneButton

        // Retrieve the notification message from the intent
        val notificationMessage = intent.getStringExtra("notificationMessage")
        notificationMessageTextView.text = notificationMessage

        // Add a click listener to the "Done" button
        doneButton.setOnClickListener {
            // Create an intent to pass data back to PlantHealthTrackerActivity
            val intent = Intent(this, PlantHealthTrackerActivity::class.java).apply {
                putExtra("plantName", intent.getStringExtra("plantName"))
                putExtra(
                    "isTaskDone",
                    true
                ) // Assuming the task is considered done when "Done" is clicked
            }

            // Start PlantHealthTrackerActivity with the updated intent
            startActivity(intent)

            // Finish the current activity
            finish()
        }

        doneButton.setOnClickListener {
            sendResultToPlantDetailsActivity(true) // Pass true if the "Done" button is clicked
        }
    }

    private fun sendResultToPlantDetailsActivity(isTaskDone: Boolean) {
        // Create an intent to pass data back to PlantDetailsActivity
        val intent = Intent().apply {
            putExtra("plantName", intent.getStringExtra("plantName"))
            putExtra("isTaskDone", isTaskDone)
        }

        // Set the result and finish the activity
        setResult(RESULT_OK, intent)
        finish()
    }
}

package com.example.gipolanfinalprojectpwr


import android.Manifest
import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.gipolanfinalprojectpwr.databinding.ActivityPlantDetailsBinding

class PlantDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlantDetailsBinding
    private val plants = mutableListOf<Plant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val plantNameTextView = binding.plantNameTextView
        val wateringIntervalTextView = binding.wateringIntervalTextView

        setUpButtonListeners()

        // Retrieve data from the intent
        val plantName = intent.getStringExtra("plantName")
        val wateringInterval = intent.getIntExtra("wateringInterval", 0)

        // Set the data to the views
        plantNameTextView.text = plantName
        wateringIntervalTextView.text = "Watering Interval: $wateringInterval days"

        binding.plantCareTipsButton.setOnClickListener {
            startPlantCareTipsActivity()
        }

        binding.plantHealthTrackerButton.setOnClickListener {
            startPlantHealthTrackerActivity()
        }

    }

    private fun setUpButtonListeners() {
        // Add a click listener for the "Set Watering Schedule" button
        binding.setWateringScheduleButton.setOnClickListener {
            openWateringScheduleDialog()
        }

        // Add a click listener for the "Reminder Notifications" button
        binding.reminderNotificationsButton.setOnClickListener {
            openReminderNotificationDialog()
        }
    }

    private fun openWateringScheduleDialog() {
        // Create a custom dialog
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_watering_schedule) // Create a layout for your dialog

        // Reference the dialog elements (e.g., EditText for schedule)
        val scheduleEditText = dialog.findViewById<EditText>(R.id.scheduleEditText)
        val saveButton = dialog.findViewById<Button>(R.id.saveButton)

        // Set a click listener for the save button
        saveButton.setOnClickListener {
            val wateringSchedule = scheduleEditText.text.toString()
            // Save the watering schedule and perform any necessary actions
            // For example, save it to a database or update your plant object
            // Then, dismiss the dialog
            dialog.dismiss()
        }

        // Show the dialog
        dialog.show()
    }

    private fun openReminderNotificationDialog() {
        // Create a custom dialog
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_reminder_notifications) // Create a layout for your dialog

        // Reference the dialog elements (e.g., CheckBox for preferences)
        val reminderCheckBox = dialog.findViewById<CheckBox>(R.id.reminderCheckBox)
        val saveButton = dialog.findViewById<Button>(R.id.saveButton2)

        // Set a click listener for the save button
        saveButton.setOnClickListener {
            val isRemindersEnabled = reminderCheckBox.isChecked
            // Update reminder preferences based on the checkbox state
            // For example, enable or disable reminders
            // Then, dismiss the dialog
            dialog.dismiss()
        }


        // Show the dialog
        dialog.show()
    }

    private fun scheduleNotification(plantName: String, wateringInterval: Int) {
        val channelId = "PlantReminderChannel"
        val notificationId = plants.size

        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        createNotificationChannel(channelId)

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Water Your Plant!")
            .setContentText("It's time to water your $plantName.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(notificationId, builder.build())
    }

    private fun createNotificationChannel(channelId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Plant Reminder"
            val descriptionText = "Plant watering reminders"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun startPlantCareTipsActivity() {
        val intent = Intent(this, PlantCareTipsActivity::class.java)
        startActivity(intent)
    }

    private fun startPlantHealthTrackerActivity() {
        val intent = Intent(this, PlantHealthTrackerActivity::class.java)
        startActivity(intent)
    }
}

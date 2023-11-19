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
import java.util.Calendar
import java.util.Locale
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import java.text.SimpleDateFormat
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.gipolanfinalprojectpwr.databinding.ActivityPlantDetailsBinding


class PlantDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlantDetailsBinding
    private val viewNotificationRequestCode = 1
    private var selectedWateringDateTime: Calendar? = null
    private val plants = mutableListOf<Plant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlantDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Hide the title bar
        supportActionBar?.hide()

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
            openPlantHealthTrackerActivity(plantName)
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

        // Add a click listener for the "Plant Care Tips" button
        binding.plantCareTipsButton.setOnClickListener {
            // Retrieve data from the intent
            val plantName = intent.getStringExtra("plantName")
            val wateringInterval = intent.getIntExtra("wateringInterval", 0)

            // Pass data to PlantCareTipsActivity
            openPlantCareTipsActivity(plantName, wateringInterval)
        }

        // Add a click listener for the "Plant Care Tips" button
        binding.plantHealthTrackerButton.setOnClickListener {
            // Retrieve data from the intent
            val plantName = intent.getStringExtra("plantName")

            // Pass data to PlantHealthTrackerActivity
            openPlantHealthTrackerActivity(plantName)
        }
    }

    private fun openPlantCareTipsActivity(plantName: String?, wateringInterval: Int) {
        val intent = Intent(this, PlantCareTipsActivity::class.java).apply {
            putExtra("plantName", plantName)
            putExtra("wateringInterval", wateringInterval)
        }
        startActivity(intent)
    }

    private fun openPlantHealthTrackerActivity(plantName: String?) {
        val intent = Intent(this, PlantHealthTrackerActivity::class.java).apply {
            putExtra("plantName", plantName)
        }

        // Start ViewNotificationActivity with the updated intent
        startActivityForResult(intent, viewNotificationRequestCode)
    }

    private fun openWateringScheduleDialog() {
        // Get the current date
        val currentDate = Calendar.getInstance()
        val year = currentDate.get(Calendar.YEAR)
        val month = currentDate.get(Calendar.MONTH)
        val day = currentDate.get(Calendar.DAY_OF_MONTH)

        // Initialize selectedWateringDateTime if it's null
        if (selectedWateringDateTime == null) {
            selectedWateringDateTime = Calendar.getInstance()
        }

        // Create a date picker dialog
        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Update the selectedWateringDateTime with the selected date
                selectedWateringDateTime?.apply {
                    set(selectedYear, selectedMonth, selectedDay)
                }

                // Create a time picker dialog
                val timePickerDialog = TimePickerDialog(
                    this,
                    { _, selectedHour, selectedMinute ->
                        // Update the selectedWateringDateTime with the selected time
                        selectedWateringDateTime?.apply {
                            set(Calendar.HOUR_OF_DAY, selectedHour)
                            set(Calendar.MINUTE, selectedMinute)
                        }

                        // Display a toast with the selected date and time (for testing)
                        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
                        val formattedDateTime = dateFormat.format(selectedWateringDateTime?.time)
                        Toast.makeText(
                            this,
                            "Watering scheduled for: $formattedDateTime",
                            Toast.LENGTH_SHORT
                        ).show()
                    },
                    currentDate.get(Calendar.HOUR_OF_DAY),
                    currentDate.get(Calendar.MINUTE),
                    true
                )

                // Show the time picker dialog
                timePickerDialog.show()
            },
            year,
            month,
            day
        )

        // Show the date picker dialog
        datePickerDialog.show()
    }


    private fun openReminderNotificationDialog() {
        // Create a custom dialog
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_reminder_notifications)

        // Reference the dialog elements (e.g., CheckBox for preferences)
        val reminderCheckBox = dialog.findViewById<CheckBox>(R.id.reminderCheckBox)
        val saveButton = dialog.findViewById<Button>(R.id.saveButton2)

        // Set a click listener for the save button
        saveButton.setOnClickListener {
            val isRemindersEnabled = reminderCheckBox.isChecked
            dialog.dismiss()

            // Display a message when reminders are enabled
            if (isRemindersEnabled) {
                Toast.makeText(this, "Reminder is enabled.", Toast.LENGTH_SHORT).show()

                // Schedule a notification when reminders are enabled
                selectedWateringDateTime?.let { wateringDateTime ->
                    val plantName = intent.getStringExtra("plantName")
                    scheduleNotification(plantName, wateringDateTime.timeInMillis)
                }

            } else {
                Toast.makeText(this, "Check the box to enable the reminder.", Toast.LENGTH_SHORT).show()
            }
        }

        dialog.show()
    }


    private fun scheduleNotification(plantName: String?, timeInMillis: Long) {
        val channelId = "PlantReminderChannel"
        val notificationId = plants.size

        val redirectIntent = Intent(this, ViewNotificationActivity::class.java).apply {
            putExtra("notificationMessage", "It's time to water your $plantName.")
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            redirectIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        createNotificationChannel(channelId)

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle("Water Your Plant!")
            .setContentText("It's time to water your $plantName.")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        // Set the notification's delivery time
        builder.setWhen(timeInMillis)

        val notificationManager = NotificationManagerCompat.from(this)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == viewNotificationRequestCode && resultCode == RESULT_OK) {
            // Handle the result from ViewNotificationActivity if needed
            // For example, you can retrieve data from the data Intent
            val isTaskDone = data?.getBooleanExtra("isTaskDone", false)

            // Start PlantHealthTrackerActivity with the updated intent
            val intent = Intent(this, PlantHealthTrackerActivity::class.java).apply {
                putExtra("plantName", intent.getStringExtra("plantName"))
                putExtra("isTaskDone", isTaskDone)
            }
            startActivity(intent)
        }
    }
}




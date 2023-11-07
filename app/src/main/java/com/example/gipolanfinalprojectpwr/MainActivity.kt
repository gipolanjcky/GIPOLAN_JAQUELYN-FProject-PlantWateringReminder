package com.example.gipolanfinalprojectpwr

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

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

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, plants)
        plantListView.adapter = adapter

        addPlantButton.setOnClickListener {
            val plantName = plantNameEditText.text.toString()
            val wateringInterval = wateringIntervalEditText.text.toString().toInt()
            val plant = Plant(plantName, wateringInterval)
            plants.add(plant)
            adapter.notifyDataSetChanged()
            scheduleNotification(plantName, wateringInterval)
            plantNameEditText.text.clear()
            wateringIntervalEditText.text.clear()
        }
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
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val name = "Plant Reminder"
            val descriptionText = "Plant watering reminders"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

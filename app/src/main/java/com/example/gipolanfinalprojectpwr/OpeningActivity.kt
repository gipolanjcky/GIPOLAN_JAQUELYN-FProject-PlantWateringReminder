package com.example.gipolanfinalprojectpwr

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity


class OpeningActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.opening_screen)

        // Hide the title bar
        supportActionBar?.hide()

        val nextButton: View = findViewById(R.id.btnNavigate)
        nextButton.setOnClickListener {

            // Start MainActivity when the button is clicked
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
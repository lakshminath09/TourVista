package com.tourvista.india

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditProfileActivity : AppCompatActivity() {

    private lateinit var editName: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPhone: EditText
    private lateinit var spinnerDestination: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_edit_profile)

        editName = findViewById(R.id.editName)
        editEmail = findViewById(R.id.editEmail)
        editPhone = findViewById(R.id.editPhone)
        spinnerDestination = findViewById(R.id.spinnerDestination)

        val btnBack = findViewById<TextView>(R.id.btnBack)
        val btnSave = findViewById<Button>(R.id.btnSave)

        val destinations = arrayOf(
            "Nature & Hill Stations",
            "Historical Places",
            "Beaches",
            "Spiritual Places",
            "Cities"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            destinations
        )

        spinnerDestination.adapter = adapter

        loadProfile()

        btnBack.setOnClickListener {
            finish()
        }

        btnSave.setOnClickListener {

            val name = editName.text.toString().trim()
            val email = editEmail.text.toString().trim()
            val phone = editPhone.text.toString().trim()

            if (name.isEmpty()) {
                editName.error = "Please enter your name"
                editName.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                editEmail.error = "Please enter your email"
                editEmail.requestFocus()
                return@setOnClickListener
            }

            if (phone.isEmpty()) {
                editPhone.error = "Please enter your phone number"
                editPhone.requestFocus()
                return@setOnClickListener
            }

            val destination =
                spinnerDestination.selectedItem.toString()

            val preferences =
                getSharedPreferences(
                    "TourVistaProfile",
                    MODE_PRIVATE
                )

            preferences.edit()
                .putString("name", name)
                .putString("email", email)
                .putString("phone", phone)
                .putString("destination", destination)
                .apply()

            Toast.makeText(
                this,
                "Profile updated successfully!",
                Toast.LENGTH_SHORT
            ).show()

            finish()
        }
    }

    private fun loadProfile() {

        val preferences =
            getSharedPreferences(
                "TourVistaProfile",
                MODE_PRIVATE
            )

        val name =
            preferences.getString(
                "name",
                "Lakshmi Devi"
            )

        val email =
            preferences.getString(
                "email",
                "lakshmidevi@example.com"
            )

        val phone =
            preferences.getString(
                "phone",
                "+91 XXXXX XXXXX"
            )

        val destination =
            preferences.getString(
                "destination",
                "Nature & Hill Stations"
            )

        editName.setText(name)
        editEmail.setText(email)
        editPhone.setText(phone)

        val destinations = arrayOf(
            "Nature & Hill Stations",
            "Historical Places",
            "Beaches",
            "Spiritual Places",
            "Cities"
        )

        val position =
            destinations.indexOf(destination)

        if (position >= 0) {
            spinnerDestination.setSelection(position)
        }
    }
}
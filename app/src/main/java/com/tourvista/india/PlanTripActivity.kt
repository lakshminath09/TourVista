package com.tourvista.india

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class PlanTripActivity : AppCompatActivity() {

    private lateinit var editDestination: EditText
    private lateinit var editStartDate: EditText
    private lateinit var editEndDate: EditText
    private lateinit var spinnerTravellers: Spinner
    private lateinit var spinnerTripType: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_plan_trip)

        val btnBack =
            findViewById<TextView>(R.id.btnBack)

        editDestination =
            findViewById(R.id.editDestination)

        editStartDate =
            findViewById(R.id.editStartDate)

        editEndDate =
            findViewById(R.id.editEndDate)

        spinnerTravellers =
            findViewById(R.id.spinnerTravellers)

        spinnerTripType =
            findViewById(R.id.spinnerTripType)

        val btnCreateTrip =
            findViewById<Button>(R.id.btnCreateTrip)

        // Back button
        btnBack.setOnClickListener {
            finish()
        }

        // Travellers
        val travellers = arrayOf(
            "1 Traveller",
            "2 Travellers",
            "3 Travellers",
            "4 Travellers",
            "5 Travellers",
            "6+ Travellers"
        )

        val travellerAdapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                travellers
            )

        spinnerTravellers.adapter =
            travellerAdapter

        // Trip type
        val tripTypes = arrayOf(
            "Adventure",
            "Beach",
            "Nature",
            "Historical",
            "Spiritual",
            "Relaxation",
            "Family"
        )

        val tripTypeAdapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                tripTypes
            )

        spinnerTripType.adapter =
            tripTypeAdapter

        // Start date
        editStartDate.setOnClickListener {
            showDatePicker(editStartDate)
        }

        // End date
        editEndDate.setOnClickListener {
            showDatePicker(editEndDate)
        }

        // Create Trip
        btnCreateTrip.setOnClickListener {

            val destination =
                editDestination.text.toString().trim()

            val startDate =
                editStartDate.text.toString().trim()

            val endDate =
                editEndDate.text.toString().trim()

            if (destination.isEmpty()) {

                editDestination.error =
                    "Enter a destination"

                editDestination.requestFocus()

                return@setOnClickListener
            }

            if (startDate.isEmpty()) {

                editStartDate.error =
                    "Select start date"

                return@setOnClickListener
            }

            if (endDate.isEmpty()) {

                editEndDate.error =
                    "Select end date"

                return@setOnClickListener
            }

            val travellers =
                spinnerTravellers.selectedItem.toString()

            val tripType =
                spinnerTripType.selectedItem.toString()

            // Save trip
            val preferences =
                getSharedPreferences(
                    "TourVistaTrips",
                    MODE_PRIVATE
                )

            preferences.edit()
                .putString(
                    "destination",
                    destination
                )
                .putString(
                    "startDate",
                    startDate
                )
                .putString(
                    "endDate",
                    endDate
                )
                .putString(
                    "travellers",
                    travellers
                )
                .putString(
                    "tripType",
                    tripType
                )
                .apply()

            Toast.makeText(
                this,
                "Trip created successfully! 🎉",
                Toast.LENGTH_SHORT
            ).show()

            finish()
        }
    }

    private fun showDatePicker(
        editText: EditText
    ) {

        val calendar =
            Calendar.getInstance()

        val year =
            calendar.get(Calendar.YEAR)

        val month =
            calendar.get(Calendar.MONTH)

        val day =
            calendar.get(Calendar.DAY_OF_MONTH)

        val datePicker =
            DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->

                    val date =
                        String.format(
                            "%02d/%02d/%04d",
                            selectedDay,
                            selectedMonth + 1,
                            selectedYear
                        )

                    editText.setText(date)
                },
                year,
                month,
                day
            )

        datePicker.show()
    }
}
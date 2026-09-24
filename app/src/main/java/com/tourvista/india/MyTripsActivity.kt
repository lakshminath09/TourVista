package com.tourvista.india

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MyTripsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_my_trips)

        val btnBack = findViewById<TextView>(R.id.btnBack)

        val btnPlanTrip =
            findViewById<Button>(R.id.btnPlanTrip)

        val tripShillong =
            findViewById<LinearLayout>(R.id.tripShillong)

        val tripGoa =
            findViewById<LinearLayout>(R.id.tripGoa)

        val tripTawang =
            findViewById<LinearLayout>(R.id.tripTawang)

        btnBack.setOnClickListener {
            finish()
        }

        tripShillong.setOnClickListener {
            Toast.makeText(
                this,
                "Shillong Escape selected",
                Toast.LENGTH_SHORT
            ).show()
        }

        tripGoa.setOnClickListener {
            Toast.makeText(
                this,
                "Goa Beach Holiday selected",
                Toast.LENGTH_SHORT
            ).show()
        }

        tripTawang.setOnClickListener {
            Toast.makeText(
                this,
                "Tawang Adventure selected",
                Toast.LENGTH_SHORT
            ).show()
        }

        btnPlanTrip.setOnClickListener {
            val intent =
                Intent(
                    this,
                    PlanTripActivity::class.java
                )

            startActivity(intent)
        }

        showCreatedTrip()
    }

    override fun onResume() {
        super.onResume()

        showCreatedTrip()
    }

    private fun showCreatedTrip() {

        val preferences =
            getSharedPreferences(
                "TourVistaTrips",
                MODE_PRIVATE
            )

        val destination =
            preferences.getString(
                "destination",
                ""
            )

        val startDate =
            preferences.getString(
                "startDate",
                ""
            )

        val endDate =
            preferences.getString(
                "endDate",
                ""
            )

        val travellers =
            preferences.getString(
                "travellers",
                ""
            )

        val tripType =
            preferences.getString(
                "tripType",
                ""
            )

        val container =
            findViewById<LinearLayout>(
                R.id.createdTripContainer
            )

        val title =
            findViewById<TextView>(
                R.id.createdTripTitle
            )

        val destinationText =
            findViewById<TextView>(
                R.id.createdTripDestination
            )

        val dates =
            findViewById<TextView>(
                R.id.createdTripDates
            )

        val details =
            findViewById<TextView>(
                R.id.createdTripDetails
            )

        if (destination!!.isNotEmpty()) {

            container.visibility = View.VISIBLE

            title.text =
                "✈️  $destination Trip"

            destinationText.text =
                "📍 $destination"

            dates.text =
                "📅 $startDate → $endDate"

            details.text =
                "👥 $travellers  •  🌟 $tripType"

        } else {

            container.visibility = View.GONE
        }
    }
}
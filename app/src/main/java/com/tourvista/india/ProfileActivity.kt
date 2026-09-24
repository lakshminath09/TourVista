package com.tourvista.india

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile)

        profileName = findViewById(R.id.profileName)

        val btnBack = findViewById<TextView>(R.id.btnBack)
        val btnEditProfile =
            findViewById<Button>(R.id.btnEditProfile)

        btnBack.setOnClickListener {
            finish()
        }

        btnEditProfile.setOnClickListener {

            val intent =
                Intent(
                    this,
                    EditProfileActivity::class.java
                )

            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

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

        profileName.text = name
    }
}
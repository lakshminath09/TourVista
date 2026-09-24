package com.tourvista.india

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)

        // -----------------------------
        // FIND VIEWS
        // -----------------------------

        val btnBack =
            findViewById<TextView>(R.id.btnBack)

        val settingProfile =
            findViewById<LinearLayout>(R.id.settingProfile)

        val settingNotifications =
            findViewById<LinearLayout>(R.id.settingNotifications)

        val settingLanguage =
            findViewById<LinearLayout>(R.id.settingLanguage)

        val settingAppearance =
            findViewById<LinearLayout>(R.id.settingAppearance)

        val settingLocation =
            findViewById<LinearLayout>(R.id.settingLocation)

        val settingFavorites =
            findViewById<LinearLayout>(R.id.settingFavorites)

        val settingTrips =
            findViewById<LinearLayout>(R.id.settingTrips)

        val settingHelp =
            findViewById<LinearLayout>(R.id.settingHelp)

        val settingAbout =
            findViewById<LinearLayout>(R.id.settingAbout)

        val settingPrivacy =
            findViewById<LinearLayout>(R.id.settingPrivacy)

        val btnLogout =
            findViewById<Button>(R.id.btnLogout)

        val notificationStatus =
            findViewById<TextView>(R.id.notificationStatus)

        val languageValue =
            findViewById<TextView>(R.id.languageValue)

        val appearanceValue =
            findViewById<TextView>(R.id.appearanceValue)

        val locationValue =
            findViewById<TextView>(R.id.locationValue)


        // -----------------------------
        // SHARED PREFERENCES
        // -----------------------------

        val preferences =
            getSharedPreferences(
                "TourVistaSettings",
                MODE_PRIVATE
            )


        // -----------------------------
        // LOAD SAVED SETTINGS
        // -----------------------------

        val savedLanguage =
            preferences.getString(
                "language",
                "English"
            )

        languageValue.text = savedLanguage


        val notificationOn =
            preferences.getBoolean(
                "notifications",
                true
            )

        notificationStatus.text =
            if (notificationOn) "ON" else "OFF"


        val darkMode =
            preferences.getBoolean(
                "darkMode",
                false
            )

        appearanceValue.text =
            if (darkMode) "Dark" else "Light"


        val locationEnabled =
            preferences.getBoolean(
                "location",
                true
            )

        locationValue.text =
            if (locationEnabled) "Enabled"
            else "Disabled"


        // -----------------------------
        // BACK BUTTON
        // -----------------------------

        btnBack.setOnClickListener {
            finish()
        }


        // -----------------------------
        // PROFILE
        // -----------------------------

        settingProfile.setOnClickListener {

            val intent =
                Intent(
                    this,
                    ProfileActivity::class.java
                )

            startActivity(intent)
        }


        // -----------------------------
        // NOTIFICATIONS
        // -----------------------------

        settingNotifications.setOnClickListener {

            val currentStatus =
                preferences.getBoolean(
                    "notifications",
                    true
                )

            val newStatus =
                !currentStatus

            preferences.edit()
                .putBoolean(
                    "notifications",
                    newStatus
                )
                .apply()

            notificationStatus.text =
                if (newStatus) "ON" else "OFF"

            if (newStatus) {

                Toast.makeText(
                    this,
                    "Notifications turned on",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                Toast.makeText(
                    this,
                    "Notifications turned off",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        // -----------------------------
        // LANGUAGE
        // -----------------------------

        settingLanguage.setOnClickListener {

            val languages = arrayOf(

                "English",

                "Hindi",

                "Telugu",

                "Tamil",

                "Bengali",

                "Marathi",

                "Gujarati",

                "Kannada",

                "Malayalam",

                "Punjabi",

                "Urdu",

                "French",

                "Spanish",

                "German",

                "Italian",

                "Portuguese",

                "Russian",

                "Japanese",

                "Korean",

                "Chinese",

                "Arabic",

                "Turkish",

                "Indonesian",

                "Vietnamese",

                "Thai"
            )


            val currentLanguage =
                preferences.getString(
                    "language",
                    "English"
                )


            val selectedIndex =
                languages.indexOf(currentLanguage)


            val builder =
                AlertDialog.Builder(this)


            builder.setTitle(
                "Choose Language"
            )


            builder.setSingleChoiceItems(
                languages,
                selectedIndex
            ) { dialog, which ->

                val selectedLanguage =
                    languages[which]


                languageValue.text =
                    selectedLanguage


                preferences.edit()
                    .putString(
                        "language",
                        selectedLanguage
                    )
                    .apply()


                dialog.dismiss()


                Toast.makeText(
                    this,
                    "Language changed to $selectedLanguage",
                    Toast.LENGTH_SHORT
                ).show()
            }


            builder.setNegativeButton(
                "Cancel",
                null
            )


            builder.show()
        }


        // -----------------------------
        // APPEARANCE
        // -----------------------------

        settingAppearance.setOnClickListener {

            val currentDarkMode =
                preferences.getBoolean(
                    "darkMode",
                    false
                )

            val newDarkMode =
                !currentDarkMode


            preferences.edit()
                .putBoolean(
                    "darkMode",
                    newDarkMode
                )
                .apply()


            if (newDarkMode) {

                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES
                )

                appearanceValue.text =
                    "Dark"

            } else {

                AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO
                )

                appearanceValue.text =
                    "Light"
            }
        }


        // -----------------------------
        // LOCATION
        // -----------------------------

        settingLocation.setOnClickListener {

            val currentLocation =
                preferences.getBoolean(
                    "location",
                    true
                )

            val newLocation =
                !currentLocation


            preferences.edit()
                .putBoolean(
                    "location",
                    newLocation
                )
                .apply()


            locationValue.text =
                if (newLocation)
                    "Enabled"
                else
                    "Disabled"


            if (newLocation) {

                Toast.makeText(
                    this,
                    "Location services enabled",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                Toast.makeText(
                    this,
                    "Location services disabled",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


        // -----------------------------
        // FAVORITES
        // -----------------------------

        settingFavorites.setOnClickListener {

            val intent =
                Intent(
                    this,
                    FavoritesActivity::class.java
                )

            startActivity(intent)
        }


        // -----------------------------
        // MY TRIPS
        // -----------------------------

        settingTrips.setOnClickListener {

            val intent =
                Intent(
                    this,
                    MyTripsActivity::class.java
                )

            startActivity(intent)
        }


        // -----------------------------
        // HELP & SUPPORT
        // -----------------------------

        settingHelp.setOnClickListener {

            Toast.makeText(
                this,
                "TourVista Help & Support",
                Toast.LENGTH_SHORT
            ).show()
        }


        // -----------------------------
        // ABOUT
        // -----------------------------

        settingAbout.setOnClickListener {

            Toast.makeText(
                this,
                "TourVista - Discover India, one journey at a time.",
                Toast.LENGTH_LONG
            ).show()
        }


        // -----------------------------
        // PRIVACY
        // -----------------------------

        settingPrivacy.setOnClickListener {

            Toast.makeText(
                this,
                "Your travel information is kept private.",
                Toast.LENGTH_SHORT
            ).show()
        }


        // -----------------------------
        // LOGOUT
        // -----------------------------

        btnLogout.setOnClickListener {

            Toast.makeText(
                this,
                "Logged out successfully",
                Toast.LENGTH_SHORT
            ).show()


            val intent =
                Intent(
                    this,
                    MainActivity::class.java
                )


            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK


            startActivity(intent)
        }
    }
}
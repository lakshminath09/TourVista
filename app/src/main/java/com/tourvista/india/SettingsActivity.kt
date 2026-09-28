package com.tourvista.india

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import java.util.Locale

class SettingsActivity : AppCompatActivity() {

    private lateinit var switchNotifications: Switch
    private lateinit var txtSelectedLanguage: TextView
    private lateinit var txtAppearance: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_settings)

        val btnBack = findViewById<TextView>(R.id.btnBack)

        switchNotifications =
            findViewById(R.id.switchNotifications)

        txtSelectedLanguage =
            findViewById(R.id.txtSelectedLanguage)

        txtAppearance =
            findViewById(R.id.txtAppearance)

        val layoutLanguage =
            findViewById<LinearLayout>(R.id.layoutLanguage)

        val layoutAppearance =
            findViewById<LinearLayout>(R.id.layoutAppearance)

        val settingsHelp =
            findViewById<TextView>(R.id.settingsHelp)

        val settingsPrivacy =
            findViewById<TextView>(R.id.settingsPrivacy)

        val settingsAbout =
            findViewById<TextView>(R.id.settingsAbout)

        val btnLogout =
            findViewById<Button>(R.id.btnLogout)

        val preferences =
            getSharedPreferences(
                "TourVistaSettings",
                MODE_PRIVATE
            )

        // -----------------------------
        // BACK
        // -----------------------------

        btnBack.setOnClickListener {
            finish()
        }

        // -----------------------------
        // LOAD APPEARANCE
        // -----------------------------

        val darkMode =
            preferences.getBoolean(
                "darkMode",
                false
            )

        txtAppearance.text =
            if (darkMode) "Dark" else "Light"

        // -----------------------------
        // LOAD LANGUAGE
        // -----------------------------

        val savedLanguage =
            preferences.getString(
                "selectedLanguage",
                "English"
            )

        txtSelectedLanguage.text =
            savedLanguage

        // -----------------------------
        // LOAD NOTIFICATIONS
        // -----------------------------

        switchNotifications.isChecked =
            preferences.getBoolean(
                "notifications",
                true
            )

        switchNotifications.setOnCheckedChangeListener {
                _, isChecked ->

            preferences.edit()
                .putBoolean(
                    "notifications",
                    isChecked
                )
                .apply()

            if (isChecked) {

                Toast.makeText(
                    this,
                    "Notifications turned ON",
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                Toast.makeText(
                    this,
                    "Notifications turned OFF",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // -----------------------------
        // LANGUAGE
        // -----------------------------

        layoutLanguage.setOnClickListener {
            showLanguageDialog()
        }

        // -----------------------------
        // APPEARANCE
        // -----------------------------

        layoutAppearance.setOnClickListener {
            showAppearanceDialog()
        }

        // -----------------------------
        // HELP & SUPPORT
        // -----------------------------

        settingsHelp.setOnClickListener {

            val intent =
                Intent(
                    this,
                    HelpSupportActivity::class.java
                )

            startActivity(intent)
        }

        // -----------------------------
        // PRIVACY & POLICY
        // -----------------------------

        settingsPrivacy.setOnClickListener {

            val intent =
                Intent(
                    this,
                    PrivacyPolicyActivity::class.java
                )

            startActivity(intent)
        }

        // -----------------------------
        // ABOUT US
        // -----------------------------

        settingsAbout.setOnClickListener {

            val intent =
                Intent(
                    this,
                    AboutActivity::class.java
                )

            startActivity(intent)
        }

        // -----------------------------
        // LOGOUT
        // -----------------------------

        btnLogout.setOnClickListener {

            val builder =
                AlertDialog.Builder(this)

            builder.setTitle("Logout")

            builder.setMessage(
                "Are you sure you want to logout?"
            )

            builder.setNegativeButton(
                "Cancel",
                null
            )

            builder.setPositiveButton(
                "Logout"
            ) { _, _ ->

                val accountPreferences =
                    getSharedPreferences(
                        "TourVistaAccount",
                        MODE_PRIVATE
                    )

                accountPreferences.edit()
                    .putBoolean(
                        "isLoggedIn",
                        false
                    )
                    .apply()

                val intent =
                    Intent(
                        this,
                        LoginActivity::class.java
                    )

                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK

                startActivity(intent)

                finish()
            }

            builder.show()
        }
    }

    // =========================================================
    // ALL AVAILABLE LANGUAGES
    // =========================================================

    private fun showLanguageDialog() {

        val preferences =
            getSharedPreferences(
                "TourVistaSettings",
                MODE_PRIVATE
            )

        val languageMap =
            mutableMapOf<String, String>()

        Locale.getAvailableLocales().forEach { locale ->

            val languageCode =
                locale.language

            if (languageCode.isNotEmpty()) {

                val languageName =
                    locale.getDisplayLanguage(
                        Locale.ENGLISH
                    )

                if (languageName.isNotEmpty()) {

                    languageMap[languageName] =
                        languageCode
                }
            }
        }

        val languages =
            languageMap.keys
                .filter {
                    it.isNotBlank()
                }
                .sortedWith(
                    compareBy(String.CASE_INSENSITIVE_ORDER) {
                        it
                    }
                )

        val languageArray =
            languages.toTypedArray()

        val currentLanguage =
            txtSelectedLanguage.text.toString()

        var selectedIndex =
            languages.indexOf(currentLanguage)

        if (selectedIndex < 0) {
            selectedIndex = 0
        }

        val builder =
            AlertDialog.Builder(this)

        builder.setTitle(
            "Choose Language"
        )

        builder.setSingleChoiceItems(
            languageArray,
            selectedIndex
        ) { dialog, which ->

            val selected =
                languageArray[which]

            txtSelectedLanguage.text =
                selected

            preferences.edit()
                .putString(
                    "selectedLanguage",
                    selected
                )
                .apply()

            Toast.makeText(
                this,
                "Language selected: $selected",
                Toast.LENGTH_SHORT
            ).show()

            dialog.dismiss()
        }

        builder.setNegativeButton(
            "Cancel",
            null
        )

        builder.show()
    }

    // =========================================================
    // APPEARANCE
    // =========================================================

    private fun showAppearanceDialog() {

        val preferences =
            getSharedPreferences(
                "TourVistaSettings",
                MODE_PRIVATE
            )

        val darkMode =
            preferences.getBoolean(
                "darkMode",
                false
            )

        val options =
            arrayOf(
                "Light",
                "Dark"
            )

        val selectedIndex =
            if (darkMode) 1 else 0

        val builder =
            AlertDialog.Builder(this)

        builder.setTitle(
            "Choose Appearance"
        )

        builder.setSingleChoiceItems(
            options,
            selectedIndex
        ) { dialog, which ->

            if (which == 0) {

                preferences.edit()
                    .putBoolean(
                        "darkMode",
                        false
                    )
                    .apply()

                txtAppearance.text =
                    "Light"

                AppCompatDelegate
                    .setDefaultNightMode(
                        AppCompatDelegate
                            .MODE_NIGHT_NO
                    )

            } else {

                preferences.edit()
                    .putBoolean(
                        "darkMode",
                        true
                    )
                    .apply()

                txtAppearance.text =
                    "Dark"

                AppCompatDelegate
                    .setDefaultNightMode(
                        AppCompatDelegate
                            .MODE_NIGHT_YES
                    )
            }

            dialog.dismiss()
        }

        builder.setNegativeButton(
            "Cancel",
            null
        )

        builder.show()
    }
}
package com.tourvista.india

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        val btnBack =
            findViewById<TextView>(R.id.btnBack)

        val editEmail =
            findViewById<EditText>(R.id.editEmail)

        val editPassword =
            findViewById<EditText>(R.id.editPassword)

        val btnLogin =
            findViewById<Button>(R.id.btnLogin)

        val signupLink =
            findViewById<TextView>(R.id.signupLink)

        // Back
        btnBack.setOnClickListener {
            finish()
        }

        // Login
        btnLogin.setOnClickListener {

            val email =
                editEmail.text.toString().trim()

            val password =
                editPassword.text.toString()

            if (email.isEmpty()) {
                editEmail.error = "Enter your email"
                editEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                editPassword.error = "Enter your password"
                editPassword.requestFocus()
                return@setOnClickListener
            }

            // Get saved account
            val preferences =
                getSharedPreferences(
                    "TourVistaAccount",
                    MODE_PRIVATE
                )

            val savedEmail =
                preferences.getString("email", "")

            val savedPassword =
                preferences.getString("password", "")

            // Check account
            if (email == savedEmail &&
                password == savedPassword
            ) {

                preferences.edit()
                    .putBoolean("isLoggedIn", true)
                    .apply()

                Toast.makeText(
                    this,
                    "Login successful!",
                    Toast.LENGTH_SHORT
                ).show()

                val intent =
                    Intent(this, MainActivity::class.java)

                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK

                startActivity(intent)

            } else {

                Toast.makeText(
                    this,
                    "Invalid email or password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Create Account
        signupLink.setOnClickListener {

            val intent =
                Intent(this, SignUpActivity::class.java)

            startActivity(intent)
        }
    }
}
package com.tourvista.india

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_sign_up)

        val btnBack = findViewById<TextView>(R.id.btnBack)

        val editName = findViewById<EditText>(R.id.editName)
        val editEmail = findViewById<EditText>(R.id.editEmail)
        val editPassword = findViewById<EditText>(R.id.editPassword)
        val editConfirmPassword =
            findViewById<EditText>(R.id.editConfirmPassword)

        val btnCreateAccount =
            findViewById<Button>(R.id.btnCreateAccount)

        val loginLink =
            findViewById<TextView>(R.id.loginLink)

        // Back button
        btnBack.setOnClickListener {
            finish()
        }

        // Create Account
        btnCreateAccount.setOnClickListener {

            val name = editName.text.toString().trim()
            val email = editEmail.text.toString().trim()
            val password = editPassword.text.toString()
            val confirmPassword =
                editConfirmPassword.text.toString()

            // Name validation
            if (name.isEmpty()) {
                editName.error = "Enter your name"
                editName.requestFocus()
                return@setOnClickListener
            }

            // Email validation
            if (email.isEmpty()) {
                editEmail.error = "Enter your email"
                editEmail.requestFocus()
                return@setOnClickListener
            }

            // Password validation
            if (password.isEmpty()) {
                editPassword.error = "Enter a password"
                editPassword.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 6) {
                editPassword.error =
                    "Password must contain at least 6 characters"
                editPassword.requestFocus()
                return@setOnClickListener
            }

            // Confirm password validation
            if (confirmPassword.isEmpty()) {
                editConfirmPassword.error =
                    "Confirm your password"
                editConfirmPassword.requestFocus()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                editConfirmPassword.error =
                    "Passwords do not match"
                editConfirmPassword.requestFocus()
                return@setOnClickListener
            }

            // Save account
            val preferences =
                getSharedPreferences(
                    "TourVistaAccount",
                    MODE_PRIVATE
                )

            preferences.edit()
                .putString("name", name)
                .putString("email", email)
                .putString("password", password)
                .putBoolean("isLoggedIn", true)
                .apply()

            Toast.makeText(
                this,
                "Account created successfully!",
                Toast.LENGTH_SHORT
            ).show()

            // Go to Home
            val intent =
                Intent(this, MainActivity::class.java)

            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }

        // Go to Login
        loginLink.setOnClickListener {

            val intent =
                Intent(this, LoginActivity::class.java)

            startActivity(intent)
        }
    }
}
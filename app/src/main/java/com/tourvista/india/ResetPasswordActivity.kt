package com.tourvista.india

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ResetPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_reset_password)

        val btnBack = findViewById<TextView>(R.id.btnBack)

        val editPassword =
            findViewById<EditText>(R.id.editPassword)

        val editConfirmPassword =
            findViewById<EditText>(R.id.editConfirmPassword)

        val btnResetPassword =
            findViewById<Button>(R.id.btnResetPassword)

        btnBack.setOnClickListener {
            finish()
        }

        btnResetPassword.setOnClickListener {

            val password =
                editPassword.text.toString()

            val confirmPassword =
                editConfirmPassword.text.toString()

            if (password.isEmpty()) {
                editPassword.error =
                    "Enter a new password"
                editPassword.requestFocus()
                return@setOnClickListener
            }

            if (password.length < 6) {
                editPassword.error =
                    "Password must contain at least 6 characters"
                editPassword.requestFocus()
                return@setOnClickListener
            }

            if (confirmPassword.isEmpty()) {
                editConfirmPassword.error =
                    "Confirm your new password"
                editConfirmPassword.requestFocus()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                editConfirmPassword.error =
                    "Passwords do not match"
                editConfirmPassword.requestFocus()
                return@setOnClickListener
            }

            val preferences =
                getSharedPreferences(
                    "TourVistaAccount",
                    MODE_PRIVATE
                )

            preferences.edit()
                .putString("password", password)
                .putBoolean("isLoggedIn", false)
                .apply()

            Toast.makeText(
                this,
                "Password reset successfully! Please login.",
                Toast.LENGTH_LONG
            ).show()

            val intent =
                Intent(
                    this,
                    LoginActivity::class.java
                )

            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }
}
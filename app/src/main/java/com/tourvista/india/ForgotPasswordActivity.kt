package com.tourvista.india

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_forgot_password)

        val btnBack =
            findViewById<TextView>(R.id.btnBack)

        val editEmail =
            findViewById<EditText>(R.id.editEmail)

        val btnContinue =
            findViewById<Button>(R.id.btnContinue)

        btnBack.setOnClickListener {
            finish()
        }

        btnContinue.setOnClickListener {

            val email =
                editEmail.text.toString().trim()

            if (email.isEmpty()) {
                editEmail.error =
                    "Enter your registered email"
                editEmail.requestFocus()
                return@setOnClickListener
            }

            val preferences =
                getSharedPreferences(
                    "TourVistaAccount",
                    MODE_PRIVATE
                )

            val savedEmail =
                preferences.getString("email", "")

            if (savedEmail.isNullOrEmpty() ||
                !email.equals(
                    savedEmail,
                    ignoreCase = true
                )
            ) {

                Toast.makeText(
                    this,
                    "Account not found. Please create an account first.",
                    Toast.LENGTH_LONG
                ).show()

                startActivity(
                    Intent(
                        this,
                        SignUpActivity::class.java
                    )
                )

                return@setOnClickListener
            }

            // Account exists
            val intent =
                Intent(
                    this,
                    ResetPasswordActivity::class.java
                )

            intent.putExtra("EMAIL", email)

            startActivity(intent)
        }
    }
}
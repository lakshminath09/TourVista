package com.tourvista.india

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_welcome)

        val btnCreateAccount =
            findViewById<Button>(R.id.btnCreateAccount)

        val btnLogin =
            findViewById<Button>(R.id.btnLogin)

        val btnGuest =
            findViewById<TextView>(R.id.btnGuest)

        btnCreateAccount.setOnClickListener {

            val intent =
                Intent(this, SignUpActivity::class.java)

            startActivity(intent)
        }

        btnLogin.setOnClickListener {

            val intent =
                Intent(this, LoginActivity::class.java)

            startActivity(intent)
        }

        btnGuest.setOnClickListener {

            val intent =
                Intent(this, MainActivity::class.java)

            startActivity(intent)

            finish()
        }
    }
}
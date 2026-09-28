package com.tourvista.india

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.lifecycleScope
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var credentialManager: CredentialManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Credential Manager
        credentialManager = CredentialManager.create(this)

        val btnBack =
            findViewById<TextView>(R.id.btnBack)

        val editEmail =
            findViewById<EditText>(R.id.editEmail)

        val editPassword =
            findViewById<EditText>(R.id.editPassword)

        val btnLogin =
            findViewById<Button>(R.id.btnLogin)

        val googleLogin =
            findViewById<TextView>(R.id.googleLogin)

        val signupLink =
            findViewById<TextView>(R.id.signupLink)

        val forgotPassword =
            findViewById<TextView>(R.id.forgotPassword)

        // BACK
        btnBack.setOnClickListener {
            finish()
        }

        // If user came from "Account already exists"
        val existingEmail =
            intent.getStringExtra("EMAIL")

        if (!existingEmail.isNullOrEmpty()) {
            editEmail.setText(existingEmail)
            editPassword.requestFocus()
        }

        // ============================================================
        // EMAIL + PASSWORD LOGIN
        // ============================================================

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

            val preferences =
                getSharedPreferences(
                    "TourVistaAccount",
                    MODE_PRIVATE
                )

            val savedEmail =
                preferences.getString("email", "")

            val savedPassword =
                preferences.getString("password", "")

            // No account exists
            if (savedEmail.isNullOrEmpty()) {

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

            // Email doesn't match
            if (!email.equals(
                    savedEmail,
                    ignoreCase = true
                )
            ) {

                Toast.makeText(
                    this,
                    "Account not found. Please create an account first.",
                    Toast.LENGTH_LONG
                ).show()

                return@setOnClickListener
            }

            // Password doesn't match
            if (password != savedPassword) {

                Toast.makeText(
                    this,
                    "Incorrect password. Please try again.",
                    Toast.LENGTH_LONG
                ).show()

                return@setOnClickListener
            }

            // Login successful
            preferences.edit()
                .putBoolean("isLoggedIn", true)
                .apply()

            Toast.makeText(
                this,
                "Login successful! 🎉",
                Toast.LENGTH_SHORT
            ).show()

            openMainActivity()
        }

        // ============================================================
        // GOOGLE LOGIN
        // ============================================================

        googleLogin.setOnClickListener {
            signInWithGoogle()
        }

        // ============================================================
        // FORGOT PASSWORD
        // ============================================================

        forgotPassword.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    ForgotPasswordActivity::class.java
                )
            )
        }

        // ============================================================
        // CREATE ACCOUNT
        // ============================================================

        signupLink.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    SignUpActivity::class.java
                )
            )
        }
    }

    // ================================================================
    // GOOGLE SIGN-IN
    // ================================================================

    private fun signInWithGoogle() {

        lifecycleScope.launch {

            try {

                // Google sign-in option
                val googleIdOption =
                    GetGoogleIdOption.Builder()
                        .setServerClientId(
                            getString(
                                R.string.default_web_client_id
                            )
                        )
                        .setFilterByAuthorizedAccounts(false)
                        .setAutoSelectEnabled(false)
                        .build()

                // Credential Manager request
                val request =
                    GetCredentialRequest.Builder()
                        .addCredentialOption(
                            googleIdOption
                        )
                        .build()

                // Show Google account selection
                val result =
                    credentialManager.getCredential(
                        this@LoginActivity,
                        request
                    )

                val credential =
                    result.credential

                // Check whether Google ID credential was returned
                if (
                    credential is CustomCredential &&
                    credential.type ==
                    GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
                ) {

                    try {

                        val googleIdTokenCredential =
                            GoogleIdTokenCredential.createFrom(
                                credential.data
                            )

                        val idToken =
                            googleIdTokenCredential.idToken

                        firebaseAuthWithGoogle(idToken)

                    } catch (
                        e: GoogleIdTokenParsingException
                    ) {

                        Toast.makeText(
                            this@LoginActivity,
                            "Google login failed. Please try again.",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } else {

                    Toast.makeText(
                        this@LoginActivity,
                        "Google account could not be verified.",
                        Toast.LENGTH_LONG
                    ).show()
                }

            } catch (e: Exception) {

                Toast.makeText(
                    this@LoginActivity,
                    "Google login cancelled or failed.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    // ================================================================
    // FIREBASE GOOGLE AUTHENTICATION
    // ================================================================

    private fun firebaseAuthWithGoogle(
        idToken: String
    ) {

        val credential =
            GoogleAuthProvider.getCredential(
                idToken,
                null
            )

        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->

                if (task.isSuccessful) {

                    val user =
                        auth.currentUser

                    val preferences =
                        getSharedPreferences(
                            "TourVistaAccount",
                            MODE_PRIVATE
                        )

                    preferences.edit()
                        .putBoolean("isLoggedIn", true)
                        .putString(
                            "email",
                            user?.email ?: ""
                        )
                        .putString(
                            "name",
                            user?.displayName ?: ""
                        )
                        .apply()

                    Toast.makeText(
                        this,
                        "Google Login successful! 🎉",
                        Toast.LENGTH_SHORT
                    ).show()

                    openMainActivity()

                } else {

                    Toast.makeText(
                        this,
                        "Google Login failed: ${
                            task.exception?.message
                                ?: "Unknown error"
                        }",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    // ================================================================
    // OPEN HOME SCREEN
    // ================================================================

    private fun openMainActivity() {

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
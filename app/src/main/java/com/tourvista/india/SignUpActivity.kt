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

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var credentialManager: CredentialManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Firebase Authentication
        auth = FirebaseAuth.getInstance()

        // Credential Manager
        credentialManager = CredentialManager.create(this)

        val btnBack =
            findViewById<TextView>(R.id.btnBack)

        val editName =
            findViewById<EditText>(R.id.editName)

        val editEmail =
            findViewById<EditText>(R.id.editEmail)

        val editPassword =
            findViewById<EditText>(R.id.editPassword)

        val editConfirmPassword =
            findViewById<EditText>(R.id.editConfirmPassword)

        val btnCreateAccount =
            findViewById<Button>(R.id.btnCreateAccount)

        val googleSignup =
            findViewById<TextView>(R.id.googleSignup)

        val loginLink =
            findViewById<TextView>(R.id.loginLink)

        // BACK
        btnBack.setOnClickListener {
            finish()
        }

        // ============================================================
        // NORMAL CREATE ACCOUNT
        // ============================================================

        btnCreateAccount.setOnClickListener {

            val name =
                editName.text.toString().trim()

            val email =
                editEmail.text.toString().trim()

            val password =
                editPassword.text.toString()

            val confirmPassword =
                editConfirmPassword.text.toString()

            if (name.isEmpty()) {
                editName.error = "Enter your name"
                editName.requestFocus()
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                editEmail.error = "Enter your email"
                editEmail.requestFocus()
                return@setOnClickListener
            }

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

            val preferences =
                getSharedPreferences(
                    "TourVistaAccount",
                    MODE_PRIVATE
                )

            val savedEmail =
                preferences.getString("email", "")

            // Account already exists
            if (!savedEmail.isNullOrEmpty() &&
                email.equals(
                    savedEmail,
                    ignoreCase = true
                )
            ) {

                Toast.makeText(
                    this,
                    "Account already exists. Please login instead.",
                    Toast.LENGTH_LONG
                ).show()

                val intent =
                    Intent(
                        this,
                        LoginActivity::class.java
                    )

                intent.putExtra(
                    "EMAIL",
                    email
                )

                startActivity(intent)

                return@setOnClickListener
            }

            // Create normal account
            preferences.edit()
                .putString("name", name)
                .putString("email", email)
                .putString("password", password)
                .putBoolean("isLoggedIn", true)
                .apply()

            Toast.makeText(
                this,
                "Account created successfully! 🎉",
                Toast.LENGTH_SHORT
            ).show()

            openMainActivity()
        }

        // ============================================================
        // GOOGLE SIGN UP
        // ============================================================

        googleSignup.setOnClickListener {
            signUpWithGoogle()
        }

        // ============================================================
        // LOGIN
        // ============================================================

        loginLink.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )
        }
    }

    // ================================================================
    // GOOGLE SIGN UP
    // ================================================================

    private fun signUpWithGoogle() {

        lifecycleScope.launch {

            try {

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

                val request =
                    GetCredentialRequest.Builder()
                        .addCredentialOption(
                            googleIdOption
                        )
                        .build()

                val result =
                    credentialManager.getCredential(
                        this@SignUpActivity,
                        request
                    )

                val credential =
                    result.credential

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
                            this@SignUpActivity,
                            "Google sign up failed. Please try again.",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } else {

                    Toast.makeText(
                        this@SignUpActivity,
                        "Google account could not be verified.",
                        Toast.LENGTH_LONG
                    ).show()
                }

            } catch (e: Exception) {

                Toast.makeText(
                    this@SignUpActivity,
                    "Google sign up cancelled or failed.",
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
                        .putBoolean(
                            "isLoggedIn",
                            true
                        )
                        .putString(
                            "name",
                            user?.displayName ?: ""
                        )
                        .putString(
                            "email",
                            user?.email ?: ""
                        )
                        .apply()

                    Toast.makeText(
                        this,
                        "Google account created successfully! 🎉",
                        Toast.LENGTH_SHORT
                    ).show()

                    openMainActivity()

                } else {

                    Toast.makeText(
                        this,
                        "Google sign up failed: ${
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
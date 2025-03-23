package com.example.heritageapp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val backImage: ImageView = findViewById(R.id.backImage)
        val loginButton: Button = findViewById(R.id.loginButton)
        val emailEditText: EditText = findViewById(R.id.emailEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)

        // Initialize Firebase
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Navigate back to MainActivity
        backImage.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                showToast("Please enter a valid email")
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                showToast("Please enter your password")
                return@setOnClickListener
            }

            // Authenticate User
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    fetchUserData(email)
                }
                .addOnFailureListener {
                    showToast("Authentication failed: ${it.message}")
                }
        }
    }

    // Fetch user data from Firestore
    private fun fetchUserData(email: String) {
        firestore.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    startActivity(Intent(this, HomeActivity::class.java))
                    finish()
                } else {
                    showToast("User not found in database")
                }
            }
            .addOnFailureListener {
                showToast("Error fetching user data: ${it.message}")
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

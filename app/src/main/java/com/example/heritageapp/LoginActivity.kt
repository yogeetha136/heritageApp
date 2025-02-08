package com.example.heritageapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize views
        val backImage: ImageView = findViewById(R.id.backImage)
        val loginButton: android.widget.Button = findViewById(R.id.loginButton)
        val facebookIcon: ImageView = findViewById(R.id.facebookIcon)
        val googleIcon: ImageView = findViewById(R.id.googleIcon)
        val emailIcon: ImageView = findViewById(R.id.emailIcon)

        // Navigate back to MainActivity when back image is clicked
        backImage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Handle login button click
        loginButton.setOnClickListener {
            val fullName = findViewById<android.widget.EditText>(R.id.fullNameEditText).text.toString()
            val email = findViewById<android.widget.EditText>(R.id.emailEditText).text.toString()
            val contact = findViewById<android.widget.EditText>(R.id.contactEditText).text.toString()
            val password = findViewById<android.widget.EditText>(R.id.passwordEditText).text.toString()

            if (fullName.isEmpty() || email.isEmpty() || contact.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                // Navigate to HomeActivity
                val intent = Intent(this, com.example.heritageapp.HomeActivity::class.java)
                startActivity(intent)
                finish() // Close LoginActivity
            }
        }

        // Social Media Icon Clicks
        facebookIcon.setOnClickListener {
            Toast.makeText(this, "Facebook Login", Toast.LENGTH_SHORT).show()
        }
        googleIcon.setOnClickListener {
            Toast.makeText(this, "Google Login", Toast.LENGTH_SHORT).show()
        }
        emailIcon.setOnClickListener {
            Toast.makeText(this, "Email Login", Toast.LENGTH_SHORT).show()
        }
    }
}

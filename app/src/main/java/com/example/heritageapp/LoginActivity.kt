package com.example.heritageapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val backImage: ImageView = findViewById(R.id.backImage)
        val loginButton: Button = findViewById(R.id.loginButton)
        val emailEditText: EditText = findViewById(R.id.emailEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)

        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

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

            val storedEmail = sharedPreferences.getString("email", "") ?: ""
            val storedPassword = sharedPreferences.getString("password", "") ?: ""

            if (email == storedEmail && password == storedPassword) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                showToast("Invalid email or password")
            }

        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

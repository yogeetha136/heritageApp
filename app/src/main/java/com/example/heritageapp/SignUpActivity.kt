package com.example.heritageapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val backImage: ImageView = findViewById(R.id.backImage)
        val fullNameEditText: EditText = findViewById(R.id.fullNameEditText)
        val ageSpinner: Spinner = findViewById(R.id.ageSpinner)
        val emailEditText: EditText = findViewById(R.id.emailEditText)
        val contactEditText: EditText = findViewById(R.id.contactEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)
        val confirmPasswordEditText: EditText = findViewById(R.id.confirmPasswordEditText)
        val genderRadioGroup: RadioGroup = findViewById(R.id.genderRadioGroup)
        val signUpButton: Button = findViewById(R.id.signUpButton)
        val progressBar: ProgressBar = findViewById(R.id.progressBar)

        // Populate Age Spinner
        val ageOptions = (18..100).map { it.toString() }
        val ageAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ageOptions)
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ageSpinner.adapter = ageAdapter

        val sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        backImage.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        signUpButton.setOnClickListener {
            val fullName = fullNameEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val contact = contactEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()
            val selectedAge = ageSpinner.selectedItem.toString()

            val selectedGenderId = genderRadioGroup.checkedRadioButtonId
            val selectedGender = if (selectedGenderId != -1) {
                findViewById<RadioButton>(selectedGenderId).text.toString()
            } else {
                null
            }

            if (!validateInput(fullName, email, contact, password, confirmPassword, selectedGender)) return@setOnClickListener

            // Show ProgressBar and disable button
            progressBar.visibility = View.VISIBLE
            signUpButton.isEnabled = false

            // Simulating a network request (2-second delay)
            Handler().postDelayed({
                // Save user details in SharedPreferences
                editor.putString("fullName", fullName)
                editor.putString("email", email)
                editor.putString("contact", contact)
                editor.putString("password", password)
                editor.putString("age", selectedAge)
                editor.putString("gender", selectedGender)
                editor.apply()

                // Hide ProgressBar and enable button
                progressBar.visibility = View.GONE
                signUpButton.isEnabled = true

                Toast.makeText(this, "Sign Up Successful!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }, 2000) // 2 seconds delay
        }
    }

    private fun validateInput(
        fullName: String,
        email: String,
        contact: String,
        password: String,
        confirmPassword: String,
        gender: String?
    ): Boolean {
        if (fullName.isEmpty()) {
            showToast("Please enter your full name")
            return false
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Please enter a valid email address")
            return false
        }
        if (contact.isEmpty() || contact.length != 10 || !contact.all { it.isDigit() }) {
            showToast("Please enter a valid 10-digit contact number")
            return false
        }
        if (gender == null) {
            showToast("Please select your gender")
            return false
        }
        if (password.isEmpty() || password.length < 6) {
            showToast("Password must be at least 6 characters")
            return false
        }
        if (password != confirmPassword) {
            showToast("Passwords do not match")
            return false
        }
        return true
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

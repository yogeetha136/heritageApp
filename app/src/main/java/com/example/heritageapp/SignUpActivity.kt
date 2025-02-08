package com.example.heritageapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        // Back Button: Navigate back to MainActivity
        val backImage: ImageView = findViewById(R.id.backImage)
        backImage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Get references to views
        val fullNameEditText: EditText = findViewById(R.id.fullNameEditText)
        val ageSpinner: Spinner = findViewById(R.id.ageSpinner)
        val emailEditText: EditText = findViewById(R.id.emailEditText)
        val contactEditText: EditText = findViewById(R.id.contactEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)
        val confirmPasswordEditText: EditText = findViewById(R.id.confirmPasswordEditText)
        val genderRadioGroup: RadioGroup = findViewById(R.id.genderRadioGroup)
        val signUpButton: Button = findViewById(R.id.signUpButton)

        // Setup age spinner options
        val ageOptions = (18..100).map { it.toString() }
        val ageAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ageOptions)
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ageSpinner.adapter = ageAdapter

        // Handle signup button click
        signUpButton.setOnClickListener {
            val fullName = fullNameEditText.text.toString()
            val selectedAge = ageSpinner.selectedItem.toString()
            val email = emailEditText.text.toString()
            val contactNumber = contactEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            val selectedGenderId = genderRadioGroup.checkedRadioButtonId
            val selectedGender = if (selectedGenderId != -1) {
                findViewById<RadioButton>(selectedGenderId).text.toString()
            } else {
                null
            }

            if (fullName.isEmpty() || email.isEmpty() || contactNumber.isEmpty() ||
                password.isEmpty() || confirmPassword.isEmpty() || selectedGender == null
            ) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Show a success message and navigate to HomeActivity
            Toast.makeText(this, "Sign Up Successful!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, com.example.heritageapp.HomeActivity::class.java)
            startActivity(intent)
            finish() // Close SignUpActivity
        }
    }
}

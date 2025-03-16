package com.example.heritageapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.ByteArrayOutputStream
import java.io.IOException

class ProfileActivity : AppCompatActivity() {
    private lateinit var ivProfile: ImageView
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // Initialize views
        val username = findViewById<TextView>(R.id.tv_username)
        val email = findViewById<TextView>(R.id.tv_email)
        val phone = findViewById<TextView>(R.id.tv_phone)
        val gender = findViewById<TextView>(R.id.tv_gender)
        val age = findViewById<TextView>(R.id.tv_age)
        val logoutButton = findViewById<Button>(R.id.btn_logout)
        val editProfileButton = findViewById<Button>(R.id.btn_edit_profile)
        ivProfile = findViewById(R.id.iv_profile)

        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)

        // Load saved user details
        username.text = sharedPreferences.getString("fullName", "N/A")
        email.text = sharedPreferences.getString("email", "N/A")
        phone.text = sharedPreferences.getString("contact", "N/A")
        gender.text = "Gender: " + sharedPreferences.getString("gender", "N/A")
        age.text = "Age: " + sharedPreferences.getString("age", "N/A")

        // Load profile image
        loadProfileImage()

        // Click event to open gallery for profile image selection
        ivProfile.setOnClickListener {
            selectImage()
        }

        // Logout functionality
        logoutButton.setOnClickListener {
            val editor = sharedPreferences.edit()
            editor.clear() // Clear saved data
            editor.apply()

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // Edit Profile button (future implementation)
        editProfileButton.setOnClickListener {
            Toast.makeText(this, "Edit Profile Clicked", Toast.LENGTH_SHORT).show()
        }
    }

    // Open gallery for selecting an image
    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val imageUri: Uri? = data.data

            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                ivProfile.setImageBitmap(bitmap)

                // Save Image in SharedPreferences
                saveProfileImage(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Failed to load image!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Save Image as Base64 string in SharedPreferences
    private fun saveProfileImage(bitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val byteArray = baos.toByteArray()
        val encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT)

        val editor = sharedPreferences.edit()
        editor.putString("profile_image", encodedImage)
        editor.apply()
    }

    // Load Image from SharedPreferences
    private fun loadProfileImage() {
        val encodedImage = sharedPreferences.getString("profile_image", null)
        if (encodedImage != null) {
            val byteArray = Base64.decode(encodedImage, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            ivProfile.setImageBitmap(bitmap)
        }
    }

    companion object {
        private const val PICK_IMAGE_REQUEST = 1
    }
}

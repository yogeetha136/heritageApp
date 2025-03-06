package com.example.heritageapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import de.hdodenhof.circleimageview.CircleImageView

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val profileImage = findViewById<CircleImageView>(R.id.profile_image)
        val username = findViewById<TextView>(R.id.tv_username)
        val email = findViewById<TextView>(R.id.tv_email)
        val phone = findViewById<TextView>(R.id.tv_phone)
        val editProfileBtn = findViewById<Button>(R.id.btn_edit_profile)

        // Set user details (You can fetch this from SharedPreferences or a database)
        username.text = "John Doe"
        email.text = "johndoe@example.com"
        phone.text = "+91 9876543210"

        // Handle Edit Profile Button Click
        //editProfileBtn.setOnClickListener {
          //  startActivity(Intent(this, EditProfileActivity::class.java))
       // }
    }
}

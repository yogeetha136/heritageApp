package com.example.heritageapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var isBackPressedOnce = false
    private lateinit var loadingDialog: LoadingDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadingDialog = LoadingDialog(this) // Initialize loading dialog

        val signUpButton: TextView = findViewById(R.id.signUpButton)
        val loginButton: TextView = findViewById(R.id.loginButton)

        signUpButton.setOnClickListener {
            loadingDialog.startLoading() // Show loading dialog
            Handler(Looper.getMainLooper()).postDelayed({
                loadingDialog.dismissLoading()
                val intent = Intent(this, SignUpActivity::class.java)
                startActivity(intent)
            }, 2000) // Simulating a delay before opening new activity
        }

        loginButton.setOnClickListener {
            loadingDialog.startLoading() // Show loading dialog
            Handler(Looper.getMainLooper()).postDelayed({
                loadingDialog.dismissLoading()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }, 2000) // Simulating a delay before opening new activity
        }

        // Handle back button press using OnBackPressedDispatcher
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isBackPressedOnce) {
                    finish()
                } else {
                    isBackPressedOnce = true
                    Toast.makeText(this@MainActivity, "Press again to exit!!", Toast.LENGTH_SHORT).show()
                    Handler(Looper.getMainLooper()).postDelayed({ isBackPressedOnce = false }, 2000)
                }
            }
        })
    }
}

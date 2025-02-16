package com.example.heritageapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {

    private var isBackPressedOnce = false
    private lateinit var loadingDialog: LoadingDialog
    private val CHANNEL_ID = "heritage_channel"

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
        createNotificationChannel()
        showNotification()

        // Find buttons
        val signUpButton: Button = findViewById(R.id.signUpButton)
        val loginButton: Button = findViewById(R.id.loginButton)

        signUpButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
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

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Heritage Channel"
            val descriptionText = "Channel for heritage notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showNotification() {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Heritage App")
            .setContentText("Discover more in Tamil roots")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(1, builder.build())
        }
    }
}

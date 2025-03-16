package com.example.heritageapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

import java.util.Calendar

class SettingsActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var switchTheme: Switch
    private lateinit var switchNotifications: Switch
    private lateinit var tvPrivacyPolicy: TextView
    private lateinit var tvDateTimePicker: TextView
    private lateinit var tvDateTime: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        sharedPreferences = getSharedPreferences("SettingsPrefs", MODE_PRIVATE)

        switchTheme = findViewById(R.id.switchTheme)
        switchNotifications = findViewById(R.id.switchNotifications)
        tvPrivacyPolicy = findViewById(R.id.tvPrivacyPolicy)
        tvDateTimePicker = findViewById(R.id.tvDateTimePicker)
        tvDateTime = findViewById(R.id.tvDateTime)

        // Load saved settings
        switchTheme.isChecked = sharedPreferences.getBoolean("dark_mode", false)
        switchNotifications.isChecked = sharedPreferences.getBoolean("notifications", true)

        // Dark Mode Toggle
        switchTheme.setOnCheckedChangeListener { _: CompoundButton, isChecked: Boolean ->
            val editor = sharedPreferences.edit()
            editor.putBoolean("dark_mode", isChecked)
            editor.apply()

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // Notifications Toggle
        switchNotifications.setOnCheckedChangeListener { _, isChecked ->
            val editor = sharedPreferences.edit()
            editor.putBoolean("notifications", isChecked)
            editor.apply()
        }

        // Privacy & Security Click Listener
        tvPrivacyPolicy.setOnClickListener {
            startActivity(Intent(this, PrivacyPolicyActivity::class.java))
        }

        // Date & Time Picker
        tvDateTimePicker.setOnClickListener {
            showDateTimePicker()
        }
    }

    private fun showDateTimePicker() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val timePickerDialog = TimePickerDialog(
                    this,
                    { _, hourOfDay, minute ->
                        tvDateTime.text =
                            "Date: $dayOfMonth/${month + 1}/$year Time: $hourOfDay:$minute"
                    },
                    calendar[Calendar.HOUR_OF_DAY],
                    calendar[Calendar.MINUTE],
                    true
                )
                timePickerDialog.show()
            },
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH],
            calendar[Calendar.DAY_OF_MONTH]
        )
        datePickerDialog.show()
    }
}

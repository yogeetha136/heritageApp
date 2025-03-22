package com.example.heritageapp

import android.Manifest
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class HomeActivity : AppCompatActivity() {
    private var mapView: MapView? = null
    private val phoneNumbers = arrayOf("9345682720", "9751423916", "8072965118")

    private val userId: String
        get() = "TN" + (1000 + (Math.random() * 9000).toInt())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance()
            .load(applicationContext, getSharedPreferences("osm_pref", MODE_PRIVATE))
        setContentView(R.layout.activity_home)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(true)

        requestSmsPermission()
        setupWhatsAppButton()

        mapView = findViewById(R.id.mapView)
        mapView?.apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setBuiltInZoomControls(true)
            setMultiTouchControls(true)
            controller.setZoom(15.0)
            addMarkers()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu) // Inflate the menu
        return true
    }






    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item1 -> {
                startActivity(Intent(this, ProfileActivity::class.java))
                return true
            }
            R.id.item2 -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                return true
            }
            R.id.item4 -> {
                startActivity(Intent(this, ReviewActivity::class.java))
                return true
            }
            R.id.item5 -> {
                showNumberSelectionDialog()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun showNumberSelectionDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select a Number to Send SMS")

        val adapter = ArrayAdapter(this, android.R.layout.select_dialog_singlechoice, phoneNumbers)
        builder.setAdapter(adapter) { _, which ->
            sendHelpSms(phoneNumbers[which])
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

    private fun sendHelpSms(number: String) {
        val message = "Hi, I need help with the Tamil Roots app. My user ID: $userId. Please contact me."
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("sms:$number")).apply {
            putExtra("sms_body", message)
        }
        try {
            startActivityForResult(intent, SMS_REQUEST_CODE)
            Toast.makeText(this, "Opening SMS app for $number", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Unable to open SMS app", Toast.LENGTH_SHORT).show()
        }
    }

    private fun requestSmsPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS), SMS_PERMISSION_REQUEST_CODE)
        }
    }

    private fun addMarkers() {
        val locations = arrayOf(
            GeoPoint(13.0827, 80.2707),
            GeoPoint(9.9252, 78.1198),
            GeoPoint(10.7905, 78.7047),
            GeoPoint(8.0883, 77.5385),
            GeoPoint(9.2889, 79.3129)
        )
        val cityInfo = arrayOf(
            "Chennai - Visitors: 1M",
            "Madurai - Visitors: 750K",
            "Trichy - Visitors: 500K",
            "Kanniyakumari - Visitors: 300K",
            "Rameshwaram - Visitors: 450K"
        )

        locations.forEachIndexed { index, geoPoint ->
            val marker = Marker(mapView)
            marker.position = geoPoint
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            marker.title = cityInfo[index]
            mapView!!.overlays.add(marker)
        }
        mapView!!.controller.setCenter(locations[0])
    }

    private fun setupWhatsAppButton() {
        val whatsappButton = findViewById<ImageView>(R.id.whatsapp_icon)
        whatsappButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Enter Number to Chat")

            val input = EditText(this)
            input.hint = "Enter phone number"
            builder.setView(input)

            builder.setPositiveButton("Chat") { _, _ ->
                val phoneNumber = input.text.toString().trim()
                if (phoneNumber.isNotEmpty()) {
                    val message = "Hello, I need help with the Tamil Roots app."
                    val uri = Uri.parse("https://wa.me/$phoneNumber?text=$message")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    try {
                        startActivity(intent)
                    } catch (e: Exception) {
                        Toast.makeText(this, "WhatsApp not installed.", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show()
                }
            }
            builder.setNegativeButton("Cancel", null)
            builder.show()
        }
    }

    companion object {
        private const val SMS_PERMISSION_REQUEST_CODE = 101
        private const val SMS_REQUEST_CODE = 102
    }
}

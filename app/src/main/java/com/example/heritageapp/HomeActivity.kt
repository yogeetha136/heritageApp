package com.example.heritageapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent // Import this to use Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.*

class HomeActivity : AppCompatActivity(), OnMapReadyCallback { // Implement OnMapReadyCallback here

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var tvDateTime: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Initialize UI Elements
        tvDateTime = findViewById(R.id.tv_date_time)
        val menuIcon: ImageView = findViewById(R.id.menu_icon)
        menuIcon.setOnClickListener { openOptionsMenu() }

        // Initialize MapView
        mapView = findViewById(R.id.map_view)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)  // Set this activity as the callback for map ready

        // Initialize Buttons
        val btnFood: Button = findViewById(R.id.btn_food)
        val btnPlace: Button = findViewById(R.id.btn_place)
        val btnCloth: Button = findViewById(R.id.btn_cloth)
        val btnFlower: Button = findViewById(R.id.btn_flower)
        val btnTemple: Button = findViewById(R.id.btn_temple)
        val btnFestival: Button = findViewById(R.id.btn_festival)

        // Set click listener for btnFood to start FoodActivity
        btnFood.setOnClickListener {
            val intent = Intent(this, FoodActivity::class.java) // Create an Intent to start FoodActivity
            startActivity(intent) // Start the new activity
        }

        // Setting click listeners to show popup menu for other buttons
        listOf(btnPlace, btnCloth, btnFlower, btnTemple, btnFestival).forEach { button ->
            button.setOnClickListener { showPopupMenu(it) }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item1 -> Toast.makeText(this, "Profile Selected", Toast.LENGTH_SHORT).show()
            R.id.item2 -> Toast.makeText(this, "Settings Selected", Toast.LENGTH_SHORT).show()
            R.id.item3 -> showDateTimePicker()
            R.id.item4 -> Toast.makeText(this, "Log out Selected", Toast.LENGTH_SHORT).show()
            R.id.item5 -> Toast.makeText(this, "Help Selected", Toast.LENGTH_SHORT).show()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.popup_menu, popupMenu.menu)
        popupMenu.show()
    }

    private fun showDateTimePicker() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val timePickerDialog = TimePickerDialog(
                    this,
                    { _, hourOfDay, minute ->
                        tvDateTime.text = "Date: $dayOfMonth/${month + 1}/$year\tTime: $hourOfDay:$minute"
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                )
                timePickerDialog.show()
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    // Correct onMapReady implementation
    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 10f))
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}

package com.example.heritageapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.util.Calendar

class HomeActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Handle Menu Icon Click
        val menuIcon: ImageView = findViewById(R.id.menu_icon)
        menuIcon.setOnClickListener { openOptionsMenu() }

        // Initialize MapView
        mapView = findViewById(R.id.map_view)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        // Initialize Buttons
        val btnFood: Button = findViewById(R.id.btn_food)
        val btnPlace: Button = findViewById(R.id.btn_place)
        val btnCloth: Button = findViewById(R.id.btn_cloth)
        val btnFlower: Button = findViewById(R.id.btn_flower)
        val btnTemple: Button = findViewById(R.id.btn_temple)
        val btnFestival: Button = findViewById(R.id.btn_festival)

        // Setting click listeners to show popup menu
        listOf(btnFood, btnPlace, btnCloth, btnFlower, btnTemple, btnFestival).forEach { button ->
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

    private fun showDateTimePicker() {
        val calendar = Calendar.getInstance()
        DatePickerDialog(this, { _, year, month, dayOfMonth ->
            TimePickerDialog(this, { _, hourOfDay, minute ->
                val selectedDateTime = "$dayOfMonth/${month + 1}/$year $hourOfDay:$minute"
                Toast.makeText(this, "Selected: $selectedDateTime", Toast.LENGTH_LONG).show()
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show()
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show()
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        val tamilNadu = LatLng(10.8505, 76.2711)
        googleMap.addMarker(MarkerOptions().position(tamilNadu).title("Tamil Nadu"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tamilNadu, 7f))
    }

    // Popup Menu Function
    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_ratings -> {
                    Toast.makeText(this, "Ratings Selected", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.menu_highly_visited -> {
                    Toast.makeText(this, "Highly Visited Selected", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
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

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }
}

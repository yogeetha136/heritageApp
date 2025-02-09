package com.example.heritageapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.view.MenuInflater
import android.view.View
import android.widget.Button
import android.widget.PopupMenu

class HomeActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Handle Menu Icon Click - fixed to use the correct ID for ImageView
        val menuIcon: ImageView = findViewById(R.id.menu_icon)  // Use the correct ID
        menuIcon.setOnClickListener {
            openOptionsMenu() // Opens the options menu
        }

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
        btnFood.setOnClickListener { showPopupMenu(it) }
        btnPlace.setOnClickListener { showPopupMenu(it) }
        btnCloth.setOnClickListener { showPopupMenu(it) }
        btnFlower.setOnClickListener { showPopupMenu(it) }
        btnTemple.setOnClickListener { showPopupMenu(it) }
        btnFestival.setOnClickListener { showPopupMenu(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item1 -> Toast.makeText(this, "Profile Selected", Toast.LENGTH_SHORT).show()
            R.id.item2 -> Toast.makeText(this, "Settings Selected", Toast.LENGTH_SHORT).show()
            R.id.item3 -> Toast.makeText(this, "Mode Selected", Toast.LENGTH_SHORT).show()
            R.id.item4 -> Toast.makeText(this, "Log out Selected", Toast.LENGTH_SHORT).show()
            R.id.item5 -> Toast.makeText(this, "Help Selected", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        val tamilNadu = LatLng(10.8505, 76.2711)
        googleMap.addMarker(MarkerOptions().position(tamilNadu).title("Tamil Nadu"))
        googleMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(tamilNadu, 7f))
    }

    // Popup Menu Function
    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        val inflater: MenuInflater = popupMenu.menuInflater
        inflater.inflate(R.menu.popup_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
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
}

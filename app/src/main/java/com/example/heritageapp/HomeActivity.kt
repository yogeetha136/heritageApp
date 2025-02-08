package com.example.heritageapp

import android.os.Bundle
import android.widget.Button // Import Button class
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class HomeActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Initialize MapView
        mapView = findViewById(R.id.map_view)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        // Button click handlers
        findViewById<Button>(R.id.btn_food).setOnClickListener {
            Toast.makeText(this, "Food clicked", Toast.LENGTH_SHORT).show()
        }
        findViewById<Button>(R.id.btn_place).setOnClickListener {
            Toast.makeText(this, "Place clicked", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Add a marker in Tamil Nadu and move the camera
        val tamilNadu = LatLng(10.8505, 76.2711)
        googleMap.addMarker(MarkerOptions().position(tamilNadu).title("Tamil Nadu"))
        googleMap.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(tamilNadu, 7f))
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

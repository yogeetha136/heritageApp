package com.example.heritageapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class HomeActivity : AppCompatActivity() {
    private var mapView: MapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Configuration.getInstance()
            .load(applicationContext, getSharedPreferences("osm_pref", MODE_PRIVATE))
        setContentView(R.layout.activity_home)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        mapView = findViewById(R.id.mapView)
        mapView?.apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setBuiltInZoomControls(true)
            setMultiTouchControls(true)
            controller.setZoom(15.0)
            addMarkers()
        }

        val btnFood = findViewById<Button>(R.id.btn_food)
        val btnPlace = findViewById<Button>(R.id.btn_place)
        val btnCloth = findViewById<Button>(R.id.btn_cloth)
        val btnFlower = findViewById<Button>(R.id.btn_flower)
        val btnTemple = findViewById<Button>(R.id.btn_temple)
        val btnFestival = findViewById<Button>(R.id.btn_festival)

        btnFood.setOnClickListener { startActivity(Intent(this, FoodActivity::class.java)) }
        btnPlace.setOnClickListener { startActivity(Intent(this, PlaceActivity::class.java)) }
        btnCloth.setOnClickListener { startActivity(Intent(this, ClothActivity::class.java)) }
        btnFlower.setOnClickListener { startActivity(Intent(this, FlowerActivity::class.java)) }
        btnTemple.setOnClickListener { startActivity(Intent(this, TempleActivity::class.java)) }
        btnFestival.setOnClickListener { startActivity(Intent(this, FestivalActivity::class.java)) }
    }

    private fun addMarkers() {
        val locations = listOf(
            Pair(GeoPoint(13.0827, 80.2707), "Chennai - Visitors: 1M"),
            Pair(GeoPoint(9.9252, 78.1198), "Madurai - Visitors: 750K"),
            Pair(GeoPoint(10.7905, 78.7047), "Trichy - Visitors: 500K"),
            Pair(GeoPoint(8.0883, 77.5385), "Kanniyakumari - Visitors: 300K"),
            Pair(GeoPoint(9.2889, 79.3129), "Rameshwaram - Visitors: 450K")
        )

        locations.forEach { (point, cityInfo) ->
            val marker = Marker(mapView)
            marker.position = point
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            marker.title = cityInfo

            marker.setOnMarkerClickListener { marker, mapView ->
                showPopupMenu(mapView, marker)
                true
            }

            mapView?.overlays?.add(marker)
        }

        mapView?.controller?.setCenter(locations[0].first)
    }

    private fun showPopupMenu(view: View, marker: Marker) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menu_ratings -> {
                    Toast.makeText(this, "Ratings for ${marker.title}", Toast.LENGTH_SHORT).show()
                }
                R.id.menu_highly_visited -> {
                    Toast.makeText(this, "${marker.title} is a highly visited place!", Toast.LENGTH_SHORT).show()
                }
            }
            true
        }
        popupMenu.show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item1 -> {
                Toast.makeText(this, "Profile Selected", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ProfileActivity::class.java))
            }
            R.id.item2 -> {
                Toast.makeText(this, "Settings Selected", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, SettingsActivity::class.java)) // Navigating to SettingsActivity
            }
            R.id.item4 -> Toast.makeText(this, "Log out Selected", Toast.LENGTH_SHORT).show()
            R.id.item5 -> Toast.makeText(this, "Help Selected", Toast.LENGTH_SHORT).show()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }
}

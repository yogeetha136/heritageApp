package com.example.heritageapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import java.util.Arrays
import java.util.Calendar

class HomeActivity : AppCompatActivity() {
    private var tvDateTime: TextView? = null
    private var mapView: MapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Configuration.getInstance()
            .load(applicationContext, getSharedPreferences("osm_pref", MODE_PRIVATE))
        setContentView(R.layout.activity_home)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        tvDateTime = findViewById(R.id.tv_date_time)
        val menuIcon = findViewById<ImageView>(R.id.menu_icon)
        menuIcon.setOnClickListener { v: View? -> openOptionsMenu() }

        mapView = findViewById(R.id.mapView)
        if (mapView != null) {
            mapView!!.setTileSource(TileSourceFactory.MAPNIK)
            mapView!!.setBuiltInZoomControls(true)
            mapView!!.setMultiTouchControls(true)

            mapView!!.controller.setZoom(15.0)

            addMarkers()
        }

        val btnFood = findViewById<Button>(R.id.btn_food)
        val btnPlace = findViewById<Button>(R.id.btn_place)
        val btnCloth = findViewById<Button>(R.id.btn_cloth)
        val btnFlower = findViewById<Button>(R.id.btn_flower)
        val btnTemple = findViewById<Button>(R.id.btn_temple)
        val btnFestival = findViewById<Button>(R.id.btn_festival)

        btnFood.setOnClickListener { v: View? ->
            startActivity(
                Intent(
                    this,
                    FoodActivity::class.java
                )
            )
        }
        btnPlace.setOnClickListener { v: View? ->
            startActivity(
                Intent(
                    this,
                    PlaceActivity::class.java
                )
            )
        }
        btnCloth.setOnClickListener { v: View? ->
            startActivity(
                Intent(
                    this,
                    ClothActivity::class.java
                )
            )
        }
        btnFlower.setOnClickListener { v: View? ->
            startActivity(
                Intent(
                    this,
                    FlowerActivity::class.java
                )
            )
        }
        btnTemple.setOnClickListener { v: View? ->
            startActivity(
                Intent(
                    this,
                    TempleActivity::class.java
                )
            )
        }
        btnFestival.setOnClickListener { v: View? ->
            startActivity(
                Intent(
                    this,
                    FestivalActivity::class.java
                )
            )
        }

        val popupListener =
            View.OnClickListener { view: View -> this.showPopupMenu(view) }
        btnPlace.setOnClickListener(popupListener)
        btnCloth.setOnClickListener(popupListener)
        btnFlower.setOnClickListener(popupListener)
        btnTemple.setOnClickListener(popupListener)
        btnFestival.setOnClickListener(popupListener)
    }

    private fun addMarkers() {
        val locations = listOf(
            GeoPoint(13.0827, 80.2707),  // Chennai
            GeoPoint(9.9252, 78.1198),   // Madurai
            GeoPoint(10.7905, 78.7047),  // Trichy
            GeoPoint(8.0883, 77.5385),   // Kanniyakumari
            GeoPoint(9.2889, 79.3129)    // Rameshwaram
        )

        for (point in locations) {
            val marker = Marker(mapView)
            marker.position = point
            marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
            marker.title = "Location: " + point.latitude + ", " + point.longitude
            mapView!!.overlays.add(marker)
        }

        mapView!!.controller.setCenter(locations[0])
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item1 -> {
                Toast.makeText(this, "Profile Selected", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
            }
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
        popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
        popupMenu.show()
    }

    private fun showDateTimePicker() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { view: DatePicker?, year: Int, month: Int, dayOfMonth: Int ->
                val timePickerDialog = TimePickerDialog(
                    this,
                    { view1: TimePicker?, hourOfDay: Int, minute: Int ->
                        tvDateTime!!.text =
                            "Date: " + dayOfMonth + "/" + (month + 1) + "/" + year + " Time: " + hourOfDay + ":" + minute
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

    override fun onResume() {
        super.onResume()
        if (mapView != null) mapView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        if (mapView != null) mapView!!.onPause()
    }
}
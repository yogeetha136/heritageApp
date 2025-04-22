package com.example.heritageapp

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.GridLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class PlaceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)
        val backImage: ImageView = findViewById(R.id.btnBack)
        val gridLayout = findViewById<GridLayout>(R.id.grid1)
        val fadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in_from_button)
        for (i in 0 until gridLayout.childCount) {
            val view = gridLayout.getChildAt(i)
            view.startAnimation(fadeInAnim)
        }
        backImage.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }
        val cityButtons = listOf(
            R.id.btnChennai,
            R.id.btnMadurai,
            R.id.btnCoimbatore,
            R.id.btnTrichy,
            R.id.btnSalem,
            R.id.btnTirunelveli,
            R.id.btnErode,
            R.id.btnThoothukudi,
            R.id.btnVellore,
            R.id.btnTanjavur,
            R.id.btnTirupur,
            R.id.btnSivakasi,
            R.id.btnKancheepuram,
            R.id.btnPollachi,
            R.id.btnVirudhunagar,
            R.id.btnDharmapuri
        )

        val clickAnim = AnimationUtils.loadAnimation(this, R.anim.bounce_scale)

        for (buttonId in cityButtons) {
            val button: Button = findViewById(buttonId)

            // Apply click animation on click
            button.setOnClickListener {
                it.startAnimation(clickAnim)
            }

        }
    }
}



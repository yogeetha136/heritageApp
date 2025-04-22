package com.example.heritageapp

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class FoodActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        val gridLayout = findViewById<GridLayout>(R.id.grid1)
        val fadeInAnim = AnimationUtils.loadAnimation(this, R.anim.fade_in_from_button)

        for (i in 0 until gridLayout.childCount) {
            val view = gridLayout.getChildAt(i)
            view.startAnimation(fadeInAnim)
        }

        val backImage: ImageView = findViewById(R.id.btnBack)
        backImage.startAnimation(fadeInAnim)
        backImage.setOnClickListener {
            finish()
        }

        val cityButtons = listOf(
            R.id.btnChennai, R.id.btnMadurai, R.id.btnCoimbatore, R.id.btnTrichy, R.id.btnSalem,
            R.id.btnTirunelveli, R.id.btnErode, R.id.btnThoothukudi, R.id.btnVellore, R.id.btnTanjavur,
            R.id.btnTirupur, R.id.btnSivakasi, R.id.btnKancheepuram, R.id.btnPollachi,
            R.id.btnVirudhunagar, R.id.btnDharmapuri
        )

        val clickAnim = AnimationUtils.loadAnimation(this, R.anim.bounce_scale)

        for (buttonId in cityButtons) {
            val button: Button = findViewById(buttonId)

            // Apply click animation on click
            button.setOnClickListener {
                it.startAnimation(clickAnim)
            }

            // Register for context menu
            registerForContextMenu(button)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu?.add(0, v?.id!!, 0, "Give a Star Rating")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val button = findViewById<Button>(item.itemId)
        showRatingDialog(this, button.text.toString())
        return true
    }

    private fun showRatingDialog(context: Context, cityName: String) {
        val dialogView = layoutInflater.inflate(R.layout.dialogue_rating, null)
        val ratingBar = dialogView.findViewById<RatingBar>(R.id.ratingBar)

        AlertDialog.Builder(context)
            .setTitle("Rate $cityName")
            .setView(dialogView)
            .setPositiveButton("Submit") { _, _ ->
                val rating = ratingBar.rating
                Toast.makeText(context, "You rated $cityName: $rating stars", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}

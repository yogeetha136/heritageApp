package com.example.heritageapp

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class ReviewActivity : AppCompatActivity() {

    private lateinit var spinnerPlaces: Spinner
    private lateinit var etReview: EditText
    private lateinit var btnSubmit: Button
    private lateinit var recyclerReviews: RecyclerView
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var db: FirebaseFirestore // Corrected duplicate declaration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        // Initialize Firebase (Ensure it's initialized in Application class too)
        FirebaseApp.initializeApp(this)
        db = FirebaseFirestore.getInstance()

        // Initialize UI elements
        spinnerPlaces = findViewById(R.id.spinner_places)
        etReview = findViewById(R.id.et_review)
        btnSubmit = findViewById(R.id.btn_submit_review)
        recyclerReviews = findViewById(R.id.recycler_reviews)

        // Spinner setup
        val places = arrayOf("Chennai", "Madurai", "Trichy", "Kanniyakumari", "Rameshwaram")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, places)
        spinnerPlaces.adapter = adapter

        // RecyclerView setup
        reviewAdapter = ReviewAdapter()
        recyclerReviews.layoutManager = LinearLayoutManager(this)
        recyclerReviews.adapter = reviewAdapter

        // Button click listener
        btnSubmit.setOnClickListener {
            submitReview()
        }

        // Load existing reviews
        loadReviews()
    }

    private fun submitReview() {
        val selectedPlace = spinnerPlaces.selectedItem.toString()
        val reviewText = etReview.text.toString().trim()

        if (reviewText.isNotEmpty()) {
            val review = hashMapOf("place" to selectedPlace, "review" to reviewText)
            db.collection("reviews").add(review)
                .addOnSuccessListener {
                    etReview.text.clear()
                    Toast.makeText(this, "Review submitted!", Toast.LENGTH_SHORT).show()
                    loadReviews()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to submit review", Toast.LENGTH_SHORT).show()
                    Log.e("ReviewActivity", "Error submitting review: ${e.message}")
                }
        } else {
            Toast.makeText(this, "Enter a review!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadReviews() {
        db.collection("reviews").addSnapshotListener { documents, error ->
            if (error != null) {
                Log.e("ReviewActivity", "Error loading reviews: ${error.message}")
                Toast.makeText(this, "Failed to load reviews", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }

            val reviews = mutableListOf<Review>()
            documents?.forEach { document ->
                val place = document.getString("place") ?: ""
                val reviewText = document.getString("review") ?: ""
                reviews.add(Review(place, reviewText))
            }

            reviewAdapter.updateReviews(reviews)
        }
    }
}

// Data class for Review
data class Review(val place: String, val review: String)

// Ensure Firebase is initialized globally
class MyApplication : android.app.Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}

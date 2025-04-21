package com.example.heritageapp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ReviewActivity : AppCompatActivity() {

    private lateinit var spinnerPlaces: Spinner
    private lateinit var etReview: EditText
    private lateinit var btnSubmit: Button
    private lateinit var recyclerReviews: RecyclerView
    private lateinit var reviewAdapter: ReviewAdapter
    private lateinit var btnCamera: Button
    private lateinit var imagePreview: ImageView
    private var imageUri: Uri? = null
    private lateinit var photoFile: File

    private val REQUEST_CAMERA_PERMISSION = 100
    private val REQUEST_IMAGE_CAPTURE = 101

    private val reviews = mutableListOf<Review>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        spinnerPlaces = findViewById(R.id.spinner_places)
        etReview = findViewById(R.id.et_review)
        btnSubmit = findViewById(R.id.btn_submit_review)
        recyclerReviews = findViewById(R.id.recycler_reviews)
        btnCamera = findViewById(R.id.btn_camera)
        imagePreview = findViewById(R.id.image_preview)

        val places = arrayOf("Chennai", "Madurai", "Trichy", "Kanniyakumari", "Rameshwaram")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, places)
        spinnerPlaces.adapter = adapter

        reviewAdapter = ReviewAdapter()
        recyclerReviews.layoutManager = LinearLayoutManager(this)
        recyclerReviews.adapter = reviewAdapter

        btnCamera.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_CAMERA_PERMISSION
                )
            } else {
                openCamera()
            }
        }

        btnSubmit.setOnClickListener {
            submitReview()
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (intent.resolveActivity(packageManager) != null) {
            try {
                photoFile = createImageFile()
                imageUri = FileProvider.getUriForFile(
                    this,
                    "${packageName}.provider",
                    photoFile
                )
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
            } catch (ex: IOException) {
                Log.e("ReviewActivity", "Error creating file: ${ex.message}")
                Toast.makeText(this, "Failed to open camera", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val imageFileName = "JPEG_${timeStamp}_"
        val storageDir: File = cacheDir
        return File.createTempFile(imageFileName, ".jpg", storageDir)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            imageUri?.let {
                imagePreview.setImageURI(it)
                imagePreview.visibility = ImageView.VISIBLE
            }
        }
    }

    private fun submitReview() {
        val selectedPlace = spinnerPlaces.selectedItem.toString()
        val reviewText = etReview.text.toString().trim()

        if (reviewText.isEmpty()) {
            Toast.makeText(this, "Enter a review!", Toast.LENGTH_SHORT).show()
            return
        }

        val imagePath = imageUri?.toString()

        val review = Review(selectedPlace, reviewText, imagePath)
        reviews.add(0, review)
        reviewAdapter.updateReviews(reviews)

        etReview.text.clear()
        imagePreview.setImageDrawable(null)
        imagePreview.visibility = ImageView.GONE
        imageUri = null

        Toast.makeText(this, "Review submitted!", Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CAMERA_PERMISSION && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera()
        } else {
            Toast.makeText(this, "Camera permission is required.", Toast.LENGTH_SHORT).show()
        }
    }
}

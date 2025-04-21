package com.example.heritageapp

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReviewAdapter : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    private var reviews: List<Review> = listOf()

    class ReviewViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvPlace: TextView = view.findViewById(R.id.tv_place)
        val tvReview: TextView = view.findViewById(R.id.tv_review)
        val imageView: ImageView = view.findViewById(R.id.review_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        holder.tvPlace.text = "Place: ${review.place}"
        holder.tvReview.text = "Review: ${review.review}"

        review.imageUri?.let {
            holder.imageView.setImageURI(Uri.parse(it))
            holder.imageView.visibility = View.VISIBLE
        } ?: run {
            holder.imageView.visibility = View.GONE
        }
    }

    override fun getItemCount() = reviews.size

    fun updateReviews(newReviews: List<Review>) {
        reviews = newReviews
        notifyDataSetChanged()
    }
}

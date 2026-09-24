package com.tourvista.india

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class FavoritesActivity : AppCompatActivity() {

    private lateinit var favoritesContainer: LinearLayout
    private lateinit var emptyMessage: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_favorites)

        favoritesContainer = findViewById(R.id.favoritesContainer)
        emptyMessage = findViewById(R.id.emptyMessage)

        loadFavorites()
    }

    override fun onResume() {
        super.onResume()
        loadFavorites()
    }

    private fun loadFavorites() {

        favoritesContainer.removeAllViews()

        val preferences =
            getSharedPreferences("TourVistaFavorites", MODE_PRIVATE)

        val places = listOf(
            Place("Taj Mahal", "Agra, Uttar Pradesh", R.drawable.tajmahal),
            Place("Kamakhya Temple", "Guwahati, Assam", R.drawable.kamakhya),
            Place("Golden Temple", "Amritsar, Punjab", R.drawable.goldentemple),
            Place("Shillong", "Meghalaya", R.drawable.shillong),
            Place("Visakhapatnam", "Andhra Pradesh", R.drawable.vizag),
            Place("Charminar", "Hyderabad, Telangana", R.drawable.charminar),
            Place("Pangong Lake", "Ladakh", R.drawable.pangong),
            Place("Baga Beach", "Goa", R.drawable.baga),
            Place("Lonavala", "Maharashtra", R.drawable.lonavala),
            Place("Tawang", "Arunachal Pradesh", R.drawable.tawang),
            Place("Haridwar", "Uttarakhand", R.drawable.haridwar)
        )

        var favoriteCount = 0

        for (place in places) {

            if (preferences.getBoolean(place.name, false)) {

                favoriteCount++

                addFavoriteCard(place)
            }
        }

        if (favoriteCount == 0) {
            emptyMessage.visibility = View.VISIBLE
        } else {
            emptyMessage.visibility = View.GONE
        }
    }

    private fun addFavoriteCard(place: Place) {

        val card = LinearLayout(this)

        card.orientation = LinearLayout.HORIZONTAL
        card.gravity = Gravity.CENTER_VERTICAL
        card.setPadding(12, 12, 12, 12)
        card.setBackgroundColor(android.graphics.Color.WHITE)

        val cardParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            110
        )

        cardParams.setMargins(0, 0, 0, 15)

        card.layoutParams = cardParams

        // Image

        val image = ImageView(this)

        val imageParams = LinearLayout.LayoutParams(100, 85)

        image.layoutParams = imageParams

        image.setImageResource(place.image)

        image.scaleType = ImageView.ScaleType.CENTER_CROP

        card.addView(image)

        // Text section

        val textLayout = LinearLayout(this)

        textLayout.orientation = LinearLayout.VERTICAL

        textLayout.gravity = Gravity.CENTER_VERTICAL

        val textParams = LinearLayout.LayoutParams(
            0,
            LinearLayout.LayoutParams.MATCH_PARENT
        )

        textParams.weight = 1f
        textParams.setMargins(15, 0, 10, 0)

        textLayout.layoutParams = textParams

        val name = TextView(this)

        name.text = place.name
        name.textSize = 18f
        name.setTextColor(android.graphics.Color.DKGRAY)
        name.setTypeface(null, android.graphics.Typeface.BOLD)

        textLayout.addView(name)

        val location = TextView(this)

        location.text = "📍 ${place.location}"
        location.textSize = 14f
        location.setTextColor(android.graphics.Color.GRAY)

        textLayout.addView(location)

        card.addView(textLayout)

        // Heart

        val heart = TextView(this)

        heart.text = "♥"
        heart.textSize = 28f
        heart.setTextColor(android.graphics.Color.RED)
        heart.gravity = Gravity.CENTER

        card.addView(heart)

        // Open destination

        card.setOnClickListener {

            val intent =
                Intent(this, PlaceDetailsActivity::class.java)

            intent.putExtra("PLACE_NAME", place.name)
            intent.putExtra("PLACE_LOCATION", place.location)
            intent.putExtra("PLACE_IMAGE", place.image)

            startActivity(intent)
        }

        favoritesContainer.addView(card)
    }

    data class Place(
        val name: String,
        val location: String,
        val image: Int
    )
}
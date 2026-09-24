package com.tourvista.india

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ExploreActivity : AppCompatActivity() {

    private lateinit var exploreSearch: EditText

    private lateinit var cardTajMahal: View
    private lateinit var cardShillong: View
    private lateinit var cardBaga: View
    private lateinit var cardGoldenTemple: View
    private lateinit var cardTawang: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_explore)

        val btnBack = findViewById<TextView>(R.id.btnBack)

        exploreSearch = findViewById(R.id.exploreSearch)

        cardTajMahal = findViewById(R.id.cardTajMahal)
        cardShillong = findViewById(R.id.cardShillong)
        cardBaga = findViewById(R.id.cardBaga)
        cardGoldenTemple = findViewById(R.id.cardGoldenTemple)
        cardTawang = findViewById(R.id.cardTawang)

        // Back button
        btnBack.setOnClickListener {
            finish()
        }

        // Destination cards
        cardTajMahal.setOnClickListener {
            openPlace(
                "Taj Mahal",
                "Agra, Uttar Pradesh",
                R.drawable.tajmahal
            )
        }

        cardShillong.setOnClickListener {
            openPlace(
                "Shillong",
                "Meghalaya",
                R.drawable.shillong
            )
        }

        cardBaga.setOnClickListener {
            openPlace(
                "Baga Beach",
                "Goa",
                R.drawable.baga
            )
        }

        cardGoldenTemple.setOnClickListener {
            openPlace(
                "Golden Temple",
                "Amritsar, Punjab",
                R.drawable.goldentemple
            )
        }

        cardTawang.setOnClickListener {
            openPlace(
                "Tawang",
                "Arunachal Pradesh",
                R.drawable.tawang
            )
        }

        // Category buttons
        findViewById<Button>(R.id.btnHistorical).setOnClickListener {
            showOnly(cardTajMahal)
        }

        findViewById<Button>(R.id.btnBeaches).setOnClickListener {
            showOnly(cardBaga)
        }

        findViewById<Button>(R.id.btnHills).setOnClickListener {
            showOnly(cardShillong, cardTawang)
        }

        findViewById<Button>(R.id.btnSpiritual).setOnClickListener {
            showOnly(cardGoldenTemple)
        }

        // Search
        exploreSearch.addTextChangedListener(
            object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int
                ) {
                    searchPlaces(s.toString())
                }

                override fun afterTextChanged(
                    s: Editable?
                ) {
                }
            }
        )
    }

    private fun searchPlaces(query: String) {

        val search = query.trim().lowercase()

        if (search.isEmpty()) {
            showAll()
            return
        }

        cardTajMahal.visibility =
            if (
                "taj mahal".contains(search) ||
                "taj".contains(search) ||
                "agra".contains(search) ||
                "historical".contains(search)
            ) {
                View.VISIBLE
            } else {
                View.GONE
            }

        cardShillong.visibility =
            if (
                "shillong".contains(search) ||
                "meghalaya".contains(search) ||
                "hill".contains(search) ||
                "mountain".contains(search)
            ) {
                View.VISIBLE
            } else {
                View.GONE
            }

        cardBaga.visibility =
            if (
                "baga".contains(search) ||
                "goa".contains(search) ||
                "beach".contains(search)
            ) {
                View.VISIBLE
            } else {
                View.GONE
            }

        cardGoldenTemple.visibility =
            if (
                "golden".contains(search) ||
                "temple".contains(search) ||
                "amritsar".contains(search) ||
                "punjab".contains(search) ||
                "spiritual".contains(search)
            ) {
                View.VISIBLE
            } else {
                View.GONE
            }

        cardTawang.visibility =
            if (
                "tawang".contains(search) ||
                "arunachal".contains(search) ||
                "mountain".contains(search) ||
                "hill".contains(search)
            ) {
                View.VISIBLE
            } else {
                View.GONE
            }
    }

    private fun showAll() {

        cardTajMahal.visibility = View.VISIBLE
        cardShillong.visibility = View.VISIBLE
        cardBaga.visibility = View.VISIBLE
        cardGoldenTemple.visibility = View.VISIBLE
        cardTawang.visibility = View.VISIBLE
    }

    private fun showOnly(vararg visibleCards: View) {

        val allCards = listOf(
            cardTajMahal,
            cardShillong,
            cardBaga,
            cardGoldenTemple,
            cardTawang
        )

        allCards.forEach {
            it.visibility = View.GONE
        }

        visibleCards.forEach {
            it.visibility = View.VISIBLE
        }
    }

    private fun openPlace(
        name: String,
        location: String,
        image: Int
    ) {

        val intent =
            Intent(
                this,
                PlaceDetailsActivity::class.java
            )

        intent.putExtra("PLACE_NAME", name)
        intent.putExtra("PLACE_LOCATION", location)
        intent.putExtra("PLACE_IMAGE", image)

        startActivity(intent)
    }
}
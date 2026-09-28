package com.tourvista.india

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var searchBox: EditText

    private lateinit var cardTajMahal: View
    private lateinit var cardKamakhya: View
    private lateinit var cardGoldenTemple: View
    private lateinit var cardShillong: View
    private lateinit var cardVizag: View
    private lateinit var cardCharminar: View
    private lateinit var cardPangong: View
    private lateinit var cardBaga: View
    private lateinit var cardLonavala: View
    private lateinit var cardTawang: View
    private lateinit var cardHaridwar: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawerLayout)

        findViewById<TextView>(R.id.btnMenu).setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        searchBox = findViewById(R.id.searchBox)

        cardTajMahal = findViewById(R.id.cardTajMahal)
        cardKamakhya = findViewById(R.id.cardKamakhya)
        cardGoldenTemple = findViewById(R.id.cardGoldenTemple)
        cardShillong = findViewById(R.id.cardShillong)
        cardVizag = findViewById(R.id.cardVizag)
        cardCharminar = findViewById(R.id.cardCharminar)
        cardPangong = findViewById(R.id.cardPangong)
        cardBaga = findViewById(R.id.cardBaga)
        cardLonavala = findViewById(R.id.cardLonavala)
        cardTawang = findViewById(R.id.cardTawang)
        cardHaridwar = findViewById(R.id.cardHaridwar)

        cardTajMahal.setOnClickListener {
            openPlace("Taj Mahal", "Agra, Uttar Pradesh", R.drawable.tajmahal)
        }

        cardKamakhya.setOnClickListener {
            openPlace("Kamakhya Temple", "Guwahati, Assam", R.drawable.kamakhya)
        }

        cardGoldenTemple.setOnClickListener {
            openPlace("Golden Temple", "Amritsar, Punjab", R.drawable.goldentemple)
        }

        cardShillong.setOnClickListener {
            openPlace("Shillong", "Meghalaya", R.drawable.shillong)
        }

        cardVizag.setOnClickListener {
            openPlace("Visakhapatnam", "Andhra Pradesh", R.drawable.vizag)
        }

        cardCharminar.setOnClickListener {
            openPlace("Charminar", "Hyderabad, Telangana", R.drawable.charminar)
        }

        cardPangong.setOnClickListener {
            openPlace("Pangong Lake", "Ladakh", R.drawable.pangong)
        }

        cardBaga.setOnClickListener {
            openPlace("Baga Beach", "Goa", R.drawable.baga)
        }

        cardLonavala.setOnClickListener {
            openPlace("Lonavala", "Maharashtra", R.drawable.lonavala)
        }

        cardTawang.setOnClickListener {
            openPlace("Tawang", "Arunachal Pradesh", R.drawable.tawang)
        }

        cardHaridwar.setOnClickListener {
            openPlace("Haridwar", "Uttarakhand", R.drawable.haridwar)
        }

        findViewById<Button>(R.id.btnHistorical).setOnClickListener {
            showOnly(cardTajMahal, cardCharminar)
        }

        findViewById<Button>(R.id.btnSpiritual).setOnClickListener {
            showOnly(cardKamakhya, cardGoldenTemple, cardHaridwar)
        }

        findViewById<Button>(R.id.btnBeaches).setOnClickListener {
            showOnly(cardVizag, cardBaga)
        }

        findViewById<Button>(R.id.btnHills).setOnClickListener {
            showOnly(cardShillong, cardLonavala, cardTawang)
        }

        findViewById<Button>(R.id.btnNature).setOnClickListener {
            showOnly(cardPangong)
        }

        findViewById<Button>(R.id.btnCities).setOnClickListener {
            showOnly(cardVizag, cardCharminar)
        }

        searchBox.addTextChangedListener(
            object : android.text.TextWatcher {

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
                    s: android.text.Editable?
                ) {
                }
            }
        )

        setupDrawerMenu()
    }

    private fun setupDrawerMenu() {

        findViewById<View>(R.id.viewProfile).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)

            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.menuHome).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        findViewById<TextView>(R.id.menuExplore).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)

            val intent = Intent(this, ExploreActivity::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.menuFavorites).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)

            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.menuTrips).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)

            val intent = Intent(this, MyTripsActivity::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.menuSettings).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)

            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.menuAbout).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)

            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.menuHelp).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)

            val intent = Intent(this, HelpSupportActivity::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.menuPrivacy).setOnClickListener {
            drawerLayout.closeDrawer(GravityCompat.START)

            val intent = Intent(this, PrivacyPolicyActivity::class.java)
            startActivity(intent)
        }

        findViewById<TextView>(R.id.menuLogout).setOnClickListener {

            showLogoutConfirmation()
        }
    }

    private fun showLogoutConfirmation() {

        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setNegativeButton("NO", null)
            .setPositiveButton("YES") { _, _ ->

                val preferences =
                    getSharedPreferences(
                        "TourVistaAccount",
                        MODE_PRIVATE
                    )

                preferences.edit()
                    .putBoolean("isLoggedIn", false)
                    .apply()

                val intent =
                    Intent(
                        this,
                        WelcomeActivity::class.java
                    )

                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK

                startActivity(intent)
            }
            .show()
    }

    private fun searchPlaces(query: String) {

        val search = query.trim().lowercase()

        if (search.isEmpty()) {
            showAll()
            return
        }

        cardTajMahal.visibility =
            if ("taj".contains(search) ||
                "taj mahal".contains(search) ||
                "agra".contains(search)
            ) View.VISIBLE else View.GONE

        cardKamakhya.visibility =
            if ("kamakhya".contains(search) ||
                "temple".contains(search) ||
                "guwahati".contains(search) ||
                "assam".contains(search)
            ) View.VISIBLE else View.GONE

        cardGoldenTemple.visibility =
            if ("golden".contains(search) ||
                "temple".contains(search) ||
                "amritsar".contains(search) ||
                "punjab".contains(search)
            ) View.VISIBLE else View.GONE

        cardShillong.visibility =
            if ("shillong".contains(search) ||
                "meghalaya".contains(search) ||
                "hill".contains(search)
            ) View.VISIBLE else View.GONE

        cardVizag.visibility =
            if ("vizag".contains(search) ||
                "visakhapatnam".contains(search) ||
                "andhra".contains(search) ||
                "beach".contains(search)
            ) View.VISIBLE else View.GONE

        cardCharminar.visibility =
            if ("charminar".contains(search) ||
                "hyderabad".contains(search) ||
                "telangana".contains(search) ||
                "city".contains(search)
            ) View.VISIBLE else View.GONE

        cardPangong.visibility =
            if ("pangong".contains(search) ||
                "ladakh".contains(search) ||
                "nature".contains(search)
            ) View.VISIBLE else View.GONE

        cardBaga.visibility =
            if ("baga".contains(search) ||
                "goa".contains(search) ||
                "beach".contains(search)
            ) View.VISIBLE else View.GONE

        cardLonavala.visibility =
            if ("lonavala".contains(search) ||
                "maharashtra".contains(search) ||
                "hill".contains(search)
            ) View.VISIBLE else View.GONE

        cardTawang.visibility =
            if ("tawang".contains(search) ||
                "arunachal".contains(search) ||
                "hill".contains(search)
            ) View.VISIBLE else View.GONE

        cardHaridwar.visibility =
            if ("haridwar".contains(search) ||
                "uttarakhand".contains(search) ||
                "spiritual".contains(search) ||
                "temple".contains(search)
            ) View.VISIBLE else View.GONE
    }

    private fun showAll() {

        cardTajMahal.visibility = View.VISIBLE
        cardKamakhya.visibility = View.VISIBLE
        cardGoldenTemple.visibility = View.VISIBLE
        cardShillong.visibility = View.VISIBLE
        cardVizag.visibility = View.VISIBLE
        cardCharminar.visibility = View.VISIBLE
        cardPangong.visibility = View.VISIBLE
        cardBaga.visibility = View.VISIBLE
        cardLonavala.visibility = View.VISIBLE
        cardTawang.visibility = View.VISIBLE
        cardHaridwar.visibility = View.VISIBLE
    }

    private fun showOnly(vararg visibleCards: View) {

        val allCards = listOf(
            cardTajMahal,
            cardKamakhya,
            cardGoldenTemple,
            cardShillong,
            cardVizag,
            cardCharminar,
            cardPangong,
            cardBaga,
            cardLonavala,
            cardTawang,
            cardHaridwar
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
            Intent(this, PlaceDetailsActivity::class.java)

        intent.putExtra("PLACE_NAME", name)
        intent.putExtra("PLACE_LOCATION", location)
        intent.putExtra("PLACE_IMAGE", image)

        startActivity(intent)
    }
}
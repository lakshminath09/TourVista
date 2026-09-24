package com.tourvista.india;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PlaceDetailsActivity extends AppCompatActivity {

    private Button btnFavorite;
    private String placeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_details);

        ImageView placeImage = findViewById(R.id.placeImage);
        TextView placeNameText = findViewById(R.id.placeName);
        TextView placeLocation = findViewById(R.id.placeLocation);
        TextView placeDescription = findViewById(R.id.placeDescription);

        Button btnMap = findViewById(R.id.btnMap);
        Button btnBack = findViewById(R.id.btnBack);
        btnFavorite = findViewById(R.id.btnFavorite);

        // Get destination data
        placeName = getIntent().getStringExtra("PLACE_NAME");
        String location = getIntent().getStringExtra("PLACE_LOCATION");
        int image = getIntent().getIntExtra("PLACE_IMAGE", 0);

        // Display destination data
        placeNameText.setText(placeName);
        placeLocation.setText("📍 " + location);
        placeImage.setImageResource(image);

        // Description
        String description = "";

        if (placeName != null) {

            switch (placeName) {

                case "Taj Mahal":
                    description = "One of India's most iconic historical monuments, the Taj Mahal is famous for its white marble architecture and beautiful Mughal design.";
                    break;

                case "Kamakhya Temple":
                    description = "Located in Guwahati, Assam, Kamakhya Temple is one of India's important Shakti Peethas and a significant spiritual destination.";
                    break;

                case "Golden Temple":
                    description = "The Golden Temple in Amritsar is a major spiritual and cultural landmark, known for its golden architecture and peaceful surroundings.";
                    break;

                case "Shillong":
                    description = "Known as the Scotland of the East, Shillong is a beautiful hill destination surrounded by green landscapes, waterfalls and pleasant weather.";
                    break;

                case "Visakhapatnam":
                    description = "Visakhapatnam, also known as Vizag, is a coastal city famous for its beaches, scenic coastline and vibrant city life.";
                    break;

                case "Charminar":
                    description = "The Charminar is one of Hyderabad's most famous landmarks, known for its historic architecture and importance to the city's heritage.";
                    break;

                case "Pangong Lake":
                    description = "Pangong Lake is a spectacular high-altitude lake in Ladakh, famous for its dramatic mountains and changing shades of blue.";
                    break;

                case "Baga Beach":
                    description = "Baga Beach is one of Goa's popular coastal destinations, known for its sandy beach, water activities and lively atmosphere.";
                    break;

                case "Lonavala":
                    description = "Lonavala is a scenic hill station in Maharashtra, surrounded by green valleys, waterfalls and beautiful viewpoints.";
                    break;

                case "Tawang":
                    description = "Tawang is a stunning mountain destination in Arunachal Pradesh, known for its monastery, mountain landscapes and breathtaking views.";
                    break;

                case "Haridwar":
                    description = "Haridwar is an important spiritual destination on the banks of the Ganga, known for its ghats, temples and evening Ganga Aarti.";
                    break;
            }
        }

        placeDescription.setText(description);

        // Back button
        btnBack.setOnClickListener(v -> finish());

        // Google Maps
        btnMap.setOnClickListener(v -> {

            Uri mapUri = Uri.parse(
                    "geo:0,0?q=" + Uri.encode(placeName + ", " + location)
            );

            Intent intent = new Intent(Intent.ACTION_VIEW, mapUri);
            startActivity(intent);
        });

        // Check if already favorite
        updateFavoriteButton();

        // Favorite button
        btnFavorite.setOnClickListener(v -> {

            SharedPreferences preferences =
                    getSharedPreferences("TourVistaFavorites", MODE_PRIVATE);

            boolean isFavorite =
                    preferences.getBoolean(placeName, false);

            preferences.edit()
                    .putBoolean(placeName, !isFavorite)
                    .apply();

            updateFavoriteButton();
        });
    }

    private void updateFavoriteButton() {

        SharedPreferences preferences =
                getSharedPreferences("TourVistaFavorites", MODE_PRIVATE);

        boolean isFavorite =
                preferences.getBoolean(placeName, false);

        if (isFavorite) {
            btnFavorite.setText("♥ Added to Favorites");
        } else {
            btnFavorite.setText("♡ Add to Favorites");
        }
    }
}
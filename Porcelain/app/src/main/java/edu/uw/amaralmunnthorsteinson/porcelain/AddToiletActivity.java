package edu.uw.amaralmunnthorsteinson.porcelain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class AddToiletActivity extends AppCompatActivity {

    private final String TAG = "AddToilet";

    // Represents the user's current location, and by extension the location of the new toilet
    // to be added
    private Double latitude;
    private Double longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_toilet);

        latitude = getIntent().getDoubleExtra(MainActivity.LATITUDE, 0);
        longitude = getIntent().getDoubleExtra(MainActivity.LONGITUDE, 0);
        Log.v(TAG, "Latitude: " + latitude);
        Log.v(TAG, "Longitude: " + longitude);
    }
}

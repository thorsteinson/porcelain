package edu.uw.amaralmunnthorsteinson.porcelain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;

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

        getSupportActionBar().setTitle("Add New Toilet");
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Make our add button visible
        View addButton = findViewById(R.id.add_toilet_add_button);
        addButton.setVisibility(View.VISIBLE);
    }

    // This method when called will take all of the data in the various forms and submit it
    // to firebase. If everything goes successfully, it will should end the activity and give a
    // happy message
    public void addToiletToFirebase(View v) {
        Log.v(TAG, "We've clicked ZE BUTTON");
        // Look up all the data and make sure the forms are completed before continuing

        // Make a call to firebase and actually add our new toilet

        // Actually ends the activity
        // finish();
    }
}

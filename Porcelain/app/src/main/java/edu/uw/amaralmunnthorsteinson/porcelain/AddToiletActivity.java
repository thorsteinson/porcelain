package edu.uw.amaralmunnthorsteinson.porcelain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

    // This method when called will take all of the data in the various forms and submit it
    // to firebase. If everything goes successfully, it will should end the activity and give a
    // happy message
    public void addToilet(View v) {
        // Look up all the data and make sure the forms are completed before continuing

        // Make a call to firebase and actually add our new toilet

        // Actually ends the activity
        finish();
    }
}

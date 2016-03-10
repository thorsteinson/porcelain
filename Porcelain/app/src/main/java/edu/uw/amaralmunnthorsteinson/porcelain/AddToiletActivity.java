package edu.uw.amaralmunnthorsteinson.porcelain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

public class AddToiletActivity extends AppCompatActivity {

    private final String TAG = "AddToilet";

    // Represents the user's current location, and by extension the location of the new toilet
    // to be added
    private Double latitude;
    private Double longitude;

    // Instance variables that come from our GUI
    // Optional
    public boolean mGenderNeutral = false;
    public boolean mFamilyFriendly = false;
    public boolean mHandicapAccessible = false;
    public String mNotes = "";
    // Required
    public Long mRating = null;
    public String mName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_toilet);

        latitude = getIntent().getDoubleExtra(MainActivity.LATITUDE, 0);
        longitude = getIntent().getDoubleExtra(MainActivity.LONGITUDE, 0);

        getSupportActionBar().setTitle("Add New Toilet");
        registerListeners();
    }

    // Registers all of the callbacks for our interface so we modify the proper instance variables
    public void registerListeners() {
        // Set up listener for the name
        EditText nameEditText = (EditText) findViewById(R.id.add_toilet_name);
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Update the name
                mName = s.toString();
                updateAddButtonVisibility();
            }
        });

        // Set up listener for the notes
        EditText notesEditText = (EditText) findViewById(R.id.add_toilet_notes);
        notesEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Update the name
                mNotes = s.toString();
                updateAddButtonVisibility();
            }
        });

        // Set up listener for the rating
        RatingBar ratingBar = (RatingBar) findViewById(R.id.add_toilet_cleanliness_rating);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mRating = ((Float) rating).longValue();
                updateAddButtonVisibility();
            }
        });

        // Set up listeners for the amenities
        Switch genderNeutralSwitch = (Switch) findViewById(R.id.add_toilet_is_gender_neutral);
        genderNeutralSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mGenderNeutral = isChecked;
            }
        });

        Switch handicapAccessibleSwitch = (Switch) findViewById(R.id.add_toilet_is_handicap_accessible);
        handicapAccessibleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mHandicapAccessible = isChecked;
            }
        });

        Switch familyFriendlySwitch = (Switch) findViewById(R.id.add_toilet_is_family_friendly);
        familyFriendlySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mFamilyFriendly = isChecked;
            }
        });
    }

    // Makes the addButton visible if all of the required attributes have been set
    public void updateAddButtonVisibility() {
        if (mName != null && mRating != null) {
            findViewById(R.id.add_toilet_add_button).setVisibility(View.VISIBLE);
        }
    }

    // This method when called will take all of the data in the various forms and submit it
    // to firebase. If everything goes successfully, it will should end the activity and give a
    // happy message
    public void addToiletToFirebase(View v) {
        // Look up all the data and make sure the forms are completed before continuing

        // Make a call to firebase and actually add our new toilet

        // Actually ends the activity
         finish();
    }
}

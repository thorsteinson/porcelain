package edu.uw.amaralmunnthorsteinson.porcelain;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.model.LatLng;

public class AddToiletActivity extends AppCompatActivity {

    private final String TAG = "AddToilet";

    // Represents the user's current location, and by extension the location of the new toilet
    // to be added
    public LatLng mLocation;

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

        View parent = findViewById(android.R.id.content);
        setupUI(parent);

        Double latitude = getIntent().getDoubleExtra(MainActivity.LATITUDE, 0);
        Double longitude = getIntent().getDoubleExtra(MainActivity.LONGITUDE, 0);
        mLocation = new LatLng(latitude, longitude);

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
        if (mName != null && mRating != null && !mNotes.equals("")) {
            findViewById(R.id.add_toilet_add_button).setVisibility(View.VISIBLE);
        }
    }

    // This method when called will take all of the data in the various forms and submit it
    // to firebase. If everything goes successfully, it will should end the activity and give a
    // happy message
    public void addToiletToFirebase(View v) {
        // Look up all the data: Assumes that no values are NULL
        Firebase rootRef = new Firebase(MainActivity.FIREBASE_URL);
        final Firebase array = rootRef.child("testArray");
        array.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toilet t = new Toilet(mName, mLocation, mRating, mNotes, mFamilyFriendly, mGenderNeutral, mHandicapAccessible, "");
                array.push().setValue(t);
                // Actually ends the activity
                finish();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    // find all instances of EditText Elements and add a touch listener to them.
    public void setupUI(View view) {
        if(!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(AddToiletActivity.this);
                    return false;
                }
            });
        }

        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    // takes an activity reference and removes focus from the keyboard
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)
                activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}

class Toilet {
    public String name;
    public LatLng latLng;
    public Long rating;
    public String descr;
    public Boolean isHandicapAccessible;
    public Boolean isGenderNeutral;
    public Boolean isFamilyFriendly;
    public String review;

    public Toilet(String n, LatLng ll, Long r, String d, Boolean isFamilyFriendly, Boolean isGenderNeutral, Boolean isHandicapAccessible, String review){
        this.name = n;
        this.latLng = ll;
        this.rating = r;
        this.descr = d;
        this.review = "";
        this.isFamilyFriendly = isFamilyFriendly;
        this.isGenderNeutral = isGenderNeutral;
        this.isHandicapAccessible = isHandicapAccessible;
        this.review = review;
    }
}

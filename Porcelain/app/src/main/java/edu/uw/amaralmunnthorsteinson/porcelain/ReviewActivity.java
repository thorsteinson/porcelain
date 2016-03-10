package edu.uw.amaralmunnthorsteinson.porcelain;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;

/**
 * Created by Allison on 3/10/16.
 *
 * Allows user to create a review of an existing restroom
 * Updates the data in Firebase accordingly
 */
public class ReviewActivity extends AppCompatActivity {

    private String key;
    private Firebase reviewRef;
    private Firebase ratingRef;
    private TextView reviewTitle;
    private RatingBar rating;
    private EditText reviewText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        View parent = findViewById(android.R.id.content);
        setupUI(parent);

        //grab this GUID from previous activity to use for searching the database
        key = getIntent().getStringExtra("GUID");

        //grab views to change
        reviewTitle = (TextView) findViewById(R.id.reviewTitle);
        rating = (RatingBar) findViewById(R.id.reviewRating);
        reviewText = (EditText) findViewById(R.id.reviewText);

        //grab the pieces of information from the Firebase
        Firebase rootRef = new Firebase(MainActivity.FIREBASE_URL);
        final Firebase bathroom = rootRef.child("testArray").child(key);
        reviewRef = bathroom.child("review");
        ratingRef = bathroom.child("rating");
        bathroom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, Object> fireToilet = (HashMap<String, Object>) dataSnapshot.getValue();

                //changes the title, so the user understands what restroom they're reviewing
                String title = (String) fireToilet.get("name");
                reviewTitle.setText("Create a new review for " + title);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    //submits rating
    public void onClick(View v) {
        long ratingVal = (long) rating.getRating();
        ratingRef.setValue(ratingVal);
        reviewRef.setValue(reviewText.getText().toString());
        finish();
    }

    // find all instances of EditText Elements and add a touch listener to them.
    public void setupUI(View view) {
        if(!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(ReviewActivity.this);
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

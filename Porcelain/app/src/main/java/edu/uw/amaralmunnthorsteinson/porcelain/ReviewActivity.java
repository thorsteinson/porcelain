package edu.uw.amaralmunnthorsteinson.porcelain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;

/**
 * Created by iguest on 3/10/16.
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


        key = getIntent().getStringExtra("GUID");

        reviewTitle = (TextView) findViewById(R.id.reviewTitle);
        rating = (RatingBar) findViewById(R.id.reviewRating);
        reviewText = (EditText) findViewById(R.id.reviewText);

        Firebase rootRef = new Firebase(MainActivity.FIREBASE_URL);
        final Firebase bathroom = rootRef.child("testArray").child(key);
        reviewRef = bathroom.child("review");
        ratingRef = bathroom.child("rating");
        bathroom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, Object> fireToilet = (HashMap<String, Object>) dataSnapshot.getValue();
                String title = (String) fireToilet.get("name");
                reviewTitle.setText("Create a new review for " + title);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
    }

    public void onClick(View v) {
        long ratingVal = (long) rating.getRating();
        ratingRef.setValue(ratingVal);
        reviewRef.setValue(reviewText.getText().toString());
        finish();
    }
}

package edu.uw.amaralmunnthorsteinson.porcelain;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

/**
 * Created by Allison on 3/9/16.
 *
 * Loads the data of the currently selected restroom and displays
 * that data on a new page
 *
 *
 */
public class ToiletDetail extends AppCompatActivity{

    public Boolean mStartedReview = false;

    private String key;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle("Restroom Details");

        setContentView(R.layout.activity_toilet_detail);

        //load all views that need to be adjusted to fit data
        final TextView mTitle = (TextView) findViewById(R.id.detailTitle);
        final TextView mFamily = (TextView) findViewById(R.id.familyDetail);
        final TextView mGender = (TextView) findViewById(R.id.genderDetail);
        final TextView mHandicap = (TextView) findViewById(R.id.handicapDetail);
        final RatingBar mRating = (RatingBar) findViewById(R.id.toiletDescriptionRating);
        final TextView mDescription = (TextView) findViewById(R.id.toiletDetailDescription);
        final TextView mReview = (TextView) findViewById(R.id.toiletDetailReview);

        //grabs guid from selected restroom on main page, so we can access values
        key = getIntent().getStringExtra("GUID");

        //Reads through restroom's data and sets all necessary fields to match that
        //data. Does not show amenities that the restroom does not have, and shows
        //its rating and most recent review
        Firebase rootRef = new Firebase(MainActivity.FIREBASE_URL);
        final Firebase bathroom = rootRef.child("testArray").child(key);
        bathroom.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, Object> fireToilet = (HashMap<String, Object>) dataSnapshot.getValue();
                String title = (String) fireToilet.get("name");
                boolean familyValue  = (boolean) fireToilet.get("isFamilyFriendly");
                boolean genderValue  = (boolean) fireToilet.get("isGenderNeutral");
                boolean handicapValue  = (boolean) fireToilet.get("isHandicapAccessible");
                long rating = (long) fireToilet.get("rating");
                String description = (String) fireToilet.get("descr");
                String review = (String) fireToilet.get("review");

                mTitle.setText(title);
                if(!familyValue) {
                    mFamily.setVisibility(TextView.GONE);
                }
                if(!genderValue) {
                    mGender.setVisibility(TextView.GONE);
                }
                if(!handicapValue) {
                    mHandicap.setVisibility(TextView.GONE);
                }
                mRating.setRating(rating);
                mDescription.setText(description);
                mReview.setText(review);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });


    }

    //Starts an activity to review an existing restroom, from that restrooms
    //details page
    public void onClick(View v) {
        Log.v("Detailed View", "Entered seeMore function");
        Intent seeToiletDetailIntent = new Intent(this, ReviewActivity.class);
        seeToiletDetailIntent.putExtra("GUID", key);

        mStartedReview = true;
        startActivity(seeToiletDetailIntent);
    }

    //Once the user returns to this page from the createReview activity
    //it immediately brings user to main page
    @Override
    protected void onResume() {
        super.onResume();
        if (mStartedReview) {
            finish();
        }
    }
}

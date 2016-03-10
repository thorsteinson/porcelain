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
 * Created by iguest on 3/9/16.
 */
public class ToiletDetail extends AppCompatActivity{

    private String key;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toilet_detail);

        final TextView mTitle = (TextView) findViewById(R.id.detailTitle);
        final TextView mFamily = (TextView) findViewById(R.id.familyDetail);
        final TextView mGender = (TextView) findViewById(R.id.genderDetail);
        final TextView mHandicap = (TextView) findViewById(R.id.handicapDetail);
        final RatingBar mRating = (RatingBar) findViewById(R.id.toiletDescriptionRating);
        final TextView mDescription = (TextView) findViewById(R.id.toiletDetailDescription);
        final TextView mReview = (TextView) findViewById(R.id.toiletDetailReview);

        key = getIntent().getStringExtra("GUID");

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

    public void onClick(View v) {
        Log.v("Detailed View", "Entered seeMore function");
        Intent seeToiletDetailIntent = new Intent(this, ReviewActivity.class);
        seeToiletDetailIntent.putExtra("GUID", key);
        startActivity(seeToiletDetailIntent);
    }


}

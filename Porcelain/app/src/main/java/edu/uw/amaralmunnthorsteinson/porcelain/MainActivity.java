package edu.uw.amaralmunnthorsteinson.porcelain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "TEST";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Firebase setup
        Firebase.setAndroidContext(this);

        // run testFirebase, which should add a new child in our list
        testFirebase();
    }

    // Testing method to make sure firebase works as expected
    // and we can really read and write data
    void testFirebase() {
        Firebase rootRef = new Firebase("https://fiery-torch-3951.firebaseio.com/");
        final Firebase array = rootRef.child("testArray");
        long maxVal;

        array.addValueEventListener(new ValueEventListener() {
            boolean addedData = false;

            @Override
            // This callback get's called when the data FIRST becomes available, and then
            // when it changes as well.
            public void onDataChange(DataSnapshot snapshot) {
                // Prevents infinite loop, we only want to change the data once
                // Without this, as soon as a value changes, it would trigger another change
                if (!addedData)  {
                    addedData = true;
                    Map<String, Long> val = (HashMap<String, Long>) snapshot.getValue();
                    Log.v(TAG, "" + val);

                    // This is a 'list' according to the firebase documentation
                    // Instead of using indices, we use unique ids so to allow multiple people
                    // to add data at the same time. Push() generates the UUID
                    //
                    // We then use a HashMap to represent the uuid, long tuple
                    array.push().setValue(snapshot.getChildrenCount());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.v(TAG, "The read failed: " + firebaseError.getMessage());
            }
        });
    }
}

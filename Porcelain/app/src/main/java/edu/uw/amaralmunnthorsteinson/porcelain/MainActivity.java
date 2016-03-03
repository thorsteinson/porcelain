package edu.uw.amaralmunnthorsteinson.porcelain;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private final String TAG = "TEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Firebase setup
        Firebase.setAndroidContext(this);

        // run testFirebase, which should add a new child in our list
        testFirebase();

        // Maps
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
                if (!addedData) {
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

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}

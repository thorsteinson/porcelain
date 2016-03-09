package edu.uw.amaralmunnthorsteinson.porcelain;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class MainActivity extends AppCompatActivity
        implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    private boolean mFirstLoc = true;
    private HashMap<Marker, Place> mMarkerMap = new HashMap<>();
    private BitmapDescriptor mToilet;

    // The marker that tracks the USER location
    private Marker mLocationMarker;

    private final String TAG = "TEST";
    private final String INIT_MARKER_TITLE = "You are here!";


    private TextView mtitleText;
    private TextView mdescriptionText;
    // Google client instance
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Firebase setup
        Firebase.setAndroidContext(this);

        // Maps
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Builds a new GoogleApiClient for dealing with everything
        if(mGoogleApiClient == null) {
            mGoogleApiClient =
                    new GoogleApiClient.Builder(this)
                            .addConnectionCallbacks(this)
                            .addOnConnectionFailedListener(this)
                            .addApi(LocationServices.API) //use location
                            .build(); //build me the client already dammit!
            mGoogleApiClient.connect();
        }
        mtitleText = (TextView) findViewById(R.id.toiletTitle);
        mdescriptionText = (TextView) findViewById(R.id.toiletDescription);
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.toilet);
        mToilet = icon;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(i);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void addPlace(){

    }

    // Testing method to make sure firebase works as expected
    // and we can really read and write data
    void testFirebase() {
        Firebase rootRef = new Firebase("https://fiery-torch-3951.firebaseio.com/");
        final Firebase array = rootRef.child("testArray");
        array.addValueEventListener(new ValueEventListener() {
            boolean addedData = false;

            @Override
            // This callback get's called when the data FIRST becomes available, and then
            // when it changes as well.
            public void onDataChange(DataSnapshot snapshot) {
                // Prevents infinite loop, we only want to change the data once
                // Without this, as soon as a value changes, it would trigger another change
                    Map<String, HashMap<String, Object>> val = (HashMap<String, HashMap<String, Object>>) snapshot.getValue();

                    //LatLng point = new LatLng(-23,44.00);
                    //Place p = new Place("A Bathroom", point, 3.0, "A clean and safe environment");
                    //Log.v(TAG, "" + val);
                    for(String s : val.keySet()){
                        HashMap h = val.get(s);
                        HashMap<String, Double> coords = (HashMap)h.get("latLng");
                        LatLng point = new LatLng((Double)coords.get("latitude"), (Double)coords.get("longitude"));

                        Marker mapPoint = mMap.addMarker(new MarkerOptions().icon(mToilet).position(point).title(s).snippet("" + h.get("name")));

                        Place p = new Place((String)h.get("name"), point, ((Double)h.get("rating")).longValue(), (String)h.get("descr"), s);
                        mMarkerMap.put(mapPoint, p);
                    }

                    // This is a 'list' according to the firebase documentation
                    // Instead of using indices, we use unique ids so to allow multiple people
                    // to add data at the same time. Push() generates the UUID
                    //
                    // We then use a HashMap to represent the uuid, long tuple
                    //array.push().setValue(p);
                    Log.v(TAG, "" + array);
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
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // Don't do anything if the initial marker gets clicked
                // Maybe there's a better way to filter this, but whatevs
                if (!marker.getTitle().equals(INIT_MARKER_TITLE)) {
                    Place pl = mMarkerMap.get(marker);
                    mtitleText.setText(pl.getName());
                    mdescriptionText.setText(pl.getDescr());
                }
                return true;
            }
        });
        // run testFirebase, which should add a new child in our list
        testFirebase();
        // Get current location and move camera there
        Location curLoc = getLocation(null);
        if (curLoc != null) {
            Log.v(TAG, "Adding initial marker");
            LatLng curPos = new LatLng(curLoc.getLatitude(), curLoc.getLatitude());
            mMap.addMarker(new MarkerOptions().position(curPos).title("You are here"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(curPos));
        }
    }

    // Google API Connection
    @Override
    public void onConnected(Bundle bundle) {
        Log.v(TAG, "onConnected called");

        LocationRequest request = new LocationRequest();
        request.setInterval(10000);
        request.setFastestInterval(5000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, request, this);
    }

    // Google API Connection
    @Override
    public void onConnectionSuspended(int i) {
        Log.v(TAG, "onConnectedSuspended called");
    }

    // Google API Connection
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.v(TAG, "onConnectionFailed called");
        //when API hasn't connected, make toast
        Toast.makeText(this, "Uh oh! Can't connect to the API", Toast.LENGTH_LONG).show();
    }

    /** Helper method for getting location **/
    public Location getLocation(View v){
        if(mGoogleApiClient != null) {
            Log.v(TAG, "Got a location, returning it");
            return LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        }
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {
        Location curLoc = getLocation(null);
        LatLng curPos = new LatLng(curLoc.getLatitude(), curLoc.getLongitude());
        if (mFirstLoc) {
            // Set the camera to something decent
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curPos, 15));
            // Don't ever touch the camera again
            mFirstLoc = false;

            // Add our initialMarker
            Log.v(TAG, "Adding initial marker");
            mLocationMarker = mMap.addMarker(new MarkerOptions().position(curPos).title(INIT_MARKER_TITLE));
        } else {
            // Update our position
            mLocationMarker.setPosition(curPos);
        }

    }
}

package edu.uw.amaralmunnthorsteinson.porcelain;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;

/**
 * Created by iguest on 3/3/16.
 */
public class SettingsActivity extends AppCompatActivity{
    public void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);

        //displaying custom ActionBar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        View mActionBarView = getLayoutInflater().inflate(R.layout.action_bar_settings, null);
        actionBar.setCustomView(mActionBarView);
        actionBar.setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_CUSTOM);

        //grab settings fragment
        getFragmentManager().beginTransaction()
        .replace(android.R.id.content, new SettingsFragment())
                .commit();

    }
}

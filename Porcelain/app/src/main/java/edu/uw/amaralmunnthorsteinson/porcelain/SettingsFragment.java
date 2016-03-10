package edu.uw.amaralmunnthorsteinson.porcelain;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Allison on 3/3/16.
 *
 * Creates settings page
 */
public class SettingsFragment extends PreferenceFragment{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}

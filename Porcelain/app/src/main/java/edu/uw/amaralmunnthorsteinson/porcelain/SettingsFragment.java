package edu.uw.amaralmunnthorsteinson.porcelain;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by iguest on 3/3/16.
 */
public class SettingsFragment extends PreferenceFragment{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}

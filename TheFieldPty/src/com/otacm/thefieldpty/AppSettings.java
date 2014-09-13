package com.otacm.thefieldpty;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class AppSettings extends PreferenceActivity {
	@SuppressWarnings("deprecation")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        addPreferencesFromResource(R.layout.settings); 
 
    }
}

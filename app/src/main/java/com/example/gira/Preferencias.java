package com.example.gira;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Preferencias extends PreferenceActivity {
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

//        pref.getBoolean("musica", false);

    }
}

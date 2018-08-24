package com.dicoding.kharisazhar.cataloguemovieuiux.settings;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dicoding.kharisazhar.cataloguemovieuiux.preference.MyPreferenceFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyPreferenceFragment myPreference = new MyPreferenceFragment();
        myPreference.setContext(this);
        getFragmentManager().beginTransaction().replace(android.R.id.content, myPreference).commit();

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}

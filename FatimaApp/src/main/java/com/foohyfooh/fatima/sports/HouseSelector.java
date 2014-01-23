package com.foohyfooh.fatima.sports;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.foohyfooh.fatima.sports.util.FatimaSports;

public class HouseSelector extends ActionBarActivity implements View.OnClickListener{

    public final static String PREFS_HOUSE = "house";
    public final static String EXTRA_RESELECT = "reselect";
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.house_selector);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(FatimaSports.getContext());
        Bundle extras = getIntent().getExtras();
        if(extras != null && extras.getBoolean(EXTRA_RESELECT, false)){
            //Nothing has to be done
            //This is simple to prevent the activity from being opened fully if the house is set
        }else if(!sharedPreferences.getString(PREFS_HOUSE, "").equals("")){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        findViewById(R.id.house_selector_matthew).setOnClickListener(this);
        findViewById(R.id.house_selector_mark).setOnClickListener(this);
        findViewById(R.id.house_selector_luke).setOnClickListener(this);
        findViewById(R.id.house_selector_john).setOnClickListener(this);
    }

    public static String getHouse(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(FatimaSports.getContext());
        return sharedPreferences.getString(PREFS_HOUSE, "");
    }

    @Override
    public void onClick(View v) {
        String house = null;
        switch (v.getId()){
            case R.id.house_selector_matthew:
                house = "matthew";
                break;
            case R.id.house_selector_mark:
                house = "mark";
                break;
            case R.id.house_selector_luke:
                house = "luke";
                break;
            case R.id.house_selector_john:
                house = "john";
                break;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREFS_HOUSE, house);
        editor.commit();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

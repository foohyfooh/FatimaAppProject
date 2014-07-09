package com.foohyfooh.fatima.sports.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.foohyfooh.fatima.sports.HouseSelector;
import com.foohyfooh.fatima.sports.R;
import com.foohyfooh.fatima.sports.adapter.SingleParticipantsAdapter;
import com.foohyfooh.fatima.sports.data.ParticipantsRow;
import com.foohyfooh.fatima.sports.datastore.DataStore;

import java.util.List;
import java.util.Map;


public class SingleParticipants extends ActionBarActivity {

    public static final String EXTRA_POS = "pos";
    public static final String EXTRA_YEAR = "year";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_participants_view);
        Intent intent = getIntent();
        int pos = intent.getIntExtra(EXTRA_POS, 0);
        String year = intent.getStringExtra(EXTRA_YEAR);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        Map<String, List<ParticipantsRow>> map = DataStore.getCachedParticipants(HouseSelector.getHouse(), false);
        List<ParticipantsRow> participantsRows = map.get(year);
        SingleParticipantsAdapter adapter = new SingleParticipantsAdapter(getSupportFragmentManager(), year, participantsRows);
        pager.setAdapter(adapter);
        pager.setCurrentItem(pos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
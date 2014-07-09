package com.foohyfooh.fatima.sports.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.foohyfooh.fatima.sports.data.ParticipantsRow;
import com.foohyfooh.fatima.sports.fragment.ParticipantsListFragment;

import java.util.List;
import java.util.Map;

/**
 * Created by Jonathan on 6/7/2014.
 */
public class ParticipantsScrollAdapter extends FragmentStatePagerAdapter {

    private String[] years;
    private Map<String, List<ParticipantsRow>> map;

    public ParticipantsScrollAdapter(FragmentManager fm, Map<String, List<ParticipantsRow>> map) {
        super(fm);
        this.map = map;
        years = map.keySet().toArray(new String[map.values().size()]);
    }

    @Override
    public Fragment getItem(int position) {
        ParticipantsListFragment fragment = new ParticipantsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ParticipantsListFragment.EXTRA_YEAR, years[position]);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return years.length;
    }
}

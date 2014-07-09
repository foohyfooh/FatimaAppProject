package com.foohyfooh.fatima.sports.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.foohyfooh.fatima.sports.data.ParticipantsRow;
import com.foohyfooh.fatima.sports.fragment.SingleParticipantsFragment;

import java.util.List;

/**
 * Created by Jonathan on 5/7/2014.
 */
public class SingleParticipantsAdapter extends FragmentStatePagerAdapter {

    private String year;
    private List<ParticipantsRow> participantsRows;
    public SingleParticipantsAdapter(FragmentManager fm, String year, List<ParticipantsRow> participantsRows) {
        super(fm);
        this.year = year;
        this.participantsRows = participantsRows;
    }

    @Override
    public Fragment getItem(int position) {
        ParticipantsRow pr = position > 0 && position <= participantsRows.size() ?
                participantsRows.get(position): participantsRows.get(0);
        SingleParticipantsFragment f = new SingleParticipantsFragment();
        Bundle b = new Bundle();
        b.putString(SingleParticipantsFragment.EXTRA_YEAR, year);
        b.putInt(SingleParticipantsFragment.EXTRA_POS, position);
        b.putParcelable(SingleParticipantsFragment.KEY, pr);
        f.setArguments(b);
        return f;
    }

    @Override
    public int getCount() {
        return participantsRows.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return position > 0 && position < participantsRows.size() ?
                participantsRows.get(position).getEvent() : "Fatima Event";
    }
}

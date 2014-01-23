package com.foohyfooh.fatima.sports.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foohyfooh.fatima.sports.HouseSelector;
import com.foohyfooh.fatima.sports.R;
import com.foohyfooh.fatima.sports.data.ParticipantsRow;
import com.foohyfooh.fatima.sports.datastore.DataStore;
import com.foohyfooh.fatima.sports.util.DisplayUtils;

import java.util.List;


public class SingleParticipants extends ActionBarActivity {
    public static final String EXTRA_HOUSE = "house";
    public static final String EXTRA_POS = "pos";
    public static final String KEY = "single_participants";

    private String house;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.single_participants_view);
        Intent intent = getIntent();
        house = HouseSelector.getHouse();
        int pos = intent.getIntExtra(EXTRA_POS, 0);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        List<ParticipantsRow> participantsRows = DataStore.getCachedParticipants(house, false);
        SingleParticipantsAdapter adapter = new SingleParticipantsAdapter(getSupportFragmentManager(),participantsRows);
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

    private class SingleParticipantsAdapter extends FragmentStatePagerAdapter{

        private List<ParticipantsRow> participantsRows;
        public SingleParticipantsAdapter(FragmentManager fm, List<ParticipantsRow> participantsRows) {
            super(fm);
            this.participantsRows = participantsRows;
        }

        @Override
        public Fragment getItem(int position) {
            return position > 0 && position <= participantsRows.size() ?
                    new SingleParticipantsFragment(participantsRows.get(position)) :
                    new SingleParticipantsFragment(participantsRows.get(0));
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

    public class SingleParticipantsFragment extends Fragment{

        private ParticipantsRow participants;

        public SingleParticipantsFragment(){}

        public SingleParticipantsFragment(ParticipantsRow participants){
            this.participants = participants;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.single_participants, null, false);

            if(savedInstanceState != null && savedInstanceState.containsKey(KEY)){
                participants = savedInstanceState.getParcelable(KEY);
            }

            DisplayUtils.setBackgroundColour(rootView, house);
            DisplayUtils.setHeaderImage(rootView, R.id.single_participants_image_header, house);

            ((TextView) rootView.findViewById(R.id.single_participants_event)).setText(participants.getEvent());
            ((TextView) rootView.findViewById(R.id.single_participants_year)).setText(participants.getYear());
            ((TextView) rootView.findViewById(R.id.single_participants_participants)).setText(participants.getParticipants());

            return rootView;
        }

        public void onSaveInstanceState(Bundle bundle){
            bundle.putParcelable(KEY, participants);
            super.onSaveInstanceState(bundle);
        }
    }
}
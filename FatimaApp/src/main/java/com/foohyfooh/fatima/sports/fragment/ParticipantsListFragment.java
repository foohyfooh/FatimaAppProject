package com.foohyfooh.fatima.sports.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.foohyfooh.fatima.sports.HouseSelector;
import com.foohyfooh.fatima.sports.R;
import com.foohyfooh.fatima.sports.adapter.ParticipantsAdapter;
import com.foohyfooh.fatima.sports.datastore.DataStore;
import com.foohyfooh.fatima.sports.util.DisplayUtils;
import com.foohyfooh.fatima.sports.util.FatimaSports;


public class ParticipantsListFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static final String EXTRA_YEAR = "year";

    private Context context;
    private ParticipantsAdapter adapter;
    private String year;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ParticipantsListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.participants, container, false);
        super.onCreate(savedInstanceState);
        context = getActivity();
        String house = HouseSelector.getHouse();
        DisplayUtils.setBackgroundColour(rootView, house);

        TextView header = (TextView) rootView.findViewById(R.id.participants_header);
        header.setText( "St. " + Character.toUpperCase(house.charAt(0)) + house.substring(1) + " House Participants");


        year = getArguments().getString(EXTRA_YEAR);
        adapter = new ParticipantsAdapter(context, R.layout.participants_row,
                DataStore.getCachedParticipants(HouseSelector.getHouse(), false).get(year));

        ListView listView = (ListView) rootView.findViewById(R.id.participants_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), SingleParticipants.class);
        intent.putExtra(SingleParticipantsFragment.EXTRA_YEAR, year);
        intent.putExtra(SingleParticipantsFragment.EXTRA_POS, position);
        startActivity(intent);
    }
}

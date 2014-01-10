package com.foohyfooh.fatima.sports.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.foohyfooh.fatima.sports.R;
import com.foohyfooh.fatima.sports.adapter.ParticipantsAdapter;
import com.foohyfooh.fatima.sports.data.ParticipantsRow;
import com.foohyfooh.fatima.sports.datastore.DataStore;
import com.foohyfooh.fatima.sports.util.DisplayUtils;
import com.foohyfooh.fatima.sports.util.GetTask;
import com.foohyfooh.fatima.sports.util.Refreshable;

import java.util.ArrayList;
import java.util.List;

public class Participants extends Fragment implements AdapterView.OnItemClickListener, Refreshable {
    public static final String ARG_HOUSE = "house";
    public static final String KEY = "participants";

    private String house;
    private Context context;
    private ListView participants;
    private ParticipantsAdapter adapter;
    List<ParticipantsRow> participantsRows;

    public Participants(){}

    public static Participants newInstance(String house){
        Participants p = new Participants();
        Bundle houseInfo = new Bundle();
        houseInfo.putString(ARG_HOUSE, house);
        p.setArguments(houseInfo);
        return p;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.participants, container, false);
        context = getActivity();
        house = getArguments().getString(ARG_HOUSE);
        DisplayUtils.setBackgroundColour(rootView, house);

        TextView header = (TextView) rootView.findViewById(R.id.participants_header);
        header.setText( "St. " + Character.toUpperCase(house.charAt(0)) + house.substring(1) + " House Participants");

        participants = (ListView) rootView.findViewById(R.id.participants_list);
        if(savedInstanceState != null && savedInstanceState.containsKey(KEY+house)){
            participantsRows = savedInstanceState.getParcelableArrayList(KEY+house);
        }else{
            participantsRows = new ArrayList<ParticipantsRow>();
        }
        adapter = new ParticipantsAdapter(context, R.layout.participants_row, participantsRows);
        participants.setAdapter(adapter);
        participants.setOnItemClickListener(this);

        new GetParticipants(context, adapter).execute(false);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(KEY+house, adapter.getRows());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void refresh(GetTask.PostExecuteTask task) {
        new GetParticipants(context, adapter, task).execute(true);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), SingleParticipants.class);
        intent.putExtra(SingleParticipants.EXTRA_HOUSE, house);
        intent.putExtra(SingleParticipants.EXTRA_POS, position);
        startActivity(intent);
    }

    private class GetParticipants extends GetTask<ParticipantsRow, ParticipantsAdapter>{


        public GetParticipants(Context context, ParticipantsAdapter adapter) {
            super(context, adapter);
        }

        public GetParticipants(Context context, ParticipantsAdapter adapter, PostExecuteTask task) {
            super(context, adapter, task);
        }

        @Override
        protected List<ParticipantsRow> doInBackground(Boolean... params) {
            return DataStore.getCachedParticipants(house, params[0]);
        }
    }

}

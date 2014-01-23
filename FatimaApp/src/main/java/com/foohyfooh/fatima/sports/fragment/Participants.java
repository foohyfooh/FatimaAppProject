package com.foohyfooh.fatima.sports.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.foohyfooh.fatima.sports.HouseSelector;
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
    public static final String KEY = "participants";

    private String house;
    private Context context;
    private ListView participants;
    private ParticipantsAdapter adapter;
    private AsyncTask getParticipantsTask;

    public Participants(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.participants, container, false);
        context = getActivity();
        house = HouseSelector.getHouse();
        DisplayUtils.setBackgroundColour(rootView, house);

        TextView header = (TextView) rootView.findViewById(R.id.participants_header);
        header.setText( "St. " + Character.toUpperCase(house.charAt(0)) + house.substring(1) + " House Participants");

        List<ParticipantsRow> participantsRows;
        participants = (ListView) rootView.findViewById(R.id.participants_list);
        if(savedInstanceState != null && savedInstanceState.containsKey(KEY)){
            participantsRows = savedInstanceState.getParcelableArrayList(KEY);
        }else{
            participantsRows = new ArrayList<ParticipantsRow>();
        }
        adapter = new ParticipantsAdapter(context, R.layout.participants_row, participantsRows);
        participants.setAdapter(adapter);
        participants.setOnItemClickListener(this);

        getParticipantsTask = new GetParticipants(context, adapter).execute(false);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(KEY, adapter.getRows());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        if(getParticipantsTask.getStatus() == AsyncTask.Status.RUNNING){
            getParticipantsTask.cancel(true);
        }
        super.onDetach();
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

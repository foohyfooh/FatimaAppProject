package com.foohyfooh.fatimaapp.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.foohyfooh.fatimaapp.R;
import com.foohyfooh.fatimaapp.adapter.ParticipantsAdapter;
import com.foohyfooh.fatimaapp.data.ParticipantsRow;
import com.foohyfooh.fatimaapp.datastore.DataStore;

import java.util.ArrayList;
import java.util.List;

public abstract class Participants extends Fragment {
    public static final String ARG_HOUSE = "house";
    private String house;
    private Context context;
    private ListView participants;
    private ParticipantsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.participants, container, false);
        context = getActivity();
        house = getArguments().getString(ARG_HOUSE);

        TextView header = (TextView) root.findViewById(R.id.participants_header);
        header.setText( "St. " + Character.toUpperCase(house.charAt(0)) + house.substring(1) + " House");

        participants = (ListView) root.findViewById(R.id.participants_list);
        adapter = new ParticipantsAdapter(context, R.id.participants_list, new ArrayList<ParticipantsRow>());
        participants.setAdapter(adapter);

        new GetTask().execute(false);
        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("participants", adapter.getRows());
        super.onSaveInstanceState(outState);
    }

    private class GetTask extends AsyncTask<Boolean, Void, List<ParticipantsRow>> {


        @Override
        protected List<ParticipantsRow> doInBackground(Boolean... params) {
            return DataStore.getCachedParticipants(context, house, params[0]);
        }

        @Override
        protected void onPostExecute(List<ParticipantsRow> rows) {
            //super.onPostExecute(rows);
            adapter.clear();
            for(ParticipantsRow row: rows){
                adapter.add(row);
            }
            adapter.notifyDataSetChanged();
        }
    }

}

package com.foohyfooh.fatimaapp;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Participants extends Fragment {

    public static final String ARG_HOUSE = "house";
    private String arg;
    private Context context;
    private ListView participants;
    private ParticipantsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.participants, container, false);
        context = getActivity();
        arg = getArguments().getString(ARG_HOUSE);

        TextView header = (TextView) root.findViewById(R.id.participants_header);
        header.setText( Character.toUpperCase(arg.charAt(0)) + arg.substring(1) + " House");

        participants = (ListView) root.findViewById(R.id.participantsList);
        adapter = new ParticipantsAdapter(context, R.id.participantsList, getParticipants());
        participants.setAdapter(adapter);
        return root;
    }

    private List<ParticipantsRow> getParticipants(){
        try {
            if(!NetworkUtils.hasConnection(context)){
                return (List<ParticipantsRow>) Collections.EMPTY_LIST;
            }
            String url = getString(R.string.participants_url) + arg;
            Log.i("url", url);
            String body = NetworkUtils.getContent(url);

            JSONObject json = new JSONObject(body);
            Log.i("fatima_json_code", String.valueOf(json.getInt("code")));
            if(json.getInt("code") != 200){
                return (List<ParticipantsRow>) Collections.EMPTY_LIST;
            }

            JSONArray data = json.getJSONArray("data");
            List<ParticipantsRow> rows = new ArrayList<ParticipantsRow>();
            for(int i = 0; i < data.length(); i++){
                JSONObject jsonObj = data.getJSONObject(i);
                int id = Integer.parseInt(jsonObj.getString("id")),
                        eventId = Integer.parseInt(jsonObj.getString("event_id")),
                        year = Integer.parseInt(jsonObj.getString("year"));
                String eventTitle = jsonObj.getString("event_title"),
                        participants = jsonObj.getString("participants");
                ParticipantsRow row = new ParticipantsRow(id, eventId, eventTitle, participants, year);
                rows.add(row);
            }
            return rows;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return (List<ParticipantsRow>) Collections.EMPTY_LIST;

    }



}

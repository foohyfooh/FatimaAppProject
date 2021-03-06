package com.foohyfooh.fatima.sports.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.foohyfooh.fatima.sports.R;
import com.foohyfooh.fatima.sports.data.ParticipantsRow;

import java.util.ArrayList;
import java.util.List;

public class ParticipantsAdapter extends ArrayAdapter<ParticipantsRow> {

    private final List<ParticipantsRow> rows;

    public ParticipantsAdapter(Context context, int resource, List<ParticipantsRow> rows) {
        super(context, resource, rows);
        this.rows = rows;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = convertView != null ? convertView : inflater.inflate(R.layout.participants_row, parent, false);

        ParticipantsRow row = rows.get(position);
        String participants = row.getParticipants();

        ((TextView) view.findViewById(R.id.participants_row_event_name)).setText(row.getEvent());
        ((TextView) view.findViewById(R.id.participants_row_participant_names)).setText(formattedParticipantList(participants));
        ((TextView) view.findViewById(R.id.participants_row_year)).setText(row.getYear());

        return view;
    }

    public String formattedParticipantList(String participants){
        //about 35 characters fit in the list line on phone
        if(participants.length() > 35)
            return participants.substring(0, 30).concat("...");
        return participants;
    }

    public ArrayList<ParticipantsRow> getRows(){
        return (ArrayList<ParticipantsRow>) rows;
    }
}

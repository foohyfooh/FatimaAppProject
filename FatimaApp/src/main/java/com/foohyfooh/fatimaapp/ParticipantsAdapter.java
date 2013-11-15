package com.foohyfooh.fatimaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ParticipantsAdapter extends ArrayAdapter<ParticipantsRow> {

    private final Context context;
    private final List<ParticipantsRow> rows;

    public ParticipantsAdapter(Context context, int resource, List<ParticipantsRow> rows) {
        super(context, resource, rows);
        this.context = context;
        this.rows = rows;
        notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = convertView != null ? convertView : inflater.inflate(R.layout.participants_row, parent, false);
        ParticipantsRow row = rows.get(position);

        ((TextView) rowView.findViewById(R.id.participants_row_event_name)).setText(row.getEvent());
        ((TextView) rowView.findViewById(R.id.participants_row_participant_names)).setText(row.getParticipants());
        ((TextView) rowView.findViewById(R.id.participants_row_year)).setText(row.getYear());

        return rowView;
    }
}

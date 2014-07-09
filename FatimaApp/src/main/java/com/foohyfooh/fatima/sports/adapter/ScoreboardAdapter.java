package com.foohyfooh.fatima.sports.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.foohyfooh.fatima.sports.R;
import com.foohyfooh.fatima.sports.data.ScoreRecord;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardAdapter extends ArrayAdapter<ScoreRecord> {


    private List<ScoreRecord> scores;
    public ScoreboardAdapter(Context context, int resource, List<ScoreRecord> scores) {
        super(context, resource, scores);
        this.scores = scores;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = convertView != null ? convertView : inflater.inflate(R.layout.scoreboard_row, parent, false);

        ScoreRecord score = scores.get(position);

        ((TextView) view.findViewById(R.id.scoreboard_row_event)).setText(score.getTitle());
        ((TextView) view.findViewById(R.id.scoreboard_row_matthew)).setText(String.valueOf(score.getMatthew()));
        ((TextView) view.findViewById(R.id.scoreboard_row_mark)).setText(String.valueOf(score.getMark()));
        ((TextView) view.findViewById(R.id.scoreboard_row_luke)).setText(String.valueOf(score.getLuke()));
        ((TextView) view.findViewById(R.id.scoreboard_row_john)).setText(String.valueOf(score.getJohn()));

        return view;
    }

    public ArrayList<ScoreRecord> getScores(){
        return (ArrayList<ScoreRecord>) scores;
    }
}

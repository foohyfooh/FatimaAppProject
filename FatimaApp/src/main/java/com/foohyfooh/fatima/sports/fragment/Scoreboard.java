package com.foohyfooh.fatima.sports.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.foohyfooh.fatima.sports.R;
import com.foohyfooh.fatima.sports.adapter.ScoreboardAdapter;
import com.foohyfooh.fatima.sports.data.ScoreRecord;
import com.foohyfooh.fatima.sports.datastore.DataStore;

import java.util.ArrayList;
import java.util.List;


public class Scoreboard extends Fragment {

    private Context context;
    private ScoreboardAdapter adapter;
    private ListView scoreList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.scoreboard, container, false);
        context = getActivity();
        List<ScoreRecord> scores;
        if(savedInstanceState != null && savedInstanceState.containsKey("scores")){
            scores = savedInstanceState.getParcelableArrayList("scores");
        }else{
            scores = new ArrayList<ScoreRecord>();
        }
        adapter = new ScoreboardAdapter(context, R.layout.scoreboard_row, scores);
        scoreList = (ListView) root.findViewById(R.id.scoreboard_list);
        scoreList.setAdapter(adapter);

        new GetTask().execute(true);
        return root;
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("scores", adapter.getScores());
        super.onSaveInstanceState(outState);
    }

    private class GetTask extends AsyncTask<Boolean, Void, List<ScoreRecord>> {

        @Override
        protected List<ScoreRecord> doInBackground(Boolean... params) {
            return DataStore.getCachedScores(context, params[0]);
        }

        @Override
        protected void onPostExecute(List<ScoreRecord> scores) {
            //super.onPostExecute(scores);
            adapter.clear();
            for(ScoreRecord score: scores){
                adapter.add(score);
            }
            adapter.notifyDataSetChanged();
        }
    }

}
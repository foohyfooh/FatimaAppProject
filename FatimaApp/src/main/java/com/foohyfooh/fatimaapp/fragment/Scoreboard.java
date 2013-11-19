package com.foohyfooh.fatimaapp.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.foohyfooh.fatimaapp.R;
import com.foohyfooh.fatimaapp.adapter.ScoreboardAdapter;
import com.foohyfooh.fatimaapp.data.ScoreRecord;
import com.foohyfooh.fatimaapp.datastore.DataStore;

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
        adapter = new ScoreboardAdapter(context, R.layout.scoreboard_row, new ArrayList<ScoreRecord>());
        scoreList = (ListView) root.findViewById(R.id.scoreboard_list);
        scoreList.setAdapter(adapter);

        new GetTask().execute(false);
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
            Log.d("adding", "Adding scores");
            adapter.clear();
            for(ScoreRecord score: scores){
                adapter.add(score);
            }
            adapter.notifyDataSetChanged();
        }
    }

}
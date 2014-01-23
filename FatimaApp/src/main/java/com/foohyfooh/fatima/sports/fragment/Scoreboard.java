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
import com.foohyfooh.fatima.sports.util.GetTask;
import com.foohyfooh.fatima.sports.util.Refreshable;

import java.util.ArrayList;
import java.util.List;


public class Scoreboard extends Fragment implements Refreshable {

    private Context context;
    private ScoreboardAdapter adapter;
    private ListView scoreList;
    private AsyncTask getScoresTask;

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

        getScoresTask = new GetScores(context, adapter).execute(false);
        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("scores", adapter.getScores());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void refresh(GetTask.PostExecuteTask task) {
        new GetScores(context, adapter, task).execute(true);
    }


    private class GetScores extends GetTask<ScoreRecord, ScoreboardAdapter>{

        public GetScores(Context context, ScoreboardAdapter adapter) {
            super(context, adapter);
        }

        public GetScores(Context context, ScoreboardAdapter adapter, PostExecuteTask task) {
            super(context, adapter, task);
        }

        @Override
        protected List<ScoreRecord> doInBackground(Boolean... params) {
            return DataStore.getCachedScores(params[0]);
        }
    }


}
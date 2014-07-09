package com.foohyfooh.fatima.sports.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import com.foohyfooh.fatima.sports.adapter.ScoreboardAdapter;
import com.foohyfooh.fatima.sports.data.ScoreRecord;
import com.foohyfooh.fatima.sports.datastore.DataStore;
import com.foohyfooh.fatima.sports.util.GetTask;
import com.foohyfooh.fatima.sports.util.NetworkUtils;
import com.foohyfooh.fatima.sports.util.Refreshable;

import java.util.List;
import java.util.Map;

public class Scoreboard extends Fragment implements Refreshable {

    private Context context;
    private WebView webView;
    private AsyncTask getScoresTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        webView = new WebView(context);
        webView.loadData("<table><tr><td>Event Name</td><td>St. Matthew</td><td>St. Mark</td><td>St. Luke</td><td>St. John</td></tr></table>", "text/html", "UTF-8");
        getScoresTask = new GetScoresTable(context, null).execute(false);
        return webView;
    }

    @Override
    public void refresh(GetTask.PostExecuteTask task) {
        getScoresTask = new GetScoresTable(context, task).execute(true);
    }

    public String produceScoreRow(ScoreRecord scoreRecord){
        return String.format("<tr><td>%s</td><td>%d</td><td>%d</td><td>%d</td><td>%d</td></tr>",
                scoreRecord.getTitle(), scoreRecord.getMatthew(), scoreRecord.getMark(), scoreRecord.getLuke(),
                scoreRecord.getJohn());
    }

    private String prepareTotal(List<ScoreRecord> scores){
        ScoreRecord r = new ScoreRecord();
        r.setTitle("Total");
        int matthewTotal = 0, markTotal = 0, lukeTotal = 0, johnTotal = 0;

        for(ScoreRecord scoreRecord: scores){
            matthewTotal += scoreRecord.getMatthew();
            markTotal += scoreRecord.getMark();
            lukeTotal += scoreRecord.getLuke();
            johnTotal += scoreRecord.getJohn();
        }
        r.setMatthew(matthewTotal);
        r.setMark(markTotal);
        r.setLuke(lukeTotal);
        r.setJohn(johnTotal);
        return produceScoreRow(r);
     }




    private class GetScoresTable extends AsyncTask<Boolean, Void, Map<String, List<ScoreRecord>>> {

        private Context context;
        private GetTask.PostExecuteTask postExecuteTask;
        private boolean hasInternet;

        public GetScoresTable(Context context, GetTask.PostExecuteTask postExecuteTask) {
            this.context = context;
            this.postExecuteTask = postExecuteTask;
            hasInternet = NetworkUtils.hasConnection();
        }

        @Override
        protected void onPreExecute() {
            if(!hasInternet){
                cancel(true);
            }
        }

        @Override
        protected Map<String, List<ScoreRecord>> doInBackground(Boolean... params) {
            return DataStore.getCachedScores(params[0]);
        }

        @Override
        protected void onPostExecute(Map<String, List<ScoreRecord>>  items) {
            //super.onPostExecute(items);
            StringBuilder body = new StringBuilder("<table>");
            for(String year: items.keySet()){
                body.append("<tr><td>"+year+"</td><td>St. Matthew</td><td>St. Mark</td><td>St. Luke</td><td>St. John</td></tr>");
                List<ScoreRecord> yearScores = items.get(year);
                for(ScoreRecord item: yearScores){
                    body.append(produceScoreRow(item));
                }
                body.append(prepareTotal(yearScores));

            }
            body.append("</table>");
            webView.loadData(body.toString(), "text/html", "UTF-8");
            if(postExecuteTask != null) postExecuteTask.execute();
        }

        @Override
        protected void onCancelled() {
            if(!hasInternet){
                Toast.makeText(context, "Turn on internet", Toast.LENGTH_LONG).show();
            }
            super.onCancelled();
        }
    }
}

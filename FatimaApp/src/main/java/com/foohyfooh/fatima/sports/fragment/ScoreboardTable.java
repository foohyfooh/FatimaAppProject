package com.foohyfooh.fatima.sports.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.foohyfooh.fatima.sports.adapter.ScoreboardAdapter;
import com.foohyfooh.fatima.sports.data.ScoreRecord;
import com.foohyfooh.fatima.sports.datastore.DataStore;
import com.foohyfooh.fatima.sports.util.GetTask;
import com.foohyfooh.fatima.sports.util.Refreshable;

import java.util.List;

public class ScoreboardTable extends Fragment implements Refreshable {

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
        getScoresTask = new GetScoresTable(context, null, task).execute(true);
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




    private class GetScoresTable extends GetTask<ScoreRecord, ScoreboardAdapter> {

        public GetScoresTable(Context context, ScoreboardAdapter adapter) {
            super(context, adapter);
        }

        public GetScoresTable(Context context, ScoreboardAdapter adapter, PostExecuteTask task) {
            super(context, adapter, task);
        }

        @Override
        protected List<ScoreRecord> doInBackground(Boolean... params) {
            return DataStore.getCachedScores(params[0]);
        }

        @Override
        protected void onPostExecute(List<ScoreRecord> items) {
            //super.onPostExecute(items);
            StringBuilder body = new StringBuilder("<table><tr><td>Event Name</td><td>St. Matthew</td><td>St. Mark</td><td>St. Luke</td><td>St. John</td></tr>");
            for(ScoreRecord item: items){
                body.append(produceScoreRow(item));
            }
            body.append(prepareTotal(items));
            body.append("</table>");
            webView.loadData(body.toString(), "text/html", "UTF-8");
        }
    }
}

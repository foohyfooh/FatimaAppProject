package com.foohyfooh.fatimaapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Scoreboard extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        WebView webView = new WebView(getActivity());
        webView.loadData(getScores(), "text/html", "UTF-8");
        return webView;
    }

    private String getScores(){
        if(!NetworkUtils.hasConnection(getActivity()))
            return "No data";
        try{
            String body = NetworkUtils.getContent(getString(R.string.scores_url));
            if (body == null){
                return "No data";
            }
            JSONObject json = new JSONObject(body);
            Log.i("fatima_json", json.toString());
            if(json.getInt("code") != 200){
                return "No data";
            }

            JSONArray jsonArray = json.getJSONArray("data");
            Log.i("fatima_json_array", jsonArray.toString());

            ArrayList<ScoreRecord> scores = new ArrayList<ScoreRecord>();
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Log.i("fatima_json_obj", jsonObject.toString());
                ScoreRecord scoreRecord =
                        new ScoreRecord(jsonObject.getString("event_title"), Integer.parseInt(jsonObject.getString("matthew")),
                                Integer.parseInt(jsonObject.getString("mark")), Integer.parseInt(jsonObject.getString("luke")),
                                Integer.parseInt(jsonObject.getString("john")));
                scores.add(scoreRecord);
                Log.i("fatima_json_score", scoreRecord.toString());
            }
            Log.i("fatima_json_scores", scores.toString());


            StringBuilder html = new StringBuilder(getString(R.string.scores_header));
            for(ScoreRecord scoreRecord: scores){
                html.append(scoreRecord.toString());
            }
            html.append(prepareTotal(scores));
            html.append("</table>");
            Log.i("fatima_html", html.toString());

            return html.toString();
        }catch (JSONException e){
            e.printStackTrace();
        }
        return "No data";
    }

    private String prepareTotal(ArrayList<ScoreRecord> scores){
        String title = "Total";
        int matthewTotal = 0, markTotal = 0, lukeTotal = 0, johnTotal = 0;

        for(ScoreRecord scoreRecord: scores){
            matthewTotal += scoreRecord.getMatthew();
            markTotal += scoreRecord.getMark();
            lukeTotal += scoreRecord.getLuke();
            johnTotal += scoreRecord.getJohn();
        }
        return new ScoreRecord(title, matthewTotal, markTotal, lukeTotal, johnTotal).toString();
    }


    public class ScoreRecord {
        private int matthew, mark, luke, john;
        private String title;

        public ScoreRecord(String title, int matthew, int mark, int luke, int john){
            this.title = title;
            this.matthew = matthew;
            this.mark = mark;
            this.luke = luke;
            this.john = john;
        }

        public int getMatthew() {
            return matthew;
        }

        public int getMark() {
            return mark;
        }

        public int getLuke() {
            return luke;
        }

        public int getJohn() {
            return john;
        }

        @Override
        public String toString(){
            return String.format("<tr><td>%s</td><td>%d</td><td>%d</td><td>%d</td><td>%d</td></tr>",
                    title, matthew, mark, luke, john);
        }
    }

}
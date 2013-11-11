package com.foohyfooh.fatimaapp;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Scoreboard extends WebFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        WebView webView = new WebView(getActivity());
        webView.loadData(getScores(), "text/html", "UTF-8");
        return webView;
    }

    private String getScores(){

        try{
            String body = getContent("http://10.0.2.2/fatima/scores_json.php");
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

            List<ScoreRecord> scores = new ArrayList<ScoreRecord>();
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



            StringBuilder html = new StringBuilder("<table><tr><td>Event Name</td><td>St. Matthew</td><td>St. Mark</td><td>St. Luke</td><td>St. John</td></tr>");
            for(ScoreRecord scoreRecord: scores){
                html.append(scoreRecord.toString());
            }
            html.append("</table>");
            Log.i("fatima_html", html.toString());

            return html.toString();
        }catch (JSONException e){
            e.printStackTrace();
        }
        return "No data";
    }

    public class ScoreRecord{
        private int matthew, mark, luke, john;
        private String title;

        public ScoreRecord(String title, int matthew, int mark, int luke, int john){
            this.title = title;
            this.matthew = matthew;
            this.mark = mark;
            this.luke = luke;
            this.john = john;
        }

        @Override
        public String toString(){
            return String.format("<tr><td>%s</td><td>%d</td><td>%d</td><td>%d</td><td>%d</td></tr>",
                    title, matthew, mark, luke, john);
        }
    }
}
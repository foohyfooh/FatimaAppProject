package com.foohyfooh.fatimaapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class Scoreboard extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        WebView webView = new WebView(getActivity());
        webView.loadData(getScores(), "text/html", "UTF-8");
        return webView;
    }

    private String getScores(){

        if(!internetCheck()) return new String();

        try {
            return new AsyncTask<Void, Void, String>(){

                @Override
                protected String doInBackground(Void... params) {
                    try{
                        URL url = new URL("http://10.0.2.2/fatima/scores_json.php");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setReadTimeout(10000);
                        connection.setConnectTimeout(15000);
                        connection.setRequestMethod("GET");
                        connection.connect();
                        if(connection.getResponseCode() != 200){
                            return "No data";
                        }
                        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String line;
                        StringBuilder data = new StringBuilder();
                        while((line = reader.readLine()) != null){
                            data.append(line);
                        }

                        JSONArray jsonArray = new JSONArray(data.toString());
                        Log.i("json_array", jsonArray.toString());

                        List<ScoreRecord> scores = new ArrayList<ScoreRecord>();
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject json = jsonArray.getJSONObject(i);
                            Log.i("json_obj", json.toString());
                            ScoreRecord scoreRecord =
                                    new ScoreRecord(json.getString("event_title"), json.getInt("scores_matthew"),
                                            json.getInt("scores_mark"), json.getInt("scores_luke"), json.getInt("scores_john"));
                            scores.add(scoreRecord);
                        }
                        Log.i("json_scores", scores.toString());

                        StringBuilder html = new StringBuilder("<table><tr><td>Event Name</td><td>St. Matthew</td><td>St. Mark</td><td>St. Luke</td><td>St. John</td></tr>");
                        for(ScoreRecord scoreRecord: scores){
                            html.append(scoreRecord);
                        }
                        html.append("</table>");
                        Log.i("json_html", html.toString());
                        return html.toString();
                    } catch (IOException e) {
                        Log.w("json_io", "Couldn't Read Stream");
                        //e.printStackTrace();
                    } catch (JSONException e) {
                        Log.w("json_error", "bad json");
                        //e.printStackTrace();
                    }
                    return "No data";
                }
            }.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "No data";
    }

    public boolean internetCheck(){
        ConnectivityManager connMgr = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            Log.i("fatimaapp_network", "Has internet");
            return false;
        }
        Log.w("fatimaapp_network", "Has no internet");
        return false;
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
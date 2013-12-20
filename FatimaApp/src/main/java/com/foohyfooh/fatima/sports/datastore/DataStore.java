package com.foohyfooh.fatima.sports.datastore;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.foohyfooh.fatima.sports.data.ParticipantsRow;
import com.foohyfooh.fatima.sports.data.ScoreRecord;
import com.foohyfooh.fatima.sports.util.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DataStore {

    private DataStore(){}

    public static final String BASE_URL = "http://10.0.2.2";
    private static Map<String, List<ParticipantsRow>> participants = new ArrayMap<String, List<ParticipantsRow>>();
    private static List<ScoreRecord> scores;

    public static List<ParticipantsRow> getCachedParticipants(Context context, String house, boolean refresh){
        if(refresh || participants.get(house) == null){
            participants.put(house, getParticipants(context, house));
        }
        return participants.get(house);
    }

    private static List<ParticipantsRow> getParticipants(Context context, String house){
        if(!NetworkUtils.hasConnection(context)){
            return (List<ParticipantsRow>) Collections.EMPTY_LIST;
        }
        String url = BASE_URL + "/fatima/participants_json.php?house=" + house;
        Log.i("url", url);
        String body = NetworkUtils.getContent(url);

        if(body == null){
            return (List<ParticipantsRow>)Collections.EMPTY_LIST;
        }

        try{
            JSONObject json = new JSONObject(body);
            if(json.getInt("code") != 200){
                Log.i("fatima_json_code", String.valueOf(json.getInt("code")));
                return (List<ParticipantsRow>) Collections.EMPTY_LIST;
            }

            JSONArray data = json.getJSONArray("data");
            List<ParticipantsRow> rows = new ArrayList<ParticipantsRow>();
            for(int i = 0; i < data.length(); i++){
                JSONObject jsonObj = data.getJSONObject(i);
                ParticipantsRow row = new ParticipantsRow();
                row.setId(Integer.parseInt(jsonObj.getString("id")));
                row.setEventId(Integer.parseInt(jsonObj.getString("event_id")));
                row.setEvent(jsonObj.getString("event_title"));
                row.setParticipants(jsonObj.getString("participants"));
                row.setYear(jsonObj.getString("year"));
                rows.add(row);
            }
            return rows;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return (List<ParticipantsRow>) Collections.EMPTY_LIST;
    }


    public static List<ScoreRecord> getCachedScores(Context context, boolean refresh){
        if(refresh || scores == null)
            scores = getScores(context);
        return scores;
    }

    private static List<ScoreRecord> getScores(Context context){
        if(!NetworkUtils.hasConnection(context))
            return (List<ScoreRecord>) Collections.EMPTY_LIST;

        String body = NetworkUtils.getContent(BASE_URL + "/fatima/scores_json.php");
        if (body == null){
            return (List<ScoreRecord>) Collections.EMPTY_LIST;
        }
        try{
            JSONObject json = new JSONObject(body);
            //Log.i("fatima_json", json.toString());
            if(json.getInt("code") != 200){
                Log.i("fatima_json_code", String.valueOf(json.getInt("code")));
                return (List<ScoreRecord>) Collections.EMPTY_LIST;
            }

            JSONArray jsonArray = json.getJSONArray("data");
            //Log.i("fatima_json_array", jsonArray.toString());

            ArrayList<ScoreRecord> scores = new ArrayList<ScoreRecord>();
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Log.i("fatima_json_obj", jsonObject.toString());
                ScoreRecord scoreRecord = new ScoreRecord();
                scoreRecord.setTitle(jsonObject.getString("event_title"));
                scoreRecord.setMatthew(Integer.parseInt(jsonObject.getString("matthew")));
                scoreRecord.setMark(Integer.parseInt(jsonObject.getString("mark")));
                scoreRecord.setLuke(Integer.parseInt(jsonObject.getString("luke")));
                scoreRecord.setJohn(Integer.parseInt(jsonObject.getString("john")));
                scoreRecord.setYear(jsonObject.getString("year"));
                scores.add(scoreRecord);
                //Log.i("fatima_json_score", scoreRecord.toString());
            }
            //Log.i("fatima_json_scores", scores.toString());

            return scores;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return(List<ScoreRecord>) Collections.EMPTY_LIST;
    }

}

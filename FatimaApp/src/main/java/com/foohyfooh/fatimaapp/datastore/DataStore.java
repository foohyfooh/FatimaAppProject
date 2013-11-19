package com.foohyfooh.fatimaapp.datastore;

import android.content.Context;
import android.util.Log;

import com.foohyfooh.fatimaapp.data.ParticipantsRow;
import com.foohyfooh.fatimaapp.data.ScoreRecord;
import com.foohyfooh.fatimaapp.util.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataStore {

    private DataStore(){}

    public static final String BASE_URL = "http://10.0.2.2";
    private static List<ParticipantsRow> participants;
    private static List<ScoreRecord> scores;

    public static List<ParticipantsRow> getCachedParticipants(Context context, String house, boolean refresh){
        if(!refresh && participants != null){
            return participants;
        }
        participants = getParticipants(context, house);
        return participants;
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
            Log.i("fatima_json_code", String.valueOf(json.getInt("code")));
            if(json.getInt("code") != 200){
                return (List<ParticipantsRow>) Collections.EMPTY_LIST;
            }

            JSONArray data = json.getJSONArray("data");
            List<ParticipantsRow> rows = new ArrayList<ParticipantsRow>();
            for(int i = 0; i < data.length(); i++){
                JSONObject jsonObj = data.getJSONObject(i);
                int id = Integer.parseInt(jsonObj.getString("id")),
                        eventId = Integer.parseInt(jsonObj.getString("event_id")),
                        year = Integer.parseInt(jsonObj.getString("year"));
                String eventTitle = jsonObj.getString("event_title"),
                        participants = jsonObj.getString("participants");
                ParticipantsRow row = new ParticipantsRow(id, eventId, eventTitle, participants, year);
                rows.add(row);
            }
            return rows;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return (List<ParticipantsRow>) Collections.EMPTY_LIST;
    }


    public static List<ScoreRecord> getCachedScores(Context context, boolean refresh){
        if(!refresh && scores != null){
            return scores;
        }
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
                return (List<ScoreRecord>) Collections.EMPTY_LIST;
            }

            JSONArray jsonArray = json.getJSONArray("data");
            //Log.i("fatima_json_array", jsonArray.toString());

            ArrayList<ScoreRecord> scores = new ArrayList<ScoreRecord>();
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Log.i("fatima_json_obj", jsonObject.toString());
                ScoreRecord scoreRecord =
                        new ScoreRecord(jsonObject.getString("event_title"), Integer.parseInt(jsonObject.getString("matthew")),
                                Integer.parseInt(jsonObject.getString("mark")), Integer.parseInt(jsonObject.getString("luke")),
                                Integer.parseInt(jsonObject.getString("john")));
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

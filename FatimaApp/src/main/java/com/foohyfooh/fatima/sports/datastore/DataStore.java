package com.foohyfooh.fatima.sports.datastore;

import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.foohyfooh.fatima.sports.data.Member;
import com.foohyfooh.fatima.sports.data.ParticipantsRow;
import com.foohyfooh.fatima.sports.data.ScoreRecord;
import com.foohyfooh.fatima.sports.util.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStore {

    private DataStore(){}

    public static final String BASE_URL = "http://www.foohyfooh.site11.com/fatima/dev/";
    //public static final String BASE_URL = "http://10.0.2.2/fatima/dev/";
    private static Map<String, List<ParticipantsRow>> participants = new ArrayMap<String, List<ParticipantsRow>>();
    private static List<ScoreRecord> scores;
    private static Map<String, List<Member>> members = new ArrayMap<String, List<Member>>();

    public static List<ParticipantsRow> getCachedParticipants(String house, boolean refresh){
        if(refresh || participants.get(house) == null || participants.get(house).isEmpty()){
            participants.put(house, getParticipants(house));
        }
        return participants.get(house);
    }

    private static List<ParticipantsRow> getParticipants(String house){
        String url = BASE_URL + "participants_json.php?house=" + house;
        Log.i("fatima_participants_url", url);
        String body = NetworkUtils.getContent(url);
        if(body == null){
            return new ArrayList<ParticipantsRow>();
        }

        try{
            JSONObject json = new JSONObject(body);
            Log.i("fatima_participants_json", json.toString());
            if(json.getInt("code") != 200){
                Log.i("fatima_participants_json_code", String.valueOf(json.getInt("code")));
                return new ArrayList<ParticipantsRow>();
            }

            JSONArray jsonArray = json.getJSONArray("data");
            Log.i("fatima_participants_json_array", jsonArray.toString());
            List<ParticipantsRow> rows = new ArrayList<ParticipantsRow>();
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObj = jsonArray.getJSONObject(i);
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
        return new ArrayList<ParticipantsRow>();
    }


    public static List<ScoreRecord> getCachedScores(boolean refresh){
        if(refresh || scores == null || scores.isEmpty())
            scores = getScores();
        return scores;
    }

    private static List<ScoreRecord> getScores(){
        String url = BASE_URL + "scores_json.php";
        Log.i("fatima_scores_url", url);
        String body = NetworkUtils.getContent(url);
        if (body == null){
            return new ArrayList<ScoreRecord>();
        }
        try{
            JSONObject json = new JSONObject(body);
            Log.i("fatima_scores_json", json.toString());
            if(json.getInt("code") != 200){
                Log.i("fatima_scores_json_code", String.valueOf(json.getInt("code")));
                return new ArrayList<ScoreRecord>();
            }

            JSONArray jsonArray = json.getJSONArray("data");
            Log.i("fatima_scores_json_array", jsonArray.toString());

            ArrayList<ScoreRecord> scores = new ArrayList<ScoreRecord>();
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                //Log.i("fatima_scores_json_obj", jsonObject.toString());
                ScoreRecord scoreRecord = new ScoreRecord();
                scoreRecord.setTitle(jsonObject.getString("event_title"));
                scoreRecord.setMatthew(Integer.parseInt(jsonObject.getString("matthew")));
                scoreRecord.setMark(Integer.parseInt(jsonObject.getString("mark")));
                scoreRecord.setLuke(Integer.parseInt(jsonObject.getString("luke")));
                scoreRecord.setJohn(Integer.parseInt(jsonObject.getString("john")));
                scoreRecord.setYear(jsonObject.getString("year"));
                scores.add(scoreRecord);
                //Log.i("fatima_scores_json_score", scoreRecord.toString());
            }
            //Log.i("fatima_scores_json_scores", scores.toString());

            return scores;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return new ArrayList<ScoreRecord>();
    }

    public static List<Member> getCachedMembers(String house, boolean refresh){
        if(refresh || members.get(house) == null || members.get(house).isEmpty()){
            members.put(house, getMembers(house));
        }
        return members.get(house);
    }

    private static List<Member> getMembers(String house){
        String url = BASE_URL + "members_json.php?house=" + house;
        Log.i("fatima_members_url", url);
        String body = NetworkUtils.getContent(url);
        if(body == null){
            return new ArrayList<Member>();
        }

        try{
            JSONObject json = new JSONObject(body);
            Log.i("fatima_members_json", json.toString());
            if(json.getInt("code") != 200){
                Log.d("fatima_members_json_code", String.valueOf(json.getInt("code")));
                return new ArrayList<Member>();
            }

            JSONArray jsonArray = json.getJSONArray("data");
            Log.i("fatima_members_json_array", jsonArray.toString());

            ArrayList<Member> members = new ArrayList<Member>();
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Member member = new Member();
                member.setName(jsonObject.getString("name"));
                member.setStatus(jsonObject.getString("status"));
                member.setHouse(jsonObject.getString("house"));
                members.add(member);
            }


            return members;
        }catch (JSONException e){
            Log.e("fatima_members_error", "", e);
        }

        return new ArrayList<Member>();
    }

}

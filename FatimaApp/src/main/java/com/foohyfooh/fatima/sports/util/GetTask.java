package com.foohyfooh.fatima.sports.util;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.List;

public abstract class GetTask<T, K extends ArrayAdapter<T>> extends AsyncTask<Boolean, Void, List<T>> {

    private Context context;
    private K adapter;
    public GetTask(Context context, K adapter) {
        this.context = context;
        this.adapter = adapter;
    }

    @Override
    protected void onPreExecute() {
        if(!NetworkUtils.hasConnection(context)){
            cancel(true);
        }
    }

    @Override
    protected abstract List doInBackground(Boolean... params);

    //Should make this final but wont as ScoreboardTable is using a makeshift version of this
    @Override
    protected void onPostExecute(List<T> items) {
        //super.onPostExecute(items);
        if(items.size() == 0)return;
        adapter.clear();
        for(T item: items){
            adapter.add(item);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCancelled() {
        Toast.makeText(context, "Turn on internet", Toast.LENGTH_LONG).show();
        super.onCancelled();
    }
}

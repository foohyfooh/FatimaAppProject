package com.foohyfooh.fatima.sports.util;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.List;

public abstract class GetTask<T, K extends ArrayAdapter<T>> extends AsyncTask<Boolean, Void, List<T>> {

    private Context context;
    private K adapter;
    private boolean hasInternet;

    private PostExecuteTask postExecuteTask;

    public GetTask(Context context, K adapter) {
        this(context, adapter, null);
    }

    public GetTask(Context context, K adapter, PostExecuteTask postExecuteTask) {
        this.context = context;
        this.adapter = adapter;
        hasInternet = false;
        this.postExecuteTask = postExecuteTask;
    }

    @Override
    protected void onPreExecute() {
        if(!NetworkUtils.hasConnection(context)){
            cancel(true);
        }
        hasInternet = true;
    }

    @Override
    protected abstract List<T> doInBackground(Boolean... params);

    //Should make this final but wont as ScoreboardTable is using a makeshift version of this
    @Override
    protected void onPostExecute(List<T> items) {
        //super.onPostExecute(items);
        if(items == null ||items.size() == 0)return;
        if(adapter == null) return;
        adapter.clear();
        for(T item: items){
            adapter.add(item);
        }
        adapter.notifyDataSetChanged();
        if(postExecuteTask != null) postExecuteTask.execute();
    }

    @Override
    protected void onCancelled() {
        if(!hasInternet){
            Toast.makeText(context, "Turn on internet", Toast.LENGTH_LONG).show();
        }
        super.onCancelled();
    }


    public interface PostExecuteTask {
        public void execute();
    }
}

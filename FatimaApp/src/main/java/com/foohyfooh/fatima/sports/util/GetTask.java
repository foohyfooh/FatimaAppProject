package com.foohyfooh.fatima.sports.util;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public abstract class GetTask<T> extends AsyncTask<Boolean, Void, T> {

    private Context context;
    private boolean hasInternet;
    private PostExecuteTask postExecuteTask;

    public GetTask(Context context) {
        this(context, null);
    }

    public GetTask(Context context, PostExecuteTask postExecuteTask) {
        this.context = context;
        hasInternet = NetworkUtils.hasConnection();
        this.postExecuteTask = postExecuteTask;
    }

    @Override
    protected final void onPreExecute() {
        if(!hasInternet){
            cancel(true);
        }
    }

    @Override
    protected abstract T doInBackground(Boolean... params);

    @Override
    protected abstract void onPostExecute(T items);

    @Override
    protected final void onCancelled() {
        if(!hasInternet){
            Toast.makeText(context, "Turn on internet", Toast.LENGTH_LONG).show();
        }
        super.onCancelled();
    }

    protected PostExecuteTask getPostExecuteTask(){
        return postExecuteTask;
    }


    public interface PostExecuteTask {
        public void execute();
    }
}

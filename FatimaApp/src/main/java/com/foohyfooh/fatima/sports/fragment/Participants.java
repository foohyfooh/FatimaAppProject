package com.foohyfooh.fatima.sports.fragment;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foohyfooh.fatima.sports.HouseSelector;
import com.foohyfooh.fatima.sports.R;
import com.foohyfooh.fatima.sports.adapter.ParticipantsScrollAdapter;
import com.foohyfooh.fatima.sports.data.ParticipantsRow;
import com.foohyfooh.fatima.sports.datastore.DataStore;
import com.foohyfooh.fatima.sports.util.DisplayUtils;
import com.foohyfooh.fatima.sports.util.GetTask;
import com.foohyfooh.fatima.sports.util.Refreshable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Participants extends Fragment implements Refreshable {

    private String house;
    private Context context;
    private ParticipantsScrollAdapter adapter;
    private AsyncTask getParticipantsTask;
    private ViewPager pager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.single_participants_view, container, false);
        context = getActivity();
        house = HouseSelector.getHouse();
        adapter = new ParticipantsScrollAdapter(getFragmentManager(), new ArrayMap<String, List<ParticipantsRow>>());
        pager = (ViewPager) rootView.findViewById(R.id.pager);
        pager.setAdapter(adapter);
        getParticipantsTask = new GetParticipants(context).execute(false);

        return rootView;
    }

    @Override
    public void onDetach() {
        if(getParticipantsTask.getStatus() == AsyncTask.Status.RUNNING){
            getParticipantsTask.cancel(true);
        }
        super.onDetach();
    }

    @Override
    public void refresh(GetTask.PostExecuteTask task) {
        new GetParticipants(context, task).execute(true);
    }

    private class GetParticipants extends GetTask< Map<String, List<ParticipantsRow>>>{


        public GetParticipants(Context context) {
            super(context);
        }

        public GetParticipants(Context context, GetTask.PostExecuteTask task) {
            super(context,task);
        }

        @Override
        protected Map<String, List<ParticipantsRow>> doInBackground(Boolean... params) {
            return DataStore.getCachedParticipants(house, params[0]);
        }

        @Override
        protected void onPostExecute(Map<String, List<ParticipantsRow>> items) {
            //super.onPostExecute(items);
            if(items == null ||items.size() == 0)return;

            adapter = new ParticipantsScrollAdapter(getFragmentManager(), items);
            pager.setAdapter(adapter);
            pager.setCurrentItem(adapter.getCount() -1);

            if(getPostExecuteTask() != null) getPostExecuteTask().execute();
        }
    }

}

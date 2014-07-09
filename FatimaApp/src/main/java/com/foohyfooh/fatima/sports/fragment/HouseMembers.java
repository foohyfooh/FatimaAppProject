package com.foohyfooh.fatima.sports.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.foohyfooh.fatima.sports.HouseSelector;
import com.foohyfooh.fatima.sports.R;
import com.foohyfooh.fatima.sports.adapter.MemberAdapter;
import com.foohyfooh.fatima.sports.data.Member;
import com.foohyfooh.fatima.sports.datastore.DataStore;
import com.foohyfooh.fatima.sports.util.DisplayUtils;
import com.foohyfooh.fatima.sports.util.GetTask;
import com.foohyfooh.fatima.sports.util.Refreshable;

import java.util.ArrayList;
import java.util.List;

public class HouseMembers extends Fragment implements Refreshable {

    private Context context;
    private ListView membersList;
    private MemberAdapter adapter;
    private String house;
    private AsyncTask getMembersTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.house_members, container, false);
        context = getActivity();
        house = HouseSelector.getHouse();
        DisplayUtils.setBackgroundColour(root, house);

        TextView header = (TextView) root.findViewById(R.id.house_members_header);
        header.setText( "St. " + Character.toUpperCase(house.charAt(0)) + house.substring(1) + " House Members");

        List<Member> members;
        if(savedInstanceState != null && savedInstanceState.containsKey("members")){
            members = savedInstanceState.getParcelableArrayList("members");
        }else{
            members = new ArrayList<Member>();
        }

        membersList = (ListView) root.findViewById(R.id.house_members_list);
        adapter = new MemberAdapter(context, R.layout.house_members_row, members);
        membersList.setAdapter(adapter);

        getMembersTask = new GetMembers(context).execute(false);
        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("members", adapter.getMembers());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDetach() {
        if(getMembersTask.getStatus() == AsyncTask.Status.RUNNING){
            getMembersTask.cancel(true);
        }
        super.onDetach();
    }

    @Override
    public void refresh(GetTask.PostExecuteTask task) {
        getMembersTask = new GetMembers(context, adapter, task).execute(true);
    }

    private class GetMembers extends GetTask<List<Member>>{

        public GetMembers(Context context) {
            super(context);
        }

        public GetMembers(Context context, MemberAdapter adapter, PostExecuteTask task) {
            super(context, task);
        }

        @Override
        protected List<Member> doInBackground(Boolean... params) {
            return DataStore.getCachedMembers(house, params[0]);
        }

        @Override
        protected void onPostExecute(List<Member> items) {
            //super.onPostExecute(items);
            if(items == null ||items.size() == 0)return;
            if(adapter == null) return;
            adapter.clear();
            for(Member item: items){
                adapter.add(item);
            }
            adapter.notifyDataSetChanged();
            if(getPostExecuteTask() != null) getPostExecuteTask().execute();
        }
    }

}

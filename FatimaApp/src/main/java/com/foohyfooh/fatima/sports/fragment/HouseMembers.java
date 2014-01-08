package com.foohyfooh.fatima.sports.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.foohyfooh.fatima.sports.R;
import com.foohyfooh.fatima.sports.adapter.MemberAdapter;
import com.foohyfooh.fatima.sports.data.Member;
import com.foohyfooh.fatima.sports.datastore.DataStore;
import com.foohyfooh.fatima.sports.util.DisplayUtils;
import com.foohyfooh.fatima.sports.util.GetTask;

import java.util.ArrayList;
import java.util.List;

public class HouseMembers extends Fragment {
    private static final String ARG_HOUSE = "house";
    private Context context;
    private ListView membersList;
    private MemberAdapter adapter;
    private String house;

    public static HouseMembers newInstance(String house) {
        HouseMembers fragment = new HouseMembers();
        Bundle args = new Bundle();
        args.putString(ARG_HOUSE, house);
        fragment.setArguments(args);
        return fragment;
    }
    public HouseMembers() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.house_members, container, false);
        context = getActivity();
        house = getArguments().getString(ARG_HOUSE);
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

        new GetMembers(context, adapter).execute(false);
        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("members", adapter.getMembers());
        super.onSaveInstanceState(outState);
    }

    private class GetMembers extends GetTask<Member, MemberAdapter> {

        public GetMembers(Context context, MemberAdapter adapter) {
            super(context, adapter);
        }

        @Override
        protected List doInBackground(Boolean... params) {
            return DataStore.getCachedMembers(house, params[0]);
        }
    }

}

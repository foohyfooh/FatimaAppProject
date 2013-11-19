package com.foohyfooh.fatimaapp.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.foohyfooh.fatimaapp.R;

public abstract class House extends Fragment {

    public static final String ARG_HOUSE = "house";
    private String house;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.house, container, false);
        house = getArguments().getString(ARG_HOUSE);

        int colour = 0;
        if(house.equals("mark")){
            colour = Color.parseColor("#006400");
        }else if(house.equals("matthew")){
            colour = Color.parseColor("#fffd12");
        }else if(house.equals("luke")){
            colour = Color.parseColor("#ff0006");
        }else if(house.equals("john")){
            colour = Color.parseColor("#0004ff");
        }
        rootView.setBackgroundColor(colour);

        ListView list = (ListView) rootView.findViewById(R.id.listView);
        list.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, new String[]{"Something", "Another","Data", "Something", "Another","Data", "Something", "Another","Data"}));
        return rootView;
    }
}

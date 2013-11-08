package com.foohyfooh.fatimaapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MarkHouse extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mark_house, container, false);
        ListView list = (ListView) rootView.findViewById(R.id.listView);
        list.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, new String[]{"Someht", "Andor", "fssds", "Someht", "Andor", "fssds", "Someht", "Andor", "fssds", "Someht", "Andor", "fssds"}));
        return rootView;
    }
}
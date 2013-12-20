package com.foohyfooh.fatima.sports.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.foohyfooh.fatima.sports.R;
import com.foohyfooh.fatima.sports.factory.ParticipantsFactory;
import com.foohyfooh.fatima.sports.util.DisplayUtils;

public abstract class House extends Fragment implements AdapterView.OnItemClickListener {

    public static final String ARG_HOUSE = "house";
    private static final String[] options = {"Participants", "Another","Data", "Something", "Another","Data", "Something", "Another","Data"};
    private String house;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.house, container, false);
        house = getArguments().getString(ARG_HOUSE);
        DisplayUtils.setHeaderImage(rootView, getResources(), R.id.header_image, house);
        DisplayUtils.setBackgroundColour(rootView, house);

        ListView list = (ListView) rootView.findViewById(R.id.listView);
        list.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, options ));
        list.setOnItemClickListener(this);
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Fragment fragment;
        switch (position){
            case 0:
                fragment = ParticipantsFactory.getInstance().newParticipants(house);
                break;
            default:
                fragment = ParticipantsFactory.getInstance().newParticipants(house);
        }
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.house_layout, fragment).addToBackStack(null).commit();
    }
}

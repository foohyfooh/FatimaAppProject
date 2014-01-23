package com.foohyfooh.fatima.sports.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foohyfooh.fatima.sports.HouseSelector;
import com.foohyfooh.fatima.sports.R;
import com.foohyfooh.fatima.sports.util.DisplayUtils;

public class House extends Fragment {

    private String house;

    public House(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.house, container, false);
        house = HouseSelector.getHouse();
        DisplayUtils.setHeaderImage(rootView, R.id.header_image, house);
        DisplayUtils.setBackgroundColour(rootView, house);
        ((TextView) rootView.findViewById(R.id.house_info)).setText(getHouseSummary());
        getActivity().setTitle("St. " + Character.toUpperCase(house.charAt(0)) + house.substring(1) + " House");
        return rootView;
    }



    private String getHouseSummary(){
        String info = "Fatima College";
        if(house.equals("matthew")){
            info = getString(R.string.matthew_summary);
        }else if(house.equals("mark")){
            info = getString(R.string.mark_summary);
        }else if(house.equals("luke")){
            info = getString(R.string.luke_summary);
        }else if(house.equals("john")){
            info = getString(R.string.john_summary);
        }
        return info;
    }

}

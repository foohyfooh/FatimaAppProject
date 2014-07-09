package com.foohyfooh.fatima.sports.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.foohyfooh.fatima.sports.HouseSelector;
import com.foohyfooh.fatima.sports.R;
import com.foohyfooh.fatima.sports.data.ParticipantsRow;
import com.foohyfooh.fatima.sports.util.DisplayUtils;

/**
 * Created by Jonathan on 5/7/2014.
 */
public class SingleParticipantsFragment extends Fragment {


    //public static final String EXTRA_HOUSE = "house";
    public static final String EXTRA_POS = "pos";
    public static final String EXTRA_YEAR = "year";
    public static final String KEY = "single_participants";

    private ParticipantsRow participants;

    public SingleParticipantsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.single_participants, null, false);

        if(savedInstanceState != null && savedInstanceState.containsKey(KEY)){
            participants = savedInstanceState.getParcelable(KEY);
        } else {
            participants = getArguments().getParcelable(KEY);
        }

        String house = HouseSelector.getHouse();
        DisplayUtils.setBackgroundColour(rootView, house);
        DisplayUtils.setHeaderImage(rootView, R.id.single_participants_image_header, house);

        ((TextView) rootView.findViewById(R.id.single_participants_event)).setText(participants.getEvent());
        ((TextView) rootView.findViewById(R.id.single_participants_year)).setText(participants.getYear());
        ((TextView) rootView.findViewById(R.id.single_participants_participants)).setText(participants.getParticipants());

        return rootView;
    }

    public void onSaveInstanceState(Bundle bundle){
        bundle.putParcelable(KEY, participants);
        super.onSaveInstanceState(bundle);
    }
}

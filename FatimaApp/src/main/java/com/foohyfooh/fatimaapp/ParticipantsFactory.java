package com.foohyfooh.fatimaapp;

import android.os.Bundle;


public class ParticipantsFactory {

    public static Participants newInstance(String house){
        Participants p = new Participants();
        Bundle houseInfo = new Bundle();
        houseInfo.putString(Participants.ARG_HOUSE, house);
        p.setArguments(houseInfo);
        return p;
    }


}

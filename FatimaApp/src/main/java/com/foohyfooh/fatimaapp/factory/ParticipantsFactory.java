package com.foohyfooh.fatimaapp.factory;

import android.os.Bundle;

import com.foohyfooh.fatimaapp.fragment.Participants;

public class ParticipantsFactory {
    private static ParticipantsFactory instance = new ParticipantsFactory();

    public static ParticipantsFactory getInstance() {
        return instance;
    }

    private ParticipantsFactory() {
    }

    public Participants newParticipants(String house){
        Participants p = new ParticipantsImpl();
        Bundle houseInfo = new Bundle();
        houseInfo.putString(Participants.ARG_HOUSE, house);
        p.setArguments(houseInfo);
        return p;
    }

    private class ParticipantsImpl extends Participants {
    }

}

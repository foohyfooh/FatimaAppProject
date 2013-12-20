package com.foohyfooh.fatima.sports.factory;


import android.os.Bundle;

import com.foohyfooh.fatima.sports.fragment.House;

public class HouseFactory {
    private static HouseFactory instance = new HouseFactory();

    public static HouseFactory getInstance() {
        return instance;
    }

    private HouseFactory() {
    }

    public House newHouse(String house){
        House h = new HouseImpl();
        Bundle houseInfo = new Bundle();
        houseInfo.putString(House.ARG_HOUSE, house);
        h.setArguments(houseInfo);
        return h;
    }

    private class HouseImpl extends House {
    }
}

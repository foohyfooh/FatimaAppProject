package com.foohyfooh.fatima.sports.util;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;


public class FatimaSports extends Application {

    private static Context context;
    private static Resources resources;

    public void onCreate(){
        super.onCreate();
        context = this;
        resources = getResources();
    }

    public static Context getContext(){
        return context;
    }

    public static Resources getAppResources(){
        return resources;
    }

}

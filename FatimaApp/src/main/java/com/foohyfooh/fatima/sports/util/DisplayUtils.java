package com.foohyfooh.fatima.sports.util;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import com.foohyfooh.fatima.sports.R;

public class DisplayUtils {

    private DisplayUtils(){}

    public static void setBackgroundColour(View view, String house){
        int colour = Color.WHITE;
        if(house.equals("matthew")){
            colour = Color.parseColor("#fffd12");
        }else if(house.equals("mark")){
            colour = Color.parseColor("#006400");
        }else if(house.equals("luke")){
            colour = Color.parseColor("#ff0006");
        }else if(house.equals("john")){
            colour = Color.parseColor("#0004ff");
        }
        view.setBackgroundColor(colour);
    }

    public static void setHeaderImage(View view, int resId, String house){
        ImageView imageView = (ImageView) view.findViewById(resId);
        Bitmap bitmap = BitmapFactory.decodeResource(FatimaSports.getAppResources(), R.drawable.fatima_college_logo);
        if(house.equals("matthew")){
            //Set Matthew Icon
        }else if(house.equals("mark")){
            //Set Mark Icon
        }else if(house.equals("luke")){
            //Set Luke Icon
        }else if(house.equals("john")){
            //Set John Icon
        }
        imageView.setImageBitmap(bitmap);
    }

}

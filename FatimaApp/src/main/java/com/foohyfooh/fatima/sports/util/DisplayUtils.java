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
            colour = Color.parseColor("#fffd12");//#f1f201
        }else if(house.equals("mark")){
            colour = Color.parseColor("#128b18");//#006400
        }else if(house.equals("luke")){
            colour = Color.parseColor("#ff0006");
        }else if(house.equals("john")){
            colour = Color.parseColor("#0004ff");
        }
        view.setBackgroundColor(colour);
    }

    public static void setHeaderImage(View view, int resId, String house){
        ImageView imageView = (ImageView) view.findViewById(resId);
        int resource;
        if(house.equals("matthew")){
            resource = R.drawable.fatima_matthew_logo;
        }else if(house.equals("mark")){
            resource = R.drawable.fatima_mark_logo;
        }else if(house.equals("luke")){
            resource = R.drawable.fatima_luke_logo;
        }else if(house.equals("john")){
            resource = R.drawable.fatima_john_logo;
        }else{
            resource = R.drawable.fatima_college_logo;
        }
        Bitmap bitmap = BitmapFactory.decodeResource(FatimaSports.getAppResources(), resource);
        imageView.setImageBitmap(bitmap);
    }

}

package com.foohyfooh.fatima.sports.data;


import com.foohyfooh.fatima.sports.R;

public class NavigationItem {

    public String title;
    public final boolean isHeader;

    private NavigationItem(String title, boolean isHeader){
        this.title = title;
        this.isHeader = isHeader;
    }

    public static NavigationItem newItem(String title){
        return new NavigationItem(title, false);
    }

    public static NavigationItem newTitle(String title){
        return new NavigationItem(title, true);
    }

    public int getLayoutResource(){
        return isHeader ? R.layout.navigation_title : R.layout.navigation_item;
    }

}

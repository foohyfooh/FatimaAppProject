package com.foohyfooh.fatima.sports.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.foohyfooh.fatima.sports.R;
import com.foohyfooh.fatima.sports.data.NavigationItem;

public class NavigationAdapter extends ArrayAdapter<NavigationItem> {


    public NavigationAdapter(Context context, int resource, NavigationItem[] items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        NavigationItem item  = getItem(position);
        int resource = item.getLayoutResource();
        View view = convertView != null ? convertView : inflater.inflate(resource, parent, false);
        ((TextView) view.findViewById(R.id.navigation_item_title)).setText(item.title);
        return view;
    }
}

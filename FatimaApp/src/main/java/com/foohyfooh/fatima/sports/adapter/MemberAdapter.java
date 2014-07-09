package com.foohyfooh.fatima.sports.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.foohyfooh.fatima.sports.R;
import com.foohyfooh.fatima.sports.data.Member;

import java.util.ArrayList;
import java.util.List;

public class MemberAdapter extends ArrayAdapter<Member> {

    private List<Member> members;

    public MemberAdapter(Context context, int resource, List<Member> members) {
        super(context, resource, members);
        this.members = members;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = convertView != null ? convertView : inflater.inflate(R.layout.house_members_row, parent, false);

        Member member = members.get(position);

        ((TextView) view.findViewById(R.id.house_members_row_name)).setText(member.getName());
        ((TextView) view.findViewById(R.id.house_members_row_status)).setText(member.getStatus());

        return view;
    }

    public ArrayList<Member> getMembers(){
        return (ArrayList<Member>)members;
    }
}

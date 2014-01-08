package com.foohyfooh.fatima.sports.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Member implements Parcelable {

    private String name, status, house;

    public Member(){}

    public Member(Parcel in) {
        name = in.readString();
        status = in.readString();
        house = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(status);
        dest.writeString(house);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Member createFromParcel(Parcel in) {
            return new Member(in);
        }
        public Member[] newArray(int size) {
            return new Member[size];
        }
    };
}

package com.foohyfooh.fatimaapp.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ScoreRecord implements Parcelable {
    private int matthew, mark, luke, john;
    private String title;

    public ScoreRecord(String title, int matthew, int mark, int luke, int john){
        this.title = title;
        this.matthew = matthew;
        this.mark = mark;
        this.luke = luke;
        this.john = john;
    }

    public int getMatthew() {
        return matthew;
    }

    public void setMatthew(int matthew) {
        this.matthew = matthew;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getLuke() {
        return luke;
    }

    public void setLuke(int luke) {
        this.luke = luke;
    }

    public int getJohn() {
        return john;
    }

    public void setJohn(int john) {
        this.john = john;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ScoreRecord(Parcel in){
        title = in.readString();
        matthew = in.readInt();
        mark = in.readInt();
        luke = in.readInt();
        john = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(title);
        out.writeInt(matthew);
        out.writeInt(mark);
        out.writeInt(luke);
        out.writeInt(john);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ScoreRecord createFromParcel(Parcel in) {
            return new ScoreRecord(in);
        }
        public ScoreRecord[] newArray(int size) {
            return new ScoreRecord[size];
        }
    };
}


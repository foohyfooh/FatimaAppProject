package com.foohyfooh.fatima.sports.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ParticipantsRow implements Parcelable {

    private int id, eventId;
    private String event, participants, house, year;

    public ParticipantsRow() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getParticipants() {
        return participants;
    }

    public void setParticipants(String participants) {
        this.participants = participants;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public ParticipantsRow(Parcel in){
        id = in.readInt();
        eventId = in.readInt();
        event = in.readString();
        participants = in.readString();
        house =  in.readString();
        year = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeInt(eventId);
        out.writeString(event);
        out.writeString(participants);
        out.writeString(house);
        out.writeString(year);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ParticipantsRow createFromParcel(Parcel in) {
            return new ParticipantsRow(in);
        }
        public ParticipantsRow[] newArray(int size) {
            return new ParticipantsRow[size];
        }
    };
}

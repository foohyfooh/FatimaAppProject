package com.foohyfooh.fatimaapp.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ParticipantsRow implements Parcelable {

    private int id, eventId, year;
    private String event, participants;

    public ParticipantsRow(int id, int eventId, String event, String participants, int year) {
        this.id = id;
        this.eventId = eventId;
        this.event = event;
        this.participants = participants;
        this.year = year;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
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

    public ParticipantsRow(Parcel in){
        id = in.readInt();
        eventId = in.readInt();
        event = in.readString();
        participants = in.readString();
        year = in.readInt();
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
        out.writeInt(year);
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

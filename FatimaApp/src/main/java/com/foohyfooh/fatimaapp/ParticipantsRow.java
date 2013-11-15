package com.foohyfooh.fatimaapp;

public class ParticipantsRow {

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

    @Override
    public String toString() {
        return "Item " + id;
    }
}

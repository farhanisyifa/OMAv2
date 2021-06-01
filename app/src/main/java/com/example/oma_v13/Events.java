package com.example.oma_v13;

public class Events {
    private String eventName;
    private String eventDescription;
    private String startDate;
    private String endDate;

    public Events(){}

    public Events(String eventName, String eventDescription, String startDate, String endDate) {
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}

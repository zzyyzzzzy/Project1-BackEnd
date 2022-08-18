package dev.zheng.entities;

import java.util.Objects;

public class Meeting {
    private int id;
    private String description;
    private String location;
    private int meetingDate;

    public Meeting(int id, String description, String location, int meetingDate) {
        this.id = id;
        this.description = description;
        this.location = location;
        this.meetingDate = meetingDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(int meetingDate) {
        this.meetingDate = meetingDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meeting meeting = (Meeting) o;
        return id == meeting.id && meetingDate == meeting.meetingDate && description.equals(meeting.description) && location.equals(meeting.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, location, meetingDate);
    }
}


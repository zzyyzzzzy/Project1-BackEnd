package dev.zheng.entities;

import java.util.Objects;

public class Complaint {
    private int id;
    private String description;
    private Priority priority;
    private int meetingId;

    public Complaint(int id, String description, Priority priority, int meetingId) {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.meetingId = meetingId;
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

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public int getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(int meetingId) {
        this.meetingId = meetingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Complaint complaint = (Complaint) o;
        return id == complaint.id && meetingId == complaint.meetingId && description.equals(complaint.description) && priority == complaint.priority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, priority, meetingId);
    }
}

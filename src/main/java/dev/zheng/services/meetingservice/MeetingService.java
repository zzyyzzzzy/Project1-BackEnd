package dev.zheng.services.meetingservice;

import dev.zheng.entities.Meeting;

import java.util.List;

public interface MeetingService {
    Meeting addMeeting(Meeting meeting);
    List<Meeting> retrieveAllMeetings();
}

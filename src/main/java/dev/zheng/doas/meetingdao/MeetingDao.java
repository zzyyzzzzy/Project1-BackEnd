package dev.zheng.doas.meetingdao;

import dev.zheng.entities.Meeting;

import java.util.List;

public interface MeetingDao {
    Meeting createMeeting(Meeting meeting);
    List<Meeting> getAllMeetings();
}

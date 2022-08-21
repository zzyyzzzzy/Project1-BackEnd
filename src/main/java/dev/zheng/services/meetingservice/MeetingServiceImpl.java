package dev.zheng.services.meetingservice;

import dev.zheng.doas.meetingdao.MeetingDao;
import dev.zheng.entities.Complaint;
import dev.zheng.entities.Meeting;
import dev.zheng.services.complaintservice.exceptions.NullComplaintDescriptionsException;

import java.util.List;

public class MeetingServiceImpl implements MeetingService{
    MeetingDao meetingDao;
    public MeetingServiceImpl(MeetingDao meetingDao){this.meetingDao = meetingDao;}
    @Override
    public Meeting addMeeting(Meeting meeting) {
        return meetingDao.createMeeting(meeting);
    }

    @Override
    public List<Meeting> retrieveAllMeetings() {
        return meetingDao.getAllMeetings();
    }
}

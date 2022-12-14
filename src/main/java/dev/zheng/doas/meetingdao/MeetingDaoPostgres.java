package dev.zheng.doas.meetingdao;

import dev.zheng.entities.Complaint;
import dev.zheng.entities.Meeting;
import dev.zheng.entities.Priority;
import dev.zheng.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MeetingDaoPostgres implements MeetingDao {

    @Override
    public Meeting createMeeting(Meeting meeting) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "insert into meeting values(default, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, meeting.getDescription());
            ps.setString(2, meeting.getLocation());
            ps.setInt(3, meeting.getMeetingDate());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();

            meeting.setId(rs.getInt("id"));
            return meeting;
        }catch (SQLException err){
            System.out.println("Unable to create a meeting due to SQL exception occurred");
            return null;
        }
    }

    @Override
    public List<Meeting> getAllMeetings() {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "select * from meeting";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List <Meeting> meetingList = new ArrayList<>();
            while (rs.next()){
                Meeting meeting = new Meeting(rs.getInt("id"),
                        rs.getString("description"), rs.getString("location"),
                        rs.getInt("meeting_date"));
                meetingList.add(meeting);
            }
            return meetingList;
        }catch (SQLException err){
            System.out.println("Unable to get all meetings due to SQL exception occurred");
            return null;
        }
    }
}

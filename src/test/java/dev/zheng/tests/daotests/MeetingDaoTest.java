package dev.zheng.tests.daotests;

import dev.zheng.doas.meetingdao.MeetingDao;
import dev.zheng.doas.meetingdao.MeetingDaoPostgres;
import dev.zheng.entities.Complaint;
import dev.zheng.entities.Meeting;
import dev.zheng.entities.Priority;
import dev.zheng.utils.ConnectionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MeetingDaoTest {
      static MeetingDao meetingDao = new MeetingDaoPostgres();
//    @BeforeAll
//    static void createTable(){
//        try(Connection conn = ConnectionUtil.createConnection()){
//            String sql = "create table meeting(\n" +
//                    "\tid serial primary key,\n" +
//                    "\tdescription varchar(200) not null,\n" +
//                    "\tlocation varchar(200) not null,\n" +
//                    "\tmeeting_date int not null -- unix epoch time\n" +
//                    ");";
//            Statement statement = conn.createStatement();
//            statement.execute(sql);
//
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//    }
    @Test
    @Order(1)
    void create_meeting_test(){
        Meeting meeting = new Meeting(0, "Meeting number 1","City Hall", 1660856609);
        Meeting savedMeeting = meetingDao.createMeeting(meeting);
        Assertions.assertNotEquals(0, savedMeeting.getId());
    }

    @Test
    @Order(1)
    void get_all_meetings_test(){
        Meeting meeting = new Meeting(0, "Meeting number 2","City Hall", 1660770209);
        Meeting savedMeeting = meetingDao.createMeeting(meeting);
        Assertions.assertEquals(3, meetingDao.getAllMeetings().size());
    }

}

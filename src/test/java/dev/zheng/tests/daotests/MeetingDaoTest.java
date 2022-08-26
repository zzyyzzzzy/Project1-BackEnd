package dev.zheng.tests.daotests;

import dev.zheng.doas.meetingdao.MeetingDao;
import dev.zheng.doas.meetingdao.MeetingDaoPostgres;
import dev.zheng.entities.Meeting;
import dev.zheng.utils.ConnectionUtil;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MeetingDaoTest {
      static MeetingDao meetingDao = new MeetingDaoPostgres();
    @BeforeAll
    static void createTable(){
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "create table meeting(\n" +
                    "\tid serial primary key,\n" +
                    "\tdescription varchar(200) not null,\n" +
                    "\tlocation varchar(200) not null,\n" +
                    "\tmeeting_date int not null -- unix epoch time\n" +
                    ");";
            String insertDummy = "insert into meeting values(-1, 'Not assigned', 'Unknown', 0);";
            Statement statement = conn.createStatement();
            statement.execute(sql);
            statement.execute(insertDummy);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    @Test
    @Order(1)
    void create_meeting_test(){
        Meeting meeting = new Meeting(0, "Meeting number 1","City Hall", 1660856609);
        Meeting savedMeeting = meetingDao.createMeeting(meeting);
        Assertions.assertNotEquals(0, savedMeeting.getId());
    }

    @Test
    @Order(2)
    void get_all_meetings_test(){
        Meeting meeting = new Meeting(0, "Meeting number 2","City Hall", 1660770209);
        meetingDao.createMeeting(meeting);
        Assertions.assertEquals(2, meetingDao.getAllMeetings().size());
    }
    @AfterAll
    static void insertDummy(){
        try(Connection conn = ConnectionUtil.createConnection()){
            String insertDummy = "insert into meeting values(-1, 'Not assigned', 'Unknown', 0);";
            Statement statement = conn.createStatement();
            statement.execute(insertDummy);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}

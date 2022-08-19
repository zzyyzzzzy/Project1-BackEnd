package dev.zheng.tests.daotests;

import dev.zheng.doas.complaintdao.ComplaintDao;
import dev.zheng.doas.complaintdao.ComplaintDaoPostgres;
import dev.zheng.entities.Complaint;
import dev.zheng.entities.Priority;
import dev.zheng.entities.User;
import dev.zheng.entities.UserTitle;
import dev.zheng.utils.ConnectionUtil;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ComplaintDaoTest {
    static ComplaintDao complaintDao = new ComplaintDaoPostgres();
    @BeforeAll
    static void createTable(){
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "create table complaint(\n" +
                    "\tid serial primary key,\n" +
                    "\tsummary varchar(30) not null,\n" +
                    "\tdescription varchar(200) not null,\n" +
                    "\tpriority varchar(20) default 'UNASSIGNED',\n" +
                    "\tmeeting_id int references meeting(id) default -1\n" +
                    ");";
            Statement statement = conn.createStatement();
            statement.execute(sql);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    void create_complaint_test(){
        Complaint complaint = new Complaint(0, "emergency","Clear the road", Priority.HIGH, -1);
        Complaint savedComplaint = complaintDao.createComplaint(complaint);
        Assertions.assertNotEquals(0, savedComplaint.getId());
        Assertions.assertEquals(Priority.UNASSIGNED, savedComplaint.getPriority());
    }

    @Test
    @Order(2)
    void get_complaint_test(){
        Complaint savedComplaint = complaintDao.getOneComplaint(1);
        Assertions.assertEquals(Priority.UNASSIGNED, savedComplaint.getPriority());
        Assertions.assertEquals("Clear the road", savedComplaint.getDescription());

    }

    @Test
    @Order(3)
    void get_all_complaints_test(){
        Complaint complaint = new Complaint(0, "Help!","HAHAHAHAHAHA", Priority.HIGH, -1);
        complaintDao.createComplaint(complaint);
        List<Complaint> allComplaints = complaintDao.getAllComplaints();
        Assertions.assertEquals(2, allComplaints.size());
    }
    @Test
    @Order(4)
    void update_complaint_priority_test(){
        Map<String, String> successEg = complaintDao.updatePriority(1, Priority.HIGH);
        Map<String, String> failedEg = complaintDao.updatePriority(3, Priority.HIGH);

        Assertions.assertEquals(Priority.HIGH.toString(), successEg.get("priority"));
        Assertions.assertNull(failedEg);
    }
    @Test
    @Order(5)
    void update_complaint_meeting_test(){
        boolean updateSuccess = complaintDao.updateAttachedMeeting(1, 1);
        boolean updateFailed = complaintDao.updateAttachedMeeting(1, 4);
        Assertions.assertTrue(updateSuccess);
        Assertions.assertFalse(updateFailed);
    }

//    @AfterAll
//    static void dropTable(){
//        try(Connection conn = ConnectionUtil.createConnection()){
//            String sql = "drop table complaint";
//            Statement statement = conn.createStatement();
//            statement.execute(sql);
//
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//    }
}

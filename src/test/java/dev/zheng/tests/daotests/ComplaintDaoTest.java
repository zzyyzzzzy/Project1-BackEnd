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

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ComplaintDaoTest {
    static ComplaintDao complaintDao = new ComplaintDaoPostgres();
    @BeforeAll
    static void createTable(){
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "create table complaint(\n" +
                    "\tid serial primary key,\n" +
                    "\tdescription varchar(200) not null,\n" +
                    "\tpriority varchar(20) default 'UNASSIGNED',\n" +
                    "\tmeeting_id int references meeting(id) default -1\n" +
                    ");\n";
            Statement statement = conn.createStatement();
            statement.execute(sql);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    void createUserTest(){
        Complaint complaint = new Complaint(0, "zuojun", Priority.UNASSIGNED, -1);
        Complaint savedComplaint = complaintDao.createComplaint(complaint);
        Assertions.assertNotEquals(0, savedComplaint.getId());
    }

    @AfterAll
    static void dropTable(){
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "drop table complaint";
            Statement statement = conn.createStatement();
            statement.execute(sql);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}

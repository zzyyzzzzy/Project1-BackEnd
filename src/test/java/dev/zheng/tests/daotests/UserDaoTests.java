package dev.zheng.tests.daotests;

import dev.zheng.doas.userdao.UserDao;
import dev.zheng.doas.userdao.UserDaoPostgres;
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
public class UserDaoTests {

    static UserDao userDao = new UserDaoPostgres();

    @BeforeAll
    static void createTable(){
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "create table app_user(\n" +
                    "\tid serial primary key,\n" +
                    "\tfname varchar(50) not null,\n" +
                    "\tlname varchar(50) not null,\n" +
                    "\tuser_name varchar(50) not null unique,\n" +
                    "\tpassword varchar(50) not null,\n" +
                    "\ttitle varchar(20) not null default 'CONSTITUENT',\n" +
                    "\tapproved boolean default false\n" +
                    ");";
            Statement statement = conn.createStatement();
            statement.execute(sql);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    @Order(1)
    void create_user_test(){
        User user = new User(0, "zuojun", "zheng", "nice123", "demon",UserTitle.CONSTITUENT, false);
        User savedUser = userDao.createUser(user);
        Assertions.assertNotEquals(0, savedUser.getId());
    }
    @Test
    @Order(2)
    void get_user_by_username(){
        User user = userDao.getUserByUsername("nice123");
        User userNotExist = userDao.getUserByUsername("nic");
        Assertions.assertEquals("zuojun", user.getFname());
        Assertions.assertEquals("zheng", user.getLname());
        Assertions.assertNull(userNotExist);
    }
    @Test
    @Order(3)
    void change_user_status(){
        Map<String, String> res = userDao.updateUserStatus(1, true);
        User user = userDao.getUserByUsername("nice123");
        Assertions.assertTrue(user.isApproved());
    }

    @Test
    @Order(4)
    void create_user_test_v2(){
        User user = new User(0, "raiden", "shogun", "raiden123", "raiden123",UserTitle.CONSTITUENT, false);
        User savedUser = userDao.createUser(user);
        Assertions.assertNotEquals(0, savedUser.getId());
    }

    @Test
    @Order(5)
    void get_all_user_test(){
        List<User> allUsers = userDao.getAllUsers();
        Assertions.assertEquals(2, allUsers.size());
    }
    @AfterAll
    static void dropTable(){
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "drop table app_user";
            Statement statement = conn.createStatement();
            statement.execute(sql);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}

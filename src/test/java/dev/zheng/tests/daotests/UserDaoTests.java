package dev.zheng.tests.daotests;

import dev.zheng.doas.userdao.UserDAO;
import dev.zheng.doas.userdao.UserDaoPostgres;
import dev.zheng.entities.User;
import dev.zheng.entities.UserTitle;
import dev.zheng.utils.ConnectionUtil;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserDaoTests {

    static UserDAO userDao = new UserDaoPostgres();

    @BeforeAll
    static void createTable(){
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "create table app_user(\n" +
                    "\tuserid serial primary key,\n" +
                    "\tuser_name varchar(50) not null unique,\n" +
                    "\tpassword varchar(50) not null unique,\n" +
                    "\ttitle varchar(20) not null\n" +
                    ");\n";
            Statement statement = conn.createStatement();
            statement.execute(sql);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Test
    void createUserTest(){
        User user = new User(0, "zuojun", "zheng", UserTitle.COUNCIL);
        User savedUser = userDao.createUser(user);
        Assertions.assertNotEquals(0, savedUser.getId());
    }
    @AfterAll
    static void dropTable(){
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "drop table users";
            Statement statement = conn.createStatement();
            statement.execute(sql);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}

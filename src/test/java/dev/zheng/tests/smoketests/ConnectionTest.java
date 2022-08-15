package dev.zheng.tests.smoketests;

import dev.zheng.utils.ConnectionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class ConnectionTest {
    @Test
    void connection_available(){
        Connection connection = ConnectionUtil.createConnection();
        Assertions.assertNotNull(connection);
    }
}

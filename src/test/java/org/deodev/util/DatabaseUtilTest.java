package org.deodev.util;

import jdk.jfr.Description;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseUtilTest {

    DatabaseUtil databaseUtil = new DatabaseUtil();

    @Test
    void getConnection() {
        Connection result =  databaseUtil.getConnection();
        assertNotNull(result);
    }
}
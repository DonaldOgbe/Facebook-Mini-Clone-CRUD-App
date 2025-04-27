package org.deodev.dao;



import org.deodev.model.User;
import org.deodev.util.DatabaseUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDao {
    public boolean save (User user) {

        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";

        try(Connection connection = DatabaseUtil.getConnection();
            PreparedStatement statement = connection != null ? connection.prepareStatement(sql) : null;) {


        } catch (SQLException e) {

        }
        return true;
    }
}

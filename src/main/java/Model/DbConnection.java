package Model;

import java.sql.*;

public class DbConnection {

    public static Connection getConnection() throws ClassNotFoundException, SQLException {

        String dbDriver = "com.mysql.jdbc.Driver";
        Class.forName(dbDriver);
        String dbName = "tododatabase";
        String dbUserName = "toDoAdmin";
        String dbPassword = "toDoAdmin";
        String dbUrl = "jdbc:mysql://localhost:3306/";
        Connection conn = DriverManager.getConnection(dbUrl + dbName,
                dbUserName, dbPassword);
        return conn;
    }

    public static void closeConnection(Connection conn, Statement stmt, ResultSet rst) {

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (rst != null) {
            try {
                rst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

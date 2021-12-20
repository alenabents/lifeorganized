package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SubItemDbUtil {
    static Connection conn;

    static {
        try {
            conn = DbConnection.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Item> getSubItems(int ui, String email, Item mainItem) { //список подсписков где первый элемент - родитель

        String uid = Integer.toString(ui);// id родителя-списка
        String tableName = ItemDbUtil.getTableName(email);
        List<Item> items = new ArrayList<>();
        items.add(mainItem);

        try {
            String sqlStatement = "select *from " + tableName + " where idMain=?";

            PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement);
            preparedStatement.setString(1, uid);
            ResultSet rst = preparedStatement.executeQuery();

            while (rst.next()) {
                int id = rst.getInt("id");
                String label = rst.getString("label");
                String date = rst.getString("date");
                String time = rst.getString("time");
                int check = rst.getInt("checkk");

                Item theItem = new Item(id, label, date, time, check);

                items.add(theItem);
            }
            DbConnection.closeStatement(preparedStatement, rst);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public static void addSubItem(String id, String email, Item task) { //добавить подзадачу
        String tableName = ItemDbUtil.getTableName(email);
        try {
            String label = task.getLabel();
            String date = task.getDate();
            String time = task.getTime();
            String check = "0";

            String sqlStatement = "insert into " + tableName
                    + " (label, date, time, checkk, idMain)"
                    + "values (?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sqlStatement);

            stmt.setString(1, label);
            stmt.setString(2, date);
            stmt.setString(3, time);
            stmt.setString(4, check);
            stmt.setString(5, id);

            stmt.execute();
            DbConnection.closeStatement(stmt, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAllSubTasks(String id, String email) {
        String tableName = ItemDbUtil.getTableName(email);
        String sqlStatement = "DELETE FROM " + tableName + " WHERE id=?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement);
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
            DbConnection.closeStatement(preparedStatement, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteSubItem(String id, String email) {
        String[] ids = id.split(" "); //первое значение - айди подзадачи, второе - айди заголовка
        String tableName = ItemDbUtil.getTableName(email);
        String sqlStatement = "DELETE FROM " + tableName + " WHERE id=? and idMain=?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement);
            preparedStatement.setString(1, ids[0]);
            preparedStatement.setString(2, ids[1]);
            preparedStatement.executeUpdate();
            DbConnection.closeStatement(preparedStatement, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addFriendSubItem(String id, String email, Item task, String response) {
        String tableName = "friend" + ItemDbUtil.getTableName(email);
        try {
            String label = task.getLabel();
            String date = task.getDate();
            String time = task.getTime();
            String check = "0";

            String sqlStatement = "insert into " + tableName
                    + " (label, date, time, checkk, shared, idMain)"
                    + "values (?, ?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sqlStatement);

            stmt.setString(1, label);
            stmt.setString(2, date);
            stmt.setString(3, time);
            stmt.setString(4, check);
            stmt.setString(5, response);
            stmt.setString(6, id);

            stmt.execute();

            DbConnection.closeStatement(stmt, null);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Item> getSubFriendItems(int mainId, String email, Item mainItem) { //список подсписков где первый элемент - родитель

        String strId = Integer.toString(mainId); // id родителя-списка
        String tableName = "friend" + ItemDbUtil.getTableName(email);
        List<Item> items = new ArrayList<>();
        items.add(mainItem);

        try {
            String sqlStatement = "select *from " + tableName + " where idMain=?";

            PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement);
            preparedStatement.setString(1, strId);
            ResultSet rst = preparedStatement.executeQuery();

            while (rst.next()) {
                int id = rst.getInt("id");
                String label = rst.getString("label");
                String date = rst.getString("date");
                String time = rst.getString("time");
                int check = rst.getInt("checkk");
                String response = rst.getString("shared");

                Item theItem = new Item(id, label, date, time, check, response);

                items.add(theItem);
            }
            DbConnection.closeStatement(preparedStatement, rst);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public static void setSubCheck(String email, String idStr, int isChkSms) {
        String[] ids = idStr.split(" "); //первое значение - айди подзадачи, второе - айди заголовка
        String tableName = ItemDbUtil.getTableName(email);

        String sqlStatement = "UPDATE " + tableName + " SET checkk=? WHERE id=?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sqlStatement);
            stmt.setInt(1, isChkSms);
            stmt.setString(2, ids[0]);
            stmt.executeUpdate();
            DbConnection.closeStatement(stmt, null);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

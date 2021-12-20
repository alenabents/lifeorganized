package Model;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDbUtil {
    static Connection conn;

    static {
        try {
            conn = DbConnection.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public static Item getItemWithId(String id, String email) {

        Item task = new Item();
        String tableName = getTableName(email);
        String sqlStatement = "SELECT *FROM " + tableName + " WHERE id=?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement);
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String label = rs.getString("label");
                String date = rs.getString("date");
                String time = rs.getString("time");
                int check = rs.getInt("checkk");
                task = new Item(Integer.parseInt(id), label, date, time, check);
                DbConnection.closeStatement(preparedStatement, null);
                return task;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return task;
    }

    public static String checkCredentials(String email, String password) throws NoSuchAlgorithmException {
        String areValid = null;
        Statement stmt = null;
        ResultSet rst = null;
        String hashPass = hashPassword(password);
        try {
            if (!isAlreadyRegistered(email)) {
                areValid = "not registered";
            } else {
                String sqlStatement = "select * from user where email = binary '" + email + "' and password = binary '" + hashPass + "'";
                stmt = conn.createStatement();
                rst = stmt.executeQuery(sqlStatement);
                if (rst.next()) {
                    areValid = "valid";
                } else {
                    areValid = "inValid";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbConnection.closeStatement(stmt, rst);
        }
        return areValid;
    }

    private static String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        byte[] bytes = md5.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes){
            stringBuilder.append(String.format("%02X", b));
        }
        System.out.println(stringBuilder.toString());
        return stringBuilder.toString();
    }

    public static void deleteItem(String id, String email) {
        String tableName = getTableName(email);
        String sqlStatement = "DELETE FROM " + tableName + " WHERE id=?";

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement);
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
            sqlStatement = "DELETE FROM " + tableName + " WHERE idMain=" + id;
            preparedStatement = conn.prepareStatement(sqlStatement);
            preparedStatement.executeUpdate();
            DbConnection.closeStatement(preparedStatement, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addItem(Item theItem, String email) {
        String tableName = getTableName(email);
        try {

            String label = theItem.getLabel();
            String date = theItem.getDate();
            String time = theItem.getTime();
            String check = "0";

            String sqlStatement = "insert into " + tableName
                    + " (label, date, time, checkk, idMain)"
                    + "values (?, ?, ?, ?, ?)";

            System.out.println(sqlStatement);
            PreparedStatement stmt = conn.prepareStatement(sqlStatement);

            stmt.setString(1, label);
            stmt.setString(2, date);
            stmt.setString(3, time);
            stmt.setString(4, check);
            stmt.setString(5, "0");
            stmt.execute();

            DbConnection.closeStatement(stmt, null);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // список списков: элемент списка - список где первый элемент это главный список
    public static List<List<Item>> getItems(String email) {

        List<Item> items = new ArrayList<>();
        List<List<Item>> globalList = new ArrayList<>();

        String tableName = getTableName(email);

        try {
            String sqlStatement = "select * from " + tableName + " where idMain = 0";
            Statement stmt = conn.createStatement();
            ResultSet rst = stmt.executeQuery(sqlStatement);

            while (rst.next()) {
                int id = rst.getInt("id");
                String label = rst.getString("label");
                String date = rst.getString("date");
                String time = rst.getString("time");
                int check = rst.getInt("checkk");

                Item theItem = new Item(id, label, date, time, check);

                items.add(theItem);  //достали главные списки
            }

            for (int i = 0; i < items.size(); i++) {  //глобальный список, в начале каждого списка первый элемент - глобальный
                globalList.add(SubItemDbUtil.getSubItems(items.get(i).getId(), email, items.get(i)));
            }
            DbConnection.closeStatement(stmt, rst);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return globalList;
    }

    public static String getTableName(String email) {
        int pos1 = email.indexOf("@");
        int pos2 = email.indexOf(".", pos1);
        String tableName = email.substring(0, pos1) + "_" + email.substring(pos1 + 1, pos2);
        return tableName;
    }

    public static void createTable(String email) { //таблица глобальных задач пользователя
        Statement smt = null;
        String tableName = getTableName(email);
        try {
            smt = conn.createStatement();
            String sql = "CREATE TABLE " + tableName + " " +
                    "(id INTEGER NOT NULL AUTO_INCREMENT, " +
                    " label VARCHAR(50), " +
                    " date VARCHAR(12), " +
                    " time VARCHAR(10), " +
                    " checkk INTEGER(10), " +
                    " idMain INTEGER(10), " +
                    " PRIMARY KEY ( id ))";
            smt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isAlreadyRegistered(String email) {
        String sqlStatement = "select * from user where email = binary '" + email + "'";
        Statement stmt = null;
        ResultSet rst = null;
        Boolean isFound = false;
        try {
            stmt = conn.createStatement();
            rst = stmt.executeQuery(sqlStatement);
            if (rst.next()) {
                isFound = true;
            } else {
                isFound = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isFound;
    }

    public static String addUser(String email, String password) throws NoSuchAlgorithmException {
        String userAdded = null;
        String hashPass = hashPassword(password);

        if (isAlreadyRegistered(email)) {
            userAdded = "already registered";
        } else {
            try {
                String sqlStatement = "insert into user "
                        + "(email, password)"
                        + "values (?, ?)";

                PreparedStatement stmt = conn.prepareStatement(sqlStatement);

                stmt.setString(1, email);
                stmt.setString(2, hashPass);
                stmt.execute();
                createTable(email);
                userAdded = "user registered";
                DbConnection.closeStatement(stmt, null);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return userAdded;
    }

    public static void updateTodo(Item task, String id, String email) { //редактировать главную задачу
        String tableName = getTableName(email);
        String sqlStatement = "UPDATE " + tableName + " SET label=?, date=?, time=? WHERE id=?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sqlStatement);
            stmt.setString(1, task.getLabel());
            stmt.setString(2, task.getDate());
            stmt.setString(3, task.getTime());
            stmt.setString(4, id);
            stmt.executeUpdate();
            DbConnection.closeStatement(stmt, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void createFriendTable(String email) {

        Statement smt = null;
        String tableName = "friend" + getTableName(email);

        try {
            smt = conn.createStatement();
            String sql = "CREATE TABLE " + tableName + " " +
                    "(id INTEGER NOT NULL AUTO_INCREMENT, " +
                    " label VARCHAR(50), " +
                    " date VARCHAR(12), " +
                    " time VARCHAR(10), " +
                    " checkk INTEGER(10), " +
                    " shared varchar(40), " +
                    " idMain varchar(40), " +
                    " PRIMARY KEY ( id ))";
            smt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int addFriendItem(Item theItem, String email, String emailUser) {// mail того кто поделился

        String tableName = "friend" + getTableName(emailUser);
        int id = 0;
        try {
            String label = theItem.getLabel();
            String date = theItem.getDate();
            String time = theItem.getTime();
            String check = "0";

            String sqlStatement = "insert into " + tableName
                    + " (label, date, time, checkk, shared, idMain)"
                    + "values (?, ?, ?, ?, ?,?)";

            PreparedStatement stmt = conn.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, label);
            stmt.setString(2, date);
            stmt.setString(3, time);
            stmt.setString(4, check);
            stmt.setString(5, email);
            stmt.setString(6, "0");
            stmt.execute();
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }
            DbConnection.closeStatement(stmt, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    //если с этим пользователем уже кто то делился списком - у него уже есть таблица -> проверка есть ли она
    private static boolean isFriendTable(String email) {

        String tableName = "friend" + getTableName(email);
        String sqlStatement = "SHOW TABLES LIKE '" + tableName + "'";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                DbConnection.closeStatement(preparedStatement, null);
                return true;
            } else {
                DbConnection.closeStatement(preparedStatement, null);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String share(String id, String email, String users, List<String> response) {
        String[] usersArray = users.split(",");
        List<String> errorEmails = new ArrayList<>();
        String error = "not found users emails ";
        String isOwner = null;
        if (usersArray.length == 0) {
            return error;
        }
        //найти главный таск
        Item mainItem = getItemWithId(id, email);

        //список подсписков где 1 элемент - родитель
        List<Item> listItems = SubItemDbUtil.getSubItems(Integer.parseInt(id), email, mainItem);

        try {
            for (int i = 0; i < usersArray.length; i++) {
                if ((!EmailValidator.isValidEmail(usersArray[i])) || ((!EmailValidator.isValidEmail(usersArray[i])) && (!isAlreadyRegistered(usersArray[i])))) {
                    errorEmails.add(usersArray[i]);
                } else if (usersArray[i].equals(email)) {
                    isOwner = "you can't share with yourself";
                } else {
                    if (!isFriendTable(usersArray[i]))   //если не создана таблица
                        createFriendTable(usersArray[i]);
                    int idMainItem = addFriendItem(mainItem, email, usersArray[i]);
                    for (int j = 1; j < listItems.size(); j++) {
                        SubItemDbUtil.addFriendSubItem(Integer.toString(idMainItem), usersArray[i], listItems.get(j), response.get(j - 1));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if ((errorEmails.size() != 0) || (isOwner != null)) {
            StringBuilder sb = new StringBuilder();
            if (errorEmails.size() != 0) {
                sb.append(error);
                for (int i = 0; i < errorEmails.size(); i++) {
                    sb.append(errorEmails.get(i) + " ");
                }
            }
            if (isOwner != null) {
                sb.append(isOwner);
            }
            return sb.toString();
        }
        return "sent successfully";
    }


    public static List<List<Item>> getFriendsItems(String email) {
        List<Item> items = new ArrayList<>();
        List<List<Item>> globalList = new ArrayList<>();
        String tableName = "friend" + getTableName(email);

        try {
            String sqlStatement = "select * from " + tableName + " where idMain = 0";
            Statement stmt = conn.createStatement();
            ResultSet rst = stmt.executeQuery(sqlStatement);

            while (rst.next()) {
                int id = rst.getInt("id");
                String label = rst.getString("label");
                String date = rst.getString("date");
                String time = rst.getString("time");
                int check = rst.getInt("checkk");
                String shared = rst.getString("shared");

                Item theItem = new Item(id, label, date, time, check, shared);

                items.add(theItem);  //достали главные списки
            }

            for (int i = 0; i < items.size(); i++) {  //глобальный список, в начале каждого списка первый элемент - глобальный
                globalList.add(SubItemDbUtil.getSubFriendItems(items.get(i).getId(), email, items.get(i)));
            }
            DbConnection.closeStatement(stmt, rst);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return globalList;
    }

    public static void deleteFriendItem(String id, String email) {
        String tableName = "friend" + getTableName(email);
        String sqlStatement = "DELETE FROM " + tableName + " WHERE id=?";
        System.out.println(sqlStatement);

        try {
            PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement);
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
            sqlStatement = "DELETE FROM " + tableName + " WHERE idMain=?";
            preparedStatement = conn.prepareStatement(sqlStatement);
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
            DbConnection.closeStatement(preparedStatement, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void setCheck(String email, String id, int check) {
        String tableName = getTableName(email);
        String sqlStatement = "UPDATE " + tableName + " SET checkk=? WHERE id=?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sqlStatement);
            stmt.setInt(1, check);
            stmt.setString(2, id);
            stmt.executeUpdate();
            DbConnection.closeStatement(stmt, null);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}


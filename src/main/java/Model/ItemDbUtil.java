package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDbUtil {

    private Connection getConnection() throws ClassNotFoundException, SQLException{

        String dbDriver = "com.mysql.jdbc.Driver";
        Class.forName(dbDriver);
        String dbName = "tododatabase";
        String dbUserName = "toDoAdmin";
        String dbPassword = "toDoAdmin";
        String dbUrl = "jdbc:mysql://localhost:3306/";
        Connection conn = DriverManager.getConnection(dbUrl + dbName,
                dbUserName,dbPassword);
        return conn;

    }

    private void closeConnection(Connection conn, Statement stmt, ResultSet rst){

        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(stmt != null){
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(rst != null){
            try {
                rst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public Item getItemWithId(String id, String email){

        Item task = new Item();
        String tableName = getTableName(email);
        String sqlStatement = "SELECT *FROM " + tableName + " WHERE id=?";
        Connection conn = null;

        try {
            conn = getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement);
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String label = rs.getString("label");
                String date = rs.getString("date");
                String time = rs.getString("time");
                int check = rs.getInt("checkk");
                task = new Item(Integer.parseInt(id), label, date,time, check);
                closeConnection(conn, preparedStatement, null);
                return task;
            }


        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return task;

    }

    public String checkCredentials(String email, String password){

        String areValid = null;

        Connection conn = null;
        Statement stmt = null;
        ResultSet rst = null;

        try{

            conn = getConnection();

            if(!isAlreadyRegistered(email, conn)){
                areValid = "not registered";
            }
            else{
                String sqlStatement = "select * from user where email = binary '" + email + "' and password = binary '" + password + "'";

                stmt = conn.createStatement();
                rst = stmt.executeQuery(sqlStatement);
                if(rst.next()){
                    areValid = "valid";
                }
                else{
                    areValid = "inValid";
                }

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally{
            closeConnection(conn, stmt, rst);
        }

        return areValid;

    }

    public void checkItem(String email, int id, boolean check){
        Connection conn = null;
        String tableName = getTableName(email);
        try {
            conn = getConnection();

            boolean ch = check;

            String sqlStatement = "insert into " + tableName
                    + " (checkk)"
                    + "values (?)" + "where id = " + id;

            PreparedStatement stmt = conn.prepareStatement(sqlStatement);

            stmt.setBoolean(1, check);

            stmt.execute();

            closeConnection(conn, stmt, null);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteItem(String id, String email){

        String tableName = getTableName(email);
        String sqlStatement = "DELETE FROM " + tableName + " WHERE id=?";

        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement);
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
            closeConnection(conn, preparedStatement, null);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addItem(Item theItem, String email){

        Connection conn = null;
        String tableName = getTableName(email);
        try {
            conn = getConnection();

            String label = theItem.getLabel();
            String date = theItem.getDate();
            String time = theItem.getTime();
            String check = "0";

            String sqlStatement = "insert into " + tableName
                    + " (label, date, time, checkk)"
                    + "values (?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, label);
            stmt.setString(2, date);
            stmt.setString(3, time);
            stmt.setString(4, check);

            stmt.execute();
            ResultSet generatedKeys = stmt.getGeneratedKeys();

            int id = 0;
            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }
            createSubTable(conn,email, Integer.toString(id));

            closeConnection(conn, stmt, null);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<List<Item>> getItems(String email){

        List<Item> items = new ArrayList<>();
        List<List<Item>> globalList = new ArrayList<>();

        String tableName = getTableName(email);

        Connection conn = null;
        try {
            conn = getConnection();

            String sqlStatement = "select * from " + tableName;
            Statement stmt = conn.createStatement();

            ResultSet rst = stmt.executeQuery(sqlStatement);

            while(rst.next()){

                // retrieve data from result set row
                int id = rst.getInt("id");
                String label = rst.getString("label");
                String date = rst.getString("date");
                String time = rst.getString("time");
                int check = rst.getInt("checkk");

                Item theItem = new Item(id, label, date, time, check);

                items.add(theItem);  //достали главные списки
            }

            for (int i = 0; i < items.size(); i++){  //глобальный список, в начале каждого списка первый элемент - глобальный
                globalList.add(getSubItems(items.get(i).getId(), email, items.get(i) ));
            }

            closeConnection(conn, stmt, rst);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return globalList;

    }

    public List<Item> getSubItems(int ui, String email, Item mainItem){ //список подсписков где первый элемент - родитель

        String uid = Integer.toString(ui);// id родителя-списка
        String tableName = getTableName(email) + uid;
        List<Item> items = new ArrayList<>();
        items.add(mainItem);

        Connection conn = null;
        try {
            conn = getConnection();

            String sqlStatement = "select *from " + tableName;
            Statement stmt = conn.createStatement();

            ResultSet rst = stmt.executeQuery(sqlStatement);
            while(rst.next()){
                // retrieve data from result set row
                int id = rst.getInt("id");
                String label = rst.getString("label");
                String date = rst.getString("date");
                String time = rst.getString("time");
                int check = rst.getInt("checkk");

                Item theItem = new Item(id, label, date, time, check);

                items.add(theItem);
            }

            closeConnection(conn, stmt, rst);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return items;

    }

    private String getTableName(String email){

        int pos1 = email.indexOf("@");

        int pos2 = email.indexOf(".",pos1);

        String tableName = email.substring(0, pos1) + "_" + email.substring(pos1+1, pos2);

        return tableName;
    }

    private void createTable(Connection conn, String email){

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
                    " PRIMARY KEY ( id ))";

            smt.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void createSubTable(Connection conn, String email, String id){

        Statement smt = null;

        String tableName = getTableName(email) + id;

        try {
            smt = conn.createStatement();
            String sql = "CREATE TABLE " + tableName + " " +
                    "(id INTEGER NOT NULL AUTO_INCREMENT, " +
                    " label VARCHAR(50), " +
                    " date VARCHAR(12), " +
                    " time VARCHAR(10), " +
                    " checkk INTEGER(10), " +
                    " PRIMARY KEY ( id ))";

            smt.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean isAlreadyRegistered(String email, Connection conn){

        String sqlStatement = "select * from user where email = binary '" + email + "'";

        Statement stmt = null;

        ResultSet rst = null;

        Boolean isFound = false;

        try {
            stmt = conn.createStatement();
            rst = stmt.executeQuery(sqlStatement);
            if(rst.next()){
                isFound = true;
            }
            else{
                isFound = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return isFound;

    }

    public String addUser(String email, String password) {

        Connection conn = null;

        String userAdded = null;

        try {
            conn = getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(isAlreadyRegistered(email, conn)){
            userAdded =  "already registered";
        }

        else{

            try {

                String sqlStatement = "insert into user "
                        + "(email, password)"
                        + "values (?, ?)";

                PreparedStatement stmt = conn.prepareStatement(sqlStatement);

                stmt.setString(1, email);
                stmt.setString(2, password);

                stmt.execute();

                createTable(conn, email);

                userAdded = "user registered";

                closeConnection(conn, stmt, null);

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        return userAdded;

    }

    public void updateTodo(Item task, String id, String email){

        Statement smt = null;

        Connection conn = null;

        String tableName = getTableName(email);
        String sqlStatement = "UPDATE " + tableName + " SET label=?, date=?, time=? WHERE id=?";

        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlStatement);
            stmt.setString(1, task.getLabel());
            stmt.setString(2, task.getDate());
            stmt.setString(3, task.getTime());
            stmt.setString(4, id);
            stmt.executeUpdate();
            closeConnection(conn, stmt, null);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addSubItem(String id, String email, Item task){
        Connection conn = null;
        String tableName = getTableName(email) + id;
        try {
            conn = getConnection();

            String label = task.getLabel();
            String date = task.getDate();
            String time = task.getTime();
            String check = "0";

            String sqlStatement = "insert into " + tableName
                    + " (label, date, time, checkk)"
                    + "values (?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sqlStatement);

            stmt.setString(1, label);
            stmt.setString(2, date);
            stmt.setString(3, time);
            stmt.setString(4, check);

            stmt.execute();

            closeConnection(conn, stmt, null);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteSubTable(String id,String email){
        String tableName = getTableName(email);
        String sqlStatement = "DROP TABLE " + tableName + id;

        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement);
            preparedStatement.executeUpdate();
            closeConnection(conn, preparedStatement, null);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteSubItem(String id, String email){
        String[] ids = id.split(" "); //первое значение - айди подзадачи, второе - айди заголовка
        String tableName = getTableName(email) + ids[1];
        String sqlStatement = "DELETE FROM " + tableName + " WHERE id=?";

        Connection conn = null;
        try {
            conn = getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement);
            preparedStatement.setString(1, ids[0]);
            preparedStatement.executeUpdate();
            closeConnection(conn, preparedStatement, null);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public Item getSubItemWithId(String id, String email){

        Item task = new Item();
        String[] ids = id.split(" "); //первое значение - айди подзадачи, второе - айди заголовка
        String tableName = getTableName(email) + ids[1];
        String sqlStatement = "SELECT *FROM " + tableName + " WHERE id=?";
        Connection conn = null;

        try {
            conn = getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sqlStatement);
            preparedStatement.setString(1, ids[0]);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String label = rs.getString("label");
                String date = rs.getString("date");
                String time = rs.getString("time");
                int check = rs.getInt("checkk");
                task = new Item(Integer.parseInt(ids[0]), label, date,time, check);
                closeConnection(conn, preparedStatement, null);
                return task;
            }


        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return task;

    }


    public void updateSubTodo(Item task, String id, String email){

        Statement smt = null;

        Connection conn = null;
        String[] ids = id.split(" "); //первое значение - айди подзадачи, второе - айди заголовка
        String tableName = getTableName(email) + ids[1];
        String sqlStatement = "UPDATE " + tableName + " SET label=?, date=?, time=? WHERE id=?";

        try {
            conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sqlStatement);
            stmt.setString(1, task.getLabel());
            stmt.setString(2, task.getDate());
            stmt.setString(3, task.getTime());
            stmt.setString(4, ids[0]);
            stmt.executeUpdate();
            closeConnection(conn, stmt, null);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createFriendTable(Connection conn, String email){

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
                    " PRIMARY KEY ( id ))";

            smt.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int addFriendItem(Item theItem, String email, String emailUser){// mail того кто поделился

        Connection conn = null;
        String tableName = "friend" + getTableName(emailUser);
        int id = 0;
        try {
            conn = getConnection();

            String label = theItem.getLabel();
            String date = theItem.getDate();
            String time = theItem.getTime();
            String check = "0";

            String sqlStatement = "insert into " + tableName
                    + " (label, date, time, checkk, shared)"
                    + "values (?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sqlStatement, Statement.RETURN_GENERATED_KEYS);

            stmt.setString(1, label);
            stmt.setString(2, date);
            stmt.setString(3, time);
            stmt.setString(4, check);
            stmt.setString(5, email);

            stmt.execute();
            ResultSet generatedKeys = stmt.getGeneratedKeys();


            if (generatedKeys.next()) {
                id = generatedKeys.getInt(1);
            }
            createSubFriendTable(conn,emailUser, Integer.toString(id));

            closeConnection(conn, stmt, null);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    private void createSubFriendTable(Connection conn, String email, String id){ //ай ди главного списка, емайл личный

        Statement smt = null;

        String tableName = "friend" + getTableName(email) + id;

        try {
            smt = conn.createStatement();
            String sql = "CREATE TABLE " + tableName + " " +
                    "(id INTEGER NOT NULL AUTO_INCREMENT, " +
                    " label VARCHAR(50), " +
                    " date VARCHAR(12), " +
                    " time VARCHAR(10), " +
                    " checkk INTEGER(10), " +
                    " response VARCHAR(50), " +
                    " PRIMARY KEY ( id ))";

            smt.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addFriendSubItem(String id, String email, Item task, String response){
        Connection conn = null;
        String tableName = "friend" + getTableName(email) + id;
        try {
            conn = getConnection();

            String label = task.getLabel();
            String date = task.getDate();
            String time = task.getTime();
            String check = "0";

            String sqlStatement = "insert into " + tableName
                    + " (label, date, time, checkk, response)"
                    + "values (?, ?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sqlStatement);

            stmt.setString(1, label);
            stmt.setString(2, date);
            stmt.setString(3, time);
            stmt.setString(4, check);
            stmt.setString(5, response);

            stmt.execute();

            closeConnection(conn, stmt, null);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public String share(String id, String email, String users, List<String> response){
        String[] usersArray = users.split(",");
        List<String> errorEmails = new ArrayList<>();
        String error = new String("not found users emails");
        if(usersArray.length == 0){
            return error;
        }

        //найти главный таск
        Item mainItem = getItemWithId(id, email);

        //список подсписков где 1 элемент - родитель
        List<Item> listItems = getSubItems(Integer.parseInt(id),email,mainItem);



        try {
            for (int i = 0; i < usersArray.length; i++){
                if(!EmailValidator.isValidEmail(usersArray[i])){
                    errorEmails.add(usersArray[i]);
                }
                /*
                * Connection conn = null;
                    Statement stmt = null;
                    ResultSet rst = null;
                    conn = getConnection();
                    if(!isAlreadyRegistered(usersArray[i], conn)){
                        errorEmails.add(usersArray[i]);
                    }
                    closeConnection(conn, stmt, rst);
                * */
                else {
                    Connection conn = null;
                    Statement stmt = null;
                    ResultSet rst = null;
                    conn = getConnection();
                    createFriendTable(conn, usersArray[i]);
                    closeConnection(conn, stmt, rst);
                    int idMainItem = addFriendItem(mainItem,email,usersArray[i]);
                    for (int j = 1; j < listItems.size(); j++){
                        addFriendSubItem(Integer.toString(idMainItem), usersArray[i], listItems.get(j), response.get(j-1));
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        if(errorEmails.size() != 0){
            StringBuilder sb = new StringBuilder();
            sb.append(error);
            for (int i = 0 ; i< errorEmails.size(); i++){
                sb.append(errorEmails.get(i) + " ");
            }
            return sb.toString();
        }

        return new String("sent successfully");
    }



}
package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class ItemDbUtilTest {

    private Connection mockConn;
    private PreparedStatement mockPs;
    private Statement mockS;
    private ResultSet mockRs;
    public DbConnection mockDbConn;
    public ItemDbUtil mockDbUtil;
    ItemDbUtil db;
    public String tableName = "alena_mail";
    String id;
    String email;
    String password;

    @BeforeEach
    void setUp() {
        mockConn = Mockito.mock(Connection.class);
        mockPs = Mockito.mock(PreparedStatement.class);
        mockS = Mockito.mock(Statement.class);
        mockDbConn = Mockito.mock(DbConnection.class);
        mockDbUtil = Mockito.mock(ItemDbUtil.class);
        mockRs = Mockito.mock(ResultSet.class);
        db = new ItemDbUtil();
        id = "10";
        email = "alena@mail.ru";
        password = "123";
    }


    @Test
    void getItemWithId() throws SQLException {
        when(mockConn.prepareStatement("SELECT *FROM " + tableName + " WHERE id=?")).thenReturn(mockPs);
        mockDbUtil.getItemWithId(id, email);
    }


    @Test
    void checkCredentials() throws SQLException {
        when(mockConn.prepareStatement("select * from user where email = binary '" + email + "' and password = binary '" + password + "'")).thenReturn(mockPs);
        mockDbUtil.checkCredentials(email, password);
    }

    @Test
    void deleteItem() throws SQLException, ClassNotFoundException {
        when(mockConn.prepareStatement("DELETE FROM pop_mail WHERE id=?")).thenReturn(mockPs);
        mockDbUtil.deleteItem(id, email);
    }

    @Test
    void addItem() throws SQLException, ClassNotFoundException {
        when(mockConn.prepareStatement("insert into " + tableName
                + " (label, date, time, checkk)"
                + "values (?, ?, ?, ?)")).thenReturn(mockPs);
        mockDbUtil.addItem(new Item("label", "date", "time", 0), email);
    }

    @Test
    void getItems() throws SQLException {
        when(mockConn.prepareStatement("select * from " + tableName)).thenReturn(mockPs);
        mockDbUtil.getItems( email);
    }

    @Test
    void getSubItems() throws SQLException {
        when(mockConn.prepareStatement("select * from " + tableName + id)).thenReturn(mockPs);
        mockDbUtil.getSubItems( 19, email, new Item(19,"label","date","time", 0));
    }

    @Test
    void addUser() throws SQLException {
        when(mockConn.prepareStatement("insert into user "
                + "(email, password)"
                + "values (?, ?)")).thenReturn(mockPs);
        mockDbUtil.addUser( email, password);
    }

    @Test
    void updateTodo() throws SQLException {
        when(mockConn.prepareStatement("UPDATE " + tableName + " SET label=?, date=?, time=? WHERE id=?")).thenReturn(mockPs);
        mockDbUtil.updateTodo(  new Item(19,"label","date","time", 0),id, email);
    }

    @Test
    void addSubItem() throws SQLException {
        when(mockConn.prepareStatement("insert into " + tableName
                + " (label, date, time, checkk)"
                + "values (?, ?, ?, ?)")).thenReturn(mockPs);
        mockDbUtil.addSubItem(id, email, new Item("label", "date", "time", 0));
    }

    @Test
    void deleteSubTable() throws SQLException {
        when(mockConn.prepareStatement("DROP TABLE " + tableName + id)).thenReturn(mockPs);
        mockDbUtil.deleteSubTable( id, email);
    }

    @Test
    void deleteSubItem() throws SQLException {
        when(mockConn.prepareStatement("DELETE FROM " + tableName + " WHERE id=?")).thenReturn(mockPs);
        mockDbUtil.deleteSubItem( "1 19", email);
    }

    @Test
    void getSubItemWithId() throws SQLException {
        when(mockConn.prepareStatement("SELECT *FROM " + tableName + " WHERE id=?")).thenReturn(mockPs);
        mockDbUtil.getSubItemWithId( "1 19", email);
    }

    @Test
    void updateSubTodo() throws SQLException {
        when(mockConn.prepareStatement(" SET label=?, date=?, time=? WHERE id=?")).thenReturn(mockPs);
        mockDbUtil.updateSubTodo( new Item("label", "date", "time", 0), "1 19", email);
    }

    @Test
    void addFriendItem() throws SQLException {
        when(mockConn.prepareStatement("insert into " + tableName
                + " (label, date, time, checkk, shared)"
                + "values (?, ?, ?, ?, ?)")).thenReturn(mockPs);
        mockDbUtil.addFriendItem( new Item("label", "date", "time", 0), email, "user1@mail.ru");
    }

    @Test
    void addFriendSubItem() throws SQLException {
        when(mockConn.prepareStatement("insert into " + tableName
                + " (label, date, time, checkk, response)"
                + "values (?, ?, ?, ?, ?)")).thenReturn(mockPs);
        mockDbUtil.addFriendSubItem( id, email, new Item("label", "date", "time", 0), "user1@mail.ru");
    }

    @Test
    void share() throws SQLException {
        List<String> response = new ArrayList<>();
        response.add("user1@mail.ru");
        mockDbUtil.share(id,email, "user1@mail.ru",response);
    }

    @Test
    void getSubFriendItems() throws SQLException {
        when(mockConn.prepareStatement("select *from " + tableName)).thenReturn(mockPs);
        mockDbUtil.getSubFriendItems( 19, email, new Item("label", "date", "time", 0));
    }

    @Test
    void getFriendsItems() throws SQLException {
        when(mockConn.prepareStatement("select * from " + tableName)).thenReturn(mockPs);
        mockDbUtil.getFriendsItems( email);
    }

    @Test
    void deleteFriendItem() throws SQLException {
        when(mockConn.prepareStatement("DELETE FROM " + tableName + " WHERE id=?")).thenReturn(mockPs);
        mockDbUtil.deleteFriendItem( id, email);
    }

    @Test
    void deleteSubFriendTable() throws SQLException {
        when(mockConn.prepareStatement("DROP TABLE " + "friend" + tableName + id)).thenReturn(mockPs);
        mockDbUtil.deleteSubFriendTable( id, email);
    }

    @Test
    void setCheck() throws SQLException {
        when(mockConn.prepareStatement("UPDATE " + tableName + " SET checkk=? WHERE id=?")).thenReturn(mockPs);
        mockDbUtil.setCheck( email, id, 0);
    }

    @Test
    void setSubCheck() throws SQLException {
        when(mockConn.prepareStatement("UPDATE " + tableName + " SET checkk=? WHERE id=?")).thenReturn(mockPs);
        mockDbUtil.setSubCheck( email, "1 19", 0);
    }
}
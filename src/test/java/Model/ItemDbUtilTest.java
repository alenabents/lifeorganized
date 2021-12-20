package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class ItemDbUtilTest {

    private Connection mockConn;
    private PreparedStatement mockPs;
    private Statement mockS;
    private ResultSet mockRs;
    public DbConnection mockDbConn;
    public ItemDbUtil mockDbUtil;
    public String tableName = "alen_mail";
    String id;
    String email;
    String password;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        mockConn = Mockito.mock(Connection.class);
        mockPs = Mockito.mock(PreparedStatement.class);
        mockS = Mockito.mock(Statement.class);
        mockDbConn = Mockito.mock(DbConnection.class);
        mockDbUtil = Mockito.mock(ItemDbUtil.class);
        mockRs = Mockito.mock(ResultSet.class);
        id = "10";
        email = "alen@mail.ru";
        password = "123";
    }

    @Test
    void getItemWithId() throws SQLException, ClassNotFoundException {
        when(mockConn.prepareStatement("SELECT *FROM " + tableName + " WHERE id=?")).thenReturn(mockPs);
        mockDbUtil.getItemWithId(id, email);
        verify(mockPs).executeUpdate();
    }

    @Test
    void checkCredentials() throws SQLException, NoSuchAlgorithmException {
        when(mockConn.prepareStatement("select * from user where email = binary '" + email + "' and password = binary '" + password + "'")).thenReturn(mockPs);
        mockDbUtil.checkCredentials(email, password);
        verify(mockPs).executeUpdate();
    }

    @Test
    void deleteItem() throws SQLException, ClassNotFoundException {
        when(mockConn.prepareStatement("DELETE FROM pop_mail WHERE id=?")).thenReturn(mockPs);
        mockDbUtil.deleteItem(id, email);
        verify(mockPs).executeUpdate();
    }

    @Test
    void addItem() throws SQLException, ClassNotFoundException {
        when(mockConn.prepareStatement("insert into " + tableName
                + " (label, date, time, checkk)"
                + "values (?, ?, ?, ?)")).thenReturn(mockPs);
        mockDbUtil.addItem(new Item("label", "date", "time", 0), email);
        verify(mockPs).executeUpdate();
    }

    @Test
    void getItems() throws SQLException {
        when(mockConn.prepareStatement("select * from " + tableName)).thenReturn(mockPs);
        mockDbUtil.getItems(email);
        verify(mockPs).executeUpdate();
    }


    @Test
    void addUser() throws SQLException, NoSuchAlgorithmException {
        when(mockConn.prepareStatement("insert into user "
                + "(email, password)"
                + "values (?, ?)")).thenReturn(mockPs);
        mockDbUtil.addUser(email, password);
        verify(mockPs).executeUpdate();
    }

    @Test
    void updateTodo() throws SQLException {
        when(mockConn.prepareStatement("UPDATE " + tableName + " SET label=?, date=?, time=? WHERE id=?")).thenReturn(mockPs);
        mockDbUtil.updateTodo(new Item(19, "label", "date", "time", 0), id, email);
        verify(mockPs).executeUpdate();
    }

    @Test
    void addFriendItem() throws SQLException {
        when(mockConn.prepareStatement("insert into " + tableName
                + " (label, date, time, checkk, shared)"
                + "values (?, ?, ?, ?, ?)")).thenReturn(mockPs);
        mockDbUtil.addFriendItem(new Item("label", "date", "time", 0), email, "user1@mail.ru");
        verify(mockPs).executeUpdate();
    }

    @Test
    void share() throws SQLException {
        List<String> response = new ArrayList<>();
        response.add("user1@mail.ru");
        mockDbUtil.share(id, email, "user1@mail.ru", response);
        verify(mockPs).executeUpdate();
    }

    @Test
    void getFriendsItems() throws SQLException {
        when(mockConn.prepareStatement("select * from " + tableName)).thenReturn(mockPs);
        mockDbUtil.getFriendsItems(email);
        verify(mockPs).executeUpdate();
    }

    @Test
    void deleteFriendItem() throws SQLException {
        when(mockConn.prepareStatement("DELETE FROM " + tableName + " WHERE id=?")).thenReturn(mockPs);
        mockDbUtil.deleteFriendItem(id, email);
        verify(mockPs).executeUpdate();
    }

    @Test
    void setCheck() throws SQLException {
        when(mockConn.prepareStatement("UPDATE " + tableName + " SET checkk=? WHERE id=?")).thenReturn(mockPs);
        mockDbUtil.setCheck(email, id, 0);
        verify(mockPs).executeUpdate();
    }

}
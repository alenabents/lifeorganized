package Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SubItemDbUtilTest {

    private Connection mockConn;
    private PreparedStatement mockPs;
    private Statement mockS;
    private ResultSet mockRs;
    public DbConnection mockDbConn;
    public SubItemDbUtil mockDbUtil;
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
        mockDbUtil = Mockito.mock(SubItemDbUtil.class);
        mockRs = Mockito.mock(ResultSet.class);
        id = "10";
        email = "alen@mail.ru";
        password = "123";
    }

    @Test
    void getSubItems() throws SQLException {
        when(mockConn.prepareStatement("insert into " + tableName
                + " (label, date, time, checkk)"
                + "values (?, ?, ?, ?)")).thenReturn(mockPs);
        mockDbUtil.addSubItem(id, email, new Item("label", "date", "time", 0));
        verify(mockPs).executeUpdate();
    }

    @Test
    void addSubItem() throws SQLException {
        when(mockConn.prepareStatement("insert into " + tableName
                + " (label, date, time, checkk)"
                + "values (?, ?, ?, ?)")).thenReturn(mockPs);
        mockDbUtil.addSubItem(id, email, new Item("label", "date", "time", 0));
        verify(mockPs).executeUpdate();
    }

    @Test
    void deleteAllSubTasks() throws SQLException {
        when(mockConn.prepareStatement("DROP TABLE " + tableName + id)).thenReturn(mockPs);
        mockDbUtil.deleteAllSubTasks( id, email);
        verify(mockPs).executeUpdate();
    }

    @Test
    void deleteSubItem() throws SQLException {
        when(mockConn.prepareStatement("DELETE FROM " + tableName + " WHERE id=?")).thenReturn(mockPs);
        mockDbUtil.deleteSubItem( "1 19", email);
        verify(mockPs).executeUpdate();
    }

    @Test
    void addFriendSubItem() throws SQLException {
        when(mockConn.prepareStatement("insert into " + tableName
                + " (label, date, time, checkk, response)"
                + "values (?, ?, ?, ?, ?)")).thenReturn(mockPs);
        mockDbUtil.addFriendSubItem( id, email, new Item("label", "date", "time", 0), "user1@mail.ru");
        verify(mockPs).executeUpdate();
    }

    @Test
    void getSubFriendItems() throws SQLException {
        when(mockConn.prepareStatement("select *from " + tableName)).thenReturn(mockPs);
        mockDbUtil.getSubFriendItems( 19, email, new Item("label", "date", "time", 0));
        verify(mockPs).executeUpdate();
    }

    @Test
    void setSubCheck() throws SQLException {
        when(mockConn.prepareStatement("UPDATE " + tableName + " SET checkk=? WHERE id=?")).thenReturn(mockPs);
        mockDbUtil.setSubCheck( email, "1 19", 0);
        verify(mockPs).executeUpdate();
    }
}
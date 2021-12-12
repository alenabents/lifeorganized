package Model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class ItemDbUtilTest {

    private Connection mockConn;
    private PreparedStatement mockPs;
    private Statement mockS;
    private ResultSet mockRs;
    public ItemDbUtil mockDbUtil;
    public String tableName = "pop_mail";
    String id;
    String email;

    @BeforeEach
    void setUp() {
        mockConn = Mockito.mock(Connection.class);
        mockPs = Mockito.mock(PreparedStatement.class);
        mockS = Mockito.mock(Statement.class);
        mockDbUtil = Mockito.mock(ItemDbUtil.class);
        mockRs = Mockito.mock(ResultSet.class);
        id = "19";
        email = "pop@mail.ru";
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getItemWithId() {
        ItemDbUtil db = new ItemDbUtil();
        assertEquals(db.getItemWithId("19", "pop@mail.ru"), new Item(Integer.parseInt(id), "g", "g", "g", 0));
    }


    @Test
    void checkCredentials() {
    }

    @Test
    void deleteItem() {
    }

    @Test
    void addItem() throws SQLException, ClassNotFoundException {
        String query = "insert into pop_mail (label, date, time, checkk) values (?, ?, ?, ?)";
        String label = "label";
        String date = "12-08-2021";
        String time = "12:22";
        String check = "0";

        when(mockDbUtil.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)).thenReturn(mockPs);
        when(mockPs.getGeneratedKeys()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt(1)).thenReturn(19);
        verify(mockDbUtil).closeConnection(mockConn, mockPs, null);
        //verify(mockDbUtil).createSubTable(mockConn, "pop@mail.ru", Integer.toString(19));
        //verify(mockDbUtil).closeConnection(mockConn, mockPs, null);
        boolean result = mockDbUtil.addItem(new Item(label,date,time,Integer.parseInt(check)), "pop@mail.ru");
        assertTrue(result);
    }

    @Test
    void getItems() {
    }

    @Test
    void getSubItems() {
    }

    @Test
    void addUser() {

    }

    @Test
    void updateTodo() {
    }

    @Test
    void addSubItem() {
    }

    @Test
    void deleteSubTable() {
    }

    @Test
    void deleteSubItem() {
    }

    @Test
    void getSubItemWithId() {
    }

    @Test
    void updateSubTodo() {
    }

    @Test
    void addFriendItem() {
    }

    @Test
    void addFriendSubItem() {
    }

    @Test
    void share() {
    }

    @Test
    void getSubFriendItems() {
    }

    @Test
    void getFriendsItems() {
    }

    @Test
    void deleteFriendItem() {
    }

    @Test
    void deleteSubFriendTable() {
    }

    @Test
    void setCheck() {
    }

    @Test
    void setSubCheck() {
    }
}
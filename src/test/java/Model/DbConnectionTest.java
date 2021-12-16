package Model;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DbConnectionTest {

    @Test
    void getConnection() throws SQLException, ClassNotFoundException {
        Connection result = DbConnection.getConnection();
        assertEquals(result != null , true);

    }

    @Test
    void closeConnection() throws SQLException, ClassNotFoundException {
        Connection result = DbConnection.getConnection();
        Statement mockS = Mockito.mock(Statement.class);
        DbConnection.closeConnection(result, mockS, null );
    }
}
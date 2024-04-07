import connection.SqlConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EmployeeImplementation {
    private static final SqlConnection connection = new SqlConnection();

    /**
     * @param ID
     * @return List of Product objects
     */
    public List<Employee> getEmployeeById(String ID) {

        CallableStatement cstmt = null;
        ResultSet rs = null;
        List<Employee> employeeList = new ArrayList<>();

        try {
            cstmt = connection.getConnection().prepareCall(
                    "{call [dbo].[getEmployeeById](?)}",
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);

            cstmt.setString("ID", ID);
            boolean results = cstmt.execute();
            int rowsAffected = 0;

            // Protects against lack of SET NOCOUNT in stored procedure
            while (results || rowsAffected != -1) {
                if (results) {
                    rs = cstmt.getResultSet();
                    break;
                } else {
                    rowsAffected = cstmt.getUpdateCount();
                }
                results = cstmt.getMoreResults();
            }

            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getString("ID"),
                        rs.getString("NAME"),
                        rs.getString("SALARY")
                      );
                employeeList.add(employee);
            }
        } catch (Exception ex) {
            Logger.getLogger(MainProgramm.class.getName()).
                    log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MainProgramm.class.getName()).
                            log(Level.WARNING, null, ex);
                }
            }
            if (cstmt != null) {
                try {
                    cstmt.close();
                } catch (SQLException ex) {
                    Logger.getLogger(MainProgramm.class.getName()).
                            log(Level.WARNING, null, ex);
                }
            }
        }
        return employeeList;
    }

    /**
     * Close the database connection
     *
     * @return
     */
    public boolean closeConnection() {
        if (connection.getConnection() != null) {
            try {
                connection.getConnection().close();
            } catch (SQLException ex) {
                Logger.getLogger(MainProgramm.class.getName()).
                        log(Level.WARNING, null, ex);
                return false;
            }
        }
        return true;
    }
}

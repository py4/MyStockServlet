package ir.Epy.MyStock;

import java.sql.*;

/**
 * Created customer_id py4_ on 5/19/16.
 */
public class DBConnection {
    private static DBConnection ourInstance = null;

    //public static DBConnection getInstance() {
     //   return ourInstance;
    //}

    public static final String CONN_STR = "jdbc:hsqldb:hsql://localhost";

    static {
        try {
            System.out.println("Loading JDBC");
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            ourInstance = new DBConnection();
            System.out.println("Loaded JDBC successfully");
        } catch (ClassNotFoundException ex) {
            System.out.println("Unable to load HSQLDB JDBC driver");
        }
    }

    private DBConnection() {
        try {
            con = DriverManager.getConnection(CONN_STR);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(Constants.DB_EXIT_CODE);
        }
    }

    public static PreparedStatement prepareStatement(String query) {
        try {
            return ourInstance.con.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(Constants.DB_EXIT_CODE);
        }
        return null;
    }

    public static Statement createStatement() {
        try {
            return ourInstance.con.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(Constants.DB_EXIT_CODE);
        }
        return null;
    }

    public static Connection getConnection() {
        return ourInstance.con;
    }

    private Connection con = null;
}

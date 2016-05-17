import javax.servlet.annotation.WebServlet;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Behiye on 5/10/2016.
 */
public class DatabaseUtils {

    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/";

    //  DatabaseUtils credentials
    static final String USER = "root";
    static final String PASS = "";

    //  Necessary for connection to database
    static Connection conn = null;
    static Statement stmt = null;

    private static void initialize() throws SQLException, ClassNotFoundException {
        if(stmt != null && conn != null) return;
        Class.forName(JDBC_DRIVER);
        conn=DriverManager.getConnection(DB_URL,USER,PASS);
        stmt = conn.createStatement();
    }

    public static boolean createDatabase() throws SQLException, ClassNotFoundException {
        initialize();
        String sql = "CREATE DATABASE behiye_database";
        System.out.println(sql);
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.execute();
        return true;
    }

    public static boolean useDatabase() throws SQLException, ClassNotFoundException {
        initialize();
        String sql = "USE behiye_database";
        System.out.println(sql);
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.execute();
        return true;

    }

    public static boolean dropDatabase() throws SQLException, ClassNotFoundException {
        initialize();
        String sql = "DROP DATABASE IF EXISTS behiye_database";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.execute();
        return true;
    }

    public static boolean createDataTable() throws SQLException, ClassNotFoundException {
        initialize();
        String sql = "CREATE TABLE behiye_data (name varchar(30) PRIMARY KEY, date int,twitterAcc varchar(30))";
        System.out.println(sql);
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.execute();
        return true;
    }

    public static boolean dropTable() throws SQLException, ClassNotFoundException {
        initialize();
        String sql="DROP TABLE IF EXISTS behiye_data";
        PreparedStatement ps=conn.prepareStatement(sql);
        ps.execute();
        return true;
    }

    public static boolean createSavedTable() throws SQLException, ClassNotFoundException {
        initialize();
        String sql = "CREATE TABLE saved_data (name varchar(30) PRIMARY KEY, date int,twitterAcc varchar(30))";
        System.out.println(sql);
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.execute();
        return true;

    }

    public static boolean addData(String name, int date, String twitterAcc) throws SQLException, ClassNotFoundException {
        initialize();
        String sql = "INSERT INTO behiye_data (name,date,twitterAcc) VALUES (?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2,Integer.toString(date));
        ps.setString(3,twitterAcc);
        ps.execute();
        return true;
    }

    public static boolean addSavedData(String name, int date, String twitterAcc) throws SQLException, ClassNotFoundException {
        initialize();
        String sql = "INSERT INTO saved_data (name,date,twitterAcc) VALUES (?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, name);
        ps.setString(2,Integer.toString(date));
        ps.setString(3,twitterAcc);
        ps.execute();
        return true;
    }

    public static ArrayList<String> makeQuery(String letter, String date) throws SQLException, ClassNotFoundException {
        initialize();
        String sql = "SELECT * FROM behiye_data WHERE date > '" + date+"' AND name LIKE '"+letter+"%'";
        System.out.println(sql);
        ResultSet rs;
        ArrayList<String> data = new ArrayList<String>();
        rs = stmt.executeQuery(sql);
        while(rs.next()) {
            data.add(rs.getString("name"));
            data.add(Integer.toString(rs.getInt("date")));
            data.add(rs.getString("twitterAcc"));
            System.out.println("----->"+rs.getString("name")+" "+Integer.toString(rs.getInt("date"))+" "+rs.getString("twitterAcc"));
        }
        rs.close();

        return data;
    }
}

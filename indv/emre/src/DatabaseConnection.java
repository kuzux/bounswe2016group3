
import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/";

    static final String USER = "root";
    static final String PASS = "root";

    static Connection conn = null;
    static Statement stmt = null;

    private static void initialize(){
        if(stmt != null && conn != null) return;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            stmt = conn.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean dropTable(){
        initialize();
        String sql = "DROP TABLE cmpe352db";
        return executeOtherQuery(sql);
    }

    public static boolean createDatabase(){
        initialize();
        String sql = "CREATE DATABASE cmpe352db";
        return executeOtherQuery(sql);
    }

    public static boolean useDatabase(){
        String sql = "USE cmpe352db";
        return executeOtherQuery(sql);
    }

    public static boolean createTable(){
        initialize();
        String sql = "CREATE TABLE cmpe352db (name varchar(100), date DATE PRIMARY KEY)";
        return executeOtherQuery(sql);
    }

    public static boolean orderTable(){
        initialize();
        String sql = "SELECT * FROM cmpe352db ORDER BY date";
        return executeOtherQuery(sql);
    }

    public static boolean saveData(DataObject myData){
        initialize();
        String sql = "INSERT INTO cmpe352savedData (name,date) VALUES ('"+String.valueOf(myData.name)+"','"+myData.date+"')";
        return executeOtherQuery(sql);
    }

    public static boolean unsaveData(Date date){
        initialize();
        String sql = "DELETE FROM cmpe352savedData WHERE date='"+date+"'";
        return executeOtherQuery(sql);
    }


    public static ArrayList<DataObject> makeQuery(int queriedMinYear, int queriedMaxYear) {
        initialize();
        String sql = "SELECT * FROM cmpe352db WHERE YEAR(date) BETWEEN " + queriedMinYear + " AND " + queriedMaxYear;
        return executeFetchQuery(sql);
    }


    public static boolean addData(DataObject myData){
        initialize();
        String sql = "INSERT INTO cmpe352db (name,date) VALUES ('"+String.valueOf(myData.name)+"','"+myData.date+"')";
        System.out.println(sql);
        return executeOtherQuery(sql);
    }


    public static ArrayList<DataObject> getSavedData() {
        initialize();
        String sql = "SELECT * FROM cmpe352savedData";
        return executeFetchQuery(sql);
    }

    private static ArrayList<DataObject> executeFetchQuery(String sql) {
        ResultSet rs;
        ArrayList<DataObject> data = new ArrayList<DataObject>();
        try {
            rs = stmt.executeQuery(sql);
            while(rs.next())
                data.add(new DataObject(rs.getString("name"), (Date) rs.getDate("date")));
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    private static boolean executeOtherQuery(String sql) {
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
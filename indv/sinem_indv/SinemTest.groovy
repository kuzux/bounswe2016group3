/**
 * Created by sinem on 10/05/16.
 */

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement

import static org.junit.Assert.*;

/**
 * Tests query for year of birth.
 */
class SinemTest extends groovy.util.GroovyTestCase {
    public void test(){
         final String DRIVER = "com.mysql.jdbc.Driver";
        final String URL = "jdbc:mysql://localhost:3306/sinem_database";
        final String user = "";
         final String key = "";
        Connection conn = null;
       Statement stmt = null;
        Sinem.initialize();
        conn = DriverManager.getConnection(URL,user,key);
        stmt = conn.createStatement();
        String sql = "SELECT DISTINCT * FROM sinem_data WHERE yob > 1960";
        java.sql.ResultSet rs;
        ArrayList<String> names=new ArrayList<String>();
        ArrayList<String> yod=new ArrayList<String>();
        ArrayList<String> yob=new ArrayList<String>();

        try {
            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                names.add(rs.getString("name"));
                yod.add(rs.getString("yod"));
                yob.add(rs.getString("yob"));
                System.out.println(rs.getString("name")+" "+rs.getString("yod")+" "+ rs.getString("yob") ) ;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

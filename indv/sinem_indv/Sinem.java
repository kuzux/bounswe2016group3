import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import java.sql.*;
import java.util.ArrayList;

import com.google.gson.Gson;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Implements a search for Actor names and their birth year by providing queries to Wikidata.
 * Data contains actor's name,
 * year of birth and year of death.
 * @author Sinem DALKILIC
 *
 */
@WebServlet("/Hello")
public class Sinem extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String jsonData = null;
    final static String base = "https://query.wikidata.org/sparql?query=";
    static final String DRIVER = "com.mysql.jdbc.Driver";
    static final String URL = "jdbc:mysql://localhost:3306/sinem_database";
    static final String user = "";
    static final String key = "";
    static Connection conn = null;
    static Statement stmt = null;
    public static ArrayList<String> names=new ArrayList<String>();
    public static ArrayList<String> yod=new ArrayList<String>();
    public static ArrayList<String> yob=new ArrayList<String>();
    /**
     * Constructor
     */
    public Sinem() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Handles the request from the user. Stores the data about actors into database.Data contains actor's name,
     * year of birth and year of death. Input size is limited to 20 to chech easily.
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.setContentType("text/html");
        String requestType = request.getParameter("name");
        PrintWriter out = response.getWriter();
        Query query = QueryFactory.create("PREFIX wikibase: <http://wikiba.se/ontology#>\n" +
                    "PREFIX bd: <http://www.bigdata.com/rdf#>\n" +
                    "PREFIX wdt: <http://www.wikidata.org/prop/direct/>\n" +
                    "PREFIX wd: <http://www.wikidata.org/entity/>\n" +
                    "SELECT ?human ?humanLabel ?yob ?yod WHERE {\n" +
                    "   ?human wdt:P31 wd:Q5\n" +
                    " ; wdt:P106 wd:Q33999 .\n" +
                    " ?human wdt:P569 ?dob . \n" +
                    "?human wdt:P569 ?dob . ?human wdt:P570 ?dod . \n" +
                    "BIND(YEAR(?dob) as ?yob) . #if available: year \n " +
                    " BIND(YEAR(?dod) as ?yod) . \n " +
                    " SERVICE wikibase:label { \n" +
                    " bd:serviceParam wikibase:language \"en\" . \n" +
                    " } \n" +
                    " } \n " +
                    "LIMIT 20 \n");

            QueryExecution qex = QueryExecutionFactory.sparqlService("https://query.wikidata.org/sparql", query);
            ResultSet results = qex.execSelect();
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            ResultSetFormatter.outputAsJSON(outStream, results);
            jsonData = new String(outStream.toByteArray());
            JSONObject myJSONobject = null;

            try {
                myJSONobject = new JSONObject(jsonData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject myJSONobject2 = null;
            try {
                myJSONobject2 = myJSONobject.getJSONObject("results");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String[] names = new String[20];
            String[] yob = new String[20];
            String[] yod = new String[20];
            try {
                JSONArray myJSONarray = myJSONobject2.getJSONArray("bindings");

                for (int i = 0; i < myJSONarray.length(); i++) {
                    JSONObject tempRecord = myJSONarray.getJSONObject(i);
                    String name = tempRecord.getJSONObject("humanLabel").getString("value");
                    String yob1 = tempRecord.getJSONObject("yob").getString("value");
                    String yod1 = tempRecord.getJSONObject("yod").getString("value");
                    names[i] = name;
                    yob[i] = yob1;
                    yod[i] = yod1;

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

            initialize();
            createTable();

            for (int i = 0; i < 20; i++) {

                String sql = "INSERT INTO sinem_data (`name`, `yob`, `yod`) VALUES (\"" + names[i] + "\",\"" + yob[i] + "\",\"" + yod[i] + "\")";
                System.out.println(sql);
                try {
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.execute();

                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();

                }
            }

    }

    /**
     * Outputs actors' name and year of birth according to the query.
     * @param response  User response
     * @param names     Names of actors
     * @param yob       Birth years of actors
     * @throws IOException
     */
    public static void print(HttpServletResponse response,ArrayList<String> names,ArrayList<String> yob) throws IOException {
        String list = "";
        PrintWriter out = response.getWriter();
        list+="<table>";
        list+="<tr>";
        list += "<td align='center'>" + "          Name: "+ "</td>";
        list += "<td align='center'>" + "Year of Birth: " + "</td>";
        list+="<form action=\"Actors\" method=\"get\">";
        for(int i = 0; i < names.size();i++) {
            list += "<tr>";
            list += "<td><input type='checkbox' name='checkbox"+"' value='"+i+"' /></td>";
            list += "<td align='center'>" + names.get(i) + "</td>";
            list += "<td align='center'>" + yob.get(i) + "</td>";
            list += "</tr>";
        }
        list+="<p><input type=\"submit\" value=\"Save\" name=\"input\" /></p>";
        list+="</form>";
        list+="</table>";
        out.println(list);
    }

    /**
     * Makes a query for the year entered by the user. Sends results to print method which outputs the results.
     * @param year Birth years of actors
     * @param response User response
     * @throws IOException
     */
    public static void makeQuery(String year,HttpServletResponse response) throws IOException {

        String sql = "SELECT DISTINCT * FROM sinem_data WHERE yob > " + year;
        java.sql.ResultSet rs;
        names=new ArrayList<String>();
        yod=new ArrayList<String>();
        yob=new ArrayList<String>();

        try {
            rs = stmt.executeQuery(sql);

            while(rs.next()) {
                names.add(rs.getString("name"));
                yod.add(rs.getString("yod"));
                yob.add(rs.getString("yob"));

            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        print(response,names,yob);
    }
    /**
     * Makes a query for the name entered by the user. Sends results to print method which outputs the results.
     * @param name Full name of actors
     * @param response User response
     * @throws IOException
     */
    public static void makeQueryName(String name,HttpServletResponse response) throws IOException {


        String sql = "SELECT DISTINCT * FROM sinem_data WHERE name LIKE \"" + name+ "\"";
        java.sql.ResultSet rs;
        names=new ArrayList<String>();
         yod=new ArrayList<String>();
         yob=new ArrayList<String>();

        try {
            rs = stmt.executeQuery(sql);

            while(rs.next()) {

                names.add(rs.getString("name"));
                yod.add(rs.getString("yod"));
                yob.add(rs.getString("yob"));

            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        print(response,names,yob);
    }

    /**
     * Saves selected results to a new table in the database.
     * @param response User response
     * @param request User request
     * @return Indicates the termination of the method
     * @throws IOException
     */
    public static String save(HttpServletResponse response,HttpServletRequest request) throws IOException {
       PrintWriter out = response.getWriter();

       createNewTable();
    String s=request.getQueryString();
        out.println(s);
            int i = Integer.parseInt(request.getParameter("checkbox"));
    for(int j=0;j<1+(s.length()/11);j++) {
        int temp = Integer.parseInt(s.substring(20 + j * 11, 21 + j * 11));


        out.println(temp);

        String sql = "INSERT INTO user_data (`name`, `yob`, `yod`) VALUES (\"" + names.get(temp) + "\",\"" + yob.get(temp) + "\",\"" + yod.get(temp) + "\")";
      // out.println(sql);
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
    }

        return "true";
    }

    /**
     * Initializes the sinem_database database.
     */
    public static void initialize(){
        if(stmt != null && conn != null) return;
        try {
            Class.forName(DRIVER);

            conn = DriverManager.getConnection(URL,user,key);
            stmt = conn.createStatement();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Done...");
    }

    /**
     * Creates sinem_data table in the existing database.
     * @return
     */
    public static boolean createTable(){
        initialize();
        String sql = "CREATE TABLE sinem_data (name varchar(255), yob varchar(255), yod varchar(255))";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
            return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
    public static boolean createNewTable(){
        initialize();
        String sql = "CREATE TABLE user_data (name varchar(255), yob varchar(255), yod varchar(255))";

        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
            return true;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Directs to doGet method.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        this.doGet(request, response);
    }

}
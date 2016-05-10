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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


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
 * Servlet implementation class Hello
 */
@WebServlet("/Hello")
public class Sinem extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static String jsonData = null;
    final static String base = "https://query.wikidata.org/sparql?query=";
    static final String DRIVER = "com.mysql.jdbc.Driver";
    static final String URL = "jdbc:mysql://localhost:3306/sinem_database";
    static final String user = "root";
    static final String PASS = "";
    static Connection conn = null;
    static Statement stmt = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sinem() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.setContentType("text/html");

        String requestType = request.getParameter("type");
        PrintWriter out = response.getWriter();
        String inputTerm = request.getParameter("input");
        String title = "Using GET Method to Read Form Data";
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
                " BIND(YEAR(?dod) as ?yod) . \n "+
                " SERVICE wikibase:label { \n" +
                " bd:serviceParam wikibase:language \"en\" . \n"+
                " } \n" +
                " } \n " +
                "LIMIT 20 \n");

        QueryExecution qExe = QueryExecutionFactory.sparqlService( "https://query.wikidata.org/sparql", query );
        ResultSet results = qExe.execSelect();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        ResultSetFormatter.outputAsJSON(outStream, results);
        jsonData = new String(outStream.toByteArray());
        JSONObject myJSONobject = null;


     //   Gson gson=new Gson();
      //  Sinem sn=gson.fromJson(jsonData,Sinem.class);



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
        try {
            JSONArray myJSONarray = myJSONobject2.getJSONArray("bindings");
            String[] names=new String[20];
            String[] yob=new String[20];
            String[] yod=new String[20];
            for(int i = 0; i<myJSONarray.length();i++){
                JSONObject tempRecord = myJSONarray.getJSONObject(i);
                String name = tempRecord.getJSONObject("humanLabel").getString("value");
                String yob1 = tempRecord.getJSONObject("yob").getString("value");
                String yod1 = tempRecord.getJSONObject("yod").getString("value");
                names[i]=name;
                yob[i]=yob1;
                yod[i]=yod1;
            //response.getWriter().write(name+"<br />");
             //   response.getWriter().write(yob+"\n");
              //  response.getWriter().write(yod+"\n");
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
       initialize();
    }
    public static void initialize(){
        if(stmt != null && conn != null) return;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL,user,PASS);
            stmt = conn.createStatement();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        this.doGet(request, response);
    }

}
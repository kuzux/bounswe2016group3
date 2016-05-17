/**
 * Created by Behiye on 5/10/2016.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.io.ByteArrayOutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/**
 * Servlet implementation class Hello
 */

public class Behiye_Servlet extends HttpServlet {

    private static String jsonString = null;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Behiye_Servlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
//            DatabaseUtils.dropDatabase();
            DatabaseUtils.createDatabase();
            DatabaseUtils.useDatabase();
            DatabaseUtils.createDataTable();
            DatabaseUtils.createSavedTable();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        response.setContentType("text/html");

        Query query = QueryFactory.create(
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n"+
                "PREFIX wikibase: <http://wikiba.se/ontology#>\n"+
                "PREFIX bd: <http://www.bigdata.com/rdf#>\n"+
                "PREFIX wd: <http://www.wikidata.org/entity/>\n"+
                "PREFIX wdt: <http://www.wikidata.org/prop/direct/>\n"+
                "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n"+

                "SELECT DISTINCT ?personLabel ?twitterName ?pic ?date WHERE {\n"+
                "?person wdt:P2002 ?twitterName ;\n"+
                "wdt:P106 ?occupation .\n"+
                "OPTIONAL { ?person wdt:P18 ?pic . }\n"+
                "?occupation wdt:P279* wd:Q177220 . # all subclasses of biologists\n"+
                "?person wdt:P569 ?date .\n"+
                "SERVICE wikibase:label {\n"+
                "bd:serviceParam wikibase:language \"en\" .\n"+
                " ?person rdfs:label ?personLabel .\n"+
                "}\n"+
                "}\n");

        QueryExecution qExe = QueryExecutionFactory.sparqlService( "https://query.wikidata.org/sparql", query );

        //converts result to jsonstring
        ResultSet results = qExe.execSelect();
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        ResultSetFormatter.outputAsJSON(outStream, results);
        jsonString = new String(outStream.toByteArray());

        try {
            JSONArray resultArray = (new JSONObject(jsonString)).getJSONObject("results").getJSONArray("bindings");
            DatabaseUtils.useDatabase();
            for(int i = 0; i<300;i++){
                JSONObject tempRecord = resultArray.getJSONObject(i);
                String name = tempRecord.getJSONObject("personLabel").getString("value");
                String tempDate=tempRecord.getJSONObject("date").getString("value");
                String twitterAcc=tempRecord.getJSONObject("twitterName").getString("value");
                System.out.println("*****"+name+" "+tempDate);
                String date=tempDate.substring(0,tempDate.indexOf('-'));
                DatabaseUtils.addData(name,Integer.parseInt(date),twitterAcc);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

}
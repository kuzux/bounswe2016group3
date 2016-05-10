import org.apache.jena.query.*;
import org.apache.jena.query.ResultSet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;

/**
 * Created by emreun on 5/9/16.
 */
@WebServlet("/DataReceiver")
public class DataReceiver extends HttpServlet {

    public DataReceiver() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        getDataToDB();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        this.doGet(request, response);
    }

    public static void getDataToDB() {

        DatabaseConnection dbcon = new DatabaseConnection();

        dbcon.createDatabase();
        dbcon.useDatabase();
        dbcon.dropTable();
        dbcon.createTable();


        String queryStr = "PREFIX bd: <http://www.bigdata.com/rdf#>\n" +
                "PREFIX wikibase: <http://wikiba.se/ontology#>\n" +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                "PREFIX p: <http://www.wikidata.org/prop/>\n" +
                "PREFIX pq: <http://www.wikidata.org/prop/qualifier/>\n" +
                "PREFIX wd: <http://www.wikidata.org/entity/>\n" +
                "PREFIX ps: <http://www.wikidata.org/prop/statement/>\n" +
                "PREFIX wdt: <http://www.wikidata.org/prop/direct/>\n" +
                "SELECT DISTINCT ?item ?itemLabel ?awardLabel ?time\n" +
                "WHERE {\n" +
                "    ?item wdt:P106/wdt:P279* wd:Q3455803 . \n" +
                "    ?item p:P166 ?awardStat . \n" +
                "    ?awardStat ps:P166 wd:Q103360 . \n" +
                "    SERVICE wikibase:label {     \n" +
                "        bd:serviceParam wikibase:language \"en\" .\n" +
                "    }\n" +
                "    ?awardStat pq:P805 ?award . \n" +
                "  \t?award rdfs:label ?awardLabel filter (lang(?awardLabel) = \"en\") . \n" +
                "  \t?award wdt:P585 ?time . \n" +
                "}\n" +
                "ORDER BY DESC(?time)\n" +
                "\n";

        Query query = QueryFactory.create(queryStr);
        QueryExecution qexec = QueryExecutionFactory.sparqlService("http://query.wikidata.org/sparql", query);
        ResultSet results = qexec.execSelect();
        while(results.hasNext()) {
            QuerySolution binding = results.nextSolution();

            String datetime = binding.get("?time").toString();
            String date = datetime.split("T")[0];

            String namedata = binding.get("?itemLabel").toString();
            String name = namedata.split("@")[0];

            DataObject dataobj = new DataObject(name, Date.valueOf(date));
            dbcon.addData(dataobj);

        }
    }
}

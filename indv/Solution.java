import org.apache.jena.query.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


/**
 * Created by sand94 on 20.04.2016.
 */

public class Solution {
    public static void main(String[] args) {
        String queryString = "PREFIX entity: <http://www.wikidata.org/entity/> " +
                " SELECT ?predicate ?object " +
                " WHERE { entity:Q23 ?predicate ?object . } ";
        //" LIMIT 100 ";
        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
        ResultSet results = qexec.execSelect();
        while (results.hasNext()) {
            QuerySolution binding = results.nextSolution();
            //Datalari MySQL e atilacak...
            System.out.println(binding.toString());
        }

        //
        /*Connection conn = null;
        PreparedStatement pst = null;
        String url = "jdbc:mysql://localhost:3306/mysql";
        String user = "";
        String password = "";
        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection Done");
        }
        catch (SQLException ex){
            System.out.println("ERROR!!!");
        }
        finally {
            System.out.println("Sayounora...");
        }*/
    }
}


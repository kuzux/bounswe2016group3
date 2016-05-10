package com.bounswe2016group3.safa;

import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.sparql.resultset.JSONOutput;
import org.apache.jena.sparql.resultset.JSONOutputResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by sand94 on 10.05.2016.
 */
public class DataSPARQL extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ArrayList<ChessMaster> data = getData();
        Database.push(data);
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        try(PrintWriter writer = response.getWriter()) {
            writer.println("<!DOCTYPE html><html>");
            writer.println("<head>");
            writer.println("<meta charset=\"UTF-8\" />");
            writer.println("<title>Obey Your Master</title>");
            writer.println("</head>");
            writer.println("<body>");
            for(ChessMaster master : data) {
                writer.println("<p>");
                writer.print(master.toString());
                writer.println("</p>");
            }
            writer.println("</body>");
            writer.println("</html>");
        }
    }

    private ArrayList<ChessMaster> getData() {
        String queryString = "PREFIX wd: <http://www.wikidata.org/entity/>" +
                        "PREFIX p: <http://www.wikidata.org/prop/>" +
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>" +
                        "" +
                        "" +
                        "" +
                        "SELECT DISTINCT ?FullName ?Title ?ELO " +
                        "WHERE {" +
                        "  VALUES ?xpValue { wd:Q752000 wd:Q105269 wd:Q752119" +
                        "                  	wd:Q20036268 wd:Q3417060}" +
                        "  ?person ?predicate ?xpValue." +
                        "  ?person rdfs:label ?FullName ." +
                        "  ?xpValue rdfs:label ?Title ." +
                        "  OPTIONAL { ?person p:P1087 ?values ." +
                        "             ?values ?unused1 ?ELO ." +
                        "             FILTER(isLiteral(?ELO) && isNumeric(?ELO))" +
                        "           }" +
                        "             " +
                        "  FILTER(LANG(?FullName) = \"en\" &&" +
                        "         LANG(?Title) = \"en\" " +
                        "        )" +
                        "}";


        Query query = QueryFactory.create(queryString);
        QueryExecution qexec = QueryExecutionFactory.sparqlService("http://query.wikidata.org/sparql", query);
        ResultSet results = qexec.execSelect();

        ArrayList<ChessMaster> masters = new ArrayList<ChessMaster>();
        while(results.hasNext()){
            QuerySolution nope = results.next();
            ChessMaster chm = new ChessMaster(nope);
            masters.add(chm);
        }



        return masters;
    }
}


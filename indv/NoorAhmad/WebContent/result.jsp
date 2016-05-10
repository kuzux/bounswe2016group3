<%-- 
	Author: Noor Ahmad 
--%>

<%-- 
	imports jena classes library
--%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="org.apache.jena.query.Query" %>    
<%@ page import="org.apache.jena.query.QueryExecution" %>
<%@ page import="org.apache.jena.query.QueryExecutionFactory" %>
<%@ page import="org.apache.jena.query.QueryFactory" %>
<%@ page import="org.apache.jena.query.ResultSet" %>
<%@ page import="org.apache.jena.query.ResultSetFormatter" %>
<%@ page import="org.apache.jena.query.QuerySolution" %>

<%@ page import="org.apache.log4j.BasicConfigurator" %>
<%@ page import="org.apache.log4j.Logger" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Semantic Web</title>
</head>
<body>
 <%= "<h2>Result:</h2><br/>" %>
 <a href="index.jsp">Back to Main</a><br/>
 
 <%-- 
	assigns GET parameters to strings
--%>
 <%
 String lat1 = request.getParameter("lat_1");
String lat2 = request.getParameter("lat_2");
String long1 = request.getParameter("long_1");
String long2 = request.getParameter("long_2");
 %>
 <%
 
 // basic configuration for the log4j.
 BasicConfigurator.configure();
 
// SPARQL query which returns lists of stadiums given two latitudes and two longitudes.
 String s2 = "PREFIX  g:    <http://www.w3.org/2003/01/geo/wgs84_pos#>\n" +
         "PREFIX  rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
         "PREFIX  onto: <http://dbpedia.org/ontology/>\n" +
         "\n" +
         "SELECT  ?subject ?stadium ?lat ?long\n" +
         "WHERE\n" +
         "  { ?subject g:lat ?lat .\n" +
         "    ?subject g:long ?long .\n" +
         "    ?subject <http://www.w3.org/1999/02/22-rdf-syntax-ns#type> onto:Stadium .\n" +
         "    ?subject rdfs:label ?stadium\n" +
         "    FILTER ( ( ( ( ( ?lat >= "+lat1+" ) && ( ?lat <= "+lat2+" ) ) && ( ?long >= "+long1+" ) ) && ( ?long <= "+long2+" ) ) && ( lang(?stadium) = \"en\" ) )\n" +
         "  }\n" +
         "LIMIT   20\n" +
         "";
         // creates query using Jena
        Query query = QueryFactory.create(s2); //s2 = the query above
        // executes the query from dbpedia.org 
        QueryExecution qExe = QueryExecutionFactory.sparqlService( "http://dbpedia.org/sparql", query );
        // executes the results
        ResultSet results = qExe.execSelect();
        
        // loops for each results
        while(results.hasNext()){
            // prints the result     
        	out.println(results.next());
            // adds newline
        	out.println("<br/>");
        }
	
        
        %>
</body>
</html>
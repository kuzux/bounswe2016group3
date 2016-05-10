<%-- 
	Author: Noor Ahmad 
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Semantic Web</title>
</head>
<body>
 <%= "<h2>Welcome</h2><br/>" %>
 
<%-- 
	Takes two latitudes and two longitudes from the user.
	When users clicks the submit button form triggers the result.jsp page
--%>
 
 <form action="result.jsp" method="GET">
Latitude #1: <input type="text" name="lat_1">
<br />
Latitude #2: <input type="text" name="lat_2" />
<br />
Long #1: <input type="text" name="long_1">
<br />
Long #2: <input type="text" name="long_2">
<br />
<input type="submit" value="Submit" />
</form>
 
</body>
</html>
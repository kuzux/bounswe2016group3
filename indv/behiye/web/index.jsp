<%--
  Created by IntelliJ IDEA.
  User: Behiye
  Date: 5/10/2016
  Time: 2:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
  <jsp:include page="Behiye"/>

  <h1> Welcome to the query of singers who has twitter account</h1>
  <p> Please enter the first letter of singer's name and minimum birth year!</p>
  <!--Change action here-->
  <form action="ResultServlet" method="GET">
    <fieldset>
      <label for='name' >First Letter of His/Her Name*:</label>
      <br>
      <input type='text' name='name' id='name' maxlength="100" />
      <br>
      <label for='date' >Birth Date*:</label>
      <br>
      <input type='text' name='date' id='date' maxlength="100" />
      <br>
    </fieldset>
    <input type="submit" value="Search" />
  </form>

  </body>
</html>

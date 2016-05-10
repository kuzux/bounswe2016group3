<%--
  Created by IntelliJ IDEA.
  User: emreun
  Date: 5/9/16
  Time: 4:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:include page="/DataReceiver" />
<html>
  <head>
    <title>Academy Award Winners</title>
  </head>
  <body>
    <h2>Academy Awards Winner</h2>
    <p>Our DB contains academy awards winners, you can filter them with year</p>
    <p><a href="/CMPE352/MyData">Saved Records</a></p>
    <div>
        <form action="/CMPE352/ShowResults" method="get">
            <span>Date Interval: </span>
            <input type="text" name="minyear">
            -
            <input type="text" name="maxyear">
            <input type="submit" value="Filter">
        </form>
    </div>
  </body>
</html>

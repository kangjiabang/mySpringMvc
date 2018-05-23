<%--
  Created by IntelliJ IDEA.
  User: zeqi
  Date: 22/5/18
  Time: 20:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>My first Web</title>
  </head>
  <body>
  <%
    String name = (String) request.getAttribute("name");
  %>
  My first Web
  </body>
</html>

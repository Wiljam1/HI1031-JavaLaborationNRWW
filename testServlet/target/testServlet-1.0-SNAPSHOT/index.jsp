<%@ page import="java.util.Date" %>
<%@ page import="bo.UserHandler" %><%--
  Created by IntelliJ IDEA.
  User: Wilja
  Date: 9/28/2023
  Time: 7:57 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome Page</title>
</head>
<body>
<h1>Welcome to the web-shop</h1>
<a> Current user:
    <%= UserHandler.getCurrentUser() %>
</a>
<br>
<%= new Date() %>
<br><br>

<a href="items.jsp">Click here for items!</a>
</body>
</html>


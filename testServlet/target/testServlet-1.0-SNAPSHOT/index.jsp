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
<<<<<<< HEAD
<a> Current user:
    <%= UserHandler.getCurrentUser() %>
</a>
<br>
=======

>>>>>>> d713d03ddac0f3765adad912f111cc9852cc5a5e
<%= new Date() %>
<br><br>

<a href="items.jsp">Click here for items!</a>

<br><br>

<a href="login.jsp">Click here for login!</a>
</body>
</html>


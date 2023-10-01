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
    <% String username = (String) request.getAttribute("username");
        String displayUsername = "Not logged in";
        if(username != null)
            displayUsername = username;
    %>
    <%= displayUsername%>
</a>
<br>
<div id="currentDate">
    <%= new Date() %>
</div>
<br><br>

<a href="items.jsp">Click here for items!</a>

<br><br>

<a href="login.jsp">Click here for login!</a>
</body>
</html>
<script type="text/javascript">
    function updateDate() {
        var currentDateElement = document.getElementById("currentDate");
        var currentDate = new Date();
        currentDateElement.innerHTML = currentDate;
    }

    // Call the updateDate function every second (1000 milliseconds)
    setInterval(updateDate, 1000);
</script>


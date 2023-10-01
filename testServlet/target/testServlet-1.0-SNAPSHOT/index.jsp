<%@ page import="java.util.Date" %>
<%@ page import="bo.UserHandler" %>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
            displayUsername = UserHandler.getUserInfo(username).getName();
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
        currentDateElement.innerHTML = new Date();
    }
    setInterval(updateDate, 1000);
</script>


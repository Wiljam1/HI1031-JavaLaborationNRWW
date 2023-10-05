<%@ page import="java.util.Date" %>
<%@ page import="bo.UserHandler" %>
<%@ page import="ui.UserInfo" %>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Welcome Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            text-align: center;
        }

        h1 {
            color: #333;
        }

        a {
            display: inline-block;
            padding: 10px 20px;
            background-color: #007bff; /* Blue color */
            color: #fff; /* Text color */
            text-decoration: none; /* Remove underlines */
            border: none; /* Remove borders */
            border-radius: 5px; /* Rounded corners */
            cursor: pointer;
            transition: background-color 0.3s ease; /* Smooth transition on hover */
        }

        a:hover {
            background-color: #0056b3; /* Darker blue on hover */
        }

        #current-user {
            font-weight: bold;
            margin: 10px 0;
        }

        #currentDate {
            font-style: italic;
            margin: 10px 0;
        }

        #links {
            margin-top: 20px;
        }
    </style>
</head>
<body>
<h1>Welcome to the web-shop</h1>
<div id="current-user">
    <%
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        String nameString;
        if (userInfo == null) {
            nameString = "Not logged in";
        } else {
            nameString = "Current user: " + userInfo.getName();
        }
    %>
    <%=nameString%>
</div>
<div id="authorization">
    <%
        String authString;
        String authLevel = "";
        if (userInfo == null) {
            authString = "";
        } else {
            authLevel = userInfo.getAuthorizationLevel();
            authString = "Authorization: " + authLevel;
        }
    %>
    <%=authString%>
</div>


<div id="currentDate">

</div>

<div id="links">
    <a href="items.jsp">Browse items</a>
    <br><br>
    <a href="login">Login</a>
    <%
        if(userInfo != null) {
    %>
    <br><br>
    <a href="userOrders.jsp">Your orders</a>
    <%
        }
        // TODO: Move logic to a servlet?
        if("admin".equals(authLevel) || "staff".equals(authLevel)) {
    %>
    <br><br>
    <a href="allOrders">All orders</a>
    <%
        }
    %>
</div>

<script type="text/javascript">
    //Ful Javascript för att få en tickande klocka
    function updateDate() {
        var currentDateElement = document.getElementById("currentDate");
        currentDateElement.innerHTML = new Date();
    }

    let set = false;
    if(!set) {
        setInterval(updateDate, 1);
        set = true;
    } else
        setInterval(updateDate, 1000);
</script>
</body>
</html>


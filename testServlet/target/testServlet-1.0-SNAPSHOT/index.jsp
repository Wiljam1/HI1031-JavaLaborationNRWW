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
            background-color: #007bff;
            color: #fff;
            text-decoration: none;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        a:hover {
            background-color: #0056b3;
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
        // nameString be set in doGet() in IndexServlet but couldn't get mappings to work on server startup.
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
            authLevel = userInfo.getAuthorization();
            authString = "Authorization: " + authLevel;
        }
    %>
    <%=authString%>
</div>
<div id="currentDate">
</div>
<div id="links">
    <a href="${pageContext.request.contextPath}/items">Browse items</a>
    <br><br>
    <a href="login">Login</a>
    <%
        if(userInfo != null) {
    %>
    <br><br>
    <a href="userOrders.jsp">Your orders</a>
    <%
        }
        if("admin".equals(authLevel) || "staff".equals(authLevel)) {
    %>
    <br><br>
    <a href="${pageContext.request.contextPath}/allOrders">All orders</a>

    <%
        }
        if ("admin".equals(authLevel)){
            %>
    <br><br>
    <a href="${pageContext.request.contextPath}/allUsers">All Users</a>
    <%
    }
    %>
</div>

<script type="text/javascript">
    //Unnecessary Javascript for clock
    function updateDate() {
        let currentDateElement = document.getElementById("currentDate");
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


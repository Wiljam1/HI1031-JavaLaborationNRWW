<%@ page import="java.util.Date" %>
<%@ page import="bo.UserHandler" %>

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
            color: #007bff;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
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
    Current user:
    <%= session.getAttribute("displayUsername")%>
</div>
<div id="authorization">
    Authorization:
    <%= session.getAttribute("authorization")%>
</div>


<div id="currentDate">

</div>

<div id="links">
    <a href="items.jsp">Click here for items!</a>
    <br><br>
    <a href="login.jsp">Click here for login!</a>
</div>

<script type="text/javascript">
    //Ful Javascript för att få en tickande klocka
    function updateDate() {
        var currentDateElement = document.getElementById("currentDate");
        currentDateElement.innerHTML = new Date();
    }
    var set = false;
    if(!set) {
        setInterval(updateDate, 1);
        set = true;
    } else
        setInterval(updateDate, 1000);
</script>
</body>
</html>


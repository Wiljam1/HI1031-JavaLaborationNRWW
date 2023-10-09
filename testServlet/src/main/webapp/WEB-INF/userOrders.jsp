<%@ page import="java.util.Collection" %>
<%@ page import="ui.ItemInfo" %>
<%@ page import="bo.UserHandler" %>
<%@ page import="ui.OrderInfo" %>
<%@ page import="ui.UserInfo" %>
<%@ page import="java.util.Collections" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My orders</title>
    <style>
        body, h1, h2, h3, p, ul, li {
            margin: 0;
            padding: 0;
        }

        body {
            font-family: Arial, Helvetica, sans-serif;
            background-color: #f2f2f2;
        }

        .container {
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            margin: 20px auto;
            max-width: 800px;
        }

        header {
            background-color: #333;
            color: #fff;
            padding: 10px;
            text-align: center;
        }

        header h1 {
            font-size: 24px;
        }

        nav {
            background-color: #444;
            text-align: center;
            padding: 10px;
        }

        nav a {
            color: #fff;
            text-decoration: none;
            padding: 5px 10px;
        }

        nav a:hover {
            background-color: #555;
        }

        #items-container {
            margin-top: 20px;
        }

        h2 {
            font-size: 20px;
            margin-bottom: 10px;
        }

        ul {
            list-style: none;
        }

        li {
            margin-bottom: 20px;
            padding: 10px;
            border: 2px solid #ddd;
            border-radius: 5px;
        }

        h3 {
            font-size: 18px;
        }

        form {
            display: inline-block;
        }
        a.home-button {
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
        a.home-button:hover {
            background-color: #0056b3;
        }
        .add-button {
            text-align: right;
            margin-top: 10px;
            margin-left: 1000%;
        }

        .add-button button {
            background-color: #fa2626;
            color: #fff;
            border: none;
            padding: 5px 10px;
            border-radius: 3px;
            cursor: pointer;
        }

        .add-button button:hover {
            background-color: #a61b1b;
        }

        p {
            margin-top: 10px;
            font-size: 14px;
        }

        ul ul {
            margin-top: 10px;
            padding-left: 20px;
        }

        ul ul li {
            margin-bottom: 2px;
        }

        b {
            font-weight: bold;
        }

        #items-container {
            margin-top: 20px;
            border: 3px solid #4cb4ff;
            padding: 10px;
            border-radius: 5px;
            cursor: pointer;
        }
    </style>
</head>
<body>
<header>
    <h1>My orders</h1>
</header>
<nav>
    <a class="home-button" href="${pageContext.request.contextPath}/index.jsp">Home</a>
</nav>
<div class="container">
    <div id="items-container">
        <%
            session = request.getSession();
            UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
            String username;
            String name = "null";
            Collection<OrderInfo> orders = Collections.emptyList();
            if(userInfo != null) {
                username = userInfo.getUsername();
                name = userInfo.getName();
                orders = (Collection<OrderInfo>) session.getAttribute("getUserOrders"); //Update orders

                if(orders == null)
                    orders = Collections.emptyList();
            }
        %>
        <h2><%=name%>'s orders</h2>
        <ul>
            <%
                for (OrderInfo order : orders) {
        %>
        <li>
            <h3>Order ID: <%=order.getId()%></h3>
            <p>Date: <%=order.getDate()%></p>
            <p>Total Cost: <%=order.getTotalCost()%></p>
            <p>Status: <%=order.getStatus()%></p>

            <ul>
                <% for (ItemInfo item : order.getItems()) { %>
                <li>
                    <b>Product:</b> <%=item.getName()%> -
                    <b>Description:</b> <%=item.getDesc()%> -
                    <b>Price per unit:</b> <%=item.getPrice()%> -
                    <b>Quantity:</b> <%=item.getQuantity()%>
                </li>
                <% } %>
            </ul>
        </li>
        <%
            }
            %>
        </ul>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
    <% Boolean transactionSuccess = (Boolean) request.getAttribute("transactionSuccess"); %>
    <% String transactionMessage = (String) request.getAttribute("transactionMessage"); %>

    <% if (transactionSuccess != null && transactionSuccess) { %>
    alert("<%= transactionMessage %>");
    <% } %>
</script>

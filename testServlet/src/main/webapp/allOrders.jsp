<%@ page import="java.util.Collection" %>
<%@ page import="ui.ItemInfo" %>
<%@ page import="bo.ItemHandler" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="bo.UserHandler" %>
<%@ page import="bo.Order" %>
<%@ page import="ui.OrderInfo" %>
<%@ page import="ui.UserInfo" %>
<%@ page import="java.util.Collections" %>
<%@ page import="db.Authorization" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All orders</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 0;
            padding: 0;
        }

        header {
            background-color: #333;
            color: #fff;
            padding: 10px;
            text-align: center;
        }

        nav {
            background-color: #444;
            color: #fff;
            padding: 10px;
            text-align: center;
        }

        .container {
            max-width: 1000px;
            margin: 0 auto;
            padding: 20px;
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
        }

        #items-container {
            flex-basis: 68%;
            background-color: #fff;
            padding: 20px;
            border: 1px solid #ccc;
            margin-bottom: 20px;
        }

        ul {
            list-style: none;
            padding: 0;
        }

        li {
            margin: 5px 0;
            border: 1px solid #ccc; /* Add a border around each li */
            padding: 10px; /* Add padding for spacing */
        }

        #items h2 {
            text-align: center;
        }

        ul {
            list-style: none;
            padding: 0;
        }

        li {
            margin: 5px 0;
        }

        form {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .item-info {
            flex-basis: 70%;
        }

        .add-button {
            flex-basis: auto;
            text-align: right;
            margin-left: auto;
        }

        button[type="submit"] {
            background-color: #ff0008;
            color: #fff;
            border: none;
            padding: 5px 10px;
            cursor: pointer;
        }

        button[type="submit"]:hover {
            background-color: #b3003f;
        }

        a.checkout-button {
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

        a.checkout-button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<header>
    <h1>All orders</h1>
</header>
<nav>
    <a class="checkout-button" href="index.jsp">Home</a>
</nav>
<div class="container">
    <%
        session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        Collection<UserInfo> users = UserHandler.getAllUsers();
        for(UserInfo user : users) {
            //Check if user has orders
            Collection<OrderInfo> orders = user.getOrders();
            if(orders != null) {
    %>
    <div id="items-container">
        <%
            //display users orders
            String name = user.getName();

        %>
        <h2><%=name%>'s orders</h2>
        <ul>
            <%
                for (OrderInfo order : orders) {
            %>
            <li>
                <h3>Order ID: <%=order.getId()%>
                <form action="transaction" method="post">
                    <!-- Hidden input field to store the item's attributes -->
                    <input type="hidden" name="transaction" value="<%="deleteOrder"%>">
                    <input type="hidden" name="transactionId" value="<%=order.getId()%>">
                    <input type="hidden" name="username" value="<%=user.getUsername()%>">

                    <div class="add-button">
                        <!-- Submit button to add the item to the cart -->
                        <button type="submit">Delete</button>
                    </div>
                </form></h3>
                <p>Date: <%=order.getDate()%></p>
                <p>Total Cost: <%=order.getTotalCost()%></p>
<%--                <p>Assigned Staff: <%=order.getAssignedStaff()%></p>--%>

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
                    }
            %>
        </ul>
    </div>
    <%
        }
    %>
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

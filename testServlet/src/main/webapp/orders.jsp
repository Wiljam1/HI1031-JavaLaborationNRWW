<%@ page import="java.util.Collection" %>
<%@ page import="ui.ItemInfo" %>
<%@ page import="bo.ItemHandler" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="bo.UserHandler" %>
<%@ page import="bo.Order" %>
<%@ page import="ui.OrderInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>My orders</title>
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
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
            display: flex;
            flex-wrap: wrap;
            justify-content: space-between;
        }
        #items-container {
            flex-basis: 68%; /* Adjust the width as needed */
            background-color: #fff;
            padding: 20px;
            border: 1px solid #ccc;
            margin-bottom: 20px;
        }
        #shopping-cart-container {
            flex-basis: 30%;
            background-color: #fff;
            padding: 20px;
            border: 1px solid #ccc;
            margin-bottom: 20px;
        }
        #items h2 {
            text-align: center; /* Center-align the "Available Items" heading */
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
            flex-basis: 20%;
            text-align: right;
        }
        button[type="submit"] {
            background-color: #007bff;
            color: #fff;
            border: none;
            padding: 5px 10px;
            cursor: pointer;
        }
        button[type="submit"]:hover {
            background-color: #0056b3;
        }
        a.checkout-button {
            display: inline-block;
            padding: 10px 20px;
            background-color: #007bff; /* Blue color */
            color: #fff; /* Text color */
            text-decoration: none; /* Remove underlines */
            border: none; /* Remove borders */
            border-radius: 5px; /* Rounded corners */
            cursor: pointer;
            transition: background-color 0.3s ease; /* Smooth transition on hover */

            /* Optional: Add hover effect */
        }
        a.checkout-button:hover {
            background-color: #0056b3; /* Darker blue on hover */
        }
    </style>
</head>
<body>
<header>
    <h1>My orders</h1>
</header>
<nav>
    <a class="checkout-button" href="index.jsp">Home</a>
</nav>
<div class="container">
    <div id="items-container">
        <%
            session = request.getSession();
            String username = (String) session.getAttribute("username");
            String name = (String) session.getAttribute("displayUsername");
            Collection<OrderInfo> orders = UserHandler.getOrders(username);
        %>
        <h2><%=name%>'s orders</h2>
        <ul>
            <%
                if (orders != null) {
                    for (OrderInfo order : orders) {
            %>
            <li>
                <h3>Order ID: <%= order.getId() %></h3>
                <p>Date: <%= order.getDate() %></p>
                <p>Total Cost: <%= order.getTotalCost() %></p>
                <p>Assigned Staff: <%= order.getAssignedStaff() %></p>

                <ul>
                    <% for (ItemInfo item : order.getItems()) { %>
                    <li>
                        <b>Product:</b> <%= item.getName() %> -
                        <b>Description:</b> <%= item.getDesc() %> -
                        <b>Price:</b> <%= item.getPrice() %> -
                        <b>In stock:</b> <%= item.getAmount() %>
                    </li>
                    <% } %>
                </ul>
            </li>
            <%
                }
            } else {
            %>
            <li>No orders found for <%= name %></li>
            <%
                }
            %>
        </ul>
    </div>
    <div id="shopping-cart-container">
        <h2>Shopping Cart for <%=name%></h2>
        <ul>
            <%
                //TODO: Hantera fallet då man inte är inloggad men ändå vill kolla på varor (dölj shopping cart)
                if (username != null) {
                    Collection<ItemInfo> cartItems = (Collection<ItemInfo>) session.getAttribute("items");
                    int price = 0;
                    if (cartItems != null) {
                        //TODO: Price kanske kan beräknas i ett business object (logik)
                        for (ItemInfo item : cartItems) {
                            price += item.getPrice() * item.getQuantity();
            %>
            <li><b>Product:</b> <%= item.getName() %> - <b>Quantity:</b> <%= item.getQuantity() %></li>
            <%
                    }
                }
            %>
        </ul>
        <p><b>Total Price:</b> <%= price %></p>
        <%
                session.setAttribute("finalPrice", price);
            }
        %>
        <a href="checkout.jsp" class="checkout-button">Checkout</a>
    </div>
</div>
</body>
</html>
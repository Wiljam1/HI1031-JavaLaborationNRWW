<%@ page import="ui.ItemInfo" %>
<%@ page import="java.util.Collection" %><%--
  Created by IntelliJ IDEA.
  User: Niklas
  Date: 2023-10-02
  Time: 09:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping Cart</title>
    <style>
        body {
            font-family: "Arial", sans-serif;
            background-color: #f2f2f2;
            text-align: center;
            margin: 0;
            padding: 0;
        }

        h1 {
            color: #333;
            margin-top: 20px;
            font-size: 36px;
        }

        ul {
            list-style-type: none;
            padding: 0;
            margin: 20px auto;
            max-width: 800px;
        }

        li {
            background-color: #fff;
            padding: 15px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 5px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            font-size: 18px;
        }

        p {
            font-weight: bold;
            font-size: 24px;
            margin: 20px;
        }

        .purchase-button {
            margin: 20px;
        }

        button {
            background-color: #007bff;
            color: #fff;
            padding: 12px 24px;
            border: none;
            border-radius: 5px;
            font-size: 20px;
            cursor: pointer;
        }

        button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<h1>Shopping Cart</h1>
<ul>
    <%
        session = request.getSession();
        String username = (String) session.getAttribute("username");
        if (username != null) {
            Collection<ItemInfo> cartItems = (Collection<ItemInfo>) session.getAttribute("items");
            if (cartItems != null) {
                for (ItemInfo item : cartItems) {
    %>
    <li><%= item.getName() %> : <%= item.getQuantity() %></li>
    <%
                }
            }
        }
    %>
</ul>
<p>Total Price: $50.00</p>
<div class="purchase-button">
    <!-- Submit button to add the item to the cart -->
    <button type="submit">Purchase</button>
</div>
</body>
</html>
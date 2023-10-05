<%@ page import="java.util.Collection" %>
<%@ page import="ui.ItemInfo" %>
<%@ page import="bo.ItemHandler" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="bo.UserHandler" %>
<%@ page import="ui.UserInfo" %>
<%@ page import="java.util.Objects" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Shopping Page</title>
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

        }
        a.checkout-button:hover {
            background-color: #0056b3; /* Darker blue on hover */
        }
        a.new-item-button {
            display: inline-block;
            padding: 10px 20px;
            background-color: #3c8a29; /* Blue color */
            color: #fff; /* Text color */
            text-decoration: none; /* Remove underlines */
            border: none; /* Remove borders */
            border-radius: 5px; /* Rounded corners */
            cursor: pointer;
            transition: background-color 0.3s ease; /* Smooth transition on hover */

        }
        a.new-item-button:hover {
            background-color: #59e13f; /* Darker blue on hover */
        }
        button[type="submit"][value="edit"] {
            background-color: orange;
            color: white;
        }
        button[type="submit"][value="edit"]:hover {
            background-color: darkorange;
        }
    </style>
</head>
<body>
<header>
    <h1>Shopping Page</h1>
</header>
<nav>
    <a class="checkout-button" href="index.jsp">Home</a>
    <%
        session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        // Kanske borde flytta denna logik till en servlet
        if(userInfo != null) {
            String authLevel = userInfo.getAuthorizationLevel();
            if(authLevel.equals("admin")) {
    %>
    <a class="new-item-button" href="addItem.jsp">Add new item</a>
    <%
            }
        }
    %>
</nav>
<div class="container">
    <div id="items-container">
        <h2>Available Items</h2>
        <ul>
            <%
                // TODO: Gör add-knapparna gråa + disabled om man inte är inloggad
                Collection<ItemInfo> items = ItemHandler.getItemsWithGroup(" ");
                for (ItemInfo item : items) {
            %>
            <form action="addItem" method="post">
                <!-- Hidden input field to store the item's attributes -->
                <% //TODO: Fixa så att man kan skicka hela item typ?%>
                <input type="hidden" name="itemId" value="<%= item.getId() %>">
                <input type="hidden" name="itemName" value="<%= item.getName() %>">
                <input type="hidden" name="itemDesc" value="<%= item.getDesc() %>">
<%--                <input type="hidden" name="itemQuantity" value="<%= item.getQuantity() %>">--%>
                <input type="hidden" name="itemAmount" value="<%= item.getAmount() %>">
                <input type="hidden" name="itemPrice" value="<%= item.getPrice() %>">


                <div class="item-info">
                    <!-- Display item name and description -->
                    <span><b>Product:</b> <%= item.getName() %> - <b>Description:</b> <%= item.getDesc() %>
                            - <b>Price:</b> <%= item.getPrice() %> - <b>In stock: <%=item.getAmount()%></b></span>
                </div>

                <div class="add-button">
                    <!-- Submit button to add the item to the cart -->
                    <%
                        if(userInfo != null) {
                    %>
                    <button type="submit" name="action" value="addToCart">Add to Cart</button>
                    <%
                        String authLevel = userInfo.getAuthorizationLevel();
                            if(authLevel.equals("admin")) {
                    %>
                    <button type="submit" name="action" value="sendToEdit">Edit</button>
                    <%
                            }
                        }
                    %>
                </div>
            </form>
            <%
                }
            %>
        </ul>
    </div>
        <%
            //TODO: Flytta logik till en servlet/Business object
            String username;
            String name;
            if(userInfo != null) {
                username = userInfo.getUsername();
                name = userInfo.getName();
        %>
    <div id="shopping-cart-container">
        <h2>Shopping Cart for <%=name%></h2>
        <ul>
            <%
                if (!Objects.equals(username, "null")) {
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
    <%
        }
    %>
</div>
</body>
</html>
<script type="text/javascript">
    <% Boolean transactionSuccess = (Boolean) request.getAttribute("transactionSuccess"); %>
    <% String transactionMessage = (String) request.getAttribute("transactionMessage"); %>

    <% if (transactionSuccess != null && !transactionSuccess) { %>
    alert("<%= transactionMessage %>");
    <% } %>
    function setAction(actionValue) {
        document.getElementById("action")[0].value = actionValue;
    }
</script>

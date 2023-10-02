<%@ page import="java.util.Collection" %>
<%@ page import="ui.ItemInfo" %>
<%@ page import="bo.ItemHandler" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="bo.UserHandler" %>
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
    </style>
</head>
<body>
<header>
    <h1>Shopping Page</h1>
</header>
<nav>
    <a href="index.jsp">Home</a>
</nav>
<div class="container">
    <div id="items-container">
        <h2>Available Items</h2>
        <ul>
            <%
                // TODO: Gör add-knapparna gråa + disabled om man inte är inloggad
                Collection<ItemInfo> items = ItemHandler.getItemsWithGroup(" ");
                for (ItemInfo item : items) {
                    String itemLink = "addToCart.jsp?itemName=" + item.getName();
            %>
            <form action="addItem" method="post">
                <!-- Hidden input field to store the item's ID -->
                <% //TODO: Fixa så att man kan skicka hela item typ?%>
                <input type="hidden" name="itemName" value="<%= item.getName() %>">
                <input type="hidden" name="itemPrice" value="<%= item.getPrice() %>">

                <div class="item-info">
                    <!-- Display item name and description -->
                    <span><b>Product:</b> <%= item.getName() %> - <b>Description:</b> <%= item.getDesc() %>
                            - <b>Price:</b> <%= item.getPrice() %></span>
                </div>

                <div class="add-button">
                    <!-- Submit button to add the item to the cart -->
                    <button type="submit">Add</button>
                </div>
            </form>
            <%
                }
            %>
        </ul>
    </div>
    <div id="shopping-cart-container">
        <%
            session = request.getSession();
            String username = (String) session.getAttribute("username");
            String name = (String) session.getAttribute("displayUsername");
        %>
        <h2>Shopping Cart for <%=name%></h2>
        <ul>
            <%
                if (username != null) {
                    Collection<ItemInfo> cartItems = (Collection<ItemInfo>) session.getAttribute("items");
                    if (cartItems != null) {
                        int price = 0;
                        for (ItemInfo item : cartItems) {
                            price += item.getPrice() * item.getQuantity();
            %>
            <li><b>Product:</b> <%= item.getName() %> - <b>Quantity:</b> <%= item.getQuantity() %></li>
            <%
                        }
            %>
        </ul>
        <p>Total Price: <%= price %></p>
        <%
                    //Sparar priset när man går till checkout
                    //TODO: Fixa så den tas emot och används i checkout
                    session.setAttribute("finalPrice", price);
                }
            }
                //TODO: Gör om <a>-checkout-grejen till en form och gör en servlet
        %>
        <a href="checkout.jsp">Checkout</a>
    </div>
</div>
</body>
</html>

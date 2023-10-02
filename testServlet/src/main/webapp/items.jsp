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
                <input type="hidden" name="itemName" value="<%= item.getName() %>">

                <div class="item-info">
                    <!-- Display item name and description -->
                    <span><%= item.getName() %> : <%= item.getDesc() %></span>
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
        <h2>Shopping Cart</h2>
        <ul>
            <%
                session = request.getSession();
                String username = (String) session.getAttribute("username");
                if (username != null) {
                    Collection<ItemInfo> cartItems = (Collection<ItemInfo>) session.getAttribute("items");
                    if (cartItems != null) {
                        int price = 0;
                        for (ItemInfo item : cartItems) {
                            // TODO: Fixa så den tar riktiga priset istället för hashcode() % 12
                            price += item.getName().hashCode() % 12 * item.getQuantity();

            %>
            <li><%= item.getName() %> : <%= item.getQuantity() %></li>
        </ul>
                    <% } %>
        <p>Total Price: <%= price %></p>
        <%
                        //Sparar priset när man går till checkout
                    //TODO: Fixa så den tas emot och används i checkout
                    session.setAttribute("finalPrice", price);
                }
            }
        %>
        <a href="checkout.jsp">Checkout</a>
    </div>
</div>
</body>
</html>

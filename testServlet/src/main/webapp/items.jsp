<%@ page import="java.util.Collection" %>
<%@ page import="ui.ItemInfo" %>
<%@ page import="bo.ItemHandler" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="bo.UserHandler" %><%--
  Created by IntelliJ IDEA.
  User: Wilja
  Date: 9/30/2023
  Time: 1:15 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="index.jsp">Home</a>
<br><br>
<div id="shopping-cart">
    <h2>Shopping Cart</h2>
    <ul>
        <%
            session = request.getSession();
            String username = (String) session.getAttribute("username");
            if(username != null) {
                Collection<ItemInfo> cartItems = (Collection<ItemInfo>) session.getAttribute("items");
                if(cartItems != null)
                    for(ItemInfo item : cartItems) {
        %>
                        <li><%=item.getName()%> : <%=item.getQuantity()%>></li>
        <%
                }
            }
        %>
    </ul>
    <p>Total Price: $50.00</p>
    <a href="checkout.jsp">Checkout</a>
</div>

<div id="items">
    <h1>Here are the items; </h1>
    <ul>
        <%
            Collection<ItemInfo> items = ItemHandler.getItemsWithGroup(" ");
            for (ItemInfo item : items) {
                String itemLink = "addToCart.jsp?itemName=" + item.getName();
        %>
        <form action="addItem" method="post">
            <!-- Hidden input field to store the item's ID -->
            <input type="hidden" name="itemName" value="<%= item.getName() %>">

            <!-- Display item name and description -->
            <span><%= item.getName() %> : <%= item.getDesc() %></span>
    </span>

            <!-- Submit button to add the item to the cart -->
            <button type="submit">Add</button>
        </form>

        <%
            }
        %>
    </ul>
</div>

</body>
</html>
<style>
    #shopping-cart {
        float: right;
        margin-right: 20px; /* Adjust the margin as needed */
        border: 1px solid #ccc; /* Optional: Add a border to the cart */
        padding: 10px; /* Optional: Add padding for spacing */
        background-color: #f9f9f9; /* Optional: Set background color */
    }
    #items {
        display: flex;
        justify-content: center;
        align-items: center;
        margin-right: 20px; /* Adjust the margin as needed */
        border: 1px solid #ccc; /* Optional: Add a border to the cart */
        padding: 10px; /* Optional: Add padding for spacing */
        background-color: #f9f9f9; /* Optional: Set background color */
    }
</style>

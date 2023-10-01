<%@ page import="java.util.Collection" %>
<%@ page import="ui.ItemInfo" %>
<%@ page import="bo.ItemHandler" %>
<%@ page import="java.util.Iterator" %><%--
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
<h1>Here are the items; </h1>
<ul>
    <%
        // Retrieve items using ItemHandler (replace this with your actual logic)
        Collection<ItemInfo> items = ItemHandler.getItemsWithGroup(" ");
        for (ItemInfo item : items) {
            // Generate a unique link for each item (e.g., "addToCart.jsp?itemId=<%= item.getName() %>")
    String itemLink = "addToCart.jsp?itemId=" + item.getId(); // Adjust this URL pattern
    %>
    <li>
        <!-- Display item name and description -->
        <span><%= item.getName() %>: <%= item.getDesc() %></span>

        <!-- Counter with "+" and "-" buttons -->
        <span>
            <button onclick="decrementCounter(this)">-</button>
            <span id="counter">0</span>
            <button onclick="incrementCounter(this)">+</button>
        </span>

        <!-- "Add" button as a link -->
        <a href="items.jsp">Add</a>
    </li>
    <%
        }
    %>
</ul>


</body>
</html>
<script type="text/javascript">
    // JavaScript functions to increment and decrement the counter
    function incrementCounter(button) {
        var counterElement = button.parentElement.querySelector('#counter');
        var currentCount = parseInt(counterElement.innerText);
        counterElement.innerText = currentCount + 1;
    }

    function decrementCounter(button) {
        var counterElement = button.parentElement.querySelector('#counter');
        var currentCount = parseInt(counterElement.innerText);
        if (currentCount > 0) {
            counterElement.innerText = currentCount - 1;
        }
    }
</script>
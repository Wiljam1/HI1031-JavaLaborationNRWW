<%@ page import="ui.ItemInfo" %>
<%@ page import="bo.ItemHandler" %>
<%@ page import="bo.UserHandler" %>
<%@ page import="ui.UserInfo" %>
<%@ page import="java.util.*" %>
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
            flex-basis: 85%; /* Adjust the width as needed */
            background-color: #fff;
            padding: 20px;
            border: 1px solid #ccc;
            margin-bottom: 20px;
        }
        #shopping-cart-container {
            flex-basis: 40%;
            background-color: #fff;
            padding: 20px;
            border: 1px solid #ccc;
            margin-bottom: 20px;
        }
        #shopping-cart-container ul li {
            margin-bottom: 10px;
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
            flex-basis: 80%;
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
        button[type="submit"][value="sendToEdit"] {
            background-color: orange;
            color: white;
        }

        button[type="submit"][value="sendToEdit"]:hover {
            background-color: darkorange;
        }
        form.remove-button {
            display: inline;
            float: right;
            margin-left: -70px;
        }

        form.remove-button button[type="submit"] {
            background-color: #d9534f;
            color: #fff;
            border: none;
            padding: 5px;
            cursor: pointer;
        }

        form.remove-button button[type="submit"]:hover {
            background-color: #c9302c;
        }
        #filter-container {
            float: right; /* Float the filter-container to the right */
            width: 15%; /* Adjust the width as needed */
            background-color: #ffffff;
            border: 1px solid #ddd;
            padding: 10px;
            box-shadow: 0 0 5px rgba(0, 0, 0, 0.1);
            margin: 10px 10px 10px -300px;
        }

        #filter-container h3 {
            font-size: 18px;
            margin-bottom: 10px;
        }

        #filter-container form {
            margin-top: 10px;
        }

        #filter-container label {
            display: block;
            margin-bottom: 5px;
        }

        #filter-container select {
            width: 100%;
            padding: 5px;
            border: 1px solid #ccc;
            border-radius: 3px;
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
<div id="filter-container">
    <h3>Filter</h3>
    <form action="addItem" method="post">
        <input type="hidden" name="action" value="setFilter">

        <label for="category">Choose category:</label>
        <select name="selectedCategory" id="category">
            <%
                String filter = (String) session.getAttribute("filter");
                Collection<String> categories = new ArrayList<>();
                categories.add("All items");
                categories.addAll(ItemHandler.getCategories());

                for (String category : categories) {
                    boolean isSelected = (filter == null && "All items".equals(category)) ||
                            (filter != null && filter.equals(category));
            %>
            <option value="<%=category%>" <%= isSelected ? "selected" : "" %>><%=category%></option>
            <%
                }
            %>
        </select>
        <button class="checkout-button" type="submit" name="action" value="setFilter">Submit filter</button>
    </form>
</div>
<div class="container">
    <div id="items-container">
        <h2>Available Items</h2>
        <ul>
            <%
                //Flytta logik till servlet?

                Collection<ItemInfo> items = ItemHandler.getItemsWithGroup();

                if(filter != null && !filter.equals("All items")) //hardcoded
                    items = ItemHandler.getItemsWithGroup(filter);

                for (ItemInfo item : items) {
            %>
            <form action="addItem" method="post">
                <% //TODO: Fixa så att man kan skicka hela item typ?%>
                <input type="hidden" name="itemId" value="<%= item.getId() %>">
                <input type="hidden" name="itemName" value="<%= item.getName() %>">
                <input type="hidden" name="itemDesc" value="<%= item.getDesc() %>">
<%--                <input type="hidden" name="itemQuantity" value="<%= item.getQuantity() %>">--%>
                <input type="hidden" name="itemAmount" value="<%= item.getAmount() %>">
                <input type="hidden" name="itemPrice" value="<%= item.getPrice() %>">
                <input type="hidden" name="itemCategory" value="<%= item.getCategory() %>">


                <div class="item-info">
                    <table>
                        <tr class="info-row">
                            <td><b>Product:</b></td><td><%= item.getName() %></td>
                            <td><b>Description:</b></td><td><%= item.getDesc() %></td>
                            <td><b>Price:</b></td><td><%= item.getPrice() %></td>
                            <td><b>In stock:</b></td><td><%= item.getAmount() %></td>
                            <td><b>Category:</b></td><td><%= item.getCategory() %></td>
                        </tr>
                    </table>
                </div>

                <div class="add-button">
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
            <li><b>Product:</b> <%= item.getName() %> - <b>Quantity:</b> <%= item.getQuantity() %> - <b>Cost:</b> <%=item.getPrice()*item.getQuantity()%>
                <form class="remove-button" action="addItem" method="post">
                    <input type="hidden" name="action" value="removeFromCart">
                    <input type="hidden" name="itemIdToRemove" value="<%= item.getId() %>">
                    <button type="submit">Remove</button>
                </form>
            </li>
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
    function submitForm() {
        document.getElementById("filter-form").submit();
    }
</script>

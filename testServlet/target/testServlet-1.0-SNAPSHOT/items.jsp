<%@ page import="ui.ItemInfo" %>
<%@ page import="bo.ItemHandler" %>
<%@ page import="bo.UserHandler" %>
<%@ page import="ui.UserInfo" %>
<%@ page import="java.util.*" %>
<%@ page import="db.Authorization" %>
<%@ page import="bo.CartHandler" %>
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
            flex-basis: 85%;
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
            background-color: #2c80fd;
            color: #fff;
            text-decoration: none;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;

        }
        a.checkout-button:hover {
            background-color: #6b9dff;
        }

        a.new-item-button {
            display: inline-block;
            padding: 10px 20px;
            background-color: #3c8a29;
            color: #fff;
            text-decoration: none;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;

        }
        a.new-item-button:hover {
            background-color: #59e13f;
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
            float: right;
            width: 15%;
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
        String authLevel = (String) session.getAttribute("authLevel");

        if(authLevel != null && authLevel.equals(Authorization.ADMIN.toString())) {
    %>
    <a class="new-item-button" href="${pageContext.request.contextPath}/addItem">Add new item</a>
    <%
        }
    %>
</nav>
<div id="filter-container">
    <h3>Filter</h3>
    <form action="items" method="post">
        <input type="hidden" name="action" value="setFilter">

        <label for="category">Choose category:</label>
        <select name="selectedCategory" id="category">
            <%
                String filter = (String) session.getAttribute("filter");
                Collection<String> categories = new ArrayList<>();
                categories.add("All items");
                categories.addAll((Collection<? extends String>) session.getAttribute("allItemCategories"));

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
                Collection<ItemInfo> items = (Collection<ItemInfo>) session.getAttribute("getAllItemsWithoutGroup");

                if(filter != null && !filter.equals("All items"))
                    items = (Collection<ItemInfo>) session.getAttribute("getAllItemsWithGroup");      //This one might be hard to move

                for (ItemInfo item : items) {
            %>
            <form action="items" method="post">
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
                        if(authLevel != null && !authLevel.equals(Authorization.UNAUTHORIZED.toString())) {
                    %>
                    <button type="submit" name="action" value="addToCart">Add to Cart</button>
                    <%
                            if(authLevel.equals(Authorization.ADMIN.toString())) {
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
            String name = (String) session.getAttribute("name");
            if(name != null) {
        %>
    <div id="shopping-cart-container">
        <h2>Shopping Cart for <%=name%></h2>
        <ul>
            <%
                {
                Collection<ItemInfo> cartItems = (Collection<ItemInfo>) session.getAttribute("items");
                int price = 0;
                if (cartItems != null) {
                    for (ItemInfo item : cartItems) {
            %>
            <li><b>Product:</b> <%= item.getName() %> - <b>Quantity:</b> <%= item.getQuantity() %> - <b>Cost:</b> <%=item.getPrice()*item.getQuantity()%>
                <form class="remove-button" action="items" method="post">
                    <input type="hidden" name="action" value="removeFromCart">
                    <input type="hidden" name="itemIdToRemove" value="<%= item.getId() %>">
                    <button type="submit">Remove</button>
                </form>
            </li>
            <%
                        }
                    price = (int) session.getAttribute("totalPriceCart");     //might be hard to move this one as well
                }
            %>
        </ul>
        <p><b>Total Price:</b> <%= price %></p>
        <%
            session.setAttribute("finalPrice", price);
            }
            Collection<ItemInfo> cartItems = (Collection<ItemInfo>) session.getAttribute("items");
            if (cartItems != null){
                if(!cartItems.isEmpty()){
        %>
        <a href="${pageContext.request.contextPath}/checkout" class="checkout-button">Checkout</a>
        <%
                }
            }
        %>
    </div>
    <%
        }
    %>
</div>
</body>
</html>
<script type="text/javascript">
    // Alert for if transaction fails
    <% Boolean transactionSuccess = (Boolean) request.getAttribute("transactionSuccess"); %>
    <% String transactionMessage = (String) request.getAttribute("transactionMessage"); %>

    <% if (transactionSuccess != null && !transactionSuccess) { %>
    alert("<%= transactionMessage %>");
    <% } %>
    function submitForm() {
        document.getElementById("filter-form").submit();
    }
</script>

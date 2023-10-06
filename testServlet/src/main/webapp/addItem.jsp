<%@ page import="db.Category" %><%--
  Created by IntelliJ IDEA.
  User: Wilja
  Date: 10/1/2023
  Time: 10:06 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add item</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            text-align: center;
        }

        h2 {
            color: #333;
        }

        form {
            background-color: #fff;
            border-radius: 5px;
            padding: 20px;
            width: 300px;
            margin: 0 auto;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
        }

        label {
            display: block;
            margin-bottom: 10px;
            color: #333;
        }

        input[type="text"],
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 3px;
        }

        input[type="submit"] {
            background-color: #007bff;
            color: #fff;
            border: none;
            padding: 10px 20px;
            border-radius: 3px;
            cursor: pointer;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }

        a {
            color: #007bff;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<nav>
    <a href="index.jsp">Home</a>
</nav>
<a href="items.jsp">Back to items</a>
<br><br>
<h2>Add item </h2>
<form action="addItem" method="post">
    <input type="hidden" name="action" value="addNewItem">

    <label for="name">Name:</label>
    <input type="text" id="name" name="itemName" required><br><br>

    <label for="description">Description:</label>
    <input type="text" id="description" name="itemDesc" required><br><br>

    <label for="amount">Amount:</label>
    <input type="text" id="amount" name="itemAmount" required><br><br>

    <label for="price">Price:</label>
    <input type="text" id="price" name="itemPrice" required><br><br>

    <label for="category">Choose category:</label>
    <select name="category" id="category">
        <option value="<%= Category.SODA.toString().toUpperCase() %>">Soda</option>
        <option value="<%= Category.CHIPS.toString().toUpperCase() %>">Chips</option>
    </select>
    <input type="submit" value="Submit">
</form>
</body>
</html>





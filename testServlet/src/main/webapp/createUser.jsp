<%@ page import="db.Authorization" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create account</title>
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
<a href="index.jsp">Home</a>
<br><br>
<h2>Create account</h2>
<form action="createUser" method="post">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username" required><br><br>

    <label for="name">Name:</label>
    <input type="text" id="name" name="name" required><br><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password" required><br><br>

    <label for="authorization">Choose Authorization:</label>
    <select name="authorization" id="authorization">
        <option value="<%= Authorization.ADMIN.toString().toUpperCase() %>">Admin</option>
        <option value="<%= Authorization.STAFF.toString().toUpperCase() %>">Staff</option>
        <option value="<%= Authorization.USER.toString().toUpperCase() %>">User</option>
    </select>
    <br><br>

    <input type="submit" value="Create">
</form>
<br><br>
<a href="createUser.jsp">Don't have an account? Create one here!</a>
</body>
</html>
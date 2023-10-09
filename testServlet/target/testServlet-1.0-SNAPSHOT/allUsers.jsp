<%@ page import="java.util.Collection" %>
<%@ page import="ui.ItemInfo" %>
<%@ page import="bo.UserHandler" %>
<%@ page import="ui.OrderInfo" %>
<%@ page import="ui.UserInfo" %>
<%@ page import="db.Authorization" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>All users</title>
  <style>
    body {
      font-family: Arial, Helvetica, sans-serif;
      background-color: #f2f2f2;
      margin: 0;
      padding: 0;
    }

    .container {
      background-color: #fff;
      border-radius: 5px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      margin: 20px auto;
      max-width: 800px;
      padding: 20px;
    }

    header {
      background-color: #333;
      color: #fff;
      padding: 10px 0;
      text-align: center;
    }

    header h1 {
      font-size: 24px;
    }

    nav {
      background-color: #444;
      text-align: center;
      padding: 10px 0;
    }

    nav a {
      color: #fff;
      text-decoration: none;
      padding: 5px 10px;
      margin: 0 5px;
      border-radius: 5px;
    }

    nav a.new-button {
      background-color: green;
    }

    nav a.new-button:hover {
      background-color: darkgreen;
    }

    nav a.home-button {
      background-color: #007bff;
    }

    nav a.home-button:hover {
      background-color: #0056b3;
    }

    .content {
      padding: 20px;
    }

    .user-container {
      border: 2px solid #ddd;
      border-radius: 5px;
      padding: 20px;
      margin: 20px 0;
      background-color: #fff;
    }

    .user-container h2 {
      font-size: 20px;
      margin-bottom: 10px;
    }

    .user-info {
      margin-top: 10px;
    }

    .user-info label {
      display: block;
      margin-bottom: 5px;
    }

    .user-info input[type="text"] {
      width: 100%;
      padding: 5px;
      border: 1px solid #ddd;
      border-radius: 3px;
    }

    .delete-button {
      text-align: right;
      margin-top: 10px;
    }

    .delete-button button {
      background-color: red;
      color: white;
      border: none;
      padding: 5px 10px;
      border-radius: 3px;
      cursor: pointer;
    }

    .delete-button button:hover {
      background-color: darkred;
    }

    .submit-button {
      text-align: right;
      margin-top: 10px;
    }

    .submit-button button {
      background-color: #27b2fd;
      color: #fff;
      border: none;
      padding: 5px 10px;
      border-radius: 3px;
      cursor: pointer;
    }

    .submit-button button:hover {
      background-color: #1b75a6;
    }
    .form-group {
      margin-bottom: 10px;
    }


    label {
      display: block;
      font-weight: bold;
    }

    input, select {
      width: 100%;
    }
  </style>
</head>
<body>
<header>
  <h1>All users</h1>
</header>
<nav>
  <a class="home-button" href="index.jsp">Home</a>
  <a class="new-button" href="${pageContext.request.contextPath}/user">Create new</a>
</nav>
<div class="container">
  <div class="content">
    <%
      Collection<UserInfo> users = UserHandler.getAllUsers();
      for(UserInfo user : users) {
    %>
    <div class="user-container">
      <form action="user" method="post">
        <input type="hidden" name="transaction" value="edit">

        <div class="form-group">
          <label for="username">Username:</label>
          <input type="text" id="username" name="username" value="<%=user.getUsername()%>" readonly>
        </div>

        <div class="form-group">
          <label for="name">Name:</label>
          <input type="text" id="name" name="name" value="<%=user.getName()%>">
        </div>

        <div class="form-group">
          <label for="authorization">Choose Authorization:</label>
          <select name="authorization" id="authorization">
            <option value="<%= Authorization.ADMIN.toString().toUpperCase() %>" <%= user.getAuthorizationLevel().equalsIgnoreCase(Authorization.ADMIN.toString()) ? "selected" : "" %>>Admin</option>
            <option value="<%= Authorization.STAFF.toString().toUpperCase() %>" <%= user.getAuthorizationLevel().equalsIgnoreCase(Authorization.STAFF.toString().toUpperCase()) ? "selected" : "" %>>Staff</option>
            <option value="<%= Authorization.USER.toString().toUpperCase() %>" <%= user.getAuthorizationLevel().equalsIgnoreCase(Authorization.USER.toString().toUpperCase()) ? "selected" : "" %>>User</option>
          </select>
        </div>

        <div class="submit-button">
          <button type="submit">Submit</button>
        </div>
      </form>
      <div class="delete-button">
        <form action="user" method="post">
          <input type="hidden" name="transaction" value="delete">
          <input type="hidden" name="username" value="<%=user.getUsername()%>">

          <button type="submit">Delete</button>
        </form>
      </div>
    </div>
    <%
      }
    %>
  </div>
</div>
</body>
</html>




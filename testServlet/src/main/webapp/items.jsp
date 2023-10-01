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
<h1>Here are the items; </h1>
<ul>
    <%
        // Retrieve items from the session attribute
        Collection<ItemInfo> items = ItemHandler.getItemsWithGroup(" ");
        for (ItemInfo item : items) { %>
<li><%=item.getDesc()%></li>
<% }
    %>
</ul>
</body>
</html>

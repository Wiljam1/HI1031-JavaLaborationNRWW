package ui;

import bo.CartHandler;
import bo.ItemHandler;
import db.Authorization;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Collection;

//TODO: Döp om till något mer passande
@WebServlet("/items")
public class ItemServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");

        String name;
        String authLevel;
        if(userInfo != null) {
            name = userInfo.getName();
            authLevel = userInfo.getAuthorization();
        } else {
            name = null;
            authLevel = Authorization.UNAUTHORIZED.toString();
        }

        session.setAttribute("name", name);
        session.setAttribute("authLevel", authLevel);

        request.getRequestDispatcher("/items.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Item parameters
        String itemId = request.getParameter("itemId");
        String itemName = request.getParameter("itemName");
        String itemDesc = request.getParameter("itemDesc");
        String itemAmount = request.getParameter("itemAmount");
        String itemPrice = request.getParameter("itemPrice");
        String itemCategory = request.getParameter("itemCategory");

        HttpSession session = request.getSession();
        String action = request.getParameter("action");

        switch (action) {
            case "addNewItem":
                ItemHandler.createItem(itemName, itemDesc, itemAmount, itemPrice, itemCategory);
                response.sendRedirect("items.jsp");
                break;
            case "sendToEdit":
                //Send parameters
                session.setAttribute("itemId", itemId);
                session.setAttribute("itemName", itemName);
                session.setAttribute("itemDesc", itemDesc);
                session.setAttribute("itemAmount", itemAmount);
                session.setAttribute("itemPrice", itemPrice);
                session.setAttribute("itemCategory", itemCategory);
                response.sendRedirect("items.jsp");
                break;
            case "editItem":
                ItemHandler.editItem(itemId, itemName, itemDesc, itemAmount, itemPrice, itemCategory);
                response.sendRedirect("items.jsp");
                break;
            case "addToCart":
                Collection<ItemInfo> cartItems = (Collection<ItemInfo>) session.getAttribute("items");
                cartItems = CartHandler.addToCart(itemId,itemName,itemDesc,itemAmount,itemPrice,itemCategory, cartItems);
                session.setAttribute("items", cartItems);
                response.sendRedirect("items.jsp");
                break;
            case "removeFromCart":
                String itemIdToRemove = request.getParameter("itemIdToRemove");
                Collection<ItemInfo> cart = (Collection<ItemInfo>) session.getAttribute("items");
                if (cart != null) {
                    session.setAttribute("items",  CartHandler.removeFromCart(itemIdToRemove, cart));
                }
                response.sendRedirect("items.jsp");
                break;
            case "setFilter":
                String filter = request.getParameter("selectedCategory");
                session.setAttribute("filter", filter);
                response.sendRedirect("items.jsp");
                break;
            default:
                System.out.println("error in itemservlet switch input action: " + action);
        }
    }
}
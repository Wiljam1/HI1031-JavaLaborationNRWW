package ui;

import bo.ItemHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

//TODO: Döp om till något mer passande
@WebServlet("/items")
public class ItemServlet extends HttpServlet {

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
                //TODO: Flytta till metod
                //ADD TO SHOPPING CART
                //TODO: Klassens logik borde flyttas till ett Business Object. Detta är en controller class!

                int amount = Integer.parseInt(itemAmount);

                Collection<ItemInfo> cartItems = (Collection<ItemInfo>) session.getAttribute("items");
                if(cartItems == null) {
                    cartItems = new ArrayList<>();
                }
                boolean itemExists = false;
                for(ItemInfo item : cartItems) {
                    if(item.getName().equals(itemName)) {
                        if(amount > item.getQuantity())
                            item.incrementQuantity();
                        itemExists = true;
                        break;
                    }
                }

                if(!itemExists && amount > 0) {
                    // TODO: Fixa snyggare, parse någon annanstans
                    cartItems.add(new ItemInfo(itemId, itemName, itemDesc, "1", itemAmount, itemPrice, itemCategory));
                }

                session.setAttribute("items", cartItems);
                response.sendRedirect("items.jsp");
                break;
            case "removeFromCart":
                String itemIdToRemove = request.getParameter("itemIdToRemove");
                Collection<ItemInfo> cartItemsToRemove = (Collection<ItemInfo>) session.getAttribute("items");
                if (cartItemsToRemove != null) {
                    Iterator<ItemInfo> iterator = cartItemsToRemove.iterator();
                    while (iterator.hasNext()) {
                        ItemInfo item = iterator.next();
                        if (item.getId().equals(itemIdToRemove)) {
                            if (item.getQuantity() > 1) {
                                item.decrementQuantity();
                            } else {
                                iterator.remove();
                            }
                            break;
                        }
                    }
                    session.setAttribute("items", cartItemsToRemove);
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
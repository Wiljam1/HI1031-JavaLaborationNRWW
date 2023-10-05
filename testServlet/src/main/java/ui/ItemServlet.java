package ui;

import bo.ItemHandler;
import bo.UserHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import db.DBManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.swing.text.Document;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;


@WebServlet("/addItem")
public class ItemServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String action = request.getParameter("action");

        //Item parameters
        String itemId = request.getParameter("itemId");
        String itemName = request.getParameter("itemName");
        String itemDesc = request.getParameter("itemDesc");
        String itemAmount = request.getParameter("itemAmount");
        String itemPrice = request.getParameter("itemPrice");

        switch (action) {
            case "add":
                ItemHandler.createItem(itemName, itemDesc, itemAmount, itemPrice);
                break;
            case "edit":
                
                break;
            default:
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
                    itemAmount = String.valueOf(itemAmount);
                    cartItems.add(new ItemInfo(itemId, itemName, itemDesc, "1", itemAmount, itemPrice));
                }

                session.setAttribute("items", cartItems);
                response.sendRedirect("items.jsp");
        }
    }
}
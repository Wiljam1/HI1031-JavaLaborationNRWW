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

        //ADD TO SHOPPING CART
        //TODO: Klassens logik borde flyttas till ett Business Object. Detta är en controller class!
        HttpSession session = request.getSession();

        String itemId = request.getParameter("itemId");
        String itemName = request.getParameter("itemName");
        String itemDesc = request.getParameter("itemDesc");
        int itemAmount = Integer.parseInt(request.getParameter("itemAmount"));
        String itemPrice = request.getParameter("itemPrice");

        Collection<ItemInfo> cartItems = (Collection<ItemInfo>) session.getAttribute("items");
        if(cartItems == null) {
            cartItems = new ArrayList<>();
        }
        boolean itemExists = false;
        for(ItemInfo item : cartItems) {
            if(item.getName().equals(itemName)) {
                if(itemAmount > item.getQuantity())
                    item.incrementQuantity();
                itemExists = true;
                break;
            }
        }

        if(!itemExists && itemAmount > 0) {
            // TODO: Fixa snyggare, parse någon annanstans
            String amount = String.valueOf(itemAmount);
            cartItems.add(new ItemInfo(itemId, itemName, itemDesc, "1", amount, itemPrice));
        }

        session.setAttribute("items", cartItems);
        response.sendRedirect("items.jsp");
    }
}
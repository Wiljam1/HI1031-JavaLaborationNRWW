package ui;

import bo.ItemHandler;
import bo.UserHandler;
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
        HttpSession session = request.getSession();
        //TODO: Ändra så man kan hämta hela item?
        String itemName = request.getParameter("itemName");
        String itemPrice = request.getParameter("itemPrice");
        int itemAmount = Integer.parseInt(request.getParameter("itemAmount"));

        //Lägg till check om varan finns på lager (jämför med quantity)

        //String username = (String) session.getAttribute("username");
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

        if(!itemExists) {
            // TODO: Fixa snyggare, parse någon annanstans
            cartItems.add(new ItemInfo(itemName, Integer.parseInt(itemPrice)));
        }

        session.setAttribute("items", cartItems);

        response.sendRedirect("items.jsp");
    }
}
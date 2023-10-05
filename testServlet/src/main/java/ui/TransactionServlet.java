package ui;

import bo.User;
import bo.UserHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@WebServlet("/transaction")
public class TransactionServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        String username = userInfo.getUsername();
        String finalPrice = request.getParameter("finalPrice");

        Collection<ItemInfo> cart = (Collection<ItemInfo>) session.getAttribute("items");

        /*
        Transaction transaction = new Transaction();
        transaction.setDescription(request.getParameter("description"));
        transaction.setAmount(Double.parseDouble(request.getParameter("amount")));
        */

        boolean success = UserHandler.transaction(username, cart, finalPrice);
        //Empty cart
        session.setAttribute("items", new ArrayList<ItemInfo>());

        if (success) {
            request.setAttribute("transactionSuccess", true);
            request.setAttribute("transactionMessage", "Transaction successful!");
            request.getRequestDispatcher("orders.jsp").forward(request, response);
        } else {
            request.setAttribute("transactionSuccess", false);
            request.setAttribute("transactionMessage", "Transaction failed!");
            request.getRequestDispatcher("items.jsp").forward(request, response);
        }

    }
}

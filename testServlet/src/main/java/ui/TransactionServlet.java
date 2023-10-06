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
        String transactionAction = request.getParameter("transaction");
        switch (transactionAction){
            case "createOrder" :
                HttpSession session = request.getSession();
                UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
                String username = userInfo.getUsername();
                String finalPrice = request.getParameter("finalPrice");

                Collection<ItemInfo> cart = (Collection<ItemInfo>) session.getAttribute("items");
                boolean success = UserHandler.transaction(username, cart, finalPrice);
                session.setAttribute("items", new ArrayList<ItemInfo>());

                if (success) {
                    request.setAttribute("transactionSuccess", true);
                    request.setAttribute("transactionMessage", "Transaction successful!");
                    request.getRequestDispatcher("userOrders.jsp").forward(request, response);
                } else {
                    request.setAttribute("transactionSuccess", false);
                    request.setAttribute("transactionMessage", "Transaction failed!");
                    request.getRequestDispatcher("items.jsp").forward(request, response);
                }
                break;
            case "deleteOrder":
                username = request.getParameter("username");
                String transactionId = request.getParameter("transactionId");
                UserHandler.removeOrder(username, transactionId);

                response.sendRedirect("allOrders.jsp");
                break;
        }
    }
}

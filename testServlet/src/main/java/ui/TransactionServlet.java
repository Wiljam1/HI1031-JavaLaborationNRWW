package ui;

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
/**
 * Servlet responsible for handling user transactions, such as creating, deleting, or marking orders as packed.
 */
@WebServlet("/transaction")
public class TransactionServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String transactionAction = request.getParameter("transaction");
        switch (transactionAction){
            case "createOrder" :
                handleCreateOrder(request, response);
                break;
            case "deleteOrder":
                handleDeleteOrder(request, response);
                break;
            case "orderPacked":
                handlePackedOrder(request,response);
                break;
            default:
                break;
        }
    }

    private void handlePackedOrder(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String username = request.getParameter("username");
        String transactionId = request.getParameter("transactionId");
        UserHandler.orderIsPacked(username, transactionId);
        
        response.sendRedirect(request.getContextPath() + "/allOrders");
    }

    private void handleCreateOrder(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        String username = userInfo.getUsername();
        String finalPrice = request.getParameter("finalPrice");

        Collection<ItemInfo> cart = (Collection<ItemInfo>) session.getAttribute("items");
        boolean success = UserHandler.addOrder(username, cart, finalPrice);
        // Empty cart
        session.setAttribute("items", new ArrayList<ItemInfo>());

        forwardToTransactionResult(request, response, success);
    }

    private void forwardToTransactionResult(HttpServletRequest request, HttpServletResponse response, boolean success)
            throws ServletException, IOException {
        String targetJSP = success ? "/userOrders" : "/items";
        String message = success ? "Transaction successful!" : "Transaction failed!";

        request.setAttribute("transactionSuccess", success);
        request.setAttribute("transactionMessage", message);
        request.getRequestDispatcher(targetJSP).forward(request, response);
    }

    private void handleDeleteOrder(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String username = request.getParameter("username");
        String transactionId = request.getParameter("transactionId");
        UserHandler.removeOrder(username, transactionId);

        request.getRequestDispatcher("/WEB-INF/allOrders.jsp").forward(request, response);
    }
}

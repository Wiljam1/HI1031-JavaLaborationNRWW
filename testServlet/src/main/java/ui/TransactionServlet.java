package ui;

import bo.UserHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Collection;

@WebServlet("/transaction")
public class TransactionServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = request.getParameter("username");



        Collection<ItemInfo> cart = (Collection<ItemInfo>) session.getAttribute("items");

        // Borde göra själva transaktionen här
        /*
        Transaction transaction = new Transaction();
        transaction.setDescription(request.getParameter("description"));
        transaction.setAmount(Double.parseDouble(request.getParameter("amount")));
        */


        //boolean success = transactionService.createTransaction(transaction);

        boolean success = true;
        //TODO: borde hända något här
        if (success) {
            response.getWriter().write("Transaction successful!");
        } else {
            response.getWriter().write("Transaction failed!");
        }
    }
}

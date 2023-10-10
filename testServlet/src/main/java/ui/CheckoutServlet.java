package ui;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Collection;
/**
 * Servlet responsible for handling requests related to the checkout process in the online store.
 * This servlet provides the user interface for the checkout page and ensures that only authenticated
 * users can access it.
 */
@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");

        if(userInfo != null) {
            request.getRequestDispatcher("/WEB-INF/checkout.jsp").forward(request, response);
        }
        else {
            response.sendRedirect("login");
        }
    }
}

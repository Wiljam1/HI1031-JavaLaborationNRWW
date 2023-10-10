package ui;


import bo.UserHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
/**
 * This servlet handles user-specific order-related operations, such as displaying user orders.
 * It retrieves user order information and forwards the request to the userOrders.jsp page.
 */
@WebServlet("/userOrders")
public class UserOrdersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        session.setAttribute("getUserOrders", UserHandler.getUserInfo(userInfo.getUsername()).getOrders());
        if(userInfo != null) {
            request.getRequestDispatcher("/WEB-INF/userOrders.jsp").forward(request, response);
        }
        else {
            response.sendRedirect("login");
        }
    }
}

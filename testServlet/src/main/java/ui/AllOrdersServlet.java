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
 * Servlet responsible for displaying a list of all orders in the online store's admin and staff interfaces.
 * This servlet retrieves order data and ensures that only authorized administrators and staff members can access it.
 */
@WebServlet("/allOrders")
public class AllOrdersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        session.setAttribute("getAllUsers", UserHandler.getAllUsers());
        if(userInfo != null) {
            String authLevel = userInfo.getAuthorization();
            boolean isAuthorized = "admin".equals(authLevel) || "staff".equals(authLevel);

            if(isAuthorized)
                request.getRequestDispatcher("/WEB-INF/allOrders.jsp").forward(request, response);
            else {
                response.sendRedirect("index.jsp");
            }
        }
        else {
            response.sendRedirect("login");
        }
    }
}

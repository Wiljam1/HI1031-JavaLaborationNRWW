package ui;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/allOrders")
public class AllOrdersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");

        if(userInfo != null) {
            String authLevel = userInfo.getAuthorizationLevel();
            boolean isAuthorized = "admin".equals(authLevel) || "staff".equals(authLevel);

            if(isAuthorized)
                request.getRequestDispatcher("/allOrders.jsp").forward(request, response);
            else {
                response.sendRedirect("index.jsp");
            }
        }
        else {
            response.sendRedirect("login");
        }
    }
}

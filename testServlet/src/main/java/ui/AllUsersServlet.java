package ui;


import bo.UserHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/allUsers")
public class AllUsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        session.setAttribute("getAllUsers", UserHandler.getAllUsers());
        if(userInfo != null) {
            String authLevel = userInfo.getAuthorization();
            boolean isAuthorized = "admin".equals(authLevel);

            if(isAuthorized)
                request.getRequestDispatcher("/WEB-INF/allUsers.jsp").forward(request, response);
            else {
                session.invalidate();
                response.sendRedirect("index.jsp");
            }
        }
        else {
            response.sendRedirect("login");
        }
    }
}

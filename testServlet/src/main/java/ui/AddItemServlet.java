package ui;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
/**
 * Servlet responsible for rendering the "Add Item" page in the online store.
 * This servlet ensures that only authenticated users can access the item addition functionality.
 */
@WebServlet("/addItem")
public class AddItemServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");

        if(userInfo != null) {
                request.getRequestDispatcher("/WEB-INF/addItem.jsp").forward(request, response);
        }
        else {
            response.sendRedirect("login");
        }
    }
}

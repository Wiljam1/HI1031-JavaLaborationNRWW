package ui;

import bo.UserHandler;
import com.mongodb.client.MongoCollection;
import db.DBManager;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import javax.swing.text.Document;
import java.io.IOException;

/**
 * Servlet responsible for handling user login functionality. It provides both a GET method for displaying the login
 * page and a POST method for processing login attempts.
 */
@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward the request to the login.jsp page
        RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
        dispatcher.forward(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (performLogin(username, password, request, response)) {
            // Successful login
            response.sendRedirect("index.jsp");
        } else {
            // Failed login
            response.sendRedirect("login");
        }
    }

    private boolean performLogin(String username, String password, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (UserHandler.authenticateUser(username, password)) {
            // Successful login
            HttpSession session = request.getSession();
            session.invalidate();
            session = request.getSession();

            UserInfo userInfo = UserHandler.getUserInfo(username);
            session.setAttribute("userInfo", userInfo);

            return true;
        } else {
            // Failed login
            request.removeAttribute("password");
            return false;
        }
    }
}
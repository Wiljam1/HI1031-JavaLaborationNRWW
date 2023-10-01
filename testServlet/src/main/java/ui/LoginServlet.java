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


@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final String VALID_USERNAME = "your_username";
    private static final String VALID_PASSWORD = "your_password";


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");


        //MongoCollection<org.bson.Document> aa = DBManager.getCollection("users");

        if (UserHandler.authenticateUser(username, password)) {
            // Successful login
            HttpSession session = request.getSession();
            session.invalidate();
            session = request.getSession();
            session.setAttribute("username", username);
            String displayUsername = "Not logged in";
            if (username != null) {
                displayUsername = UserHandler.getUserInfo(username).getName();
            }
            session.setAttribute("displayUsername", displayUsername);
            // RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
            //dispatcher.forward(request, response);
            response.sendRedirect("index.jsp"); // Redirect to a welcome page
        } else {
            // Failed login
            response.sendRedirect("login.jsp"); // Redirect back to the login page
        }
    }
}
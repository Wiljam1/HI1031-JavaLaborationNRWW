package ui;

import com.mongodb.client.MongoCollection;
import db.DBManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

        if (DBManager.authenticateUser(username, password)) {
            // Successful login
            response.sendRedirect("shoppingcart.jsp"); // Redirect to a welcome page
        } else {
            // Failed login
            response.sendRedirect("login.jsp"); // Redirect back to the login page
        }
    }
}
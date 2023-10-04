package ui;

import bo.UserHandler;
import db.Authorization;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/createUser")
public class createUserServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String authParameter = request.getParameter("authorization");
        Authorization authorization = Authorization.valueOf(authParameter);

        // TODO: implement check of validity of user inputs (ingen skadlig kod)
        if(true) {
            // Valid user to create
            UserHandler.createUser(username, name, password, authorization);
            response.sendRedirect("login.jsp");
        } else {
            // Failed to create user TODO: Implementera felmeddelande (som visas i Alert)
            response.sendRedirect("createUser.jsp");
        }
    }
}

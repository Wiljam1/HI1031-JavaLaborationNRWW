package ui;

import bo.UserHandler;
import db.Authorization;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String authParameter = request.getParameter("authorization");
        Authorization authorization = Authorization.valueOf(authParameter);

        boolean userCreation = UserHandler.createUser(username, name, password, authorization);
        if(userCreation) {
            // Valid user to create
            response.sendRedirect("login.jsp");
        } else {
            // Failed to create user
            response.sendRedirect("createUser.jsp");
        }
    }
}

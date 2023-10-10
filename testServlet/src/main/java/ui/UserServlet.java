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
/**
 * This servlet handles user-related operations such as creating, editing, and deleting users.
 * It also manages user authorization levels and redirects users to appropriate pages.
 */
@WebServlet("/user")
public class UserServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");

        if (userInfo != null && userInfo.getAuthorization().equals(Authorization.ADMIN.toString())) {
            session.setAttribute("authLevel", Authorization.ADMIN.toString());
        }

        response.sendRedirect("createUser.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");
        String currentUsername = userInfo.getUsername();
        Authorization currentAuthorization = Authorization.valueOf(userInfo.getAuthorization().toUpperCase());

        String transactionAction = request.getParameter("transaction");
        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String authParameter = request.getParameter("authorization");
        Authorization authorization;
        if(authParameter != null)
             authorization = Authorization.valueOf(authParameter);
        else
            authorization = Authorization.USER;

        switch (transactionAction){
            case "create":
                boolean userCreation = UserHandler.createUser(username, name, password, authorization);
                if(userCreation) {
                    // Valid user to create
                    response.sendRedirect("login.jsp");
                } else {
                    // Failed to create user
                    response.sendRedirect("createUser.jsp");
                }
                break;
            case "edit":
                boolean userEdit = UserHandler.editUser(username, name, authorization);
                if(userEdit) {
                    // Valid user to edit
                    if(currentUsername.equals(username) && !currentAuthorization.equals(authorization)) {
                        session.invalidate();
                        response.sendRedirect("index.jsp");
                    } else {
                        request.getRequestDispatcher("/WEB-INF/allUsers.jsp").forward(request, response);
                    }
                } else {
                    // Failed to edit user
                    request.getRequestDispatcher("/WEB-INF/allUsers.jsp").forward(request, response);
                }
                break;
            case "delete":
                boolean deleteEdit = UserHandler.deleteUser(username);
                if (deleteEdit) {
                    // Successfully deleted user
                } else {
                    // Failed to delete user
                }
                response.sendRedirect(request.getContextPath() + "/allUsers");
                break;
            default:
                break;
        }
    }
}

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

@WebServlet("/user")
public class UserServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute("userInfo");

        //Check if admin
        if (userInfo != null && userInfo.getAuthorizationLevel().equals(Authorization.ADMIN.toString())) {
            session.setAttribute("authLevel", Authorization.ADMIN.toString());
        }

        response.sendRedirect("createUser.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String transactionAction = request.getParameter("transaction");
        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String authParameter = request.getParameter("authorization");
        Authorization authorization = Authorization.valueOf(authParameter);
        // TODO: implement check of validity of user inputs (ingen skadlig/för lång kod)

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
                    // Valid user to create
                    System.out.println("test1");
                    //TODO: maybe an alert here
                    response.sendRedirect("allUsers.jsp");
                } else {
                    // Failed to create user
                    //TODO: maybe an alert here
                    System.out.println("test1");
                    response.sendRedirect("allUsers.jsp");
                }
                break;
            case "delete":
                boolean deleteEdit = UserHandler.deleteUser(username);
                if(deleteEdit) {
                    // Valid user to create
                    //TODO: maybe an alert here
                    response.sendRedirect("allUsers.jsp");
                } else {
                    // Failed to create user
                    //TODO: maybe an alert here
                    response.sendRedirect("allUsers.jsp");
                }
                break;
            default:
                break;

        }


    }
}

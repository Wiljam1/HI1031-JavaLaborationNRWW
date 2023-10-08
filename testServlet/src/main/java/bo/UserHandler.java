package bo;

import db.Authorization;
import db.UserDB;
import org.bson.Document;
import ui.ItemInfo;
import ui.OrderInfo;
import ui.UserInfo;

import java.util.ArrayList;
import java.util.Collection;

public class UserHandler {

    public static UserInfo getUserInfo(String username) {
        User u = User.searchUser(username);
        Collection<OrderInfo> orders = User.convertToOrderInfo(u.getOrders());
        return new UserInfo(u.getUsername(), u.getName(), u.getCart(), u.getAuthorizationLevel(), orders);
    }

    public static boolean createUser(String username, String name, String password, Authorization authorization) {
        return User.createUser(username, name, password, authorization);
    }

    public static boolean authenticateUser(String username, String password) {

        Document userDoc = UserDB.getUserDocument(username);

        if (userDoc != null) {
            String DBPassword = userDoc.getString("password");

            if (password.equals(DBPassword)) {
                return true; // Passwords match
            }
        }

        return false; // User not found or password doesn't match
    }

    public static boolean transaction(String username, Collection<ItemInfo> cart, String finalPrice){
        return User.initTransaction(username, cart, finalPrice);
    }

    public static Collection<UserInfo> getAllUsers() {
        Collection c = User.getAllUsers();
        ArrayList<UserInfo> users = new ArrayList<>();
        for (Object o : c) {
            User u = (User) o;
            Collection<OrderInfo> orders = User.convertToOrderInfo(u.getOrders());
            users.add(new UserInfo(u.getUsername(), u.getName(), u.getCart(), u.getAuthorization(), orders));
        }
        return users;
    }



    public static void removeOrder(String username, String transaction) {
        UserDB.removeOrderWithTransaction(username, transaction);
    }

    public static boolean editUser(String username, String name, Authorization authorization) {
        return UserDB.editUserDB(username,name,authorization);
    }

    public static boolean deleteUser(String username) {
        return false;
    }
}

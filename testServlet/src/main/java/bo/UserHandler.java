package bo;

import db.Authorization;
import db.UserDB;
import org.bson.Document;
import ui.ItemInfo;
import ui.UserInfo;

import java.util.ArrayList;
import java.util.Collection;

public class UserHandler {

    public static UserInfo getUserInfo(String username) {
        User u = User.searchUser(username);
        return new UserInfo(u.getUsername(), u.getName(), u.getCart(), u.getAuthorizationLevel(), u.fetchOrders(username));
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
        return UserDB.performTransaction(username, cart, finalPrice);
    }

    public static Collection<UserInfo> getAllUsers() {
        Collection c = User.getAllUsers();
        ArrayList<UserInfo> users = new ArrayList<>();
        for (Object o : c) {
            User u = (User) o;
            users.add(new UserInfo(u.getUsername(), u.getName(), u.getCart(), u.getAuthorization(), u.getOrders()));
        }
        return users;
    }
}

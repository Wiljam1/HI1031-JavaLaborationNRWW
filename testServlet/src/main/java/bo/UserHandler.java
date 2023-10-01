package bo;

import db.UserDB;
import org.bson.Document;
import ui.ItemInfo;
import ui.UserInfo;

import java.util.Collection;

public class UserHandler {
    private int id;
    private String username;
    private String name;
    private Collection<ItemInfo> cart;

    public static UserInfo getUserInfo(String username) {
        User theUser = User.searchUser(username);
        return new UserInfo(theUser.getName(), theUser.getCart());
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
}

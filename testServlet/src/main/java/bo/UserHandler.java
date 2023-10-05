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
    private int id;
    private String username;
    private String name;
    private Collection<ItemInfo> cart;
    private Collection<OrderInfo> orders;

    public static UserInfo getUserInfo(String username) {
        User theUser = User.searchUser(username);
        return new UserInfo(theUser.getName(), theUser.getCart(), theUser.getAuthorizationLevel());
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

    public Collection<OrderInfo> getOrders() {
        return orders;
    }

    // TODO: Skriv om så man gör getUserInfo(username).getOrders() istället
    // Då behöver man lägga till Collection<Order> på alla User-klasser och fixa alla konstruktorer.
    public static Collection<OrderInfo> getOrders(String username) {
        Collection c = User.getOrders(username);
        if(c == null || c.isEmpty())
            return null;
        else {
            ArrayList<OrderInfo> orders = new ArrayList<>();
            for (Object o : c) {
                OrderInfo ord = (OrderInfo) o;
                orders.add(new OrderInfo(ord.getId(), ord.getDate(), ord.getItems(), ord.getTotalCost(), ord.getAssignedStaff()));
            }
            return orders;
        }
    }
}

package bo;

import db.Authorization;
import db.ItemDB;
import db.OrderDB;
import db.UserDB;
import ui.ItemInfo;
import ui.OrderInfo;

import java.util.ArrayList;
import java.util.Collection;
/**
 * The User class represents a user in the system. It contains user-specific information
 * such as username, name, authorization level, shopping cart, and order history.
 */
public class User {
    private String username;
    private String name;
    private Collection<ItemInfo> cart;
    private String authorization;
    private Collection orders; //Vet inte om det ska vara Collection<Order> eller bara Collection

    protected User(String username, String name, String authorization) {
        this.username = username;
        this.name = name;
        this.authorization = authorization;
        this.cart = null;
        this.orders = null;
    }

    protected User(String username, String name, String authorization, Collection orders) {
        this.username = username;
        this.name = name;
        this.authorization = authorization;
        this.cart = null;
        this.orders = orders;
    }

    public static boolean createUser(String username, String name, String password, Authorization authorization) {
        return UserDB.createUser(username, name, password, authorization);
    }

    public Collection getOrders() {
        return orders;
    }

    public static Collection fetchOrders(String username) {
        return UserDB.fetchOrders(username);
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public Collection<ItemInfo> getCart() {
        return cart;
    }

    public static User searchUser(String username) {
        return UserDB.searchUser(username);
    }

    public static Collection getAllUsers() {
        return UserDB.getAllUsers();
    }

    public String getAuthorizationLevel() { return authorization ;}

    public String getAuthorization() {
        return authorization;
    }


}

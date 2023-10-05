package bo;

import db.Authorization;
import db.UserDB;
import ui.ItemInfo;

import java.util.Collection;

public class User {
    private String username;
    private String name;
    private Collection<ItemInfo> cart;
    private String authorization;
    private Collection orders;

    protected User(String username, String name, String authorization) {
        this.username = username;
        this.name = name;
        this.authorization = authorization;
        this.cart = null;
        this.orders = null; //Kanske borde hämta existerande ordrar från databasen?
    }

    protected User(String username, String name, String authorization, Collection orders) {
        this.username = username;
        this.name = name;
        this.authorization = authorization;
        this.cart = null;
        this.orders = orders; //Kanske borde hämta existerande ordrar från databasen?
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

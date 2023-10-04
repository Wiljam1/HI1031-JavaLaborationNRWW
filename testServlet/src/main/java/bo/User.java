package bo;

import db.Authorization;
import db.UserDB;
import ui.ItemInfo;
import ui.OrderInfo;

import java.util.Collection;

public class User {
    private String username;
    private String name;
    private Collection<ItemInfo> cart;
    private String authorization;
    private Collection<OrderInfo> orders;

    protected User(String username, String name, String authorization) {
        this.username = username;
        this.name = name;
        this.authorization = authorization;
        this.cart = null;
        this.orders = null; //Kanske borde hämta existerande ordrar från databasen?
    }

    protected User(String username, String name, String authorization, Collection<OrderInfo> orders) {
        this.username = username;
        this.name = name;
        this.authorization = authorization;
        this.cart = null;
        this.orders = orders; //Kanske borde hämta existerande ordrar från databasen?
    }

    public static boolean createUser(String username, String name, String password, Authorization authorization) {
        return UserDB.createUser(username, name, password, authorization);
    }

    public static Collection getOrders(String username) {
        return UserDB.getOrders(username);
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

    public String getAuthorizationLevel() {return authorization;}
}

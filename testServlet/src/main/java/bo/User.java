package bo;

import db.Authorization;
import db.UserDB;
import ui.ItemInfo;

import java.util.Collection;
/**
 * The User class represents a user in the system. It contains user-specific information
 * such as username, name, authorization level, shopping cart, and order history.
 */
public class User {
    private final String username;
    private final String name;
    private final Collection<ItemInfo> cart;
    private final String authorization;
    private final Collection orders;

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

    public Collection getOrders() {
        return orders;
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

    public String getAuthorizationLevel() { return authorization ;}

    public String getAuthorization() {
        return authorization;
    }


}

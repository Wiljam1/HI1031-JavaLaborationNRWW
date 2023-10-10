package ui;

import java.util.Collection;
/**
 * Represents user information, including username, name, shopping cart, authorization level,
 * and a collection of user orders.
 */
public class UserInfo {
    private final String username;
    private final String name;
    private final Collection<ItemInfo> cart;
    private final String authorization;
    private final Collection<OrderInfo> orders;

    public UserInfo(String username, String name, Collection<ItemInfo> cart, String authorization, Collection<OrderInfo> orders) {
        this.username = username;
        this.name = name;
        this.cart = cart;
        this.authorization = authorization;
        this.orders = orders;
    }

    public String getName() {
        return name;
    }

    public String getAuthorization() { return authorization; }

    public Collection<OrderInfo> getOrders() {
        return orders;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getUsername() {
        return this.username;
    }
}

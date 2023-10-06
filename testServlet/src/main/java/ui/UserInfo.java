package ui;

import java.util.Collection;

public class UserInfo {
    private String username;
    private String name;
    private Collection<ItemInfo> cart; //TODO: ska vara ItemInfo
    private String authorization;
    private Collection<OrderInfo> orders;

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

    public Collection<ItemInfo> getCart() {
        return cart;
    }

    public String getAuthorizationLevel() { return authorization; }

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

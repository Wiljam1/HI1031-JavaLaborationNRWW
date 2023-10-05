package ui;

import java.util.Collection;

public class UserInfo {
    private String name;
    private Collection<ItemInfo> cart;
    private String authorization;
    private Collection<OrderInfo> orders;

    public UserInfo(String name, Collection<ItemInfo> cart, String authorization) {
        this.name = name;
        this.cart = cart;
        this.authorization = authorization;
    }

    public String getName() {
        return name;
    }

    public Collection<ItemInfo> getCart() {
        return cart;
    }

    public String getAuthorizationLevel() { return authorization; }

    // TODO: Implementera så denna kan användas med UserInfo-konstruktorn
    public Collection<OrderInfo> getOrders() {
        return orders;
    }

    @Override
    public String toString() {
        return name;
    }
}

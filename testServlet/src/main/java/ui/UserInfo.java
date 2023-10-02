package ui;

import java.util.Collection;

public class UserInfo {
    private String name;
    private Collection<ItemInfo> cart;
    private String authorization;

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

    public String getAuthorizationLevel() {return authorization;}

    @Override
    public String toString() {
        return name;
    }
}

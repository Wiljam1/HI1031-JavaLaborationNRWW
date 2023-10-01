package ui;

import java.util.Collection;

public class UserInfo {
    private String name;
    private Collection<ItemInfo> cart;

    public UserInfo(String name, Collection<ItemInfo> cart) {
        this.name = name;
        this.cart = cart;
    }

    public String getName() {
        return name;
    }

    public Collection<ItemInfo> getCart() {
        return cart;
    }
}

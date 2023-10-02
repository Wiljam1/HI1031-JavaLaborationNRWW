package bo;

import db.UserDB;
import ui.ItemInfo;

import java.util.Collection;

public class User {
    private int id;
    private String username;
    private String name;
    private Collection<ItemInfo> cart;
    private String authorization;

    protected User(String id, String username, String name, String authorization) {
        this.id = Integer.parseInt(id);
        this.username = username;
        this.name = name;
        this.authorization = authorization;
        this.cart = null;
    }

    public int getId() {
        return id;
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

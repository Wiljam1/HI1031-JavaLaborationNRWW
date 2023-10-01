package bo;

import db.ItemDB;
import db.UserDB;
import ui.ItemInfo;

import java.util.Collection;

public class User {
    private int id;
    private static String name;
    private Collection<ItemInfo> cart;

    protected User(String id, String name) {
        this.id = Integer.parseInt(id);
        this.name = name;
        this.cart = null;
    }

    public static Object searchUser(String username) {
        return UserDB.searchUser(username);
    }
}

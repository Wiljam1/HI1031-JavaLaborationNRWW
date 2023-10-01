package bo;

import ui.ItemInfo;
import ui.UserInfo;

import java.util.ArrayList;
import java.util.Collection;

public class UserHandler {
    private int id;
    private static String name;
    private Collection<ItemInfo> cart;

    public static UserInfo getCurrentUser() {
        return (UserInfo) User.searchUser(name);
    }
}

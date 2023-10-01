package bo;

import ui.ItemInfo;
import ui.UserInfo;

import java.util.ArrayList;
import java.util.Collection;

public class UserHandler {
    private int id;
    private String username;
    private String name;
    private Collection<ItemInfo> cart;

    public static UserInfo getUserInfo(String username) {
        User theUser = User.searchUser(username);
        return new UserInfo(theUser.getName(), theUser.getCart());
    }
}

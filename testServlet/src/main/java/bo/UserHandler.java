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
        //Koppla med login sen
        User theUser = User.searchUser("ww");
        return new UserInfo(theUser.getName(), theUser.getCart());
    }
}

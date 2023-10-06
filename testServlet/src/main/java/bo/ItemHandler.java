package bo;


import ui.ItemInfo;

import java.util.ArrayList;
import java.util.Collection;

public class ItemHandler {

    public static Collection<ItemInfo> getItemsWithGroup(String s) {
        Collection c = Item.searchItems(s);
        ArrayList<ItemInfo> items = new ArrayList<>();
        for (Object o : c) {
            Item item = (Item) o;
            items.add(new ItemInfo(item.getId(), item.getName(), item.getDesc(), "0", item.getAmount(), item.getPrice(), item.getCategory()));
        }
        return items;
    }

    public static boolean createItem(String name, String description, String amount, String price, String itemCategory) {
        return Item.createItem(name, description, amount, price, itemCategory);
    }

    public static boolean editItem(String id, String name, String description, String amount, String price, String itemCategory) {
        return Item.editItem(id, name, description, amount, price, itemCategory);
    }

}

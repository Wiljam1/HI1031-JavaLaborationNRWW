package bo;


import db.ItemDB;
import ui.ItemInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class ItemHandler {

    public static Collection<ItemInfo> getItemsWithGroup(String s) {
        Collection c = bo.Item.searchItems(s);
        ArrayList<ItemInfo> items = new ArrayList<>();
        for (Object o : c) {
            bo.Item item = (bo.Item) o;
            items.add(new ItemInfo(item.getId(), item.getName(), item.getDesc(), "0", item.getAmount(), item.getPrice(), item.getCategory()));
        }
        return items;
    }

    public static Collection<ItemInfo> getItemsWithGroup() {
        Collection c = bo.Item.searchItems();
        ArrayList<ItemInfo> items = new ArrayList<>();
        for (Object o : c) {
            bo.Item item = (bo.Item) o;
            items.add(new ItemInfo(item.getId(), item.getName(), item.getDesc(), "0", item.getAmount(), item.getPrice(), item.getCategory()));
        }
        return items;
    }

    public static boolean createItem(String name, String description, String amount, String price, String itemCategory) {
        return bo.Item.createItem(name, description, amount, price, itemCategory);
    }

    public static boolean editItem(String id, String name, String description, String amount, String price, String itemCategory) {
        return bo.Item.editItem(id, name, description, amount, price, itemCategory);
    }

    public static HashSet<String> getCategories(){
        return ItemDB.getCategories();
    }
}

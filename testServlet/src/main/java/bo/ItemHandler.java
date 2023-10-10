package bo;


import db.ItemDB;
import ui.ItemInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * The ItemHandler class provides methods for managing items, including retrieval,
 * creation, and editing. It acts as an intermediary between the UI layer and the database layer.
 */
public class ItemHandler {

    public static Collection<ItemInfo> getItemsWithGroup(String s) {
        Collection c = ItemDB.searchItems(s);
        ArrayList<ItemInfo> items = new ArrayList<>();
        for (Object o : c) {
            Item item = (Item) o;
            items.add(new ItemInfo(item.getId(), item.getName(), item.getDesc(), "0", item.getAmount(), item.getPrice(), item.getCategory()));
        }
        return items;
    }

    public static Collection<ItemInfo> getItemsWithGroup() {
        Collection c = ItemDB.searchItems();
        ArrayList<ItemInfo> items = new ArrayList<>();
        for (Object o : c) {
            Item item = (Item) o;
            items.add(new ItemInfo(item.getId(), item.getName(), item.getDesc(), "0", item.getAmount(), item.getPrice(), item.getCategory()));
        }
        return items;
    }

    public static boolean createItem(String name, String description, String amount, String price, String itemCategory) {
        return ItemDB.createItem(name, description, amount, price, itemCategory);
    }

    public static boolean editItem(String id, String name, String description, String amount, String price, String itemCategory) {
        return ItemDB.editItem(id, name, description, amount, price, itemCategory);
    }

    // Ser både View och Model (men vi är i en controller klass? eller aa ett business object)
    protected static Collection<ItemInfo> convertItemsToItemInfo(Collection<ItemDB> itemDBCollection) {
        Collection<ItemInfo> itemInfoCollection = new ArrayList<>();

        for (ItemDB itemDB : itemDBCollection) {
            String itemId = itemDB.getId();
            String itemName = itemDB.getName();
            String itemDesc = itemDB.getDesc();
            String itemQuantity = itemDB.getQuantity();
            String itemAmount = itemDB.getAmount();
            String itemPrice = itemDB.getPrice();
            String category = itemDB.getCategory();

            ItemInfo itemInfo = new ItemInfo(itemId, itemName, itemDesc, itemQuantity, itemAmount, itemPrice, category);
            itemInfoCollection.add(itemInfo);
        }
        return itemInfoCollection;
    }

    protected static Collection<ItemDB> convertItemInfosToItem(Collection<ItemInfo> itemInfoCollection) {
        Collection<ItemDB> itemDBCollection = new ArrayList<>();

        for (ItemInfo item : itemInfoCollection) {
            String itemId = item.getId();
            String itemName = item.getName();
            String itemDesc = item.getDesc();
            String itemQuantity = String.valueOf(item.getQuantity());
            String itemAmount = String.valueOf(item.getAmount());
            String itemPrice = String.valueOf(item.getPrice());
            String category = item.getCategory();

            ItemDB itemDB = ItemDB.createItemDB(itemId, itemName, itemDesc, itemQuantity, itemAmount, itemPrice, category);
            itemDBCollection.add(itemDB);
        }
        return itemDBCollection;
    }

    public static HashSet<String> getCategories(){
        return ItemDB.getCategories();
    }
}

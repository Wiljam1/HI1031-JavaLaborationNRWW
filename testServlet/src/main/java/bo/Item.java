package bo;

import db.ItemDB;
import ui.ItemInfo;

import java.util.ArrayList;
import java.util.Collection;

public class Item {
    private String id;
    private String name;
    private String desc;
    private String quantity;
    private String amount;
    private String price;
    private String category;

    public static Collection searchItems(String group) {
        return ItemDB.searchItems(group);
    }

    public static Collection searchItems() {
        return ItemDB.searchItems();
    }

    protected Item(String id, String name, String desc, String quantity, String amount, String price, String category) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.quantity = quantity;
        this.amount = amount;
        this.price = price;
        this.category = category;
    }

    //Factory
    protected static Item createItem(String id, String name, String desc, String quantity, String amount, String price, String category) {
        return new Item(id, name, desc, quantity, amount, price, category);
    }

    // Ser både View och Model (men vi är i en controller klass? eller aa ett business object)
    public static Collection<ItemInfo> convertItemsToItemInfo(Collection<ItemDB> itemDBCollection) {
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

    public static Collection<ItemDB> convertItemInfosToItem(Collection<ItemInfo> itemInfoCollection) {
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

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getDesc() {
        return desc;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAmount() {
        return this.amount;
    }
    public String getPrice() {
        return this.price;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getQuantity() {
        return quantity;
    }

    public static boolean createItem(String name, String description, String amount, String price, String itemCategory) {
        return ItemDB.createItem(name, description, amount, price, itemCategory);
    }

    public static boolean editItem(String id, String name, String description, String amount, String price, String itemCategory) {
        return ItemDB.editItem(id, name, description, amount, price, itemCategory);
    }

    public String getCategory() { return this.category;}
}

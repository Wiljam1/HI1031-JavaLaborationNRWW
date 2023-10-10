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

    protected Item(String id, String name, String desc, String quantity, String amount, String price, String category) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.quantity = quantity;
        this.amount = amount;
        this.price = price;
        this.category = category;
    }

    // TODO: Ta bort kanske
    //Factory
    protected static Item createItem(String id, String name, String desc, String quantity, String amount, String price, String category) {
        return new Item(id, name, desc, quantity, amount, price, category);
    }

//    public static Collection searchItems(String group) {
//        return ItemDB.searchItems(group);
//    }
//
//    public static Collection searchItems() {
//        return ItemDB.searchItems();
//    }
//
//    public static boolean createItem(String name, String description, String amount, String price, String itemCategory) {
//        return ItemDB.createItem(name, description, amount, price, itemCategory);
//    }
//
//    public static boolean editItem(String id, String name, String description, String amount, String price, String itemCategory) {
//        return ItemDB.editItem(id, name, description, amount, price, itemCategory);
//    }

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

    public String getCategory() { return this.category;}


}

package bo;

import db.ItemDB;
import ui.ItemInfo;

import java.util.ArrayList;
import java.util.Collection;

public class Item {
    private String id;
    private final String name;
    private String desc;
    private final String quantity;
    private String amount;
    private final String price;
    private final String category;

    protected Item(String id, String name, String desc, String quantity, String amount, String price, String category) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.quantity = quantity;
        this.amount = amount;
        this.price = price;
        this.category = category;
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

    public String getAmount() {
        return this.amount;
    }

    public String getPrice() {
        return this.price;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getCategory() { return this.category;}


}

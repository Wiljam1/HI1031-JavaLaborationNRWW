package ui;

public class ItemInfo {
    private String name;
    private String desc;
    private int quantity;
    private String amount;
    private String price;

    public ItemInfo(String name, String desc) {
        this.name = name;
        this.desc = desc;
        this.quantity = 1; //Update in the future (get from database?)
    }

    public ItemInfo(String name) {
        this.name = name;
        this.desc = "";
        this.quantity = 1; //Update in the future (get from database?)
        this.amount = "0";
        this.price = "0";
    }
    public ItemInfo(String name, String desc, String amount, String price) {
        this.name = name;
        this.desc = desc;
        this.quantity = 1; //Update in the future (get from database?)
        this.amount = amount;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public int getQuantity() {
        return quantity;
    }

    public void incrementQuantity() {
        this.quantity++;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

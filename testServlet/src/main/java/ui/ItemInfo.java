package ui;

public class ItemInfo {
    private String name;
    private String desc;
    private int quantity;
    private int amount;
    private int price;

    public ItemInfo(String name, String desc) {
        this.name = name;
        this.desc = desc;
        this.quantity = 1; //Update in the future (get from database?)
    }

    public ItemInfo(String name, int price) {
        this.name = name;
        this.desc = "";
        this.quantity = 1; //Update in the future (get from database?)
        this.amount = 0;
        this.price = price;
    }
    public ItemInfo(String name, String desc, String amount, String price) {
        this.name = name;
        this.desc = desc;
        this.quantity = 1; //Update in the future (get from database?)
        this.amount = Integer.parseInt(amount);
        this.price = Integer.parseInt(price);
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public int getPrice() {
        return price;
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

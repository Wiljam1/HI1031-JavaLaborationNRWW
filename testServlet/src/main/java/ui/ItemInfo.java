package ui;

public class ItemInfo {
    private String name;
    private String desc;
    private int quantity;

    public ItemInfo(String name, String desc) {
        this.name = name;
        this.desc = desc;
        this.quantity = 0; //Update in the future (get from database?)
    }

    public ItemInfo(String name, int quantity) {
        this.name = name;
        this.desc = "";
        this.quantity = quantity;
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

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

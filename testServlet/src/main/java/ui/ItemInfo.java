package ui;

/**
 * Represents information about an item in the store. This class stores details such as item ID, name, description,
 * category, quantity, amount, and price. It provides methods for incrementing and decrementing the quantity of the item.
 */
public class ItemInfo {
    private final String id;
    private final String name;
    private String desc;
    private final String category;
    private int quantity; //TODO: gör om till String
    private final int amount;
    private final int price;
    public ItemInfo(String id, String name, String desc, String quantity, String amount, String price, String category) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.quantity = Integer.parseInt(quantity);
        this.amount = Integer.parseInt(amount);
        this.price = Integer.parseInt(price);
        this.category = category;
    }

    public String getId() {
        return id;
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
    public String getCategory(){return this.category;}

    public void incrementQuantity() {
        this.quantity++;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "ItemInfo{" +
                "name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", quantity=" + quantity +
                ", amount=" + amount +
                ", price=" + price +
                '}';
    }

    public void decrementQuantity() {
        this.quantity--;
    }
}

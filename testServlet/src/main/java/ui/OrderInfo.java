package ui;

import java.util.Collection;
/**
 * Represents information about an order, including its unique identifier, date, items, total cost, and status.
 */
public class OrderInfo {
    private String id;
    private String date;
    private Collection<ItemInfo> items;
    private String totalCost;
    private String status;

    public OrderInfo(String id, String date, Collection<ItemInfo> items, String totalCost, String status) {
        this.id = id;
        this.date = date;
        this.items = items;
        this.totalCost = totalCost;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public Collection<ItemInfo> getItems() {
        return items;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public Object getStatus() {return status;}
}

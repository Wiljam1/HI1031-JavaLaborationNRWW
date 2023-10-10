package ui;

import java.util.Collection;
/**
 * Represents information about an order, including its unique identifier, date, items, total cost, and status.
 */
public class OrderInfo {
    private final String id;
    private final String date;
    private final Collection<ItemInfo> items;
    private final String totalCost;
    private final String status;

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

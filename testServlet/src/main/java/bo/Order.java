package bo;

import ui.ItemInfo;

import java.util.Collection;

public class Order {
    private String id;
    private String date;
    private Collection items;
    private String totalCost;
    private String status;

    protected Order(String id, String date, Collection items, String totalCost, String status) {
        this.id = id;
        this.date = date;
        this.items = items;
        this.totalCost = totalCost;
        this.status = status;
    }

    // Factory
    protected static Order createOrder(String orderId, String orderDate, Collection orderItems, String orderCost, String status) {
        return new Order(orderId, orderDate, orderItems, orderCost, status);
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public Collection getItems() {
        return items;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public String getStatus() {
        return status;
    }
}

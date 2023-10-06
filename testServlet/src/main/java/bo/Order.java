package bo;

import ui.ItemInfo;

import java.util.Collection;

public class Order {
    private String id;
    private String date;
    private Collection items;
    private String totalCost;
    private String assignedStaff;

    protected Order(String id, String date, Collection items, String totalCost, String assignedStaff) {
        this.id = id;
        this.date = date;
        this.items = items;
        this.totalCost = totalCost;
        this.assignedStaff = assignedStaff;
    }

    // Factory
    protected static Order createOrder(String orderId, String orderDate, Collection orderItems, String orderCost, String orderStaff) {
        return new Order(orderId, orderDate, orderItems, orderCost, orderStaff);
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

    public String getAssignedStaff() {
        return assignedStaff;
    }
}

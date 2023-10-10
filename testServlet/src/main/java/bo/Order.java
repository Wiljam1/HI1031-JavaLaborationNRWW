package bo;

import ui.ItemInfo;

import java.util.Collection;
/**
 * The Order class represents an order in the system. It contains order-specific information
 * such as order ID, order date, order items, total cost, and order status.
 */
public class Order {
    private final String id;
    private final String date;
    private final Collection items;
    private final String totalCost;
    private final String status;

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

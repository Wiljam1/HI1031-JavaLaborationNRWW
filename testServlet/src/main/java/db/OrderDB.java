package db;

import java.util.Collection;
/**
 * Extension of the Order class that represents an order with database-specific functionality.
 * This class extends the base Order class and provides methods for creating order instances.
 */
public class OrderDB extends bo.Order{

    private OrderDB(String id, String date, Collection items, String totalCost, String status) {
        super(id, date, items, totalCost, status);
    }

    //Factory
    protected static OrderDB createOrder(String orderId, String orderDate, Collection orderItems, String orderCost, String status) {
        return new OrderDB(orderId, orderDate, orderItems, orderCost, status);
    }
}

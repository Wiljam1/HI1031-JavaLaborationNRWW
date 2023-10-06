package db;

import java.util.Collection;

public class OrderDB extends bo.Order{

    private OrderDB(String id, String date, Collection items, String totalCost, String assignedStaff) {
        super(id, date, items, totalCost, assignedStaff);
    }

    //Factory
    protected static OrderDB createOrder(String orderId, String orderDate, Collection orderItems, String orderCost, String orderStaff) {
        return new OrderDB(orderId, orderDate, orderItems, orderCost, orderStaff);
    }
}

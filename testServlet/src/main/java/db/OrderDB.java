package db;

import java.util.Collection;

public class OrderDB extends bo.Order{

    private OrderDB(String id, String date, Collection items, String totalCost, String status) {
        super(id, date, items, totalCost, status);
    }

    //Factory
    protected static OrderDB createOrder(String orderId, String orderDate, Collection orderItems, String orderCost, String status) {
        return new OrderDB(orderId, orderDate, orderItems, orderCost, status);
    }
}

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

    //TODO: Fixa status för varje order (ej packad, packad), för att vara på den säkra sidan
    // ...när man packar kan personalen som packades namn dyka upp (assigned staff)
    // ... "Lagerpersonal ska kunna titta på ordrar och 'packa' dem."
}

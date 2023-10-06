package bo;

import db.Authorization;
import db.ItemDB;
import db.OrderDB;
import db.UserDB;
import ui.ItemInfo;
import ui.OrderInfo;

import java.util.ArrayList;
import java.util.Collection;

public class User {
    private String username;
    private String name;
    private Collection<ItemInfo> cart;
    private String authorization;
    private Collection orders; //Vet inte om det ska vara Collection<Order> eller bara Collection

    protected User(String username, String name, String authorization) {
        this.username = username;
        this.name = name;
        this.authorization = authorization;
        this.cart = null;
        this.orders = null; //Kanske borde hämta existerande ordrar från databasen?
    }

    protected User(String username, String name, String authorization, Collection orders) {
        this.username = username;
        this.name = name;
        this.authorization = authorization;
        this.cart = null;
        this.orders = orders;
    }

    public static boolean createUser(String username, String name, String password, Authorization authorization) {
        return UserDB.createUser(username, name, password, authorization);
    }

    public static boolean initTransaction(String username, Collection<ItemInfo> cart, String finalPrice) {
        Collection<ItemDB> cartItems = Item.convertItemInfosToItem(cart);
        return UserDB.performTransaction(username, cartItems, finalPrice);
    }

    public Collection getOrders() {
        return orders;
    }

    // Ser både view och model?
    public static Collection<OrderInfo> convertToOrderInfo(Collection<OrderDB> orderDBCollection) {
        Collection<OrderInfo> orderInfoCollection = new ArrayList<>();

        for (OrderDB orderDB : orderDBCollection) {
            String orderId = orderDB.getId();
            String orderDate = orderDB.getDate();
            System.out.println("converting, getItems(): " + orderDB.getItems());
            Collection<ItemInfo> orderItems = Item.convertItemsToItemInfo(orderDB.getItems()); // Kanske inte funkar
            String orderCost = orderDB.getTotalCost();
            String orderStaff = orderDB.getAssignedStaff();

            OrderInfo orderInfo = new OrderInfo(orderId, orderDate, orderItems, orderCost, orderStaff);

            orderInfoCollection.add(orderInfo);
        }

        return orderInfoCollection;
    }

    public static Collection fetchOrders(String username) {
        return UserDB.fetchOrders(username);
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public Collection<ItemInfo> getCart() {
        return cart;
    }

    public static User searchUser(String username) {
        return UserDB.searchUser(username);
    }

    public static Collection getAllUsers() {
        return UserDB.getAllUsers();
    }

    public String getAuthorizationLevel() { return authorization ;}

    public String getAuthorization() {
        return authorization;
    }


}

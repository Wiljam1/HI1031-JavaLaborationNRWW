package bo;

import db.Authorization;
import db.ItemDB;
import db.OrderDB;
import db.UserDB;
import org.bson.Document;
import ui.ItemInfo;
import ui.OrderInfo;
import ui.UserInfo;

import java.util.ArrayList;
import java.util.Collection;
/**
 * The UserHandler class provides methods for handling user-related operations, including
 * user authentication, user creation, and retrieving user information.
 */
public class UserHandler {

    @SuppressWarnings("unchecked")
    public static UserInfo getUserInfo(String username) {
        User u = UserDB.searchUser(username);
        Collection<OrderInfo> orders = convertToOrderInfo(u.getOrders());
        return new UserInfo(u.getUsername(), u.getName(), u.getCart(), u.getAuthorizationLevel(), orders);
    }

    public static boolean createUser(String username, String name, String password, Authorization authorization) {
        return UserDB.createUser(username, name, password, authorization);
    }

    public static boolean authenticateUser(String username, String password) {

        Document userDoc = UserDB.getUserDocument(username);

        if (userDoc != null) {
            String DBPassword = userDoc.getString("password");
            return password.equals(DBPassword);
        }
        return false;
    }

    public static boolean addOrder(String username, Collection<ItemInfo> cart, String finalPrice) {
        Collection<ItemDB> cartItems = ItemHandler.convertItemInfosToItem(cart);
        return UserDB.addOrderDB(username, cartItems, finalPrice);
    }

    @SuppressWarnings("unchecked")
    public static Collection<UserInfo> getAllUsers() {
        Collection c = UserDB.getAllUsers();
        ArrayList<UserInfo> users = new ArrayList<>();
        for (Object o : c) {
            User u = (User) o;
            Collection<OrderInfo> orders = convertToOrderInfo(u.getOrders());
            users.add(new UserInfo(u.getUsername(), u.getName(), u.getCart(), u.getAuthorization(), orders));
        }
        return users;
    }

    @SuppressWarnings("unchecked")
    protected static Collection<OrderInfo> convertToOrderInfo(Collection<OrderDB> orderDBCollection) {
        Collection<OrderInfo> orderInfoCollection = new ArrayList<>();

        if(orderDBCollection != null) {
            for (OrderDB orderDB : orderDBCollection) {
                String orderId = orderDB.getId();
                String orderDate = orderDB.getDate();
                Collection<ItemInfo> orderItems = ItemHandler.convertItemsToItemInfo(orderDB.getItems());
                String orderCost = orderDB.getTotalCost();
                String status = orderDB.getStatus();

                OrderInfo orderInfo = new OrderInfo(orderId, orderDate, orderItems, orderCost, status);

                orderInfoCollection.add(orderInfo);
            }
        }
        return orderInfoCollection;
    }

    public static void removeOrder(String username, String transaction) {
        UserDB.removeOrderWithTransaction(username, transaction);
    }

    public static boolean editUser(String username, String name, Authorization authorization) {
        return UserDB.editUser(username,name,authorization);
    }

    public static boolean deleteUser(String username) {
        return UserDB.deleteUser(username);
    }

    public static void orderIsPacked(String username, String transactionId) {
        UserDB.orderIsPackedDB(username,transactionId);
    }
}

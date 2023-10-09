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

public class UserHandler {

    @SuppressWarnings("unchecked")
    public static UserInfo getUserInfo(String username) {
        User u = User.searchUser(username);
        Collection<OrderInfo> orders = convertToOrderInfo(u.getOrders());
        return new UserInfo(u.getUsername(), u.getName(), u.getCart(), u.getAuthorizationLevel(), orders);
    }

    public static boolean createUser(String username, String name, String password, Authorization authorization) {
        return User.createUser(username, name, password, authorization);
    }

    public static boolean authenticateUser(String username, String password) {

        Document userDoc = UserDB.getUserDocument(username);

        if (userDoc != null) {
            String DBPassword = userDoc.getString("password");
            if (password.equals(DBPassword)) {
                return true;
            }
        }
        return false;
    }

    public static boolean addOrder(String username, Collection<ItemInfo> cart, String finalPrice) {
        Collection<ItemDB> cartItems = ItemHandler.convertItemInfosToItem(cart);
        return UserDB.addOrderDB(username, cartItems, finalPrice);
    }

    @SuppressWarnings("unchecked")
    public static Collection<UserInfo> getAllUsers() {
        Collection c = User.getAllUsers();
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
                String orderStaff = orderDB.getAssignedStaff();

                OrderInfo orderInfo = new OrderInfo(orderId, orderDate, orderItems, orderCost, orderStaff);

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

}

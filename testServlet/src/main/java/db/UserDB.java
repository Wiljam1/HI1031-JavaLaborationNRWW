package db;

import bo.User;
import com.mongodb.MongoException;
import com.mongodb.client.ClientSession;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;
import static db.ItemDB.createItemFromDocument;
/**
 * Extension of the User class that provides database-related functionality for user management.
 * This class handles tasks such as searching for users, creating users, adding orders, editing user profiles,
 * deleting users, and updating order statuses in the database.
 */
public class UserDB extends bo.User{

    public static User searchUser(String searchedUsername) {
        User user = null;
        try {
            MongoCollection<Document> collection = DBManager.getCollection("users");
            Bson filter = eq("username", searchedUsername);
            FindIterable<Document> results = collection.find(filter);

            for(Document doc : results) {
                String username = doc.getString("username");
                String name = doc.getString("name");
                String authorization = doc.getString("authorization");
                List<Document> ordersList = doc.getList("orders", Document.class);
                Collection<OrderDB> allOrders = processOrderDocuments(ordersList);
                user = new UserDB(username, name, authorization, allOrders);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }


    public static Collection getAllUsers() {
        Collection<Object> users = new ArrayList<>();
        try {
            MongoCollection<Document> collection = DBManager.getCollection("users");

            for(Document doc : collection.find()) {
                String username = doc.getString("username");
                String name = doc.getString("name");
                String authorization = doc.getString("authorization");
                List<Document> ordersList = doc.getList("orders", Document.class);
                Collection allOrders = processOrderDocuments(ordersList);

                users.add(new UserDB(username, name, authorization, allOrders));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }

    public static Document getUserDocument(String username) {
        return DBManager.getCollection("users").find(new Document("username", username)).first();
    }

    public static Collection fetchOrders(String username) {
        User user = searchUser(username);
        if (user != null) {
            Collection orders = user.getOrders();
            if(orders != null) {
                return orders;
            }
            else{
                return new ArrayList();
            }
        } else {
            return new ArrayList();
        }
    }

    private static Collection<OrderDB> processOrderDocuments(List<Document> ordersList) {
        try {
            if (ordersList == null || ordersList.isEmpty())
                return null;

            Collection<OrderDB> allOrders = new ArrayList<>();

            for (Document order : ordersList) {
                String orderId = order.getString("id");
                String orderDate = order.getString("date");
                Collection<ItemDB> orderItems = createItemsFromDocumentList(order.getList("items", Document.class));
                String orderCost = order.getString("totalCost");
                String status = order.getString("status");

                OrderDB createdOrder = OrderDB.createOrder(orderId, orderDate, orderItems, orderCost, status);
                allOrders.add(createdOrder);
            }
            return allOrders;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Collection<ItemDB> createItemsFromDocumentList(List<Document> itemsList) {
        if (itemsList == null)
            return null;

        Collection<ItemDB> orderItems = new ArrayList<>();
        for (Document itemDoc : itemsList) {
            ItemDB item = createItemFromDocument(itemDoc);
            orderItems.add(item);
        }
        return orderItems;
    }

    public static boolean createUser(String username, String name, String password, Authorization authorization) {
        try {
            if (username.length() < 3 || username.length() > 12 ||
                    name.length() < 3 || name.length() > 12 ||
                    password.length() < 3 || password.length() > 16) {
                return false;
            }

            MongoCollection<Document> collection = DBManager.getCollection("users");
            for (Document doc : collection.find()){
                if (doc.get("username", username).equals(username)){
                    return false;
                }
            }
            Document userDoc = new Document()
                    .append("username", username)
                    .append("name", name)
                    .append("password", password)
                    .append("authorization", authorization.toString())
                    .append("orders", new ArrayList<>());

            collection.insertOne(userDoc);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public static boolean addOrderDB(String username, Collection<ItemDB> cart, String finalPrice) {
        MongoClient mongoClient = DBManager.getInstance().getMongoClient();
        ClientSession session = mongoClient.startSession();
        MongoCollection<Document> collection = DBManager.getCollection("users");

        try {
            session.startTransaction();
            Collection<OrderDB> existingOrders = fetchOrders(username);

            int largestId = existingOrders.stream()
                    .mapToInt(orderDB -> Integer.parseInt(orderDB.getId()))
                    .max()
                    .orElse(0);

            Date date = new Date();
            Document order = new Document()
                    .append("id", String.valueOf(largestId + 1))
                    .append("date", date.toString())
                    .append("totalCost", finalPrice)
                    .append("status", "not Packed");

            ArrayList<Document> itemsList = new ArrayList<>();

            for (ItemDB item : cart) {
                Document itemDocument = new Document()
                        .append("id", item.getId())
                        .append("name", item.getName())
                        .append("description", item.getDesc())
                        .append("amount", String.valueOf(item.getAmount()))
                        .append("price", String.valueOf(item.getPrice()))
                        .append("quantity", String.valueOf(item.getQuantity()))
                        .append("category", item.getCategory());

                itemsList.add(itemDocument);

                ItemDB.modifyItemAmount(item.getName(),item.getQuantity(),session);
            }
            order.append("items", itemsList);
            existingOrders.add(OrderDB.createOrder(
                    order.getString("id"),
                    order.getString("date"),
                    cart,
                    finalPrice,
                    "not Packed"
            ));

            Bson filterUsername = eq("username", username);
            List<Document> orderList = existingOrders.stream()
                    .map(UserDB::addExistingOrders)
                    .collect(Collectors.toList());

            collection.updateOne(session, filterUsername, Updates.set("orders", orderList));
            session.commitTransaction();
        } catch (MongoException e) {
            e.printStackTrace();
            session.abortTransaction();
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    public static Document addExistingOrders(OrderDB cart){
        Document order = new Document();
        ArrayList<Document> itemsList = new ArrayList<>();

        order.append("id", cart.getId())
                .append("date", cart.getDate())
                .append("totalCost", cart.getTotalCost())
                .append("status", cart.getStatus());

        for (Object item: cart.getItems()){
            if (item instanceof ItemDB) {
                Document itemDocument = new Document();
                itemDocument.append("id", ((ItemDB) item).getId())
                        .append("name", ((ItemDB) item).getName())
                        .append("description", ((ItemDB) item).getDesc())
                        .append("amount", ((ItemDB) item).getAmount())
                        .append("price", ((ItemDB) item).getPrice())
                        .append("quantity", ((ItemDB) item).getQuantity())
                        .append("category", ((ItemDB) item).getCategory());

                itemsList.add(itemDocument);
            }
        }
        order.append("items", itemsList);
        return order;
    }

    private UserDB(String username, String name, String authorization, Collection orders) {
        super(username, name, authorization, orders);
    }

    public static void removeOrderWithTransaction(String username, String transactionId) {
        MongoClient mongoClient = DBManager.getInstance().getMongoClient();
        ClientSession session = mongoClient.startSession();
        MongoCollection<Document> collection = DBManager.getCollection("users");
        Bson filterUsername = eq("username", username);
        try {
            session.startTransaction();
            ArrayList<Document> orderList = new ArrayList<>();
            Collection generalOrders = fetchOrders(username);
            Collection<OrderDB> existingOrders = new ArrayList<>();

            for(Object o : generalOrders) {
                OrderDB orderDB = (OrderDB) o;
                existingOrders.add(orderDB);
            }

            for (OrderDB orderDB: existingOrders) {
                if (orderDB.getId().equals(transactionId)){
                    //Removes the item by not adding it
                }
                else {
                     orderList.add(addExistingOrders(orderDB));
                }
            }
            collection.updateOne(session, filterUsername, Updates.set("orders", orderList));

            session.commitTransaction();
        } catch (MongoException e) {
            e.printStackTrace();
            session.abortTransaction();
        } finally {
            session.close();
        }
    }

    public static boolean editUser(String username, String name, Authorization authorization) {
        try {
            if (username.length() < 3 || username.length() > 12 || name.length() < 3 || name.length() > 12) {
                return false;
            }
            MongoCollection<Document> collection = DBManager.getCollection("users");

            for (Document doc : collection.find()) {
                if (doc.get("username").equals(username)) {
                    Document updateDoc = new Document("$set", new Document("name", name)
                            .append("authorization", authorization.toString().toLowerCase()));

                    collection.updateOne(eq("username", username), updateDoc);
                    break;
                }
            }
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteUser(String username) {
        try {
            MongoCollection<Document> collection = DBManager.getCollection("users");

            for (Document doc : collection.find()) {
                if (doc.get("username").equals(username)) {
                    collection.deleteOne(eq("username", username));
                    break;
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void orderIsPackedDB(String username, String transactionId) {
        try {
            MongoCollection<Document> collection = DBManager.getCollection("users");

            Document filter = new Document("username", username)
                    .append("orders.id", transactionId);

            Document update = new Document("$set", new Document("orders.$.status", "Packed"));

            collection.updateOne(filter, update);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

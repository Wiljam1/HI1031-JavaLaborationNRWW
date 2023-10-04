package db;

import bo.Order;
import bo.User;
import com.mongodb.MongoException;
import com.mongodb.client.ClientSession;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import ui.ItemInfo;
import ui.OrderInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDB extends bo.User{

    //Kanske dåligt att den här returnar en User? Kanske bör returna något mer generellt
    public static User searchUser(String searchedUsername) {
        User user = null;
        try {
            MongoCollection<Document> collection = DBManager.getCollection("users");
            Bson filter = Filters.eq("username", searchedUsername);
            FindIterable<Document> results = collection.find(filter);

            for(Document doc : results) {
                String username = doc.getString("username");
                String name = doc.getString("name");
                String authorization = doc.getString("authorization");
                user = new UserDB(username, name, authorization);
            }

        }
        catch (Exception e) {
            //Robust logging implementation?
            e.printStackTrace();
        }
        return user;
    }

    public static boolean createUser(String username, String name, String password, Authorization authorization) {
        try {
            MongoCollection<Document> collection = DBManager.getCollection("users");

            Document userDoc = new Document()
                    .append("username", username)
                    .append("name", name)
                    .append("password", password)
                    .append("authorization", authorization.toString())
                    .append("orders", "");

            collection.insertOne(userDoc);
            return true;
        }
        catch (Exception e) {
            //Robust logging implementation?
            e.printStackTrace();
            return false;
        }
    }

    // TODO: Kolla om man kan få bort ItemInfo importeringen här
    public static boolean performTransaction(String username, Collection<ItemInfo> cart) {
        MongoClient mongoClient = DBManager.getInstance().getMongoClient();
        ClientSession session = mongoClient.startSession();
        MongoCollection<Document> collection = DBManager.getCollection("users"); // kanske ska vara inne i transaktionen?
        try {
            session.startTransaction();

            /*
            for(Document doc : collection.find()) {
                String u = doc.getString("username");
                if (username.equals(u)){
                    Document order = new Document("order", cart);
                    doc.("authorization", "admin");
                    System.out.println("Updated user");
                }
            }
            */
            Bson filter = Filters.eq("username", username);
            Document updateDoc = new Document("$set", new Document("authorization", "admin"));
            collection.updateOne(session,filter,updateDoc);
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

    // TODO: Missledande namn? hämtar endast anvädarnamnet för en användare
    public static Document getUserDocument(String username) {
        return DBManager.getCollection("users").find(new Document("username", username)).first();
    }

    public static Collection getOrders(String username) {
        try {
            Document userDoc = getUserDocument(username);
            List<Document> orders = userDoc.getList("orders", Document.class);
            Collection allOrders = new ArrayList<>();

            for(Document orderDoc : orders) {
                String orderId = orderDoc.getString("id");
                String orderDate = orderDoc.getString("date");
                Collection<ItemInfo> orderItems = new ArrayList<>();
                List<Document> items = orderDoc.getList("items", Document.class);
                for(Document itemDoc : items) {
                    String itemId = itemDoc.getString("id");
                    String itemAmount = itemDoc.getString("amount");

                    ItemDB item = (ItemDB) ItemDB.searchItem(itemId);   // Skum lösning här
                    item.setAmount(itemAmount);
                    //Vad som ska visas i gettern
                    ItemInfo itemInfo = new ItemInfo(item.getName(), item.getDesc(), item.getAmount(), item.getPrice());
                    orderItems.add(itemInfo);
                }
                String orderCost = orderDoc.getString("totalCost");
                String orderStaff = orderDoc.getString("assignedStaff");
                // TODO: Vet inte om man kan ha OrderInfo här i DB.
                allOrders.add(new OrderInfo(orderId, orderDate, orderItems, orderCost, orderStaff));
            }
            return allOrders;
        }
        catch (Exception e) {
            //Robust logging implementation?
            e.printStackTrace();
            return null;
        }
    }

    private UserDB(String username, String name, String authorization) {
        super(username, name, authorization);
    }



}

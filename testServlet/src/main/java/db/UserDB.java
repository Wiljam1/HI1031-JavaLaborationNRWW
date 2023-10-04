package db;

import bo.Order;
import bo.Item;
import bo.User;
import com.mongodb.MongoException;
import com.mongodb.client.ClientSession;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;
import ui.ItemInfo;
import ui.OrderInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class UserDB extends bo.User{

    //Kanske dåligt att den här returnar en User? Kanske bör returna något mer generellt
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
        MongoCollection<Document> collectionItems = DBManager.getCollection("items");
        try {
            session.startTransaction();

            Document cartDocument = new Document();
            Bson filter = eq("username", username);
            ArrayList<Document> documents = new ArrayList<>();

            for (ItemInfo item: cart) {

                Document itemDocument = new Document();
                String desc = item.getDesc();
                String name = item.getName();
                String amount = String.valueOf(item.getAmount());
                String price = String.valueOf(item.getPrice());
                String quantity = String.valueOf(item.getQuantity());
                itemDocument.append("id", "0")              //still hard coded value
                        .append("name", name)
                        .append("description", desc)
                        .append("amount", amount)
                        .append("price", price)
                        .append("quantity", quantity);
                //cartDocument.append("item", itemDocument);
                documents.add(itemDocument);

                Bson filterItems = eq("name", name);
                Document items = collectionItems.find(filterItems).first();

                if (items != null){
                    int quantityItems = Integer.parseInt(items.getString("amount"));
                    System.out.println(quantityItems);
                    quantityItems -= Integer.parseInt(quantity);
                    if (quantityItems < 0){
                        session.abortTransaction();
                    }
                    items.put("amount", quantityItems);
                    collectionItems.updateOne(session,filterItems, Updates.set("amount", String.valueOf(quantityItems)));

                }

            }
            cartDocument.append("items", documents);
            // ADD WHAT MORE THAT NEEDS TO BE IN THE ORDER
            //cartDocument.append()
            //collection.insertOne(session, cartDocument);
            collection.updateOne(session, filter, Updates.set("orders", cartDocument));



            session.commitTransaction();
            System.out.println("Added cart to user's orders");



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

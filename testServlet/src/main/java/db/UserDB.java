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
import ui.ItemInfo;
import ui.OrderInfo;

import java.util.*;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.or;

public class UserDB extends bo.User{

    //Kanske dåligt att den här returnar en User? Kanske bör returna något mer generellt
    public static User searchUser(String searchedUsername) {
        User user = null;
        try {
            MongoCollection<Document> collection = DBManager.getCollection("users");
            Bson filter = eq("username", searchedUsername);
            FindIterable<Document> results = collection.find(filter); //Kanske kan göras om med getUserDocument

            for(Document doc : results) {
                String username = doc.getString("username");
                String name = doc.getString("name");
                String authorization = doc.getString("authorization");
                List<Document> ordersList = doc.getList("orders", Document.class);
                Collection allOrders = processOrderDocuments(ordersList);

                user = new UserDB(username, name, authorization, allOrders);
            }

        }
        catch (Exception e) {
            //Robust logging implementation?
            e.printStackTrace();
        }
        return user;
    }

    public static Collection fetchOrders(String username) {
        User user = searchUser(username);
        if (user != null) {
            return user.getOrders();
        } else {
            return Collections.emptyList();
        }
    }

    private static Collection processOrderDocuments(List<Document> ordersList) {
        try {
            if (ordersList == null || ordersList.isEmpty())
                return null; // no orders found

            Collection allOrders = new ArrayList<>();

            for(Document order : ordersList) {
                String orderId = order.getString("id");
                String orderDate = order.getString("date");
                Collection<ItemInfo> orderItems = new ArrayList<>();
                List<Document> items = order.getList("items", Document.class);
                if(items != null)
                    for(Document itemDoc : items) {
                        String itemId = itemDoc.getString("id");
                        String itemAmount = itemDoc.getString("amount");

                        ItemDB itemObject = ItemDB.searchItem(itemId);

                        if(itemObject != null) { //tror inte if-satsen behövs
                            itemObject.setAmount(itemAmount);
                            ItemInfo itemInfo = new ItemInfo(itemObject.getName(), itemObject.getDesc(), itemObject.getAmount(), itemObject.getPrice());
                            orderItems.add(itemInfo);
                        }
                        else {
                            System.out.println("Invalid item: " + itemObject);
                            return null; //invalid item found? Kanske ta bort else satsen senare
                        }

                    }
                String orderCost = order.getString("totalCost");
                String orderStaff = order.getString("assignedStaff");
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

    public static boolean createUser(String username, String name, String password, Authorization authorization) {
        try {
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
    public static boolean performTransaction(String username, Collection<ItemInfo> cart, String finalPrice) {
        MongoClient mongoClient = DBManager.getInstance().getMongoClient();
        ClientSession session = mongoClient.startSession();
        MongoCollection<Document> collection = DBManager.getCollection("users"); // kanske ska vara inne i transaktionen?
        MongoCollection<Document> collectionItems = DBManager.getCollection("items");

        try {
            session.startTransaction();

            // Byt plats på detta
            ArrayList<Document> orderList = new ArrayList<>();
            Bson filterUsername = eq("username", username);
            Date date = new Date();
            Document order = new Document();

            //handles old orders
            Collection fakeColl = fetchOrders(username);
            Collection<OrderInfo> coll = new ArrayList<>();

            for(Object o : fakeColl) {
                OrderInfo orderInfo = (OrderInfo) o;
                coll.add(orderInfo);
            }

            int largestId = 0;
            if (coll != null){
                for (OrderInfo orderInfo: coll){
                    orderList.add(addExistingOrders(orderInfo));
                    int id = Integer.parseInt(orderInfo.getId());
                    if (id > largestId){
                        largestId = id;
                    }
                }
                largestId++;
            }
            order.append("id", String.valueOf(largestId))
                    .append("date", date.toString())
                    .append("totalCost", finalPrice)
                    .append("assignedStaff", "ingen");
            ArrayList<Document> itemsList = new ArrayList<>();
            for (ItemInfo item: cart) {

                Document itemDocument = new Document();
                String desc = item.getDesc();
                String name = item.getName();
                String amount = String.valueOf(item.getAmount());
                String price = String.valueOf(item.getPrice());
                String quantity = String.valueOf(item.getQuantity());
                itemDocument.append("id", "2")              //still hard coded value
                        .append("name", name)
                        .append("description", desc)
                        .append("amount", amount)
                        .append("price", price)
                        .append("quantity", quantity);
                itemsList.add(itemDocument);

                //For changing item amount in database
                //could be own method
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
                //
            }
            order.append("items", itemsList);
            orderList.add(order);
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

    public static Document addExistingOrders(OrderInfo cart){
        Document order = new Document();
        ArrayList<Document> itemsList = new ArrayList<>();

        order.append("id", cart.getId())
                .append("date", cart.getDate())
                .append("totalCost", cart.getTotalCost())
                .append("assignedStaff", cart.getAssignedStaff());

        for (ItemInfo item: cart.getItems()){
            Document itemDocument = new Document();
            String desc = item.getDesc();
            String name = item.getName();
            String amount = String.valueOf(item.getAmount());
            String price = String.valueOf(item.getPrice());
            String quantity = String.valueOf(item.getQuantity());
            itemDocument.append("id", "2")              //still hard coded value
                    .append("name", name)
                    .append("description", desc)
                    .append("amount", amount)
                    .append("price", price)
                    .append("quantity", quantity);
            itemsList.add(itemDocument);
        }

        order.append("items", itemsList);
        return order;
    }

    // TODO: Missledande namn? hämtar endast anvädarnamnet för en användare

    public static Document getUserDocument(String username) {
        return DBManager.getCollection("users").find(new Document("username", username)).first();
    }

    private UserDB(String username, String name, String authorization, Collection orders) {
        super(username, name, authorization, orders);
    }
}

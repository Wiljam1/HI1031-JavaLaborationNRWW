package db;

import com.mongodb.client.ClientSession;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Collection;

public class ItemDB extends bo.Item {

    //Tar just nu alla items i collection
    public static Collection searchItems(String item_group) {
        Collection<Object> items = new ArrayList<>();
        try {
            MongoCollection<Document> collection = DBManager.getCollection("items");

            for(Document doc : collection.find()) {
                String id = doc.getString("id");
                String name = doc.getString("name");
                String desc = doc.getString("description");
                String quantity = doc.getString("quantity");
                String amount = doc.getString("amount");
                String price = doc.getString("price");
                items.add(new ItemDB(id, name, desc, quantity, amount, price));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    public static ItemDB searchItem(String itemId) {
        ItemDB item = null;
        try {
            MongoCollection<Document> collection = DBManager.getCollection("items");

            Document filter = new Document("id", itemId);
            FindIterable<Document> result = collection.find(filter);

            for(Document doc : result) {
                String id = doc.getString("id");
                String name = doc.getString("name");
                String desc = doc.getString("description");
                String quantity = doc.getString("quantity");
                String amount = doc.getString("amount");
                String price = doc.getString("price");
                item = new ItemDB(id, name, desc, quantity, amount, price);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }

    private ItemDB(String id, String name, String desc, String quantity, String amount, String price) {
        super(id, name, desc, quantity, amount, price);
    }

    public static boolean createItem(String name, String description, String amount, String price) {
        MongoClient mongoClient = DBManager.getInstance().getMongoClient();
        ClientSession session = mongoClient.startSession();
        try {
            session.startTransaction();
            MongoCollection<Document> collection = DBManager.getCollection("items");
            int largestId = 0;
            for (Document doc: collection.find()){
                int id = Integer.parseInt(doc.getString("id"));
                if (id > largestId){
                    largestId = id;
                }
            }
            largestId++;
            Document itemDoc = new Document()
                    .append("id", String.valueOf(largestId))
                    .append("name", name)
                    .append("description", description)
                    .append("amount", amount)
                    .append("price", price);

            collection.insertOne(itemDoc);
            session.commitTransaction();

        }
        catch (Exception e) {
            e.printStackTrace();
            session.abortTransaction();
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    public static boolean editItem(String id, String name, String description, String amount, String price) {
        MongoClient mongoClient = DBManager.getInstance().getMongoClient();
        ClientSession session = mongoClient.startSession();
        try {
            session.startTransaction();
            MongoCollection<Document> collection = DBManager.getCollection("items");
            ItemDB item = searchItem(id);

            Bson updateQuery = Updates.combine(
                    Updates.set("name", name),
                    Updates.set("description", description),
                    Updates.set("amount", amount),
                    Updates.set("price", price)
            );

            Bson filter = Filters.eq("id", item.getId());

            collection.updateOne(filter, updateQuery);

            session.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            session.abortTransaction();
            return false;
        } finally {
            session.close();
        }
        return true;
    }

}

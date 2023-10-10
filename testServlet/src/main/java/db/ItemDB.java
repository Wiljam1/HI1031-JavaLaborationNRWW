package db;

import com.mongodb.client.ClientSession;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import static com.mongodb.client.model.Filters.eq;

/**
 * The ItemDB class represents items stored in a MongoDB database.
 * It provides methods for searching, creating, editing, and managing items.
 * This class extends the bo.Item class.
 */
public class ItemDB extends bo.Item {

    public static Collection<ItemDB> searchItems(String filter) {
        Collection<ItemDB> items = new ArrayList<>();
        try {
            MongoCollection<Document> collection = DBManager.getCollection("items");

            Bson filterCategory = eq("category", filter);

            for (Document doc : collection.find(filterCategory)) {
                items.add(createItemFromDocument(doc));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    public static Collection<ItemDB> searchItems() {
        Collection<ItemDB> items = new ArrayList<>();
        try {
            MongoCollection<Document> collection = DBManager.getCollection("items");

            for (Document doc : collection.find()) {
                items.add(createItemFromDocument(doc));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    public static ItemDB searchItem(String itemId) {
        try {
            MongoCollection<Document> collection = DBManager.getCollection("items");
            Document filter = new Document("id", itemId);
            FindIterable<Document> result = collection.find(filter);

            for (Document doc : result) {
                return createItemFromDocument(doc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static ItemDB createItemFromDocument(Document doc) {
        String id = doc.getString("id");
        String name = doc.getString("name");
        String desc = doc.getString("description");
        String quantity = doc.getString("quantity");
        String amount = doc.getString("amount");
        String price = doc.getString("price");
        String category = doc.getString("category");

        return ItemDB.createItemDB(id, name, desc, quantity, amount, price, category);
    }

    private ItemDB(String id, String name, String desc, String quantity, String amount, String price, String category) {
        super(id, name, desc, quantity, amount, price, category);
    }

    //Factory
    public static ItemDB createItemDB(String id, String name, String desc, String quantity, String amount, String price, String category) {
        return new ItemDB(id, name, desc, quantity, amount, price, category);
    }

    public static boolean createItem(String name, String description, String amount, String price, String itemCategory) {
        MongoClient mongoClient = DBManager.getInstance().getMongoClient();

        try (ClientSession session = mongoClient.startSession()) {
            session.startTransaction();
            MongoCollection<Document> collection = DBManager.getCollection("items");

            int largestId = findLargestItemId(collection);
            largestId++;

            Document itemDoc = new Document()
                    .append("id", String.valueOf(largestId))
                    .append("name", name)
                    .append("description", description)
                    .append("amount", amount)
                    .append("price", price)
                    .append("category", itemCategory);

            collection.insertOne(itemDoc);
            session.commitTransaction();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static int findLargestItemId(MongoCollection<Document> collection) {
        int largestId = 0;
        for (Document doc : collection.find()) {
            int id = Integer.parseInt(doc.getString("id"));
            if (id > largestId) {
                largestId = id;
            }
        }
        return largestId;
    }

    public static boolean editItem(String id, String name, String description, String amount, String price, String itemCategory) {
        MongoClient mongoClient = DBManager.getInstance().getMongoClient();

        try (ClientSession session = mongoClient.startSession()) {
            session.startTransaction();
            MongoCollection<Document> collection = DBManager.getCollection("items");
            ItemDB item = searchItem(id);

            Bson updateQuery = Updates.combine(
                    Updates.set("name", name),
                    Updates.set("description", description),
                    Updates.set("amount", amount),
                    Updates.set("price", price),
                    Updates.set("category", itemCategory)
            );

            Bson filter = eq("id", item.getId());
            collection.updateOne(filter, updateQuery);

            session.commitTransaction();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    public static HashSet<String> getCategories(){
        HashSet<String> categories = new HashSet<>();
        MongoCollection<Document> collection = DBManager.getCollection("items");
        for (Document doc: collection.find()){
            categories.add(doc.getString("category"));
        }
        return categories;
    }

    public static void modifyItemAmount(String itemName, String quantity, ClientSession session) {
        MongoCollection<Document> collectionItems = DBManager.getCollection("items");
        Bson filterItems = eq("name", itemName);

        Document item = collectionItems.find(filterItems).first();
        if (item != null) {
            int currentAmount = Integer.parseInt(item.getString("amount"));
            int updatedAmount = currentAmount - Integer.parseInt(quantity);

            if (updatedAmount < 0) {
                session.abortTransaction();
                return;
            }

            item.put("amount", String.valueOf(updatedAmount));
            collectionItems.updateOne(session, filterItems, Updates.set("amount", String.valueOf(updatedAmount)));
        }
    }
}

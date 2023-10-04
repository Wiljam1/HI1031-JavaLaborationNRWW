package db;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

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
                String amount = doc.getString("amount");
                String price = doc.getString("price");
                items.add(new ItemDB(id, name, desc, amount, price));
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
                String amount = doc.getString("amount");
                String price = doc.getString("price");
                item = new ItemDB(id, name, desc, amount, price);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }


    private ItemDB(String id, String name, String desc, String amount, String price) {
        super(id, name, desc, amount, price);
    }
}

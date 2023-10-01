package db;

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
                items.add(new ItemDB(id, name, desc));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    private ItemDB(String id, String name, String desc) {
        super(id, name, desc);
    }
}

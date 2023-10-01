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
            MongoCollection<Document> collection = DBManager.getCollection();

            for(Document doc : collection.find()) {
                String id = doc.getString("id");
                String desc = doc.getString("desc");
                items.add(new ItemDB(id, desc));
            }

        }
        catch (Exception e) {
            //Robust logging implementation?
            e.printStackTrace();
        }
        return items;
    }

    private ItemDB(String id, String desc) {
        super(id, desc);
    }
}

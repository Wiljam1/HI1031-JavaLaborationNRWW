package db;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.Collection;

public class UserDB extends bo.User{

    public static Object searchUser(String username) {
        Object user = new Object();
        try {
            MongoCollection<Document> collection = DBManager.getCollection("users");
//            Bson filter = eq(username, )
            for(Document doc : collection.find()) {
                String id = doc.getString("id");
                String name = doc.getString("desc");
                user = new UserDB(id, name);
            }

        }
        catch (Exception e) {
            //Robust logging implementation?
            e.printStackTrace();
        }
        return user;
    }

    private UserDB(String id, String desc) {
        super(id, desc);
    }

}

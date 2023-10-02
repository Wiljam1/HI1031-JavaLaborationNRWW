package db;

import bo.User;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;

public class UserDB extends bo.User{

    //Kanske dåligt att den här returnar en User? Kanske bör returna något mer generellt
    public static User searchUser(String searchedUsername) {
        User user = null;
        try {
            MongoCollection<Document> collection = DBManager.getCollection("users");
            Bson filter = Filters.eq("username", searchedUsername);
            FindIterable<Document> results = collection.find(filter);

            for(Document doc : results) {
                String id = doc.getString("id");
                String username = doc.getString("username");
                String name = doc.getString("name");
                String authorization = doc.getString("authorization");
                user = new UserDB(id, username, name, authorization);
            }

        }
        catch (Exception e) {
            //Robust logging implementation?
            e.printStackTrace();
        }
        return user;
    }

    public static Document getUserDocument(String username) {
        return DBManager.getCollection("users").find(new Document("username", username)).first();
    }

    private UserDB(String id, String username, String name, String authorization) {
        super(id, username, name, authorization);
    }

}

package db;

import bo.User;
import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import ui.ItemInfo;

import java.util.Collection;

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
                    .append("authorization", authorization.toString());

            collection.insertOne(userDoc);
            return true;
        }
        catch (Exception e) {
            //Robust logging implementation?
            e.printStackTrace();
            return false;
        }
    }

    public static boolean performTransaction(String username, Collection<ItemInfo> cart) {
        MongoClient mongoClient = DBManager.getInstance().getMongoClient();
        MongoDatabase database = DBManager.getInstance().getDatabase();
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

    public static Document getUserDocument(String username) {
        return DBManager.getCollection("users").find(new Document("username", username)).first();
    }

    private UserDB(String username, String name, String authorization) {
        super(username, name, authorization);
    }

}

package db;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBManager {
    private static DBManager instance = null;
    MongoDatabase database = null;

    //Singleton
    private static DBManager getInstance() {
        if(instance == null)
            instance = new DBManager();
        return instance;
    }

    private DBManager() {
        try {
            ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/");
            MongoClient mongoClient = MongoClients.create(connectionString);
            this.database  = mongoClient.getDatabase("JavaLaboration");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean authenticateUser(String username, String password) {
        Document userDoc = DBManager.getCollection("users").find(new Document("username", username)).first();

        if (userDoc != null) {
            String DBPassword = userDoc.getString("password");

            if (password.equals(DBPassword)) {
                return true; // Passwords match
            }
        }

        return false; // User not found or password doesn't match
    }

    public static MongoCollection<Document> getCollection(String collection) {
        return getInstance().database.getCollection(collection);
    }
}

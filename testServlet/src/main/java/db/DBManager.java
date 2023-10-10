package db;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 * The DBManager class is responsible for managing the connection to the MongoDB database.
 * It provides methods for obtaining a reference to a MongoDB collection, as well as access
 * to the underlying MongoDatabase and MongoClient.
 */
public class DBManager {
    private static DBManager instance = null;
    private static MongoClient client;
    MongoDatabase database = null;

    static DBManager getInstance() {
        if(instance == null)
            instance = new DBManager();
        return instance;
    }

    private DBManager() {
        try {
            ConnectionString connectionString = new ConnectionString("mongodb+srv://User:123@cluster0.8g0bsdj.mongodb.net/");
            client = MongoClients.create(connectionString);

            this.database  = client.getDatabase("JavaLaboration");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MongoCollection<Document> getCollection(String collection) {
        return getInstance().database.getCollection(collection);
    }

    public MongoClient getMongoClient() {
        return client;
    }
}

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
    MongoCollection<Document> collection = null;

    //Singleton
    private static DBManager getInstance(String collection) {
        if(instance == null)
            instance = new DBManager(collection);
        return instance;
    }

    private DBManager(String collection) {
        try {
            ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/");
            MongoClient mongoClient = MongoClients.create(connectionString);
            MongoDatabase database = mongoClient.getDatabase("JavaLaboration");
            this.collection = database.getCollection(collection);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MongoCollection<Document> getCollection(String collection) {
        return getInstance(collection).collection;
    }
}

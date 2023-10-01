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
    private static DBManager getInstance() {
        if(instance == null)
            instance = new DBManager();
        return instance;
    }

    private DBManager() {
        try {
            ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/");
            MongoClient mongoClient = MongoClients.create(connectionString);
            MongoDatabase database = mongoClient.getDatabase("JavaLaboration");
            collection = database.getCollection("testServlet");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MongoCollection<Document> getCollection() {
        return getInstance().collection;
    }
}

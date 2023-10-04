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
    private static MongoClient client;
    MongoDatabase database = null;

    //kan inte är singelton längre
    static DBManager getInstance() {
        if(instance == null)
            instance = new DBManager();
        return instance;
    }

    private DBManager() {
        try {
            ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/");
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

    public MongoDatabase getDatabase() {
        return database;
    }

    public MongoClient getMongoClient() {
        return client;
    }
}

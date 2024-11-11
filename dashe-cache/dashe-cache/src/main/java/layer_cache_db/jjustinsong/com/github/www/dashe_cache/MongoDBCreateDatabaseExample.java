import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class MongoDBCreateDatabaseExample {
    public static void main(String[] args) {
        // Create a MongoClient instance
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");

        try {
            // Access the database (will be created upon data insertion)
            MongoDatabase database = mongoClient.getDatabase("myDatabase");

            // Access the collection (will be created upon data insertion)
            MongoCollection<Document> collection = database.getCollection("myCollection");

            // Create a document
            Document document = new Document("name", "Alice")
                    .append("age", 30)
                    .append("email", "alice@example.com");

            // Insert the document into the collection
            collection.insertOne(document);

            System.out.println("Database and collection created, document inserted.");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the client
            mongoClient.close();
        }
    }
}

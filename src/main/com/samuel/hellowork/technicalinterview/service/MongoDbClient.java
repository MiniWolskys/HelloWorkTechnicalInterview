package main.com.samuel.hellowork.technicalinterview.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateOptions;
import main.com.samuel.hellowork.technicalinterview.record.WorkOfferRecord;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;

public class MongoDbClient {
    public static void store(List<WorkOfferRecord> workOffers) {
        final String uri = "mongodb://localhost:27017";
        final String DATABASE_NAME = "HelloWork";
        final String COLLECTION_NAME = "work_offer";

        UpdateOptions updateOptions = new UpdateOptions().upsert(true);

        try (MongoClient client = MongoClients.create(uri)) {
            MongoDatabase database = client.getDatabase(DATABASE_NAME);

            MongoCollection<WorkOfferRecord> collection = database.getCollection(COLLECTION_NAME, WorkOfferRecord.class);
            for (WorkOfferRecord workOffer : workOffers) {
                Bson filter = Filters.eq("_id", workOffer.reference());
                Document updatedData = new Document("$set", new Document()
                        .append("description", workOffer.description())
                        .append("date", workOffer.date()));
                if (workOffer.reference().equals("10")) {
                    System.out.println(workOffer.date());
                }
                collection.updateOne(filter, updatedData, updateOptions);
            }
        }
    }
}

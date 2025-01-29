package main.com.samuel.hellowork.technicalinterview;

import main.com.samuel.hellowork.technicalinterview.exception.XMLServiceException;
import main.com.samuel.hellowork.technicalinterview.record.WorkOfferRecord;
import main.com.samuel.hellowork.technicalinterview.service.MongoDbClient;
import main.com.samuel.hellowork.technicalinterview.service.XMLService;

import java.util.List;

public class TechnicalInterview {
    public static void main(String[] args) {
        List<WorkOfferRecord> workOffers;
        XMLService xmlService = new XMLService();

        try {
            xmlService.transform();
            workOffers = xmlService.getWorkOffers();
        } catch (XMLServiceException e) {
            System.err.println("Error in XMLService. Aborting.\n" + e);
            return ;
        }

        MongoDbClient.store(workOffers);
    }
}

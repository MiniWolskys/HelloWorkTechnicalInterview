package main.com.samuel.hellowork.technicalinterview.service;

import main.com.samuel.hellowork.technicalinterview.exception.EmptyOfferFieldException;
import main.com.samuel.hellowork.technicalinterview.exception.XMLServiceException;
import main.com.samuel.hellowork.technicalinterview.helpers.Helpers;
import main.com.samuel.hellowork.technicalinterview.record.WorkOfferRecord;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XMLService {
    private final String XMLOutput = "src/main/resources/output.xml";
    private final int DESCRIPTION_NODE_POSITION = 1;
    private final int REFERENCE_NODE_POSITION = 3;
    private final int DATE_NODE_POSITION = 5;

    public void transform() throws XMLServiceException {
        Source xmlSource = new StreamSource("src/main/resources/input.xml");
        Source xsltSource = new StreamSource("src/main/resources/xmlParse.xsl");

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer(xsltSource);

            StreamResult result = new StreamResult(new File(XMLOutput));
            transformer.transform(xmlSource, result);
        } catch (TransformerException e) {
            throw new XMLServiceException("Error in transform : " + e.getMessage());
        }
    }

    public List<WorkOfferRecord> getWorkOffers() throws XMLServiceException {
        File xmlFile = new File(XMLOutput);
        List<WorkOfferRecord> workOffers;

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(xmlFile);

            NodeList nodeList = document.getElementsByTagName("TransformedOffer");
            workOffers = parseNodesToWorkOffer(nodeList);
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new XMLServiceException("Error getting work offers : " + e.getMessage());
        }
        return workOffers;
    }

    private List<WorkOfferRecord> parseNodesToWorkOffer(NodeList nodeList) {
        List<WorkOfferRecord> workOffers = new ArrayList<>();
        String offerDescription;
        String offerReference;
        Date offerDate;
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            try {
                offerDescription = getOfferDescription(node);
                offerReference = getOfferReference(node);
                offerDate = getOfferDate(node);
            } catch (EmptyOfferFieldException | ParseException e) {
                // TODO : Log cette erreur
                continue;
            }
            workOffers.add(new WorkOfferRecord(offerReference, offerDescription, offerDate));
        }
        return workOffers;
    }

    private String getOfferDescription(Node node) throws EmptyOfferFieldException {
        String description = node.getChildNodes().item(DESCRIPTION_NODE_POSITION).getTextContent();
        if (description.isEmpty()) {
            throw new EmptyOfferFieldException();
        }
        return description;
    }

    private String getOfferReference(Node node) throws EmptyOfferFieldException {
        String reference = node.getChildNodes().item(REFERENCE_NODE_POSITION).getTextContent();
        if (reference.isEmpty()) {
            throw new EmptyOfferFieldException();
        }
        return reference;
    }

    private Date getOfferDate(Node node) throws ParseException, EmptyOfferFieldException {
        String date = node.getChildNodes().item(DATE_NODE_POSITION).getTextContent();
        if (date.isEmpty()) {
            throw new EmptyOfferFieldException();
        }
        return Helpers.formatDate(date);
    }
}

package com.org.xml.parser;

import com.org.dto.XMLData;
import com.org.dto.XMLParsedData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Year;

public class XMLConvertor {


    public static void createXmlDocument(XMLData xmlData) {

        System.out.println("Creating XML Files");
        try {
            DocumentBuilderFactory dbFactory =
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = null;
            dBuilder = dbFactory.newDocumentBuilder();

            Document doc = dBuilder.newDocument();
            doc.setXmlStandalone(true);
            // root element
            Element rootElement = doc.createElement("records");
            doc.appendChild(rootElement);

            for (XMLParsedData data : xmlData.getXmlParsedData()) {
                Element record = doc.createElement("record");
                rootElement.appendChild(record);
                record.setAttribute("xmlns:dc", "http://purl.org/dc/elements/1.1/");
                record.setAttribute("xmlns:id", "http://indexdata.com/xml/dcExtension/");

                Element id = doc.createElement("id:id");
                addElementToRootElement(record, id, data.getId());

                Element title = doc.createElement("dc:title");
                addElementToRootElement(record, title, data.getTitle());

                Element enCreator = doc.createElement("dc:creator");
                enCreator.setAttribute("xml:lang", "en");
                addElementToRootElement(record, enCreator, "OECD");

                Element frCreator = doc.createElement("dc:creator");
                frCreator.setAttribute("xml:lang", "fr");
                addElementToRootElement(record, frCreator, "OCDE");

                Element description = doc.createElement("dc:description");
                addElementToRootElement(record, description, data.getDescription());

                Element frPublisher = doc.createElement("dc:publisher");
                frPublisher.setAttribute("xml:lang", "fr");
                addElementToRootElement(record, frPublisher, data.getFrPublisher());

                Element enPublisher = doc.createElement("dc:publisher");
                enPublisher.setAttribute("xml:lang", "en");
                addElementToRootElement(record, enPublisher, data.getEnPublisher());

                Element frSubject = doc.createElement("dc:subject");
                frSubject.setAttribute("xml:lang", "fr");
                addElementToRootElement(record, frSubject, data.getFrSubject());

                Element enSubject = doc.createElement("dc:subject");
                enSubject.setAttribute("xml:lang", "en");
                addElementToRootElement(record, enSubject, data.getEnSubject());

                Element volume = doc.createElement("id:volume");
                String value = data.getVolume() != null ? data.getVolume() : Year.now().toString();
                addElementToRootElement(record, volume, value);

                Element coverage = doc.createElement("dc:coverage");
                addElementToRootElement(record, coverage, data.getCoverage());

                Element date = doc.createElement("dc:date");
                addElementToRootElement(record, date, data.getDate());

                Element isbn1 = doc.createElement("id:isbn");
                addElementToRootElement(record, isbn1, data.getIsbn1());

                Element isbn2 = doc.createElement("id:isbn");
                addElementToRootElement(record, isbn2, data.getIsbn2());

                Element issn1 = doc.createElement("id:issn");
                addElementToRootElement(record, issn1, data.getIssn1());

                Element issn2 = doc.createElement("id:issn");
                addElementToRootElement(record, issn2, data.getIssn2());

                Element docType = doc.createElement("id:doctype");
                addElementToRootElement(record, docType, data.getDocType());

                Element language = doc.createElement("dc:language");
                addElementToRootElement(record, language, data.getLanguage());

                Element identifier = doc.createElement("dc:identifier");
                addElementToRootElement(record, identifier, data.getIdentifier());

                Element contents = doc.createElement("id:contents");
                addElementToRootElement(record, contents, data.getContents());

                Element igo = doc.createElement("id:igo");
                String igoValue = data.getIgo() != null ? igoValue = data.getIgo().toLowerCase() : null;
                addElementToRootElement(record, igo, igoValue);

                Element harvestTimestamp = doc.createElement("id:harvest-timestamp");
                addElementToRootElement(record, harvestTimestamp, data.getHarvestTimestamp());

                Element harvestDate = doc.createElement("id:harvest-date");
                addElementToRootElement(record, harvestDate, data.getHarvestDate());
            }

            createXMLDocument(doc);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createXMLDocument(Document doc) {


        String xmlFilePath = "./XML/output-" + new SimpleDateFormat("ddMMyyyyHHmmss").format(new java.util.Date()) + ".xml";

        // write the content into xml file
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(xmlFilePath));
            transformer.transform(source, result);
            System.out.println("File saved!");

        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    private static void addElementToRootElement(Element record, Element toAdd, String value) {

        toAdd.setTextContent(value);
        record.appendChild(toAdd);
    }


}

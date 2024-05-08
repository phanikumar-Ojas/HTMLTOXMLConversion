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
                record.appendChild(id);
                id.setTextContent(data.getId());
                Element title = doc.createElement("dc:title");
                record.appendChild(title);
                title.setTextContent(data.getTitle());
                Element enCreator = doc.createElement("dc:creator");
                enCreator.setAttribute("xml:lang", "en");
                enCreator.setTextContent("OECD");
                record.appendChild(enCreator);
                Element frCreator = doc.createElement("dc:creator");
                frCreator.setAttribute("xml:lang", "fr");
                frCreator.setTextContent("OCDE");
                record.appendChild(frCreator);
                Element description = doc.createElement("dc:description");
                record.appendChild(description);
                description.setTextContent(data.getDescription());
                Element frPublisher = doc.createElement("dc:publisher");
                frPublisher.setAttribute("xml:lang", "fr");
                record.appendChild(frPublisher);
                Element enPublisher = doc.createElement("dc:publisher");
                enPublisher.setAttribute("xml:lang", "en");
                record.appendChild(enPublisher);
                Element frSubject = doc.createElement("dc:subject");
                frSubject.setAttribute("xml:lang", "fr");
                record.appendChild(frSubject);
                Element enSubject = doc.createElement("dc:subject");
                enSubject.setAttribute("xml:lang", "en");
                record.appendChild(enSubject);
                Element volume = doc.createElement("id:volume");
                record.appendChild(volume);
                Element coverage = doc.createElement("dc:coverage");
                record.appendChild(coverage);
                Element date = doc.createElement("dc:date");
                record.appendChild(date);
                Element isbn1 = doc.createElement("id:isbn");
                record.appendChild(isbn1);
                Element isbn2 = doc.createElement("id:isbn");
                record.appendChild(isbn2);
                Element issn1 = doc.createElement("id:issn");
                record.appendChild(issn1);
                Element issn2 = doc.createElement("id:issn");
                record.appendChild(issn2);
                Element docType = doc.createElement("id:doctype");
                record.appendChild(docType);
                Element language = doc.createElement("dc:language");
                record.appendChild(language);
                Element identifier = doc.createElement("dc:identifier");
                record.appendChild(identifier);
                Element contents = doc.createElement("dc:contents");
                record.appendChild(contents);
                Element igo = doc.createElement("id:igo");
                record.appendChild(igo);
                Element harvestTimestamp = doc.createElement("id:harvest-timestamp");
                record.appendChild(harvestTimestamp);
                Element harvestDate = doc.createElement("id:harvest-date");
                record.appendChild(harvestDate);
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


}

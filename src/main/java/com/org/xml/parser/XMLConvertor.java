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
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

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
                id.setTextContent(data.getId());
                record.appendChild(id);
                Element title = doc.createElement("dc:title");
                title.setTextContent(data.getTitle());
                record.appendChild(title);
                Element enCreator = doc.createElement("dc:creator");
                enCreator.setAttribute("xml:lang", "en");
                enCreator.setTextContent("OECD");
                record.appendChild(enCreator);
                Element frCreator = doc.createElement("dc:creator");
                frCreator.setAttribute("xml:lang", "fr");
                frCreator.setTextContent("OCDE");
                record.appendChild(frCreator);
                Element description = doc.createElement("dc:description");
                description.setTextContent(data.getDescription());
                record.appendChild(description);
                Element frPublisher = doc.createElement("dc:publisher");
                frPublisher.setAttribute("xml:lang", "fr");
                frPublisher.setTextContent(data.getFrPublisher());
                record.appendChild(frPublisher);
                Element enPublisher = doc.createElement("dc:publisher");
                enPublisher.setAttribute("xml:lang", "en");
                enPublisher.setTextContent(data.getEnPublisher());
                record.appendChild(enPublisher);
                Element frSubject = doc.createElement("dc:subject");
                frSubject.setAttribute("xml:lang", "fr");
                frSubject.setTextContent(data.getFrSubject());
                record.appendChild(frSubject);
                Element enSubject = doc.createElement("dc:subject");
                enSubject.setAttribute("xml:lang", "en");
                enSubject.setTextContent(data.getEnSubject());
                record.appendChild(enSubject);
                Element volume = doc.createElement("id:volume");
                volume.setTextContent(data.getVolume());
                record.appendChild(volume);
                Element coverage = doc.createElement("dc:coverage");
                coverage.setTextContent(data.getCoverage());
                record.appendChild(coverage);
                Element date = doc.createElement("dc:date");
                date.setTextContent(data.getDate());
                record.appendChild(date);
                Element isbn1 = doc.createElement("id:isbn");
                isbn1.setTextContent(data.getIsbn1());
                record.appendChild(isbn1);
                Element isbn2 = doc.createElement("id:isbn");
                isbn2.setTextContent(data.getIsbn2());
                record.appendChild(isbn2);
                Element issn1 = doc.createElement("id:issn");
                issn1.setTextContent(data.getIssn1());
                record.appendChild(issn1);
                Element issn2 = doc.createElement("id:issn");
                issn2.setTextContent(data.getIssn2());
                record.appendChild(issn2);
                Element docType = doc.createElement("id:doctype");
                docType.setTextContent(data.getDocType());
                record.appendChild(docType);
                Element language = doc.createElement("dc:language");
                language.setTextContent(data.getLanguage());
                record.appendChild(language);
                Element identifier = doc.createElement("dc:identifier");
                identifier.setTextContent(data.getIdentifier());
                record.appendChild(identifier);
                Element contents = doc.createElement("id:contents");
                contents.setTextContent(data.getContents());
                record.appendChild(contents);
                Element igo = doc.createElement("id:igo");
                igo.setTextContent(data.getIgo());
                record.appendChild(igo);
                Element harvestTimestamp = doc.createElement("id:harvest-timestamp");
                harvestTimestamp.setTextContent(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));
                record.appendChild(harvestTimestamp);
                Element harvestDate = doc.createElement("id:harvest-date");
                harvestDate.setTextContent(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
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

package com.org.xml.parser;

import com.org.dto.XMLData;
import com.org.dto.XMLParsedData;
import com.org.xml.XMLTagEnum;
import com.org.xml.util.ReflectionUtil;
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
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.List;

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
                for (Field field : ReflectionUtil.getFields(data)) {
                    createElementAndAppendValue(doc, record, field,data);
                }
            }

            createXMLDocument(doc);
        } catch (ParserConfigurationException | IllegalAccessException e) {
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

    private static void createElementAndAppendValue(Document document, Element record, Field field,XMLParsedData xmlParsedData) throws IllegalAccessException {
       field.setAccessible(true);
        Object fieldValue = field.get(xmlParsedData);
        if(fieldValue == null || fieldValue.toString().isEmpty()){
            return;
        }
        Element fieldTagElement = document.createElement(XMLTagEnum.valueOf(field.getName().toUpperCase()).getTag());

        if(field.getType() == List.class){
            List<String> list = (List<String>) fieldValue;
            for(String value : list){
                Element listElement = document.createElement(XMLTagEnum.valueOf(field.getName().toUpperCase()).getTag());

                addElementToRootElement(record, listElement, value);
            }
            return;
        }
        addElementToRootElement(record, fieldTagElement, String.valueOf(fieldValue));
    }

    private static void addElementToRootElement(Element record, Element toAdd, String value) {

        toAdd.setTextContent(value);
        record.appendChild(toAdd);
    }


}

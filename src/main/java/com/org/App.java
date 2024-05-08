package com.org;

import com.org.dto.HtmlContentList;
import com.org.dto.XMLData;
import com.org.html.HtmlParser;
import com.org.transformer.HtmlContentToXMLContentTransformer;
import com.org.xml.parser.XMLConvertor;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Started Parsing HTML Files" );

        createXMLFiles();
    }

    public static void createXMLFiles() {
        // Code to create XML files from HTML files
        HtmlParser htmlParser = new HtmlParser();
        try {
            HtmlContentList htmlContentList = htmlParser.parseHtmlFiles();
            XMLData xmlData = new XMLData(htmlContentList.getXmlParsedDataList());
            XMLConvertor.createXmlDocument(xmlData);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}

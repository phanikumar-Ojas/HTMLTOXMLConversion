package com.org.html;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.org.dto.HtmlContentList;
import com.org.dto.XMLParsedData;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.org.util.JsoupUtils.getElementsBasedOnTag;


public class HtmlParser {

    private static final String HTML_PATH = "./HTMLFiles";

    public Set<String> listFiles(String dir) {
        return Stream.of(Objects.requireNonNull(new File(dir).listFiles()))
                .filter(file -> !file.isDirectory())
                .map(File::getName)
                .collect(Collectors.toSet());
    }

    public HtmlContentList parseHtmlFiles() throws IOException {
        System.out.println("Parsing HTML Files");
        Set<String> files = listFiles(HTML_PATH);
        List<XMLParsedData> xmlParsedDataList = new ArrayList<>();
        for (String file : files) {
            String htmlContent = String.join("\n", Files.readAllLines(Paths.get(HTML_PATH + "/" + file)));
            xmlParsedDataList.add(parseHtmlContent(htmlContent));
        }
        return new HtmlContentList(xmlParsedDataList);
    }


    private XMLParsedData parseHtmlContent(String htmlContent) {
        XMLParsedData xmlParsedData = new XMLParsedData();
        Document doc = Jsoup.parse(htmlContent);
        appendIDAndTitleData(doc, xmlParsedData);
        appendDescData(doc, xmlParsedData);
        return xmlParsedData;
    }

    private void getScriptsAsMap(String scriptContent, XMLParsedData xmlParsedData) {
        String data_suffix = "}]";
        int index = scriptContent.indexOf(data_suffix);
        String jsonData = scriptContent.substring(0, index + 2);
        System.out.println("jsonData");
        String substring = jsonData.replace("dataLayer = ", "").replace("'", "\"");

        // Create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        // Parse JSON data
        JsonNode rootNode = null;
        try {
            rootNode = objectMapper.readTree(substring);
            // Iterate over each object in the array
            for (JsonNode node : rootNode) {
                JsonNode contentDOI1 = node.get("contentDOI");
                if (contentDOI1 != null) {
                    // Extract contentDOI value
                    String contentDOI = contentDOI1.asText();
                    System.out.println("contentDOI: " + contentDOI);
                    xmlParsedData.setId(contentDOI);
                }
                JsonNode siteNameNode = node.get("siteName");
                if (siteNameNode != null) {
                    // Extract contentDOI value
                    String siteName = siteNameNode.asText();
                    System.out.println("siteName: " + siteName);
                }
            }
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }

    }

    private void appendIDAndTitleData(Document doc, XMLParsedData xmlParsedData) {
        Elements bodyElements = getElementsBasedOnTag(doc, "head");
        Element headElement = bodyElements.get(0);
        Elements title = headElement.getElementsByTag("title");
        String titleData = title.html();
        xmlParsedData.setTitle(titleData);

        Elements scriptElements = headElement.getElementsByTag("script");
        Elements type1 = scriptElements.attr("type", "text/javascript");
        String html = type1.html();
        getScriptsAsMap(html, xmlParsedData);
    }

    private void appendDescData(Document doc, XMLParsedData xmlParsedData) {
        Elements bodyElements = getElementsBasedOnTag(doc, "body");
        Element bodyElement = bodyElements.get(0);
        Elements divElements = bodyElement.getElementsByTag("div");

String description = null;
        for (Element element : divElements) {
            Elements colXs12Elements = element.getElementsByAttributeValue("class", "col-xs-12");
            for (Element colX12 : colXs12Elements) {
                Elements descElements = colX12.getElementsByAttributeValue("class", "description js-desc-fade");
                for (Element desc : descElements) {
                    Elements paraElements = desc.getElementsByTag("div").get(0).getElementsByTag("p");
                    String html = paraElements.get(0).html();
                    String strongHtml = "";
                    if(paraElements.size()>1) {
                         strongHtml = paraElements.get(1).getElementsByTag("strong").html();

                    }
                    description = html+ strongHtml;
                }
            }
        }
        xmlParsedData.setDescription(description);


    }


}

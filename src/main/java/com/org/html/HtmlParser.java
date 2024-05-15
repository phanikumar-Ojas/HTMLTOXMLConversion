package com.org.html;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import java.time.LocalDate;
import java.time.Year;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class HtmlParser {

    private static final String HTML_PATH = "./HTMLFiles";

    private static final List<String> years = IntStream.rangeClosed(2000, Year.now().getValue())
            .boxed()
            .map(String::valueOf)
            .collect(Collectors.toList());

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
        xmlParsedData.setFrPublisher("Éditions OCDE");
        xmlParsedData.setEnPublisher("OECD Publishing");
        xmlParsedData.setHarvestTimestamp(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")));
        xmlParsedData.setHarvestDate(ZonedDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        Document doc = Jsoup.parse(htmlContent);
        Element headElement = getElementsBasedOnTag(doc,"head").get(0);
        Element bodyElement = getElementsBasedOnTag(doc,"body").get(0);
        appendIDAndTitleData(headElement, xmlParsedData);
        appendDescData(bodyElement, xmlParsedData);
        appendVolume(bodyElement, xmlParsedData);
        addCoverageData(bodyElement, xmlParsedData);
        addContents(bodyElement, xmlParsedData);
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
                    xmlParsedData.setIdentifier(contentDOI);
                }
                JsonNode siteNameNode = node.get("siteName");
                if (siteNameNode != null) {
                    // Extract contentDOI value
                    String siteName = siteNameNode.asText();
                    System.out.println("siteName: " + siteName);
                }
                JsonNode pageTopicNode = node.get("pageTopic");
                if (pageTopicNode != null) {
                    // Extract contentDOI value
                    String pageTopic = pageTopicNode.asText();
                    System.out.println("pageTopic: " + pageTopic);
                    xmlParsedData.setFrSubject(pageTopic);
                    if (pageTopic.contains(",")) {
                        String[] split = pageTopic.split(",");
                        xmlParsedData.setFrPublisher(split[0]);
                        xmlParsedData.setEnPublisher(split[1]);
                    }
                }
                JsonNode pageLanguageNode = node.get("pageLanguage");
                if (pageLanguageNode != null) {
                    // Extract contentDOI value
                    String pageLanguage = pageLanguageNode.asText();
                    System.out.println("pageLanguage: " + pageLanguage);
                    xmlParsedData.setLanguage(pageLanguage);
                }
                JsonNode pageTypeNode = node.get("pageType");
                if (pageTypeNode != null) {
                    // Extract contentDOI value
                    String pageType = pageTypeNode.asText();
                    if (pageType.contains(",")) {
                        String[] split = pageType.split(",");
                        pageType = split[0];
                    }
                    System.out.println("pageType: " + pageType.toLowerCase());
                    xmlParsedData.setDocType(pageType.toLowerCase());
                }
            }
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }

    }

    private void appendIDAndTitleData(Element mainElement, XMLParsedData xmlParsedData) {
        Elements title = mainElement.getElementsByTag("title");
        String titleData = title.html();
        if (!titleData.isEmpty()) {
            titleData = titleData.split("\\|")[0];
        }
        xmlParsedData.setTitle(titleData);

        Elements scriptElements = mainElement.getElementsByTag("script");
        Elements type1 = scriptElements.attr("type", "text/javascript");
        String html = type1.html();
        getScriptsAsMap(html, xmlParsedData);
    }

    private void appendDescData(Element mainElement, XMLParsedData xmlParsedData) {

        Elements divElements = mainElement.getElementsByTag("div");

        String description = null;
        for (Element element : divElements) {
            Elements colXs12Elements = element.getElementsByAttributeValue("class", "col-xs-12");
            for (Element colX12 : colXs12Elements) {
                Elements descElements = colX12.getElementsByAttributeValue("class", "description js-desc-fade");
                for (Element desc : descElements) {
                    Elements paraElements = desc.getElementsByTag("div").get(0).getElementsByTag("p");
                    String html = paraElements.get(0).html();
                    String strongHtml = "";
                    if (paraElements.size() > 1) {
                        strongHtml = paraElements.get(1).getElementsByTag("strong").html();

                    }
                    description = html + strongHtml;
                }
            }
        }
        xmlParsedData.setDescription(description);
    }

    private void appendVolume(Element mainElement, XMLParsedData xmlParsedData) {
        Elements divElements = mainElement.getElementsByTag("ul");
        for (Element element : divElements) {

            Elements elementsByAttributeValue = element.getElementsByAttributeValue("class", "volumes-list");
            for (Element ele : elementsByAttributeValue) {
                Elements liElements = ele.getElementsByTag("li");
                if (!liElements.isEmpty()) {
                    String volume = liElements.get(0).getElementsByTag("a").html();
                    xmlParsedData.setVolume(volume);

                }
            }
        }
    }

    private void addCoverageData( Element mainElement, XMLParsedData xmlParsedData) {
        Elements divElements = mainElement.getElementsByTag("span");
        for (Element element : divElements) {
            Elements elementsByAttributeValue = element.getElementsByAttributeValue("class", "meta-item");
            for (Element span : elementsByAttributeValue) {
                String spanData = span.html();
                String[] spanArray = spanData.split(" ");
                String firstIndex = spanArray[0];
                if (spanData.contains("pages")) {
                    xmlParsedData.setCoverage(firstIndex);
                }
                for (String year : years) {
                    if (spanData.endsWith(year)) {
                        int yearValue = Integer.parseInt(spanArray[2]);
                        xmlParsedData.setDate(LocalDate.of(yearValue, 3, Integer.parseInt(firstIndex)).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                    }
                }

            }

            for (Element span : elementsByAttributeValue) {
                if (span.html().contains("PDF")) {
                    xmlParsedData.setIsbn2(span.html().split(" ")[0]);
                }
                if (span.html().contains("HTML")) {
                    xmlParsedData.setIsbn1(span.html().split(" ")[0]);
                }

            }
        }
        Elements liElements = mainElement.getElementsByTag("li");
        for (Element element : liElements) {
            String html = element.html();
            if (html.contains("ISSN :")) {
                html = html.split(" ")[2];
                xmlParsedData.setIssn2(html.substring(0, 4) + '-' + html.substring(4));
            }
        }
        String igo = mainElement.getElementsByTag("input").attr("data-igo");
        xmlParsedData.setIgo(igo);

    }

    private void addContents(Element mainElement, XMLParsedData xmlParsedData) {
        Elements pElements = mainElement.getElementsByTag("p");
        List<String> contentsList = new ArrayList<>();

        for (Element element : pElements) {
            Elements aClassElements = element.getElementsByAttributeValue("class", "intro-item");
            for (Element aClassElement : aClassElements) {
                Elements aElements = aClassElement.getElementsByTag("a");
                for (Element aElement : aElements) {
                    contentsList.add(aElement.html());
                }
            }
        }
        xmlParsedData.setContents(String.join("--", contentsList));
    }

    private Elements getElementsBasedOnTag(Document doc, String tagName) {
        return doc.getElementsByTag(tagName);
    }


}

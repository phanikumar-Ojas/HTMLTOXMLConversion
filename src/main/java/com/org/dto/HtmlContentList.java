package com.org.dto;

import java.util.List;

public class HtmlContentList {

    private List<XMLParsedData> xmlParsedDataList;

    public List<XMLParsedData> getXmlParsedDataList() {
        return xmlParsedDataList;
    }

    public void setXmlParsedDataList(List<XMLParsedData> xmlParsedDataList) {
        this.xmlParsedDataList = xmlParsedDataList;
    }

    public HtmlContentList() {
    }

    public HtmlContentList(List<XMLParsedData> xmlParsedDataList) {
        this.xmlParsedDataList = xmlParsedDataList;
    }
}

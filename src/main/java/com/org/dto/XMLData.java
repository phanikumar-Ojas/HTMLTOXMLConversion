package com.org.dto;

import java.util.List;

public class XMLData {

    private List<XMLParsedData> xmlParsedData;

    public XMLData(List<XMLParsedData> xmlParsedData) {
        this.xmlParsedData = xmlParsedData;
    }

    public List<XMLParsedData> getXmlParsedData() {
        return xmlParsedData;
    }

    public void setXmlParsedData(List<XMLParsedData> xmlParsedData) {
        this.xmlParsedData = xmlParsedData;
    }



}

package com.org.transformer;

import com.org.dto.HtmlContentList;
import com.org.dto.XMLData;
import com.org.dto.XMLParsedData;

import java.util.List;
import java.util.stream.Collectors;

public class HtmlContentToXMLContentTransformer {

    public static XMLData transform(HtmlContentList htmlContentList) {
        List<XMLParsedData> xmlParsedData = htmlContentList.getHtmlContent().stream().map(htmlContent -> new XMLParsedData(null)).collect(Collectors.toList());

        return new XMLData(xmlParsedData);


    }


}


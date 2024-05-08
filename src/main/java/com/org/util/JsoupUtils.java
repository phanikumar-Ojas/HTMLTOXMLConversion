package com.org.util;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class JsoupUtils {

    public static Elements getElementsBasedOnTag(Document doc, String tagName){
        return doc.getElementsByTag(tagName);
    }

}

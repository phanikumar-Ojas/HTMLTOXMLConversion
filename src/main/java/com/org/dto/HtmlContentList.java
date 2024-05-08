package com.org.dto;

import java.util.List;

public class HtmlContentList {

    private List<String> htmlContent;

    public HtmlContentList(List<String> htmlContent) {
        this.htmlContent = htmlContent;
    }

    public List<String> getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(List<String> htmlContent) {
        this.htmlContent = htmlContent;
    }

}

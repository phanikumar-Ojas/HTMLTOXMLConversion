package com.org.dto;

import java.time.Year;
import java.util.List;

public class XMLParsedData {

    private String id;
    private String title;
    private String frCreator;
    private String enCreator;
    private String description;
    private String frPublisher;
    private String enPublisher;
    private String frSubject;
    private String enSubject;
    private String volume;
    private String coverage;
    private String date;
    private List<String> isbnList;
    private List<String> issnList;
    private String docType;
    private String language;
    private String identifier;
    private String contents;
    private String igo;
    private String harvestTimestamp;
    private String harvestDate;

    public XMLParsedData() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFrCreator() {
        return frCreator;
    }

    public void setFrCreator(String frCreator) {
        this.frCreator = frCreator;
    }

    public String getEnCreator() {
        return enCreator;
    }

    public void setEnCreator(String enCreator) {
        this.enCreator = enCreator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrPublisher() {
        return frPublisher;
    }

    public void setFrPublisher(String frPublisher) {
        this.frPublisher = frPublisher;
    }

    public String getEnPublisher() {
        return enPublisher;
    }

    public void setEnPublisher(String enPublisher) {
        this.enPublisher = enPublisher;
    }

    public String getFrSubject() {
        return frSubject;
    }

    public void setFrSubject(String frSubject) {
        this.frSubject = frSubject;
    }

    public String getEnSubject() {
        return enSubject;
    }

    public void setEnSubject(String enSubject) {
        this.enSubject = enSubject;
    }

    public String getVolume() {
        return volume!=null?volume: Year.now().toString();
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getIsbnList() {
        return isbnList;
    }

    public void setIsbnList(List<String> isbnList) {
        this.isbnList = isbnList;
    }

    public List<String> getIssnList() {
        return issnList;
    }

    public void setIssnList(List<String> issnList) {
        this.issnList = issnList;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getIgo() {
        return igo;
    }

    public void setIgo(String igo) {
        this.igo = igo;
    }

    public String getHarvestTimestamp() {
        return harvestTimestamp;
    }

    public void setHarvestTimestamp(String harvestTimestamp) {
        this.harvestTimestamp = harvestTimestamp;
    }

    public String getHarvestDate() {
        return harvestDate;
    }

    public void setHarvestDate(String harvestDate) {
        this.harvestDate = harvestDate;
    }
}


package com.org.xml;

public enum XMLTagEnum {

    ID("id:id"),
    TITLE("dc:title"),
    ENCREATOR("dc:creator"),
    FRCREATOR("dc:creator"),
DESCRIPTION("dc:description"),
    FRPUBLISHER("dc:publisher"),
    ENPUBLISHER("dc:publisher"),
    FRSUBJECT("dc:subject"),
    ENSUBJECT("dc:subject"),
    VOLUME("id:volume"),
    COVERAGE("dc:coverage"),
    DATE("dc:date"),
    ISBN("id:isbn"),
    ISSN("id:issn"),
    DOCTYPE("id:doctype"),
    LANGUAGE("dc:language"),
    IDENTIFIER("dc:identifier"),
    CONTENTS("id:contents"),
    IGO("id:igo"),
    ISBNLIST("id:isbn"),
    ISSNLIST("id:issn"),
    HARVESTTIMESTAMP("id:harvest-timestamp"),
    HARVESTDATE("id:harvest-date");
    final String tag;

    public String getTag() {
        return tag;
    }

    XMLTagEnum(String tag) {
        this.tag = tag;
    }
}

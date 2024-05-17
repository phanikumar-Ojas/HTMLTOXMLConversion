package com.org.xml;

public enum XMLTagEnum {

    ID("id:id",null,null),
    TITLE("dc:title",null,null),
    ENCREATOR("dc:creator","xml:lang","en"),
    FRCREATOR("dc:creator","xml:lang","fr"),
DESCRIPTION("dc:description",null,null),
    FRPUBLISHER("dc:publisher","xml:lang","fr"),
    ENPUBLISHER("dc:publisher","xml:lang","en"),
    FRSUBJECT("dc:subject","xml:lang","fr"),
    ENSUBJECT("dc:subject","xml:lang","en"),
    VOLUME("id:volume",null,null),
    COVERAGE("dc:coverage",null,null),
    DATE("dc:date",null,null),
    ISBN("id:isbn",null,null),
    ISSN("id:issn",null,null),
    DOCTYPE("id:doctype",null,null),
    LANGUAGE("dc:language",null,null),
    IDENTIFIER("dc:identifier",null,null),
    CONTENTS("id:contents",null,null),
    IGO("id:igo",null,null),
    ISBNLIST("id:isbn",null,null),
    ISSNLIST("id:issn",null,null),
    HARVESTTIMESTAMP("id:harvest-timestamp",null,null),
    HARVESTDATE("id:harvest-date",null,null);
    final String tag;
    final String attrKey;
    final String attrValue;


    public String getTag() {
        return tag;
    }

    public String getAttrKey() {
        return attrKey;
    }

    public String getAttrValue() {
        return attrValue;
    }

    XMLTagEnum(String tag,String attrKey,String attrValue) {
        this.tag = tag;
        this.attrKey = attrKey;
        this.attrValue = attrValue;

    }
}

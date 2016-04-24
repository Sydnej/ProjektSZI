package model.area;

public enum Position {
    UPPER_LEFT("upper-left"),
    UPPER_RIGHT("upper-right"),
    BOTTOM_LEFT("bottom-left"),
    BOTTOM_RIGHT("bottom-right");

    private final String xmlElementName;

    Position(String xmlElementName) {
        this.xmlElementName = xmlElementName;
    }

    public String getXmlElementName() {
        return xmlElementName;
    }
}

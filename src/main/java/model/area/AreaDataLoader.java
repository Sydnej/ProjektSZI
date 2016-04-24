package model.area;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class AreaDataLoader {

    private String areaImagePath;

    public void loadData(Map<Integer, Field> fields, Map<Integer, GraphVertex> graphVertices, InputStream
            mapInputStream) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document d = db.parse(mapInputStream);
            d.getDocumentElement().normalize();

            parseFields(fields, d);
            parseGraphVertices(graphVertices, d);

            Element element = (Element) d.getElementsByTagName("mapImage").item(0);
            areaImagePath = element.getTextContent();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private void parseGraphVertices(Map<Integer, GraphVertex> graphVertices, Document d) {
        NodeList graphVerticesList = d.getElementsByTagName("vertex");
        for (int i = 0; i < graphVerticesList.getLength(); i++) {
            Element element = (Element) graphVerticesList.item(i);

            int graphVertexId = Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent());
            double x = Double.parseDouble(element.getElementsByTagName("posX").item(0).getTextContent());
            double y = Double.parseDouble(element.getElementsByTagName("posY").item(0).getTextContent());

            GraphVertex graphVertex = new GraphVertex(graphVertexId, x, y);

            for (int j = 0; j < ((Element) element.getElementsByTagName("linked").item(0)).getElementsByTagName
                    ("vertexID").getLength(); j++) {
                int linkedVertexId = Integer.parseInt(((Element) element.getElementsByTagName("linked").item(0))
                        .getElementsByTagName("vertexID").item(j).getTextContent());
                graphVertex.addLinkedVertice(linkedVertexId);
            }

            graphVertices.put(graphVertexId, graphVertex);
        }
    }

    private void parseFields(Map<Integer, Field> fields, Document d) {
        NodeList fieldsList = d.getElementsByTagName("field");
        for (int i = 0; i < fieldsList.getLength(); i++) {
            Element fieldElement = (Element) fieldsList.item(i);

            int fieldId = Integer.parseInt(fieldElement.getElementsByTagName("id").item(0).getTextContent());
            Field field = new Field(fieldId);

            setCorner(field.getCorners()[0], fieldElement, Position.UPPER_LEFT);
            setCorner(field.getCorners()[1], fieldElement, Position.UPPER_RIGHT);
            setCorner(field.getCorners()[2], fieldElement, Position.BOTTOM_LEFT);
            setCorner(field.getCorners()[3], fieldElement, Position.BOTTOM_RIGHT);

            fields.put(fieldId, field);
        }
    }

    private void setCorner(FieldVertex fieldVertex, Element fieldElement, Position position) {
        Element cornerElement = (Element) fieldElement.getElementsByTagName(position.getXmlElementName()).item(0);
        int x = Integer.parseInt(cornerElement.getElementsByTagName("posX").item(0).getTextContent());
        int y = Integer.parseInt(cornerElement.getElementsByTagName("posY").item(0).getTextContent());
        fieldVertex.setPosition(position);
        fieldVertex.setX(x);
        fieldVertex.setY(y);
    }

    public String getAreaImagePath() {
        return areaImagePath;
    }

}

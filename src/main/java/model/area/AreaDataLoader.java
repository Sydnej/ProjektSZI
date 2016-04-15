package model.area;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;

public class AreaDataLoader {

    public void loadData(Map<Integer, Field> fields, Map<Integer, GraphVertice> graphVertices) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document d = db.parse(getClass().getResourceAsStream("/xml/map.xml"));
            d.getDocumentElement().normalize();

            NodeList fieldsList = d.getElementsByTagName("field");
            for(int i=0; i<fieldsList.getLength(); i++) {
                Element element = (Element) fieldsList.item(i);

                int fieldId = Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent());
                Field field = new Field(fieldId);

                int x = Integer.parseInt(((Element) element.getElementsByTagName("upper-left").item(0)).getElementsByTagName("posX").item(0).getTextContent());
                int y = Integer.parseInt(((Element) element.getElementsByTagName("upper-left").item(0)).getElementsByTagName("posY").item(0).getTextContent());
                field.getVertices()[0].setPosition(Position.UPPER_LEFT);
                field.getVertices()[0].setX(x);
                field.getVertices()[0].setY(y);

                x = Integer.parseInt(((Element) element.getElementsByTagName("upper-right").item(0)).getElementsByTagName("posX").item(0).getTextContent());
                y = Integer.parseInt(((Element) element.getElementsByTagName("upper-right").item(0)).getElementsByTagName("posY").item(0).getTextContent());
                field.getVertices()[1].setPosition(Position.UPPER_RIGHT);
                field.getVertices()[1].setX(x);
                field.getVertices()[1].setY(y);

                x = Integer.parseInt(((Element) element.getElementsByTagName("bottom-left").item(0)).getElementsByTagName("posX").item(0).getTextContent());
                y = Integer.parseInt(((Element) element.getElementsByTagName("bottom-left").item(0)).getElementsByTagName("posY").item(0).getTextContent());
                field.getVertices()[2].setPosition(Position.BOTTOM_LEFT);
                field.getVertices()[2].setX(x);
                field.getVertices()[2].setY(y);

                x = Integer.parseInt(((Element) element.getElementsByTagName("bottom-right").item(0)).getElementsByTagName("posX").item(0).getTextContent());
                y = Integer.parseInt(((Element) element.getElementsByTagName("bottom-right").item(0)).getElementsByTagName("posY").item(0).getTextContent());
                field.getVertices()[3].setPosition(Position.BOTTOM_RIGHT);
                field.getVertices()[3].setX(x);
                field.getVertices()[3].setY(y);

                fields.put(fieldId, field);
            }

            NodeList graphVerticesList = d.getElementsByTagName("vertice");
            for(int i=0; i<graphVerticesList.getLength(); i++) {
                Element element = (Element) graphVerticesList.item(i);

                int graphVerticeId = Integer.parseInt(element.getElementsByTagName("id").item(0).getTextContent());
                double x = Double.parseDouble(element.getElementsByTagName("posX").item(0).getTextContent());
                double y = Double.parseDouble(element.getElementsByTagName("posY").item(0).getTextContent());

                GraphVertice graphVertice = new GraphVertice(graphVerticeId, x, y);

                for(int j=0; j<((Element) element.getElementsByTagName("linked").item(0)).getElementsByTagName("verticeID").getLength(); j++) {
                    int linkedVerticeId = Integer.parseInt(((Element) element.getElementsByTagName("linked").item(0)).getElementsByTagName("verticeID").item(j).getTextContent());
                    graphVertice.addLinkedVertice(linkedVerticeId);
                }

                graphVertices.put(graphVerticeId, graphVertice);
            }
        }
        catch(ParserConfigurationException | SAXException | IOException e) {
            throw new IllegalStateException();
        }
    }
}

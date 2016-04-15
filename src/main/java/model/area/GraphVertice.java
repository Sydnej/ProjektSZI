package model.area;

import java.util.ArrayList;
import java.util.List;

public class GraphVertice {

    private int id;
    private double x;
    private double y;
    private List<Integer> linkedVertices;

    public GraphVertice(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
        linkedVertices = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public List<Integer> getLinkedVertices() {
        return linkedVertices;
    }

    public void addLinkedVertice(int linkedVerticeId) {
        linkedVertices.add(linkedVerticeId);
    }

}

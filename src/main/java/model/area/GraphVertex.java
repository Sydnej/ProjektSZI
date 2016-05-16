package model.area;

import model.City;

import java.util.ArrayList;
import java.util.List;

public class GraphVertex {

    private int id;
    private double x;
    private double y;
    private List<Integer> linkedVertices;

    public GraphVertex(int id, double x, double y ) {

        this.id = id;
        this.x = x;
        this.y = y;
        linkedVertices = new ArrayList<>();

    }



    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public List<Integer> getLinkedVertices() {
        return linkedVertices;
    }

    public void addLinkedVertice(int linkedVerticeId) {
        linkedVertices.add(linkedVerticeId);
    }

    // Gets the distance to given vertex
    public double distanceTo(GraphVertex city){
        double xDistance = Math.abs(getX() - city.getX());
        double yDistance = Math.abs(getY() - city.getY());
        double distance = Math.sqrt( (xDistance*xDistance) + (yDistance*yDistance) );

        return distance;
    }
}

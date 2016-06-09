package model.area;

import java.util.ArrayList;
import java.util.List;

public class GraphVertex {

    private int id;
    private double x;
    private double y;
    private List<Integer> linkedVertices;

    public GraphVertex(int id, double x, double y) {

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
    public double distanceTo(GraphVertex city) {
        double xDistance = Math.abs(getX() - city.getX());
        double yDistance = Math.abs(getY() - city.getY());
        double distance = Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));

        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GraphVertex vertex = (GraphVertex) o;

        if (id != vertex.id) return false;
        if (Double.compare(vertex.x, x) != 0) return false;
        if (Double.compare(vertex.y, y) != 0) return false;
        return linkedVertices.equals(vertex.linkedVertices);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        temp = Double.doubleToLongBits(x);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + linkedVertices.hashCode();
        return result;
    }
}

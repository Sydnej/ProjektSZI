package model;

import model.area.Area;
import model.area.GraphVertex;
import model.weather.Weather;

public class Tractor {

    private Weather weather;
    private Area area;
    private GraphVertex currentPosition;

    public Tractor() {
        weather = new Weather();
        area = new Area();
    }

    public Area getArea() {
        return area;
    }

    public GraphVertex getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(GraphVertex currentPosition) {
        this.currentPosition = currentPosition;
    }
}

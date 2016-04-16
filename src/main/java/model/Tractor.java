package model;

import model.area.Area;
import model.weather.Weather;

public class Tractor {

    private Weather weather;
    private Area area;

    public Tractor() {
        weather = new Weather();
        area = new Area();
    }

    public Area getArea() {
        return area;
    }

}

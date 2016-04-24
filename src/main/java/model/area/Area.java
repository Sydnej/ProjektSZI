package model.area;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Area {

    private Map<Integer, Field> fields;
    private Map<Integer, GraphVertex> graphVertices;
    private AreaDataLoader areaDataLoader;

    public Area() {
        fields = new HashMap<>();
        graphVertices = new HashMap<>();
        areaDataLoader = new AreaDataLoader();
    }

    public Map<Integer, Field> getFields() {
        return fields;
    }

    public Map<Integer, GraphVertex> getGraphVertices() {
        return graphVertices;
    }

    public String getAreaImagePath() {
        return areaDataLoader.getAreaImagePath();
    }

    public void loadData(InputStream mapInputStream) {
        areaDataLoader.loadData(fields, graphVertices, mapInputStream);
    }

}

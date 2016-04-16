package model.area;

import java.util.HashMap;
import java.util.Map;

public class Area {

    private Map<Integer, Field> fields;
    private Map<Integer, GraphVertice> graphVertices;
    private AreaDataLoader areaDataLoader;

    public Area() {
        fields = new HashMap<>();
        graphVertices = new HashMap<>();
        areaDataLoader = new AreaDataLoader();
    }

    public Map<Integer, Field> getFields() {
        return fields;
    }

    public Map<Integer, GraphVertice> getGraphVertices() {
        return graphVertices;
    }

    public String getAreaImagePath() {
        return areaDataLoader.getAreaImagePath();
    }

    public void loadData(String filePath) {
        areaDataLoader.loadData(fields, graphVertices, filePath);
    }

}

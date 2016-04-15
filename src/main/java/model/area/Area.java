package model.area;

import java.util.HashMap;
import java.util.Map;

public class Area {

    private Map<Integer, Field> fields;
    private Map<Integer, GraphVertice> graphVertices;

    public Area() {
        fields = new HashMap<>();
        graphVertices = new HashMap<>();

        AreaDataLoader loader = new AreaDataLoader();
        loader.loadData(fields, graphVertices);
    }

    public Map<Integer, Field> getFields() {
        return fields;
    }

    public Map<Integer, GraphVertice> getGraphVertices() {
        return graphVertices;
    }

}

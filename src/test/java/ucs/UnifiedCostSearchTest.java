package ucs;

import model.area.Area;
import model.area.GraphVertex;
import org.junit.Test;

import java.util.LinkedList;
import java.util.Map;

public class UnifiedCostSearchTest {

    @Test
    public void testCalc() {
        UnifiedCostSearch ucs = new UnifiedCostSearch();
        Area area = new Area();
        area.loadData(getClass().getResourceAsStream("../xml/map.xml"));
        Map<Integer, GraphVertex> graphVertices = area.getGraphVertices();
        State result = ucs.calc(area, graphVertices.get(0), graphVertices.get(10), null);
        printPath(result);
        LinkedList<GraphVertex> vertices = UnifiedCostSearch.buildPath(result);
        System.out.println();
        vertices.forEach(v -> System.out.println(v.getId()));
    }

    private void printPath(State result) {
        System.out.println(result.getGraphVertex().getId());
        if (result.hasParent()) {
            printPath(result.getParent());
        }
    }


}
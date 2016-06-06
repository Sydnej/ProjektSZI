package ucs;

import model.area.GraphVertex;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UnifiedCostSearchTest {

    private GraphVertex v0;
    private GraphVertex v1;
    private GraphVertex v2;
    private GraphVertex v3;
    private GraphVertex v4;
    private GraphVertex v5;
    private Map<Integer, GraphVertex> vertices = new HashMap<>();

    @Before
    public void setUp() {
        v0 = new GraphVertex(0, 100, 100);
        v1 = new GraphVertex(1, 100, 200);
        v2 = new GraphVertex(2, 100, 1000);
        v3 = new GraphVertex(3, 300, 1000);
        v4 = new GraphVertex(4, 200, 1000);
        v5 = new GraphVertex(5, 400, 1000);

        vertices.put(0, v0);
        vertices.put(1, v1);
        vertices.put(2, v2);
        vertices.put(3, v3);
        vertices.put(4, v4);
        vertices.put(5, v5);
    }

    @Test
    public void ifAllConnectedPathShouldBeDirect() {
        Collection<GraphVertex> values = vertices.values();
        //connections from all to all
        values.forEach(graphVertex -> values.stream().filter(v -> !v.equals(graphVertex)).forEach(v -> graphVertex
                .addLinkedVertice(v.getId())));

        State result = UnifiedCostSearch.calc(vertices, v0, v5);
        LinkedList<GraphVertex> verticesSorted = UnifiedCostSearch.buildPath(result);
        verticesSorted.forEach(v -> System.out.println(v.getId()));
        assertEquals(2, verticesSorted.size());
        assertEquals(v0, verticesSorted.getFirst());
        assertEquals(v5, verticesSorted.getLast());
    }

    @Test
    public void shouldReturnNullIfNoConnections() {
        State result = UnifiedCostSearch.calc(vertices, v0, v5);
        assertNull(result);
    }

    @Test
    public void shouldFindShortestPath() {
        v0.addLinkedVertice(v1.getId());
        v0.addLinkedVertice(v2.getId());
        v0.addLinkedVertice(v5.getId());
        v2.addLinkedVertice(v4.getId());
        v2.addLinkedVertice(v3.getId());
        v3.addLinkedVertice(v4.getId());
        v4.addLinkedVertice(v5.getId());

        State result = UnifiedCostSearch.calc(vertices, v0, v5);
        LinkedList<GraphVertex> verticesSorted = UnifiedCostSearch.buildPath(result);
        assertEquals(2, verticesSorted.size());
        assertEquals(v0, verticesSorted.getFirst());
        assertEquals(v5, verticesSorted.getLast());
    }

    @Test
    public void shouldGoThroughV1() {
        v0.addLinkedVertice(v1.getId());
        v0.addLinkedVertice(v5.getId());
        v1.addLinkedVertice(v2.getId());
        v5.addLinkedVertice(v2.getId());

        State result = UnifiedCostSearch.calc(vertices, v0, v2);
        LinkedList<GraphVertex> verticesSorted = UnifiedCostSearch.buildPath(result);
        assertEquals(3, verticesSorted.size());
        assertEquals(v0, verticesSorted.get(0));
        assertEquals(v1, verticesSorted.get(1));
        assertEquals(v2, verticesSorted.get(2));
    }
}
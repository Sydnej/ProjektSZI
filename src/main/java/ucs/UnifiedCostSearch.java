package ucs;

import model.area.Area;
import model.area.Field;
import model.area.GraphVertex;

import java.util.*;
import java.util.function.Function;

public class UnifiedCostSearch {

    public static LinkedList<GraphVertex> buildPath(State state) {
        LinkedList<GraphVertex> path = new LinkedList<>();
        addToPath(path, state);
        return path;
    }

    private static void addToPath(LinkedList<GraphVertex> path, State state) {
        path.addFirst(state.getGraphVertex());
        if (state.hasParent()) {
            addToPath(path, state.getParent());
        }
    }

    /**
     * @param area
     * @param startVertex
     * @param endVertex
     * @param costFunction
     * @return null if path not exists. Otherwise best path.
     */
    public State calc(Area area, GraphVertex startVertex, GraphVertex endVertex, Function<Field, Double>
            costFunction) {
        State startState = new State(startVertex);
        State endState = new State(endVertex);
        Set<State> open = new HashSet<>();
        open.add(startState);
        Set<State> closed = new HashSet<>();

        while (!open.isEmpty()) {
            Optional<State> result = open.stream().sorted(Comparator.comparingDouble(this::calcCost)).filter
                    (vertex -> !closed.contains(vertex)).findFirst();
            if (result.isPresent()) {
                State state = result.get();
                open.remove(state);
                if (state.equals(endState)) {
                    return state;
                }
                closed.add(state);
                for (Integer vertexNumber : state.getGraphVertex().getLinkedVertices()) {
                    GraphVertex childVertex = area.getGraphVertices().get(vertexNumber);
                    State childState = new State(childVertex);
                    childState.setParent(state);
                    open.add(childState);
                }
            }
        }
        return null;
    }

    private double calcCost(State state) {
        if (state.hasParent()) {
            return distanceToParent(state) + calcCost(state.getParent());
        }
        return 0;
    }

    private double distanceToParent(State state) {
        GraphVertex stateVertex = state.getGraphVertex();
        GraphVertex parentVertex = state.getParent().getGraphVertex();
        if (stateVertex.getX() == parentVertex.getX()) {
            return Math.abs(stateVertex.getY() - parentVertex.getY());
        }
        if (stateVertex.getY() == parentVertex.getY()) {
            return Math.abs(stateVertex.getX() - parentVertex.getX());
        }
        return Integer.MAX_VALUE;
    }

}

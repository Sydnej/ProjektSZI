package ucs;

import model.area.GraphVertex;

import java.util.*;

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
     * @param graphVertices
     * @param startVertex
     * @param endVertex
     * @return null if path not exists. Otherwise best path.
     */
    public static State calc(Map<Integer, GraphVertex> graphVertices, GraphVertex startVertex, GraphVertex endVertex) {
        State startState = new State(startVertex);
        State endState = new State(endVertex);
        Set<State> open = new HashSet<>();
        open.add(startState);
        Set<State> closed = new HashSet<>();

        while (!open.isEmpty()) {
            Optional<State> result = open.stream().sorted(Comparator.comparingDouble(UnifiedCostSearch::calcCost))
                    .filter(vertex -> !closed.contains(vertex)).findFirst();
            if (result.isPresent()) {
                State state = result.get();
                open.remove(state);
                if (state.equals(endState)) {
                    return state;
                }
                closed.add(state);
                for (Integer vertexNumber : state.getGraphVertex().getLinkedVertices()) {
                    GraphVertex childVertex = graphVertices.get(vertexNumber);
                    State childState = new State(childVertex);
                    childState.setParent(state);
                    open.add(childState);
                }
            }
        }
        return null;
    }

    private static double calcCost(State state) {
        if (state.hasParent()) {
            return distanceToParent(state) + calcCost(state.getParent());
        }
        return 0;
    }

    private static double distanceToParent(State state) {
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

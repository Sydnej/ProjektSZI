package ucs;

import model.area.Area;
import model.area.Field;
import model.area.GraphVertex;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Set;
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
            Optional<State> result = open.stream().sorted((o1, o2) -> calcCost(o2) - calcCost(o1)).filter
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

    private int calcCost(State state) {
        if (state.hasParent()) {
            return 1 + calcCost(state.getParent());
        }
        return 0;
    }

}

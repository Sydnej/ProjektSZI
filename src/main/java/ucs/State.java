package ucs;

import model.area.GraphVertex;

public class State {
    private final GraphVertex graphVertex;

    private State parent;

    public State(GraphVertex graphVertex) {
        this.graphVertex = graphVertex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        State state = (State) o;

        return graphVertex.equals(state.graphVertex);

    }

    @Override
    public int hashCode() {
        return graphVertex.hashCode();
    }

    public boolean hasParent() {
        return parent != null;
    }

    public GraphVertex getGraphVertex() {
        return graphVertex;
    }

    public State getParent() {
        return parent;
    }

    public void setParent(State parent) {
        this.parent = parent;
    }
}

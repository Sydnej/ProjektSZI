package model.area;

public class Field {

    private int id;
    private FieldVertex[] vertices;
    private Level yields;
    private Level weed;

    public Field(int id) {
        this.id = id;
        this.vertices = new FieldVertex[4];
        for(int i=0; i<4; i++) {
            vertices[i] = new FieldVertex();
        }
    }

    public int getId() {
        return id;
    }

    public FieldVertex[] getVertices() {
        return vertices;
    }

    public Level getYields() {
        return yields;
    }

    public Level getWeed() {
        return weed;
    }

    public void setYields(Level yields) {
        this.yields = yields;
    }

    public void setWeed(Level weed) {
        this.weed = weed;
    }
}

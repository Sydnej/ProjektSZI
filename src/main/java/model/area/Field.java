package model.area;

/**
 * Klasa reprezentująca pole.
 * <p>
 * Pole jest opisywane przez identyfikator, tablicę wierzchołków oraz zestaw wskaźników pola o wartościach wyrażonych
 * w %:
 * <ul>
 * <li>plony</li>
 * <li>chwasty</li>
 * <li>minerały</li>
 * <li>nawodnienie</li>
 * </ul>
 * </p>
 */
public class Field {

    private int id;
    private FieldVertex[] corners;

    private double yields;
    private double weed;
    private double minerals;
    private double hydration;

    public Field(int id) {
        this.id = id;
        this.corners = new FieldVertex[4];
        for (int i = 0; i < 4; i++) {
            corners[i] = new FieldVertex();
        }
    }

    public int getId() {
        return id;
    }

    public FieldVertex[] getCorners() {
        return corners;
    }

    public double getYields() {
        return yields;
    }

    public void setYields(double yields) {
        this.yields = yields;
    }

    public double getWeed() {
        return weed;
    }

    public void setWeed(double weed) {
        this.weed = weed;
    }

    public double getMinerals() {
        return minerals;
    }

    public void setMinerals(double minerals) {
        this.minerals = minerals;
    }

    public double getHydration() {
        return hydration;
    }

    public void setHydration(double hydration) {
        this.hydration = hydration;
    }
}

package model.area;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Random;

/**
 * Klasa reprezentująca pole.
 * <p>
 * Pole jest opisywane przez identyfikator, tablicę wierzchołków oraz zestaw wskaźników pola o wartościach wyrażonych
 * w %:
 * <ul>
 * <li>plony</li>
 * <li>chwasty</li>
 * <li>minerały</li>
 * <li>wilgotność</li>
 * </ul>
 * </p>
 */
public class Field {

    private FieldVertex[] corners;
    private IntegerProperty id = new SimpleIntegerProperty();
    private IntegerProperty yields = new SimpleIntegerProperty();
    private IntegerProperty weeds = new SimpleIntegerProperty();
    private IntegerProperty minerals = new SimpleIntegerProperty();
    private IntegerProperty humidity = new SimpleIntegerProperty();

    public Field(int id) {
        this.id.setValue(id);
        this.corners = new FieldVertex[4];
        for (int i = 0; i < 4; i++) {
            corners[i] = new FieldVertex();
        }

        Random random = new Random();
        yields.setValue(Math.abs(random.nextInt() % 101));
        weeds.setValue(Math.abs(random.nextInt() % 101));
        minerals.setValue(Math.abs(random.nextInt() % 101));
     //   minerals.setValue(0);
        humidity.setValue(Math.abs(random.nextInt() % 101));
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public synchronized int getYields() {
        return yields.get();
    }

    public void setYields(int yields) {
        this.yields.set(yields);
    }

    public IntegerProperty yieldsProperty() {
        return yields;
    }

    public synchronized int getWeeds() {
        return weeds.get();
    }

    public void setWeeds(int weeds) {
        this.weeds.set(weeds);
    }

    public IntegerProperty weedsProperty() {
        return weeds;
    }

    public synchronized int getMinerals() {
        return minerals.get();
    }

    public void setMinerals(int minerals) {
        this.minerals.set(minerals);
    }

    public IntegerProperty mineralsProperty() {
        return minerals;
    }

    public int getHumidity() {
        return humidity.get();
    }

    public void setHumidity(int humidity) {
        this.humidity.set(humidity);
    }

    public IntegerProperty humidityProperty() {
        return humidity;
    }

    public FieldVertex[] getCorners() {
        return corners;
    }

}

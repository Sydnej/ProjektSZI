package gui;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ObservableField {

    private IntegerProperty id = new SimpleIntegerProperty();

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public double getYields() {
        return yields.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public void setYields(double yields) {
        this.yields.set(yields);
    }

    public void setWeeds(double weeds) {
        this.weeds.set(weeds);
    }

    public void setMinerals(double minerals) {
        this.minerals.set(minerals);
    }

    public DoubleProperty yieldsProperty() {
        return yields;
    }

    public double getWeeds() {
        return weeds.get();
    }

    public DoubleProperty weedsProperty() {
        return weeds;
    }

    public double getMinerals() {
        return minerals.get();
    }

    public DoubleProperty mineralsProperty() {
        return minerals;
    }

    private DoubleProperty yields = new SimpleDoubleProperty();
    private DoubleProperty weeds = new SimpleDoubleProperty();
    private DoubleProperty minerals = new SimpleDoubleProperty();

}

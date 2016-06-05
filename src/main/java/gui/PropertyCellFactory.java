package gui;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import model.area.Field;

class PropertyCellFactory implements javafx.util.Callback<javafx.scene.control.TableColumn<model.area.Field,
        Integer>, javafx.scene.control.TableCell<model.area.Field, Integer>> {

    private final boolean reversed;

    PropertyCellFactory(boolean reversed) {
        this.reversed = reversed;
    }

    @Override
    public TableCell<Field, Integer> call(TableColumn<Field, Integer> param) {
        return new PropertyTableCell(reversed);
    }

    private class PropertyTableCell extends TableCell<Field, Integer> {
        private final String highColor;
        private final String lowColor;
        private final String middleColor;

        PropertyTableCell(boolean reversed) {
            if (reversed) {
                highColor = "lightgreen";
                lowColor = "indianred";
            } else {
                highColor = "indianred";
                lowColor = "lightgreen";
            }
            middleColor = "goldenrod";
        }

        @Override
        protected void updateItem(Integer item, boolean empty) {
            super.updateItem(item, empty);
            if (item == null || empty) {
                setText(null);
                setStyle("");
            } else {
                setText(item.toString());
                if (item >= 70) {
                    setStyle("-fx-background-color: " + highColor);
                } else if (item >= 50) {
                    setStyle("-fx-background-color: " + middleColor);
                } else {
                    setStyle("-fx-background-color: " + lowColor);
                }
            }
        }
    }

}
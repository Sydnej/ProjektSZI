package model.neural;

import java.util.ArrayList;
import java.util.List;

public class LearningSet {

    private List<List<Three>> set;

    public LearningSet() {
        set = new ArrayList<>();

        for(int i=0; i<10; i++) {
            List<Three> layer = new ArrayList<>();
            if(i != 9) {
                for(int j=0; j<3; j++) {
                    Three three = new Three();
                    layer.add(three);
                }
            }
            else {
                Three three = new Three();
                layer.add(three);
            }
            set.add(layer);
        }

        loadLearningData();
    }

    public List<List<Three>> getLearningSet() {
        return set;
    }

    private void loadLearningData() {
        set.get(0).get(0).setValues(30, 45, 32);
        set.get(0).get(0).setExpectedOutput(36);

        set.get(0).get(1).setValues(29, 15, 32);
        set.get(0).get(1).setExpectedOutput(31);

        set.get(0).get(2).setValues(75, 27, 34);
        set.get(0).get(2).setExpectedOutput(51);


        set.get(1).get(0).setValues(5, 3, 53);
        set.get(1).get(0).setExpectedOutput(27);

        set.get(1).get(1).setValues(49, 12, 24);
        set.get(1).get(1).setExpectedOutput(35);

        set.get(1).get(2).setValues(13, 25, 68);
        set.get(1).get(2).setExpectedOutput(49);


        set.get(2).get(0).setValues(1, 0, 5);
        set.get(2).get(0).setExpectedOutput(2);

        set.get(2).get(1).setValues(4, 41, 81);
        set.get(2).get(1).setExpectedOutput(46);

        set.get(2).get(2).setValues(68, 43, 27);
        set.get(2).get(2).setExpectedOutput(39);


        set.get(3).get(0).setValues(90, 67, 82);
        set.get(3).get(0).setExpectedOutput(79);

        set.get(3).get(1).setValues(12, 56, 23);
        set.get(3).get(1).setExpectedOutput(36);

        set.get(3).get(2).setValues(67, 45, 34);
        set.get(3).get(2).setExpectedOutput(47);


        set.get(4).get(0).setValues(35, 34, 33);
        set.get(4).get(0).setExpectedOutput(33);

        set.get(4).get(1).setValues(78, 56, 89);
        set.get(4).get(1).setExpectedOutput(72);

        set.get(4).get(2).setValues(12, 67, 23);
        set.get(4).get(2).setExpectedOutput(42);


        set.get(5).get(0).setValues(23, 69, 83);
        set.get(5).get(0).setExpectedOutput(53);

        set.get(5).get(1).setValues(93, 65, 2);
        set.get(5).get(1).setExpectedOutput(46);

        set.get(5).get(2).setValues(83, 15, 4);
        set.get(5).get(2).setExpectedOutput(34);


        set.get(6).get(0).setValues(5, 1, 23);
        set.get(6).get(0).setExpectedOutput(9);

        set.get(6).get(1).setValues(27, 23, 32);
        set.get(6).get(1).setExpectedOutput(27);

        set.get(6).get(2).setValues(0, 3, 12);
        set.get(6).get(2).setExpectedOutput(5);


        set.get(7).get(0).setValues(45, 15, 32);
        set.get(7).get(0).setExpectedOutput(29);

        set.get(7).get(1).setValues(20, 42, 12);
        set.get(7).get(1).setExpectedOutput(22);

        set.get(7).get(2).setValues(14, 14, 15);
        set.get(7).get(2).setExpectedOutput(14);


        set.get(8).get(0).setValues(76, 15, 56);
        set.get(8).get(0).setExpectedOutput(47);

        set.get(8).get(1).setValues(16, 14, 62);
        set.get(8).get(1).setExpectedOutput(31);

        set.get(8).get(2).setValues(2, 7, 8);
        set.get(8).get(2).setExpectedOutput(5);


        set.get(9).get(0).setValues(89, 12, 23);
        set.get(9).get(0).setExpectedOutput(40);
    }

}

class Three {

    private double firstValue;
    private double secondValue;
    private double thirdValue;
    private double expectedOutput;

    public double getFirstValue() {
        return firstValue;
    }

    public double getSecondValue() {
        return secondValue;
    }

    public double getThirdValue() {
        return thirdValue;
    }

    public double getExpectedOutput() {
        return expectedOutput;
    }

    public void setValues(double firstValue, double secondValue, double thirdValue) {
        this.firstValue = firstValue;
        this.secondValue = secondValue;
        this.thirdValue = thirdValue;
    }

    public void setExpectedOutput(double expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

}
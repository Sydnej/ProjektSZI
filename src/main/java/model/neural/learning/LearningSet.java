package model.neural.learning;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LearningSet {

    private List<LearningElement> set;
    private String fileName;

    public LearningSet(String fileName) {
        this.set = new ArrayList<>();
        this.fileName = fileName;

        try {
            loadLearningData();
        }
        catch(FileNotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<LearningElement> getLearningSet() {
        return set;
    }

    private void loadLearningData() throws FileNotFoundException {
        String projectPath = System.getProperty("user.dir");
        InputStream is;
        if(projectPath.contains("\\")) {
            is = new FileInputStream(projectPath + "\\src\\neural\\learning\\" + fileName);
        }
        else {
            is = new FileInputStream(projectPath + "/src/neural/learning/" + fileName);
        }
        Scanner scanner = new Scanner(is);
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] stringValues = line.split(",");
            int[] values = new int[4];
            for(int i=0; i<4; i++) {
                values[i] = Integer.parseInt(stringValues[i]);
            }

            LearningElement element = new LearningElement();
            element.setValues(values[0], values[1], values[2]);
            element.setExpectedOutput(values[3]);
            set.add(element);
        }
        scanner.close();
    }

    public class LearningElement {

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

}
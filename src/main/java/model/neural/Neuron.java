package model.neural;

import java.util.ArrayList;
import java.util.List;

public class Neuron {

    private List<Double> inputSignals;
    private List<Double> weights;
    private double expectedOutput;

    public Neuron() {
        inputSignals = new ArrayList<>();
        weights = new ArrayList<>();
    }

    // dodanie sygnału wejściowego i przypisanej mu wagi
    public void addSignalAndItsWeight(double signal, double weight) {
        inputSignals.add(signal);
        weights.add(weight);
    }

    public double getSignal(int position) {
        return inputSignals.get(position);
    }

    public double getWeight(int position) {
        return weights.get(position);
    }

    public double getExpectedOutput() {
        return expectedOutput;
    }

    // zmiana wartości wybranego sygnału wejściowego i jego wagi
    public void setSignalAndItsWeight(int position, double signal, double weight) {
        inputSignals.set(position, signal);
        weights.set(position, weight);
    }

    public void setSignal(int position, double signal) {
        inputSignals.set(position, signal);
    }

    public void setWeight(int position, double weight) {
        weights.set(position, weight);
    }

    // ustawienie oczekiwanego wyjścia podczas uczenia sieci
    public void setExpectedOutput(double expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    public int getNumberOfSignals() {
        return inputSignals.size();
    }

    public double getSumOfSignalsAndWeights() {
        double sum = 0;
        for(int i=0; i<inputSignals.size(); i++) {
            sum = sum + (getSignal(i) * getWeight(i));
        }
        return sum;
    }

    // funkcja aktywacji
    public double getOutput() {
        return getSumOfSignalsAndWeights() / getNumberOfSignals();
    }

}

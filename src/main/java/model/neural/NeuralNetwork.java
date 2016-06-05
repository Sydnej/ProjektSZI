package model.neural;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NeuralNetwork {

    private List<List<Neuron>> layers;

    public NeuralNetwork() {
        layers = new ArrayList<>();

        for(int i=0; i<10; i++) {
            List<Neuron> layer = new ArrayList<>();
            if(i != 9) {
                for(int j=0; j<3; j++) {
                    Neuron neuron = new Neuron();
                    neuron.addSignalAndItsWeight(0, new Random().nextDouble());
                    neuron.addSignalAndItsWeight(0, new Random().nextDouble());
                    neuron.addSignalAndItsWeight(0, new Random().nextDouble());
                    layer.add(neuron);
                }
            }
            else {
                Neuron neuron = new Neuron();
                neuron.addSignalAndItsWeight(0, new Random().nextDouble());
                neuron.addSignalAndItsWeight(0, new Random().nextDouble());
                neuron.addSignalAndItsWeight(0, new Random().nextDouble());
                layer.add(neuron);
            }
            layers.add(layer);
        }
    }

    public void addNeuron(Neuron neuron, int layerIndex) {
        layers.get(layerIndex).add(neuron);
    }

    public List<Neuron> getLayer(int layerIndex) {
        return layers.get(layerIndex);
    }

    public Neuron getNeuron(int layerIndex, int position) {
        return layers.get(layerIndex).get(position);
    }

    public int getNumberOfLayers() {
        return layers.size();
    }

    public void connectAllLayers() {
        for(int i=0; i<getNumberOfLayers()-1; i++) {
            for(int j=0; j<layers.get(i).size(); j++) {
                for(int k=0; k<layers.get(i+1).size(); k++) {
                    layers.get(i+1).get(k).setSignal(j, layers.get(i).get(j).getOutput());
                }
            }
        }
    }

    public void connectTwoLayers(int layerFromIndex) {
        for(int j=0; j<layers.get(layerFromIndex).size(); j++) {
            for(int k=0; k<layers.get(layerFromIndex+1).size(); k++) {
                layers.get(layerFromIndex+1).get(k).setSignal(j, layers.get(layerFromIndex).get(j).getOutput());
            }
        }
    }

    public void learn(LearningSet learningSet) {
        for(int i=0; i<learningSet.getLearningSet().size(); i++) {
            for(int j=0; j<learningSet.getLearningSet().get(i).size(); j++) {
                Three neuron = learningSet.getLearningSet().get(i).get(j);

                getNeuron(i, j).setSignal(0, neuron.getFirstValue()); // plony
                getNeuron(i, j).setSignal(1, neuron.getSecondValue()); // chwasty
                getNeuron(i, j).setSignal(2, neuron.getThirdValue()); // minerały
                getNeuron(i, j).setExpectedOutput(neuron.getExpectedOutput()); // oczekiwane wyjście
            }
        }

        for(int i=0; i<layers.size(); i++) {
            for(int j=0; j<layers.get(i).size(); j++) {
                Neuron neuron = getNeuron(i, j);
                while(Math.abs(neuron.getOutput() - neuron.getExpectedOutput()) > 0.5) {
                    if(neuron.getOutput() > neuron.getExpectedOutput()) {
                        neuron.setWeight(0, neuron.getWeight(0) - 0.01);
                        neuron.setWeight(1, neuron.getWeight(1) - 0.01);
                        neuron.setWeight(2, neuron.getWeight(2) - 0.01);
                    }
                    else {
                        neuron.setWeight(0, neuron.getWeight(0) + 0.01);
                        neuron.setWeight(1, neuron.getWeight(1) + 0.01);
                        neuron.setWeight(2, neuron.getWeight(2) + 0.01);
                    }
                }
            }
        }
    }

}

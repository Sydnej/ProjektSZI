package model.neural;

import model.neural.learning.LearningSet;

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

    public void connectFromLayer(int layerFromIndex) {
        for(int i=layerFromIndex; i<getNumberOfLayers()-1; i++) {
            for(int j=0; j<layers.get(i).size(); j++) {
                for(int k=0; k<layers.get(i+1).size(); k++) {
                    layers.get(i+1).get(k).setSignal(j, layers.get(i).get(j).getOutput());
                }
            }
        }
    }

    public double getNetworkOutput() {
        return layers.get(getNumberOfLayers()-1).get(0).getOutput();
    }

    public void learnByBackpropagation(LearningSet learningSet) {
        // temperature
        // humidity
        // rain

        for(LearningSet.LearningElement element : learningSet.getLearningSet()) {
            for(Neuron neuron : layers.get(0)) {
                neuron.setSignal(0, element.getFirstValue());
                neuron.setSignal(1, element.getSecondValue());
                neuron.setSignal(2, element.getThirdValue());
            }

            connectAllLayers();

            double delta = element.getExpectedOutput() - getNetworkOutput();

            for(int i=layers.size()-1; i>=0; i--) {
                while(Math.abs(delta) > 0.5) {
                    for(Neuron neuron : layers.get(i)) {
                        if(delta > 0) {
                            neuron.setWeight(0, neuron.getWeight(0) + 0.1);
                            neuron.setWeight(1, neuron.getWeight(1) + 0.1);
                            neuron.setWeight(2, neuron.getWeight(2) + 0.1);
                        }
                        else {
                            neuron.setWeight(0, neuron.getWeight(0) - 0.1);
                            neuron.setWeight(1, neuron.getWeight(1) - 0.1);
                            neuron.setWeight(2, neuron.getWeight(2) - 0.1);
                        }
                        connectFromLayer(i);
                        delta = element.getExpectedOutput() - getNetworkOutput();
                    }
                }
            }
        }
    }

}

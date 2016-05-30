package DecisionTrees;

import java.io.*;
import java.util.*;

public class Tree {
	public Node buildTree(ArrayList<Record> records, Node root, LearningSet learningSet) {
		int bestAttribute = -1;
		double bestGain = 0;
		root.setEntropy(Entropy.calculateEntropy(root.getData()));
		
		if(root.getEntropy() == 0) {
			return root;
		}
		
		for(int i = 0; i < Hw1.NUM_ATTRS - 2; i++) {
			if(!Hw1.isAttributeUsed(i)) {
				double entropy = 0;
				ArrayList<Double> entropies = new ArrayList<Double>();
				ArrayList<Integer> setSizes = new ArrayList<Integer>();
				
				for(int j = 0; j < Hw1.NUM_ATTRS - 2; j++) {
					ArrayList<Record> subset = subset(root, i, j);
					setSizes.add(subset.size());
					
					if(subset.size() != 0) {
						entropy = Entropy.calculateEntropy(subset);
						entropies.add(entropy);
					}
				}
				
				double gain = Entropy.calculateGain(root.getEntropy(), entropies, setSizes, root.getData().size());
				
				if(gain > bestGain) {
					bestAttribute = i;
					bestGain = gain;
				}
			}
		}
		if(bestAttribute != -1) {
			int setSize = Hw1.setSize(Hw1.attrMap.get(bestAttribute));
			root.setTestAttribute(new DiscreteAttribute(Hw1.attrMap.get(bestAttribute), 0));
			root.children = new Node[setSize];
			root.setUsed(true);
			Hw1.usedAttributes.add(bestAttribute);
			
			for (int j = 0; j< setSize; j++) {
				root.children[j] = new Node();
				root.children[j].setParent(root);
				root.children[j].setData(subset(root, bestAttribute, j));
				root.children[j].getTestAttribute().setName(Hw1.getLeafNames(bestAttribute, j));
				root.children[j].getTestAttribute().setValue(j);
			}

			for (int j = 0; j < setSize; j++) {
				buildTree(root.children[j].getData(), root.children[j], learningSet);
			}

			root.setData(null);
		}
		else {
			return root;
		}
		
		return root;
	}
	
	public ArrayList<Record> subset(Node root, int attr, int value) {
		ArrayList<Record> subset = new ArrayList<Record>();
		
		for(int i = 0; i < root.getData().size(); i++) {
			Record record = root.getData().get(i);
			
			if(record.getAttributes().get(attr).getValue() == value) {
				subset.add(record);
			}
		}
		return subset;
	}
	
	public double calculateSurrogates(ArrayList<Record> records) {
		return 0;
		
	}
	
	public void pruneTree() {
		
	}
}

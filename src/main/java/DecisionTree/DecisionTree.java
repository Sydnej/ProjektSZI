package DecisionTree;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DecisionTree {


	private Node root = null;


	public DecisionTree(List<Element> trainingSet, String[] attribute, int numOfOutput) {
		Bag firstBag = new Bag(null, null, trainingSet, attribute, numOfOutput);
		buildTree(firstBag, null);
	}


	private void buildTree(Bag bag, Node parent) {
		Bag[] classifiedBags = bag.classifyByMaxIG();


		if (classifiedBags == null) {

			String ouputString = bag.getElementList().get(0).getOutput();
			Node ouputNode = new Node(ouputString, parent);

			parent.addChildren(bag.getName(), ouputNode);
			return;
		}


		String rootAttribute = classifiedBags[0].getRootAttribute();
		Node newNode = new Node(rootAttribute, parent);


		if (root == null) {
			root = newNode;
		} else {

			parent.addChildren(bag.getName(), newNode);
		}

		// Budowa gałęzi drzewa
		for (Bag b : classifiedBags) {
			buildTree(b, newNode);
		}
	}


    class Node {

        public String name;
        private Node parent;
        private Map<String, Node> childrens = new HashMap<String, Node>();


        public Node(String name, Node parent) {
        		this.name = name;
        		this.parent = parent;
        }


        public void addChildren(String branchName, Node child) {
        		childrens.put(branchName, child);
        }


        public String getName() {
			return name;
		}


        public Node getParent() {
			return parent;
		}


        public Map<String, Node> getChildrens() {
			return childrens;
		}


        public Node getOneChild(String branchName) {
			return childrens.get(branchName);
		}

    }

    public Node getRoot() {
		return root;
	}


    public String findAnswer(Element element) {
    		Node finialNode = visitNode(root, element);


    		if (finialNode == null) {
    			return null;
    		}

    		return finialNode.name;
	}


    private Node visitNode(Node node, Element element) {
    		// Zła ścieżka
    		if (node == null) {
    			return null;
    		}

    		// Liście
    		if (node.getChildrens().isEmpty()) {
    			return node;
    		}

    		// Następny węzeł
       // System.out.println("Node: " + node.name);
       // System.out.println("getAtributeDate: "+element.getAttributeData(node.name));
    		Node nextNode = node.getOneChild(element.getAttributeData(node.name));
      //  System.out.println("nextNode: " + nextNode.name);
		return visitNode(nextNode, element);
	}


    public double calculateAccuracy(List<Element> testSet) {
    		int correct = 0;
    		for (Element e : testSet) {

    			if (findAnswer(e) != null && findAnswer(e).equals(e.getOutput())) {
    				correct++;
    			}
    		}
		return correct * 1.0 / testSet.size();
	}



	public String newDecision(newElement Element) {


        Node finalNode = SearcgTree(root, Element);
        return finalNode.name;
	}

    private Node SearcgTree(Node node, newElement element){
        // Zła ścieżka
        if (node == null) {
            return null;
        }
        // Liście
        if (node.getChildrens().isEmpty()) {
            return node;
        }
        Node nextNode = node.getOneChild(element.getAttributeData(node.name));
        return SearcgTree(nextNode, element);
    }

}
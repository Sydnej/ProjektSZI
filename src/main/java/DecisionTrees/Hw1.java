package DecisionTrees;

import java.util.*;

public class Hw1 {
	public static int NUM_ATTRS = 6;
	public static ArrayList<String> attrMap;
	public static ArrayList<Integer> usedAttributes = new ArrayList<Integer>();

	public static void main(String[] args) {
		populateAttrMap();
		Tree t = new Tree();
		ArrayList<Record> records;
		LearningSet learningSet = new LearningSet();
		
		// read in all our data
		records = FileReader.buildRecords();
		
		Node root = new Node();
		
		for(Record record : records) {
			root.getData().add(record);
		}
		
		t.buildTree(records, root, learningSet);
		traverseTree(records.get(12), root);
		return;
	}
	
	public static void traverseTree(Record r, Node root) {
		while(root.children != null) {
			double nodeValue = 0;
			for(int i = 0; i < r.getAttributes().size(); i++) {
				if(r.getAttributes().get(i).getName().equalsIgnoreCase(root.getTestAttribute().getName())) {
					nodeValue = r.getAttributes().get(i).getValue();
					break;
				}
			}
			for(int i = 0; i < root.getChildren().length; i++) {
				if(nodeValue == root.children[i].getTestAttribute().getValue()) {
					traverseTree(r, root.children[i]);
				}
			}
		}
		
		System.out.print("Prediction for Play Tennis: ");
		if(root.getTestAttribute().getValue() == 0) {
			System.out.println("No");
		}
		else if(root.getTestAttribute().getValue() == 0) {
			System.out.println("Yes");
		}

		return;
	}
	
	public static boolean isAttributeUsed(int attribute) {
		if(usedAttributes.contains(attribute)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static int setSize(String set) {
		if(set.equalsIgnoreCase("Outlook")) {
			return 3;
		}
		else if(set.equalsIgnoreCase("Wind")) {
			return 2;
		}
		else if(set.equalsIgnoreCase("Temperature")) {
			return 3;
		}
		else if(set.equalsIgnoreCase("Humidity")) {
			return 2;
		}
		else if(set.equalsIgnoreCase("PlayTennis")) {
			return 2;
		}
		return 0;
	}
	
	public static String getLeafNames(int attributeNum, int valueNum) {
		if(attributeNum == 0) {
			if(valueNum == 0) {
				return "Sunny";
			}
			else if(valueNum == 1) {
				return "Overcast";
			}
			else if(valueNum == 2) {
				return "Rain";
			}
		}
		else if(attributeNum == 1) {
			if(valueNum == 0) {
				return "Hot";
			}
			else if(valueNum == 1) {
				return "Mild";
			}
			else if(valueNum == 2) {
				return "Cool";
			}
		}
		else if(attributeNum == 2) {
			if(valueNum == 0) {
				return "High";
			}
			else if(valueNum == 1) {
				return "Normal";
			}
		}
		else if(attributeNum == 3) {
			if(valueNum == 0) {
				return "Weak";
			}
			else if(valueNum == 1) {
				return "Strong";
			}
		}
		
		return null;
	}
	
	public static void populateAttrMap() {
		attrMap = new ArrayList<String>();
		attrMap.add("Outlook");
		attrMap.add("Temperature");
		attrMap.add("Humidity");
		attrMap.add("Wind");
		attrMap.add("PlayTennis");
	}
}

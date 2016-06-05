package DecisionTree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Bag {
	

	private String[] unusedAttributes;		

	private String rootAttribute;	

	private String name;

	private List<Element> elementsInBag = new ArrayList<Element>();
	// 結果的種類數
	private int numberOfOutput;
	

	public Bag(String rootAttribute, 
			   String branchName, 
			   List<Element> elementsInBag,
			   String[] unusedAttributes, 
			   int numberOfOutput) {
		this.rootAttribute = rootAttribute;
		this.name = branchName;
		this.unusedAttributes = unusedAttributes;
		this.numberOfOutput = numberOfOutput;
		this.elementsInBag = elementsInBag;
	}
	

	public String getName() {
		return name;
	}
	

	public String[] getUnusedAttrubutes() {
		return unusedAttributes;
	}
	

	public List<Element> getElementList() {
		return elementsInBag;
	}
	

	public String getRootAttribute() {
		return rootAttribute;
	}
		

	public Bag[] classifyByMaxIG() {

		double maxInformationGain = 0;

		String targetAttribute = "";

		Map<String, List<Element>> targetClassifiedElements = new HashMap<String, List<Element>>();
		

		double entropyOfBag = calculateEntropy(countNumberOfEachOutput(elementsInBag));
		

		for (String attribute : unusedAttributes) {
			//
			Map<String, List<Element>> classifiedElements = classifyElements(attribute);
			

			double entropyOfAttribute = 0;

			for (String value : classifiedElements.keySet()) {
				Map<String, Integer> numberOfEachOutput = countNumberOfEachOutput(classifiedElements.get(value));

				double entropyOfBranch = calculateEntropy(numberOfEachOutput);

				double scaleOfBranch = countNumberOfElements(numberOfEachOutput) * 1.0 / elementsInBag.size();
				
				entropyOfAttribute += entropyOfBranch * scaleOfBranch;
			}
			

			double informationGain = entropyOfBag - entropyOfAttribute;
			

			if (informationGain > maxInformationGain) {
				maxInformationGain = informationGain;
				targetAttribute = attribute;
				targetClassifiedElements = classifiedElements;
			}
		}
		

		if (targetAttribute.equals("")) {
			return null;
		}
		

		Bag[] classifiedBags = new Bag[targetClassifiedElements.keySet().size()];
		String[] newUnusedAttributes = removeElement(unusedAttributes, targetAttribute);
		int index = 0;
		for (String value : targetClassifiedElements.keySet()) {
			classifiedBags[index] = new Bag(targetAttribute, 
											value, 
											targetClassifiedElements.get(value) ,
											newUnusedAttributes, 
											numberOfOutput);
			index++;
		}
		
		return classifiedBags;
	}
	

	private String[] removeElement(String[] array, String e) {
		String[] newArray = new String[array.length - 1];		
		for (int i = 0, counter = 0; i < array.length; i++) {
			if (!array[i].equals(e)) {
				newArray[counter++] = array[i];
			}
		}
		
		return newArray;
	}
	

	private Map<String, List<Element>> classifyElements(String attribute) {
		Map<String, List<Element>> classifiedElements = new HashMap<String, List<Element>>();
		for (Element e : elementsInBag) {
			String attributeData = e.getAttributeData(attribute);
			if (!classifiedElements.containsKey(attributeData)) {
				classifiedElements.put(attributeData, new ArrayList<Element>());	
			}
			classifiedElements.get(attributeData).add(e);
		}
		return classifiedElements;
	}
	

	public Map<String, Integer> countNumberOfEachOutput(List<Element> elements) {
		Map<String, Integer> numberOfEachOutput = new HashMap<String, Integer>();
		for (Element e : elements) {
			String output = e.getOutput();
			if (!numberOfEachOutput.containsKey(output)) {
				numberOfEachOutput.put(output, 1);
			} else {
				numberOfEachOutput.put(output, numberOfEachOutput.get(output) + 1);
			}
		}
		return numberOfEachOutput;
	}
	

	private double calculateEntropy(Map<String, Integer> numberOfEachOutput) {
		double entropy = 0.0;
		int numberOfElements = countNumberOfElements(numberOfEachOutput);
		
		for (String key : numberOfEachOutput.keySet()) {
            double probability = numberOfEachOutput.get(key) * 1.0 / numberOfElements;
            entropy -= probability * Math.log(probability) / Math.log(numberOfOutput);
        }
		return entropy;
	}
	

	private int countNumberOfElements(Map<String, Integer> numberOfEachOutput) {
		int sum = 0;
		for (String key : numberOfEachOutput.keySet()) {
			sum += numberOfEachOutput.get(key);
		}
		return sum;
	}
}


class Element {
	

	private Map<String, String> attDataMap = new HashMap<String, String>();


	private String output;


	public Element(String[] attributes, String[] attData, String result) {
		for (int i = 0; i < attributes.length; i++) {
			this.attDataMap.put(attributes[i], attData[i]);
		}
		this.output = result;
	}
	

	public String getAttributeData(String attribute) {
		return this.attDataMap.get(attribute);
	}
	

	public String getOutput() {
		return this.output;
	}
}

class newElement {


	private Map<String, String> attDataMap = new HashMap<String, String>();

	public newElement(String pogoda, String temperatura, String wilgotnosc, String wiatr) {

		attDataMap.put("Pogoda", pogoda);
		attDataMap.put("Temperatura", temperatura);
		attDataMap.put("Wilgotnosc", wilgotnosc);
		attDataMap.put("Wiatr", wiatr);


	}


	public String getAttributeData(String attribute) {
		return this.attDataMap.get(attribute);
	}


}
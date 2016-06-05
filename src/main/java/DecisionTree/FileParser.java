package DecisionTree;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class FileParser {
	

	private String[] attributes;

	private List<Element> elementList = new ArrayList<Element>();

	private Set<String> outputsSet = new HashSet<String>();
	

	public FileParser(String fileName) {
		try (Scanner scanner = new Scanner(new File(fileName))) {

			if (scanner.hasNext()) {
				loadAttribute(scanner.nextLine());
			}

			while (scanner.hasNext()) {
				addData(scanner.nextLine());
			}
		} catch (IOException e) { 
			e.printStackTrace(); 
		}
	}
	

	private void loadAttribute(String attStr) {
		String[] raw = attStr.trim().split("\t");
		attributes = Arrays.copyOfRange(raw, 0, raw.length - 1);
	}
	

	private void addData(String dataStr) {

		if (dataStr == null || dataStr.equals("")) {
			return;
		}


		String[] dataArray = dataStr.split("\t");
		

		if (dataArray.length != getAttributesLength() + 1) {
			return;
		}
		

		elementList.add(new Element(getAttributes(),
							Arrays.copyOfRange(dataArray, 0, getAttributesLength()),
							dataArray[getAttributesLength()])
					   );
		

		outputsSet.add(dataArray[getAttributesLength()]);
	}
	

	public int getAttributesLength() {
		if (attributes == null) {
			return 0; 
		}
		return attributes.length;
	}
	

	public String[] getAttributes() {
		return attributes;
	}
	

	public List<Element> getElementList() {
		return new ArrayList<Element>(elementList);
	}
	

	public int getNumberOfOutput() {
		return outputsSet.size();
	}
}
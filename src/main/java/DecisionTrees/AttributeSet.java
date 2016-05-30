package DecisionTrees;
import java.util.*;

public class AttributeSet {
	private String name;
	private ArrayList<DiscreteAttribute> attributes;
	private double entropy;
	private boolean isUsed;
	
	public AttributeSet() {
		attributes = new ArrayList<DiscreteAttribute>();
		entropy = -1;
		isUsed = false;
	}
	
	public void setAttributes(ArrayList<DiscreteAttribute> attributes) {
		this.attributes = attributes;
	}

	public ArrayList<DiscreteAttribute> getAttributes() {
		return attributes;
	}

	public void setEntropy(double entropy) {
		this.entropy = entropy;
	}

	public double getEntropy() {
		return entropy;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}

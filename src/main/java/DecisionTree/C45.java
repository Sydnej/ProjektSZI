package DecisionTree;

import java.util.ArrayList;
import java.util.List;


public class C45 {


	// Procent danych testowych
	private final static double PERCENT = 0.1;
	// Próg dokładności
	private final static double MINACCURACY = 0.6;
	

	private static List<Element> trainingSet = new ArrayList<Element>();
	private static List<Element> testSet  = new ArrayList<Element>();
	//private static newElement elementSet = new newElement();
	private DecisionTree decisionTree;

	public  void C45(String[] args) {
		
		// Okno
		Gui gui = new Gui();
		
		double accuracy = 0;	// Dokładność zestaw testowy
		do {
			// Czytanie wszystkich wierszy danych

			//String fileName = getClass().getResource("Data.txt").getFile();
			//System.out.println("ścieżka: " + fileName);
			FileParser filePaser = new FileParser(System.getProperty("user.dir") + "\\src\\main\\resources\\model\\Data.txt");
			List<Element> elementList = filePaser.getElementList();
			
			// Dzielenie zestawu na trening i test
			assignElements(elementList);
			
			// Okno danych
			gui.setAttribute(filePaser.getAttributes());
			gui.setTrainingSet(trainingSet);
			gui.setTestSet(testSet);
			
			// Drzewo decyzyjne przy pomocy zestawu szkoleniowy
			 decisionTree = new DecisionTree(trainingSet,
														filePaser.getAttributes(), 
														filePaser.getNumberOfOutput());
			
			// Rysuj drzewo
			gui.setTree(decisionTree);
			
			// Testowanie poprawności zestawu testowego
			accuracy = decisionTree.calculateAccuracy(testSet);
			//System.out.println(decisionTree.newDecision(testSet));

			/*newElement elementSet = new newElement("overcast","hot","normal","weak");
			String result = decisionTree.newDecision(elementSet);
			System.out.println("Wynik nowego elementu: " + result);
*/

		} while (accuracy < MINACCURACY);
		

		gui.setAccuracy(accuracy * 100);

	}
	
	public String MakeDecision(String Pogoda,String Temp, String Wilg, String Wiatr ){
		newElement elementSet = new newElement(Pogoda,Temp,Wilg,Wiatr);
		String result = decisionTree.newDecision(elementSet);
		return result;
	}

	private static void assignElements(List<Element> elementList) {

		//System.out.println(elementList.size());
		int numOfTest = (int)(elementList.size() * PERCENT);
		

		for (int i = 0; i < numOfTest; i++) {
			int randomNum = (int)(Math.random() * (elementList.size() - 1));
			testSet.add(elementList.get(randomNum));
			elementList.remove(randomNum);
		}
		
		// Pozostałe dane dla zbioru uczącego
		trainingSet = elementList;
	}
}
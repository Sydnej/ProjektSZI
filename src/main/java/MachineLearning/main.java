package MachineLearning;

import weka.core.converters.ArffLoader;
import java.io.File;
import weka.core.Instances;
import weka.classifiers.trees.LMT;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.core.Instance;


import java.util.Enumeration;
import java.text.DecimalFormat;


//Testujemy nasz training set
//Wypisujemy prawdopodobienstwa przynalezenia do kazdej z  3 mozliwych klas
//Algorytm LMT
public class main {
    public static void main(String[] args) throws Exception {


        // ladujemy plik Arff z pelnymi danymi jako training set
        ArffLoader loader = new ArffLoader();
        loader.setFile(new File("test.arff"));
        Instances trainingSet = loader.getDataSet();
        // szczegolowosc danych, tj. ilosc etykiet ktore im przypisujemy ( w tym przypadku 5)
        int classIdx = 5;

        // ladjemy plik Arff jako test set
        ArffLoader loader2 = new ArffLoader();
        loader2.setFile(new File("test2.arff"));
        Instances testSet = loader2.getDataSet();

        // Ustawiamy klase (etykiete ktora szukamy) na 5-tej wartosci
        trainingSet.setClassIndex(classIdx);
        testSet.setClassIndex(classIdx);

        // Uzywamy algorytmu LMT skladajacego sie z drzew decyzyjnych i regresji logistycznej
        Classifier classifier = new LMT();
        classifier.buildClassifier(trainingSet);

        // Wykonujemy test
        Evaluation eval = new Evaluation(trainingSet);
        eval.evaluateModel(classifier, testSet);

        // Wypisz wynik testu
        System.out.println(eval.toSummaryString());

        // Confusion matrix = tablica pomnylek/macierz bledow
        double[][] confusionMatrix = eval.confusionMatrix();


        // Ladujemy zestaw bez klasy by procentowo ocenic do ktorego zbioru nalezy
        ArffLoader loader3 = new ArffLoader();
        loader3.setFile(new File("test3.arff"));
        Instances dataSet = loader3.getDataSet();

        DecimalFormat df = new DecimalFormat("#.##");
        for (Enumeration<Instance> en = dataSet.enumerateInstances(); en.hasMoreElements();) {
            double[] results = classifier.distributionForInstance(en.nextElement());
            for (double result : results) {
                System.out.print(df.format(result) + " ");
            }
            System.out.println();

        };

    }
}



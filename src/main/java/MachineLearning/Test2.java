package MachineLearning;

import weka.classifiers.Classifier;
import weka.classifiers.functions.SMO;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

//Support vector machine/Maszyna wektorów nośnych
// klasyfikator, którego nauka ma na celu wyznaczenie hiperpłaszczyzny rozdzielającej z maksymalnym marginesem przykłady należące do dwóch klas.
// Na podstawie zwierzat ktore wprowadzamy okreslamy prognoze rasy dla zwierzecian na podstawie jego wysokosci, wagi i kraju wystepowania
public class Test2 {

    public static void main(String[] args) throws Exception{

        // Deklarujemy dwa numeryczne atrybuty
        Attribute Attribute1 = new Attribute("height");
        Attribute Attribute2 = new Attribute("weight");

        // Deklarujemy atrybuty lokalizacji
        FastVector Location = new FastVector(3);
        Location.addElement("australia");
        Location.addElement("africa");
        Location.addElement("america");
        Attribute Attribute3 = new Attribute("location", Location);

        //Deklarujemy atrybuty klasy
        FastVector Classes = new FastVector(2);
        Classes.addElement("cat");
        Classes.addElement("elephant");
        Attribute ClassAttribute = new Attribute("Class", Classes);


        // Tworzymy wektor cech
        FastVector fvWekaAttributes = new FastVector(4);
        fvWekaAttributes.addElement(Attribute1);
        fvWekaAttributes.addElement(Attribute2);
        fvWekaAttributes.addElement(Attribute3);
        fvWekaAttributes.addElement(ClassAttribute);

        // Tworzymy training set
        Instances isTrainingSet = new Instances("Rel", fvWekaAttributes, 10);

        //  Ustawiamy Class Index (ustawiamy jako klase 3 argument ,w naszym przypadku rase zwierzecia)
        isTrainingSet.setClassIndex(3);

        // Tworzymy instancje treningowa
        Instance iExample = new DenseInstance(4);
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(0), 0.3);
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(1), 6);
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(2), "america");
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(3), "cat");

        // Dodajemy instancje do trening set
        isTrainingSet.add(iExample);

        // Tworzymy druga instantcje treningowa (nowe zwierze)
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(0), 10);
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(1), 500);
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(2), "africa");
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(3), "elephant");

        // Dodajemy druga instancje do traning set
        isTrainingSet.add(iExample);



        System.out.println("Training data: " + isTrainingSet);

        //Tworzymy klasyfikator
        Classifier model = (Classifier)new SMO();
        model.buildClassifier(isTrainingSet);

        //Tworzymy test data
        Instances evalSet = new Instances("Rel", fvWekaAttributes, 10);
        evalSet.setClassIndex(3);
        Instance testInstance = new DenseInstance(4);
        testInstance.setValue((Attribute)fvWekaAttributes.elementAt(0), 5);
        testInstance.setValue((Attribute)fvWekaAttributes.elementAt(1), 500);
        testInstance.setValue((Attribute)fvWekaAttributes.elementAt(2), "africa");
        evalSet.add(testInstance);

        System.out.println("Data do ewaluacji: " + evalSet.instance(0));

        //Klasyfikujemy nowa instantcje (wypisanie numery klasy)
        double ClassLabel = 100;
        ClassLabel = model.classifyInstance(evalSet.instance(0));
        System.out.println("Klasyfikacja: " + ClassLabel);
        // Wypisanie zawartosci klasy (rasy)
        Double d = new Double(ClassLabel);
        System.out.println("Klasyfikowany rodziaj zwierzecia: " + Classes.elementAt(d.intValue()));
    }


}

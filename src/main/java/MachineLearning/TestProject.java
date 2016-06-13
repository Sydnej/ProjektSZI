package MachineLearning;

import weka.classifiers.Classifier;
import weka.classifiers.functions.SMO;
import weka.core.*;
import weka.core.converters.ArffLoader;

import java.io.File;
import weka.classifiers.Classifier;
import weka.classifiers.functions.SMO;
import weka.core.*;
import weka.core.converters.ArffLoader;

import java.io.File;

//Support vector machine/Maszyna wektorów nośnych
// klasyfikator, którego nauka ma na celu wyznaczenie hiperpłaszczyzny rozdzielającej z maksymalnym marginesem przykłady należące do dwóch klas.
// Na podstawie zwierzat ktore wprowadzamy okreslamy prognoze rasy dla zwierzecian na podstawie jego wysokosci, wagi i kraju wystepowania
public class TestProject {

    public static void main(String[] args) throws Exception{




        // ladujemy plik Arff z pelnymi danymi jako training set
        ArffLoader loader = new ArffLoader();
        loader.setFile(new File("testprojekt.arff"));
        Instances isTrainingSet = loader.getDataSet();
        // szczegolowosc danych, tj. ilosc etykiet ktore im przypisujemy ( w tym przypadku 5)
        int classIdx = 3;
        isTrainingSet.setClassIndex(classIdx);

        System.out.println("Training data: " + isTrainingSet);

        //Tworzymy klasyfikator
        Classifier model = (Classifier)new SMO();
        model.buildClassifier(isTrainingSet);


        // Deklarujemy trzy atrybuty numeryczne
        Attribute Attribute1 = new Attribute("yields");
        Attribute Attribute2 = new Attribute("weeds");
        Attribute Attribute3 = new Attribute("minerals");

        // Deklarujemy etykiete/atrybut symboliczny wraz z jego wartościami
        FastVector fvNominalVal = new FastVector(3);
        fvNominalVal.addElement("harvest");
        fvNominalVal.addElement("cultivation");
        fvNominalVal.addElement("fertilization");
        Attribute ClassAttribute = new Attribute("priority", fvNominalVal);



        // Deklarujemy vektor cech zlaczony z powzyszych atrybutow
        FastVector fvWekaAttributes = new FastVector(4);
        fvWekaAttributes.addElement(Attribute1);
        fvWekaAttributes.addElement(Attribute2);
        fvWekaAttributes.addElement(Attribute3);
        fvWekaAttributes.addElement(ClassAttribute);


        //Tworzymy test data
        Instances evalSet = new Instances("Rel", fvWekaAttributes, 10);
        evalSet.setClassIndex(3);
        Instance testInstance = new DenseInstance(4);
        testInstance.setValue((Attribute)fvWekaAttributes.elementAt(0), 30);
        testInstance.setValue((Attribute)fvWekaAttributes.elementAt(1), 55);
        testInstance.setValue((Attribute)fvWekaAttributes.elementAt(2),  10);
        evalSet.add(testInstance);

        System.out.println("Data do ewaluacji: " + evalSet.instance(0));

        //Klasyfikujemy nowa instantcje (wypisanie numery klasy)
        double ClassLabel = 100;
        ClassLabel = model.classifyInstance(evalSet.instance(0));
        System.out.println("Klasyfikacja: " + ClassLabel);
        // Wypisanie zawartosci klasy (rasy)
        Double d = new Double(ClassLabel);
        System.out.println("Zalecany priorytet: " + fvNominalVal.elementAt(d.intValue()));



        //Dodawanie uzyskanego wpisu do traning set
        Instance Learn = new DenseInstance(4);
        Learn.setValue((Attribute)fvWekaAttributes.elementAt(0), 90);
        Learn.setValue((Attribute)fvWekaAttributes.elementAt(1), 90);
        Learn.setValue((Attribute)fvWekaAttributes.elementAt(2), 90);
        Learn.setValue((Attribute)fvWekaAttributes.elementAt(3),  fvNominalVal.elementAt(d.intValue()).toString());

        // Dodajemy druga instancje do traning set
        isTrainingSet.add(Learn);


        System.out.println("Training data: " + isTrainingSet);


    }



}

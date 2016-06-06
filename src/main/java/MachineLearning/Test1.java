package MachineLearning;


        import weka.classifiers.Classifier;
        import weka.classifiers.Evaluation;
        import weka.classifiers.bayes.NaiveBayes;
        import weka.core.Attribute;
        import weka.core.FastVector;
        import weka.core.Instance;
        import weka.core.Instances;

        import weka.core.DenseInstance;

//Naiwny klasyfikator bayesowski
//Wykonujemy prognoze negative/positive dla dodatkowego wpisu na podstawie 2 wpisów w training set
//Testuejmy nasz trainingset
public class Test1 {


    public static void main(String[] args) throws Exception{

        // Deklarujemy dwa atrybuty numeryczne
        Attribute Attribute1 = new Attribute("firstNumeric");
        Attribute Attribute2 = new Attribute("secondNumeric");

        // Deklarujemy etykiete/atrybut symboliczny wraz z jego wartościami
        FastVector fvNominalVal = new FastVector(3);
        fvNominalVal.addElement("blue");
        fvNominalVal.addElement("gray");
        fvNominalVal.addElement("black");
        Attribute Attribute3 = new Attribute("aNominal", fvNominalVal);

        // Deklarujemy atrybut klasowy wraz z jej wartosciami
        FastVector fvClassVal = new FastVector(2);
        fvClassVal.addElement("positive");
        fvClassVal.addElement("negative");
        Attribute ClassAttribute = new Attribute("theClass", fvClassVal);

        // Deklarujemy vektor cech zlaczony z powzyszych atrybutow
        FastVector fvWekaAttributes = new FastVector(4);
        fvWekaAttributes.addElement(Attribute1);
        fvWekaAttributes.addElement(Attribute2);
        fvWekaAttributes.addElement(Attribute3);
        fvWekaAttributes.addElement(ClassAttribute);

        // Tworzymy training set
        Instances isTrainingSet = new Instances("Rel", fvWekaAttributes, 10);

        // Ustawiamy Class Index
        isTrainingSet.setClassIndex(3);



        // Tworzymy instancje
        Instance iExample = new DenseInstance(4);
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(0), 1.0);
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(1), 0.5);
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(2), "gray");
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(3), "positive");

        // dodajemy nowa instancje
        isTrainingSet.add(iExample);

        iExample.setValue((Attribute)fvWekaAttributes.elementAt(0), 0.5);
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(1), 1.0);
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(2), "blue");
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(3), "negative");

        // dodajemy nowa instancje
        isTrainingSet.add(iExample);

        /*
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(0), 0.5);
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(1), 0.5);
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(2), "blue");
        iExample.setValue((Attribute)fvWekaAttributes.elementAt(3), "negative");

        isTrainingSet.add(iExample);
        */

        // Tworzymy instancje z wartoscami niewiadomymi
        Instances isTestSet = new Instances("Rel", fvWekaAttributes, 10);
        isTestSet.setClassIndex(3);
        Instance iTest = new DenseInstance(4);
        iTest.setValue((Attribute)fvWekaAttributes.elementAt(0), 0.5);
        iTest.setValue((Attribute)fvWekaAttributes.elementAt(1), 0.5);
        iTest.setValue((Attribute)fvWekaAttributes.elementAt(2), "black");
        isTestSet.add(iTest);



        Classifier cModel = (Classifier)new NaiveBayes();
        cModel.buildClassifier(isTrainingSet);





        // Testujemy model
        Evaluation eTest = new Evaluation(isTrainingSet);
        eTest.evaluateModel(cModel, isTrainingSet);


        // Wypisanie predykcji dla naszego elementu
        double ClassLabel = 100;
        ClassLabel = cModel.classifyInstance(isTestSet.instance(0));
        // Wypisanie zawartosci klasy (rasy)
        Double d = new Double(ClassLabel);
        System.out.println(fvClassVal.elementAt(d.intValue()));


        // Wypisz rezultat (podobnie jak Weka explorer):
        String strSummary = eTest.toSummaryString();
        System.out.println(strSummary);

        // Macierz bledow
        double[][] cmMatrix = eTest.confusionMatrix();
        for(int row_i=0; row_i<cmMatrix.length; row_i++){
            for(int col_i=0; col_i<cmMatrix.length; col_i++){
                System.out.print(cmMatrix[row_i][col_i]);
                System.out.print("|");
            }
            System.out.println();
        }
    }



}

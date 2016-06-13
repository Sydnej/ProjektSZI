package MachineLearning;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.SMO;
import weka.classifiers.trees.LMT;
import weka.core.*;
import weka.core.converters.ArffLoader;
import weka.classifiers.Evaluation;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.evaluation.NominalPrediction;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.PART;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.J48;
import weka.core.FastVector;
import weka.core.Instances;
import java.io.File;
import java.util.Scanner;

//Support vector machine/Maszyna wektorów nośnych
// klasyfikator, którego nauka ma na celu wyznaczenie hiperpłaszczyzny rozdzielającej z maksymalnym marginesem przykłady należące do dwóch klas.
// Na podstawie zwierzat ktore wprowadzamy okreslamy prognoze rasy dla zwierzecian na podstawie jego wysokosci, wagi i kraju wystepowania
public class TestProjectAlgorithms {


    public static double calculateAccuracy(FastVector predictions) {
        double correct = 0;

        for (int i = 0; i < predictions.size(); i++) {
            NominalPrediction np = (NominalPrediction) predictions.elementAt(i);
            if (np.predicted() == np.actual()) {
                correct++;
            }
        }

        return 100 * correct / predictions.size();
    }

    public static void main(String[] args) throws Exception {


        // ladujemy plik Arff z pelnymi danymi jako training set
        ArffLoader loader = new ArffLoader();
        loader.setFile(new File("testprojekt.arff"));
        Instances isTrainingSet = loader.getDataSet();
        // szczegolowosc danych, tj. ilosc etykiet ktore im przypisujemy ( w tym przypadku 5)
        int classIdx = 3;
        isTrainingSet.setClassIndex(classIdx);

        double max = 0;
        String method = "";
        Classifier model_name = (Classifier) new J48();


        System.out.println("Training data: " + isTrainingSet);

        //Tworzymy klasyfikator
        //SMO - 50%
        //J48 - 78,57%
        //PART - 78.57%
        //DecisionTable - 42.85%
        //DecisionStump - 64,28%
        //NaiveBayes() 78,57%
        //LMT 92.85

        //Tworzenie klasyfikacji
        Classifier model = (Classifier) new LMT();
        model.buildClassifier(isTrainingSet);
        //Wektor zawierajacy prognozy
        FastVector predictions = new FastVector();
        //Ewaluacja (test modelu)
        Evaluation eval = new Evaluation(isTrainingSet);
        eval.evaluateModel(model, isTrainingSet);
        //Wypelnienie wektoru wynikami prognoz
        predictions.appendElements(eval.predictions());
        //Obliczanie i wypisywanie celnosci
        double accuracy = calculateAccuracy(predictions);
        if(accuracy>max){max=accuracy;method="Logistic Model Tree";model_name=model;}
        System.out.println("\nCelnosc Logistic Model Tree (logiczna regresja + drzewa decyzyjne) " + String.format("%.2f%%", accuracy));

        ///////////////////////

        Classifier model2 = (Classifier) new SMO();
        model2.buildClassifier(isTrainingSet);
        Evaluation eval2 = new Evaluation(isTrainingSet);
        eval2.evaluateModel(model2, isTrainingSet);
        predictions.removeAllElements();
        predictions.appendElements(eval2.predictions());
        accuracy = calculateAccuracy(predictions);
        if(accuracy>max){max=accuracy;method="Sequential Minimal Optimization";model_name=model2;}
        System.out.println("Celnosc Sequential Minimal Optimization (wektory nośne)" + String.format("%.2f%%", accuracy));

        ///////////////////////

        Classifier model3 = (Classifier) new J48();
        model3.buildClassifier(isTrainingSet);
        Evaluation eval3 = new Evaluation(isTrainingSet);
        eval3.evaluateModel(model3, isTrainingSet);
        predictions.removeAllElements();
        predictions.appendElements(eval3.predictions());
        accuracy = calculateAccuracy(predictions);
        if(accuracy>max){max=accuracy;method="Drzewo Decyzyjne";model_name=model3;}
        System.out.println("Celnosc Drzewa Decyzyjnego " + String.format("%.2f%%", accuracy));

        ///////////////////////

        Classifier model4 = (Classifier) new PART();
        model4.buildClassifier(isTrainingSet);
        Evaluation eval4 = new Evaluation(isTrainingSet);
        eval4.evaluateModel(model4, isTrainingSet);
        predictions.removeAllElements();
        predictions.appendElements(eval4.predictions());
        accuracy = calculateAccuracy(predictions);
        if(accuracy>max){max=accuracy;method="Lista Decyzyjna PART";model_name=model4;}
        System.out.println("Celnosc Lista Decyzyjna PART (drzewa decyzyjne) " + String.format("%.2f%%", accuracy));

        ///////////////////////

        Classifier model5 = (Classifier) new NaiveBayes();
        model5.buildClassifier(isTrainingSet);
        Evaluation eval5 = new Evaluation(isTrainingSet);
        eval5.evaluateModel(model5, isTrainingSet);
        predictions.removeAllElements();
        predictions.appendElements(eval5.predictions());
        accuracy = calculateAccuracy(predictions);
        if(accuracy>max){max=accuracy;method="Nawiny Klasyfikator Bayesowski";model_name=model5;}
        System.out.println("Celnosc Naiwnego Klasyfikatora Bayesowskiego " + String.format("%.2f%%", accuracy));

        /////////////////////

        Classifier model6 = (Classifier) new DecisionStump();
        model6.buildClassifier(isTrainingSet);
        Evaluation eval6 = new Evaluation(isTrainingSet);
        eval6.evaluateModel(model6, isTrainingSet);
        predictions.removeAllElements();
        predictions.appendElements(eval6.predictions());
        accuracy = calculateAccuracy(predictions);
        if(accuracy>max){max=accuracy;method="Decision Stump";model_name=model6;}
        System.out.println("Celnosc Decision Stump (jednopoziomowe drzewo decyzyjne) " + String.format("%.2f%%", accuracy));

        ////////////////////

        System.out.println("\nZwycieca jest " + method + " ktore mialo racje w " + String.format("%.2f%%",max) + " przypadkach \n");


        Evaluation eval7 = new Evaluation(isTrainingSet);
        eval7.evaluateModel(model_name, isTrainingSet);

        String strSummary2 = eval.toSummaryString();
        System.out.println(strSummary2);

    }



}

package MachineLearning;


import weka.classifiers.functions.LinearRegression;
import weka.core.Instance;
import weka.core.Instances;

import java.io.BufferedReader;
import java.io.FileReader;

//regresja liniowa - bedziemy na jej podstawie prognozowac
public class TestProjectLinear {

    public static void main(String args[]) throws Exception{
        //wladuj dane
        Instances data = new Instances(new BufferedReader(new FileReader("testprojektlinear.arff")));
        data.setClassIndex(data.numAttributes() - 1);

        //zbuduj model
        LinearRegression model = new LinearRegression();
        model.buildClassifier(data); //ostatnia instacja bez klasy nie jest uzyta
        System.out.println(model);

        //zklasyfikuj ostatnia instancje - wypisz prognozowana wartosc
        Instance myHouse = data.lastInstance();
        double price = model.classifyInstance(myHouse);
        System.out.println("My house ("+myHouse+"): "+price);
    }


}

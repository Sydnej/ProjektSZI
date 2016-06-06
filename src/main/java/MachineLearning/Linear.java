package MachineLearning;


import java.io.BufferedReader;
import java.io.FileReader;
import weka.core.Instance;
import weka.core.Instances;
import weka.classifiers.functions.LinearRegression;

//regresja liniowa - bedziemy na jej podstawie prognozowac ile bedize kosztować dom na podstawie danych sprzedażowych
public class Linear {

    public static void main(String args[]) throws Exception{
        //wladuj dane
        Instances data = new Instances(new BufferedReader(new FileReader("house.arff")));
        data.setClassIndex(data.numAttributes() - 1);

        //zbuduj model
        LinearRegression model = new LinearRegression();
        model.buildClassifier(data); //ostatnia instacja bez klasy nie jest uzyta
        System.out.println(model);

        //zklasyfikuj ostatnia instancje - wypisz prognozowana cene nastepnego domu
        Instance myHouse = data.lastInstance();
        double price = model.classifyInstance(myHouse);
        System.out.println("My house ("+myHouse+"): "+price);
    }


}

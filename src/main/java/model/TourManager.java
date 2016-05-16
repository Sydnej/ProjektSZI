package model;

import model.area.GraphVertex;

import java.util.ArrayList;
import java.util.List;

public class TourManager {
    // Holds our cities
    private static List destinationCities = new ArrayList<City>();

    //Holds our vertex
    private static List destinationVertex = new ArrayList<GraphVertex>();

    // Adds a destination city
    public static void addCity(City city) {
        destinationCities.add(city);
    }

    //Adds a destination vertex
    public static void addVertex(GraphVertex city) { destinationVertex.add(city); }

    // Get a city
    public static City getCity(int index){
        return (City)destinationCities.get(index);
    }

    // Get a vertex
    public static GraphVertex getVertex(int index){
        return (GraphVertex) destinationVertex.get(index);
    }

    // Get the number of destination cities
    public static int numberOfCities(){
        return destinationCities.size();
    }

    // Get the number of destination vertex
    public static int numberOfVertex(){
        return destinationVertex.size();
    }

}

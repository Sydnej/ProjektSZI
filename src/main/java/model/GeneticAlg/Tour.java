package model.GeneticAlg;

import model.area.GraphVertex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tour {
    // Holds our tour of cities
    //private List tour = new ArrayList<City>();
    private List tour = new ArrayList<GraphVertex>();
    // Cache
    private double fitness = 0;
    private int distance = 0;

    // Constructs a blank tour numberOfCities()
    public Tour(){
        for (int i = 0; i < TourManager.numberOfVertex(); i++) {
            tour.add(null);
        }
    }

    public Tour(ArrayList tour){
        this.tour = tour;
    }

    // Creates a random individual
    public void generateIndividual() {
        // Loop through all our destination cities and add them to our tour
        for (int cityIndex = 0; cityIndex < TourManager.numberOfVertex(); cityIndex++) {
            setVertex(cityIndex, TourManager.getVertex(cityIndex));
        }
        // Randomly reorder the tour
        Collections.shuffle(tour);
    }

    // Gets a city from the tour
    //public City getVertex(int tourPosition) {
     //   return (GraphVertex)tour.get(tourPosition);
    //}

    // Gets a vertex from the tour
    public GraphVertex getVertex(int tourPosition) {
        return (GraphVertex)tour.get(tourPosition);
    }

    // Sets a city in a certain position within a tour
    public void setCity(int tourPosition, City city) {
        tour.set(tourPosition, city);
        // If the tours been altered we need to reset the fitness and distance
        fitness = 0;
        distance = 0;
    }

    // Sets a city in a certain position within a tour
    public void setVertex(int tourPosition, GraphVertex city) {
        tour.set(tourPosition, city);
        // If the tours been altered we need to reset the fitness and distance
        fitness = 0;
        distance = 0;
    }

    // Gets the tours fitness
    public double getFitness() {
        if (fitness == 0) {
            fitness = 1/(double)getDistance();
        }
        return fitness;
    }

    // Gets the total distance of the tour
    public int getDistance(){
        if (distance == 0) {
            int tourDistance = 0;
            // Loop through our tour's cities
            for (int cityIndex=0; cityIndex < tourSize(); cityIndex++) {
                // Get city we're travelling from
                GraphVertex fromCity = getVertex(cityIndex);
                // City we're travelling to
                GraphVertex destinationVertex;
                // Check we're not on our tour's last city, if we are set our
                // tour's final destination city to our starting city
                if(cityIndex+1 < tourSize()){
                    destinationVertex = getVertex(cityIndex+1);
                }
                else{
                    destinationVertex = getVertex(0);
                }
                // Get the distance between the two cities
                tourDistance += fromCity.distanceTo(destinationVertex);
            }
            distance = tourDistance;
        }
        return distance;
    }

    // Get number of cities on our tour
    public int tourSize() {
        return tour.size();
    }

    // Check if the tour contains a city
    public boolean containsCity(GraphVertex city){
        return tour.contains(city);
    }

    @Override
    public String toString() {
        String geneString = "|";
        for (int i = 0; i < tourSize(); i++) {
            geneString += getVertex(i).getX()+","+getVertex(i).getY()+"|";
        }
        return geneString;
    }

}
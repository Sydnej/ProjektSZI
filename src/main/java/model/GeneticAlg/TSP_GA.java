package model.GeneticAlg;

import model.GeneticAlg.GA;
import model.GeneticAlg.Population;
import model.GeneticAlg.TourManager;
import model.area.GraphVertex;

public class TSP_GA {

    public static void main(String[] args) {

        //GraphVertex city21 = new GraphVertex(1,1,1);
        // TourManager.addCity(city21); City ->Graph Vertex

        // Create and add our cities
        //City city = new City(60, 200);
        //TourManager.addCity(city);
        //City city2 = new City(180, 200);
        //TourManager.addCity(city2);
        //City city3 = new City(80, 180);
        //TourManager.addCity(city3);
        //City city4 = new City(140, 180);
        //TourManager.addCity(city4);
        //City city5 = new City(20, 160);
        //TourManager.addCity(city5);
        //City city6 = new City(100, 160);
        //TourManager.addCity(city6);
        //City city7 = new City(200, 160);
        //TourManager.addCity(city7);
        //City city8 = new City(140, 140);
        //TourManager.addCity(city8);
        //City city9 = new City(40, 120);
        //TourManager.addCity(city9);
        //City city10 = new City(100, 120);
        //TourManager.addCity(city10);
        //City city11 = new City(180, 100);
        //TourManager.addCity(city11);
        //City city12 = new City(60, 80);
        //TourManager.addCity(city12);
        //City city13 = new City(120, 80);
        //TourManager.addCity(city13);
        //City city14 = new City(180, 60);
        //TourManager.addCity(city14);
        //City city15 = new City(20, 40);
        //TourManager.addCity(city15);
        //City city16 = new City(100, 40);
        //TourManager.addCity(city16);
        //City city17 = new City(200, 40);
        //TourManager.addCity(city17);
        //City city18 = new City(20, 20);
        //TourManager.addCity(city18);
        //City city19 = new City(60, 20);
        //TourManager.addCity(city19);
        //City city20 = new City(160, 20);
        //TourManager.addCity(city20);

        GraphVertex city = new GraphVertex(1,200,60);
        TourManager.addVertex(city);
        GraphVertex city2 = new GraphVertex(2,200,180);
        TourManager.addVertex(city2);
        GraphVertex city3 = new GraphVertex(3,180,80);
        TourManager.addVertex(city3);
        GraphVertex city4 = new GraphVertex(4,180,140);
        TourManager.addVertex(city4);
        GraphVertex city5 = new GraphVertex(5,160,20);
        TourManager.addVertex(city5);
        GraphVertex city6 = new GraphVertex(6,160,100);
        TourManager.addVertex(city6);
        GraphVertex city7 = new GraphVertex(7,120,60);
        TourManager.addVertex(city7);
        GraphVertex city8 = new GraphVertex(8,140,140);
        TourManager.addVertex(city8);
        GraphVertex city9 = new GraphVertex(9,120,40);
        TourManager.addVertex(city9);
        GraphVertex city10 = new GraphVertex(10,120,100);
        TourManager.addVertex(city10);
        GraphVertex city11 = new GraphVertex(11,180,100);
        TourManager.addVertex(city11);
        GraphVertex city12 = new GraphVertex(12,60,80);
        TourManager.addVertex(city12);
        GraphVertex city13 = new GraphVertex(13,120,80);
        TourManager.addVertex(city13);
        GraphVertex city14 = new GraphVertex(14,180,60);
        TourManager.addVertex(city14);
        GraphVertex city15 = new GraphVertex(15,20,40);
        TourManager.addVertex(city15);
        GraphVertex city16 = new GraphVertex(16,100,40);
        TourManager.addVertex(city16);
        GraphVertex city17 = new GraphVertex(17,200,40);
        TourManager.addVertex(city17);
        GraphVertex city18 = new GraphVertex(18,60,20);
        TourManager.addVertex(city18);
        GraphVertex city19 = new GraphVertex(19,60,20);
        TourManager.addVertex(city19);
        GraphVertex city20 = new GraphVertex(20,160,20);
        TourManager.addVertex(city20);

        // Initialize population
        Population pop = new Population(50, true);
        System.out.println("Initial distance: " + pop.getFittest().getDistance());

        // Evolve population for 100 generations
        pop = GA.evolvePopulation(pop);
        for (int i = 0; i < 100; i++) {
            pop = GA.evolvePopulation(pop);
        }

        // Print final results
        System.out.println("Finished");
        System.out.println("Final distance: " + pop.getFittest().getDistance());
        System.out.println("Solution:");
        System.out.println(pop.getFittest());
    }

}

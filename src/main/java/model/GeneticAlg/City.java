package model.GeneticAlg;

import model.area.GraphVertex;

public class City {

    int x;
    int y;
    int id;

    // Constructs a randomly placed city
    public City(){
        this.x = (int)(Math.random()*200);
        this.y = (int)(Math.random()*200);
        this.id = id;
    }

    // Constructs a city at chosen x, y location
    public City(int x, int y){
        this.x = x;
        this.y = y;
        this.id = id;
    }

    // Gets city's x coordinate
    public int getX(){
        return this.x;
    }

    // Gets city's y coordinate
    public int getY(){
        return this.y;
    }

    //Gets city's id coordinate
    public int getId() { return this.id; }

    // Gets the distance to given city
    //public double distanceTo(City city){
    //    int xDistance = Math.abs(getX() - city.getX());
    //    int yDistance = Math.abs(getY() - city.getY());
     //   double distance = Math.sqrt( (xDistance*xDistance) + (yDistance*yDistance) );

     //   return distance;
    //}

    // Gets the distance to given vertex
    public double distanceTo(GraphVertex city){
        double xDistance = Math.abs(getX() - city.getX());
        double yDistance = Math.abs(getY() - city.getY());
        double distance = Math.sqrt( (xDistance*xDistance) + (yDistance*yDistance) );

        return distance;
    }

    @Override
    public String toString(){
        return getX()+", "+getY();
    }

}

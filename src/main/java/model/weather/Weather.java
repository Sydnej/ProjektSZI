package model.weather;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.Random;

public class Weather {

    private IntegerProperty temperature = new SimpleIntegerProperty();    // w st. C
    private IntegerProperty humidity = new SimpleIntegerProperty();       // w %
    private IntegerProperty rain = new SimpleIntegerProperty();           // w mm/m2
    private Season season = Season.SPRING;

    public Weather() {
        update();
    }

    public int getTemperature() {
        return temperature.get();
    }

    public void setTemperature(int temperature) {
        this.temperature.set(temperature);
    }

    public IntegerProperty temperatureProperty() {
        return temperature;
    }

    public int getHumidity() {
        return humidity.get();
    }

    public void setHumidity(int humidity) {
        this.humidity.set(humidity);
    }

    public IntegerProperty humidityProperty() {
        return humidity;
    }

    public int getRain() {
        return rain.get();
    }

    public void setRain(int rain) {
        this.rain.set(rain);
    }

    public IntegerProperty rainProperty() {
        return rain;
    }

    public void updateTemperature() {
        Random random = new Random();

        switch (season) {
            case SPRING:
                setTemperature(random.nextInt(25) - 5);   // od -5 do 19
                break;
            case SUMMER:
                setTemperature(random.nextInt(20) + 15);  // od 15 do 34
                break;
            case AUTUMN:
                setTemperature(random.nextInt(25) - 5);   // od -5 do 19
                break;
            case WINTER:
                setTemperature(random.nextInt(30) - 20);  // od -20 do 9
                break;
        }
    }

    public void updateHumidity() {
        Random random = new Random();
        setHumidity(random.nextInt(101));             // 0 do 100
    }

    public void updateRain() {
        Random random = new Random();

        switch (season) {
            case SPRING:
                setRain(random.nextInt(20));         // od 30 do 49
                break;
            case SUMMER:
                setRain(random.nextInt(20));         // od 55 do 74
                break;
            case AUTUMN:
                setRain(random.nextInt(20));         // od 30 do 49
                break;
            case WINTER:
                setRain(random.nextInt(10));         // od 30 do 39
                break;
        }
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public void update() {
        updateHumidity();
        updateRain();
        updateTemperature();
    }
}

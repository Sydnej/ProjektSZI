package model.weather;

import java.util.Random;

public class Weather {

    private int temperature;    // w st. C
    private int humidity;       // w %
    private int rain;           // w mm/m2
    private Season season;

    public Weather() {
        season = Season.SPRING;
    }

    public int generateTemperature() {
        Random random = new Random();

        if (season == Season.SPRING) {
            temperature = random.nextInt(25) - 5;   // od -5 do 19
        } else if (season == Season.SUMMER) {
            temperature = random.nextInt(20) + 15;  // od 15 do 34
        } else if (season == Season.AUTUMN) {
            temperature = random.nextInt(25) - 5;   // od -5 do 19
        } else {
            temperature = random.nextInt(30) - 20;  // od -20 do 9
        }

        return temperature;
    }

    public int generateHumidity() {
        Random random = new Random();

        humidity = random.nextInt(101);             // 0 do 100

        return humidity;
    }

    public int generateRain() {
        Random random = new Random();

        if (season == Season.SPRING) {
            rain = random.nextInt(20) + 30;         // od 30 do 49
        } else if (season == Season.SUMMER) {
            rain = random.nextInt(20) + 55;         // od 55 do 74
        } else if (season == Season.AUTUMN) {
            rain = random.nextInt(20) + 30;         // od 30 do 49
        } else {
            rain = random.nextInt(10) + 30;         // od 30 do 39
        }

        return rain;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

}

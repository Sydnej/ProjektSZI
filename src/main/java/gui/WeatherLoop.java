package gui;

import javafx.application.Platform;
import model.area.Area;
import model.area.Field;
import model.weather.Season;
import model.weather.Weather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


class WeatherLoop implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherLoop.class);
    private final Area area;
    private final Weather weather;
    private int daySpeed = 10000;
    private int numberOfDays = 0;

    WeatherLoop(Area area, Weather weather) {
        this.area = area;
        this.weather = weather;
    }

    @Override
    public void run() {
        while (true) {
            Platform.runLater(weather::update);
            area.getFields().values().forEach(this::updateProperties);
            try {
                sleepOneDay();
            } catch (InterruptedException e) {
                LOGGER.info("Interrupted");
                Thread.currentThread().interrupt();
            }
            numberOfDays++;
            if ((numberOfDays % 90 == 0) && (numberOfDays > 0)) {
                Season currentSeason = weather.getSeason();
                weather.setSeason(Season.values()[(currentSeason.ordinal() + 1) % 4]);
            }
        }
    }

    private void updateProperties(Field field) {
        int rain = weather.getRain();
        int humidity = weather.getHumidity();
        int temperature = weather.getTemperature();

        if (temperature > 7 && rain > 20) {
            if (field.getYields() < 100) {
                field.setYields(field.getYields() + 1);
            }
        }
    }

    public int getNumberOfDays() {
        return numberOfDays;
    }

    public void setDaySpeed(int daySpeed) {
        this.daySpeed = daySpeed;
    }

    public void sleepOneDay() throws InterruptedException {
        Thread.sleep(daySpeed);
    }

}

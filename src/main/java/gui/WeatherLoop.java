package gui;

import javafx.application.Platform;
import model.area.Area;
import model.area.Field;
import model.weather.Weather;

import java.util.logging.Logger;

class WeatherLoop implements Runnable {
    private static final Logger LOGGER = Logger.getGlobal();
    private final Area area;
    private final Weather weather;

    WeatherLoop(Area area, Weather weather) {
        this.area = area;
        this.weather = weather;
    }

    @Override
    public void run() {
        while (isRunning()) {
            Platform.runLater(weather::update);
            area.getFields().values().forEach(this::updateProperties);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                LOGGER.info("Interrupted");
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

    private boolean isRunning() {
        return true;
    }
}

package gui;

import DecisionTree.C45;
import javafx.application.Platform;
import model.FuzzyLogic2;
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
    public C45 TreeDecision = new C45();

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

        FuzzyLogic2 flogic = new FuzzyLogic2();

        int rain = weather.getRain();
        int humidity = weather.getHumidity();
        int temperature = weather.getTemperature();
        System.out.println("Dzeszcz: " + rain);
        System.out.println("Chwasty: " + humidity);
        System.out.println("Wilgotnosc fLogic: " + flogic.CountHumidity(rain));
        System.out.println("Chwasty fLogic: " + flogic.CountWeeds(humidity));
        String minerals, humidit, weeds, temp;

        if (field.getMinerals() < 33) minerals = "niskie";
        else if (field.getMinerals() < 66) minerals = "srednie";
        else minerals = "wysokie";

        if (field.getHumidity() < 33) humidit = "niska";
        else if (field.getHumidity() < 66) humidit = "srednia";
        else humidit = "wysoka";

        if (field.getWeeds() < 33) weeds = "niskie";
        else if (field.getWeeds() < 66) weeds = "srednie";
        else weeds = "wysokie";

        if (weather.getTemperature() < 10) temp = "niska";
        else if (weather.getTemperature() < 20) temp = "srednia";
        else temp = "wysoka";


        TreeDecision.C45();

        System.out.println(TreeDecision.MakeDecision("niska", "niska", "niskie", "niskie"));
/*
        String dod = TreeDecision.MakeDecision(temp, humidit, weeds, minerals);
        System.out.println("Wartość dod: " + dod );
        int dod2 = Integer.parseInt(dod);
        System.out.println("Wartość dod2: " + dod2 );
        if (field.getYields() < 100 && field.getYields() + dod2 < 100) {
            field.setYields(field.getYields() + dod2);
        }
*/
        // if (rain > )
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

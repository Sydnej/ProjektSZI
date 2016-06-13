package gui;

import DecisionTree.C45;
import javafx.application.Platform;
import model.FuzzyLogic2;
import model.area.Area;
import model.area.Field;
import model.neural.NeuralNetwork;
import model.neural.learning.LearningSet;
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
    private NeuralNetwork weedsNetwork;
    private NeuralNetwork mineralsNetwork;
    private NeuralNetwork humidityNetwork;

    WeatherLoop(Area area, Weather weather) {
        this.area = area;
        this.weather = weather;

        weedsNetwork = new NeuralNetwork();
        mineralsNetwork = new NeuralNetwork();
        humidityNetwork = new NeuralNetwork();
    }

    @Override
    public void run() {
        TreeDecision.C45();
        weedsNetwork.learnByBackpropagation(new LearningSet("weedsSet.txt"));
        mineralsNetwork.learnByBackpropagation(new LearningSet("mineralsSet.txt"));
        humidityNetwork.learnByBackpropagation(new LearningSet("humiditySet.txt"));

        while (true) {
            Platform.runLater(weather::update);
            area.getFields().values().forEach(this::updateProperties);
            System.out.print("while");
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
        int weatherRain = weather.getRain();
        int weatherHumidity = weather.getHumidity();
        int weatherTemperature = weather.getTemperature();

        weedsNetwork.setInputSignals(weatherTemperature, weatherHumidity, weatherRain);
        weedsNetwork.connectAllLayers();
        int weeds = field.getWeeds() + (int) weedsNetwork.getNetworkOutput();
        if(weeds > 100) {
            weeds = 100;
        }
        if(weeds < 0) {
            weeds = 0;
        }
        field.setWeeds(weeds);

        mineralsNetwork.setInputSignals(weatherTemperature, weatherHumidity, weatherRain);
        mineralsNetwork.connectAllLayers();
        int minerals = field.getMinerals() + (int) mineralsNetwork.getNetworkOutput();
        if(minerals > 100) {
            minerals = 100;
        }
        if(minerals < 0) {
            minerals = 0;
        }
        field.setMinerals(minerals);

        humidityNetwork.setInputSignals(weatherTemperature, weatherHumidity, weatherRain);
        humidityNetwork.connectAllLayers();
        int humidity = field.getHumidity() + (int) humidityNetwork.getNetworkOutput();
        if(humidity > 100) {
            humidity = 100;
        }
        if(humidity < 0) {
            humidity = 0;
        }
        field.setHumidity(humidity);


        FuzzyLogic2 flogic = new FuzzyLogic2();

        System.out.println("Deszcz: " + weatherRain);
        System.out.println("Chwasty: " + weatherHumidity);
        System.out.println("Wilgotnosc fLogic: " + flogic.CountHumidity(weatherRain));
        System.out.println("Chwasty fLogic: " + flogic.CountWeeds(weatherHumidity));
        String miner, humidit, weed, temp;

        if (field.getMinerals() < 33) miner = "niskie";
        else if (field.getMinerals() < 66) miner = "srednie";
        else miner = "wysokie";

        if (field.getHumidity() < 33) humidit = "niska";
        else if (field.getHumidity() < 66) humidit = "srednia";
        else humidit = "wysoka";

        if (field.getWeeds() < 33) weed = "niskie";
        else if (field.getWeeds() < 66) weed = "srednie";
        else weed = "wysokie";

        if (weather.getTemperature() < 10) temp = "niska";
        else if (weather.getTemperature() < 20) temp = "srednia";
        else temp = "wysoka";


        //TreeDecision.C45();

        //System.out.println(TreeDecision.MakeDecision("niska", "niska", "niskie", "niskie"));

        System.out.println(temp + " " + humidit + " " + weed + " " + miner);
        String dod = TreeDecision.MakeDecision(temp, humidit, weed, miner);
        //System.out.println("Wartość dod: " + dod );
        int dod2 = Integer.parseInt(dod);
        //System.out.println("Wartość dod2: " + dod2 );
        if (field.getYields() < 100 && field.getYields() + dod2 < 100) {
            field.setYields(field.getYields() + dod2);
        }

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

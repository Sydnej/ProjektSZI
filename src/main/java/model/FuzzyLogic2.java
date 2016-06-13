package model;

import model.area.Field;
import net.sourceforge.jFuzzyLogic.FIS;

import java.util.logging.Logger;

/**
 * Created by Karol on 24.05.2016.
 */
public class FuzzyLogic2 {

    private static final Logger LOGGER = Logger.getGlobal();
    private FIS fis;

    public int CountWeeds(int h) {
        int wilgotnosc = h; // do 100%
        String fileName = getClass().getResource("wather_controller.fcl").getFile();
        fis = FIS.load(fileName, true);
        if (fis == null) {
            throw new IllegalStateException("Nie moge zaladowc pliku: '" + fileName + "'");
        }
        fis.setVariable("humidity", wilgotnosc);
        fis.evaluate();
        int pvalue =(int) fis.getVariable("weeds").getValue();
        return pvalue;
    }

    public int CountHumidity(int ra) {
        int rain = ra; // do 100%
        String fileName = getClass().getResource("wather_controller.fcl").getFile();
        fis = FIS.load(fileName, true);
        if (fis == null) {
            throw new IllegalStateException("Nie moge zaladowc pliku: '" + fileName + "'");
        }
        fis.setVariable("rain", rain);
        fis.evaluate();
        int pvalue =(int) fis.getVariable("humidity").getValue();
        return pvalue;
    }

}

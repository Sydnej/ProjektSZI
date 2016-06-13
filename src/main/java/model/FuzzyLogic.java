package model;

import model.area.Field;
import net.sourceforge.jFuzzyLogic.FIS;

import java.util.logging.Logger;

/**
 * Created by Karol on 24.05.2016.
 */
public class FuzzyLogic {

    private static final Logger LOGGER = Logger.getGlobal();
    private FIS fis;

    public FuzzyLogic() {
        String fileName = getClass().getResource("field_controller.fcl").getFile();
        fis = FIS.load(fileName, true);
        if (fis == null) {
            throw new IllegalStateException("Nie moge zaladowc pliku: '" + fileName + "'");
        }
    }

    public double calcPriority(Field field) {
        fis.setVariable("yields", field.getYields());
        fis.setVariable("weeds", field.getWeeds());
        fis.setVariable("minerals", field.getMinerals());
        fis.evaluate();
        return fis.getVariable("priority").getValue();
    }
}

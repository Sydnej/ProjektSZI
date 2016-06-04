package model;

import model.area.Field;
import net.sourceforge.jFuzzyLogic.FIS;

/**
 * Created by Karol on 24.05.2016.
 */
public class FuzzyLogic {

    private FIS fis;

    public FuzzyLogic() {
        String fileName = getClass().getResource("controller.fcl").getFile();
        fis = FIS.load(fileName, true);
        if (fis == null) {
            throw new IllegalStateException("Nie moge zaladowc pliku: '" + fileName + "'");
        }
    }

    /**
     * Priorytet do nawożenia
     *
     * @param field
     * @return priority
     */
    public double calcPriorityForFertilization(Field field) {
        return calcPriority(field, "fertilizationPriority");
    }

    /**
     * Priorytet do pielenia
     *
     * @param field
     * @return priority
     */
    public double calcPriorityForCultivation(Field field) {
        return calcPriority(field, "cultivationPriority");
    }

    /**
     * Priorytet do zbierania plonów
     *
     * @param field
     * @return priority
     */
    public double calcPriorityForHarvest(Field field) {
        return calcPriority(field, "harvestPriority");
    }

    private double calcPriority(Field field, String outputParameterName) {
        fis.setVariable("yields", field.getYields());
        fis.setVariable("weeds", field.getWeeds());
        fis.setVariable("minerals", field.getMinerals());
        fis.evaluate();
        return fis.getVariable(outputParameterName).getValue();
    }
}

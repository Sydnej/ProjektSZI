package model; /**
 * Created by Karol on 24.05.2016.
 */

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;

import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;

import net.sourceforge.jFuzzyLogic.rule.Variable;


public class FuzzyLogic {

    public int CountPriority(int w, int t, int j) {
        // Load from 'FCL' file
        String fileName = System.getProperty("user.dir") + "\\src\\main\\java\\model\\controller.fcl";




        int wilgotnosc = w; // do 100%
        int temperatura = t;
        int jakosc = j; // do 100%

        FIS fis = FIS.load(fileName, true);
        if( fis == null ) {
            System.err.println("Nie moge zaladowc pliku: '" + fileName + "'");
            return 0;
        }

        fis.setVariable("wilgotnosc", wilgotnosc);
        fis.setVariable("temperatura", temperatura);
        fis.setVariable("jakosc", jakosc);

        fis.evaluate();

        System.out.println("start...");
        System.out.println("Priorytet:" + fis.getVariable("priorytet").getValue());
        int pvalue =(int) fis.getVariable("priorytet").getValue();
        return pvalue;
    }
}

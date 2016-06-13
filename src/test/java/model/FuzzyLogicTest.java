package model;

import model.area.Area;
import model.area.Field;
import org.junit.Before;

import java.util.Collection;

public class FuzzyLogicTest {
    private FuzzyLogic fuzzyLogic = new FuzzyLogic();
    private Collection<Field> fields;

    @Before
    public void setUp() {
        Area area = new Area();
        area.loadData(getClass().getResourceAsStream("../xml/map.xml"));
        fields = area.getFields().values();
    }

//    @Test
//    public void calcPriorityForFertilization() throws Exception {
//        fields.forEach(field -> System.out.println(fuzzyLogic.calcPriorityForFertilization(field)));
//    }
//
//    @Test
//    public void calcPriorityForCultivation() throws Exception {
//        fields.forEach(field -> System.out.println(fuzzyLogic.calcPriorityForCultivation(field)));
//    }
//
//    @Test
//    public void calcPriorityForHarvest() throws Exception {
//        fields.forEach(field -> System.out.println(fuzzyLogic.calcPriority(field)));
//    }

}
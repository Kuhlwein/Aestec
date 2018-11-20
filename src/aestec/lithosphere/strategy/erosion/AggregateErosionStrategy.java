package aestec.lithosphere.strategy.erosion;

import aestec.lithosphere.Lithosphere;

import java.util.ArrayList;
import java.util.HashMap;

public class AggregateErosionStrategy implements ErosionStrategy {
    private HashMap<ErosionStrategy,Integer> map;
    private int count;

    public AggregateErosionStrategy() {
        map = new HashMap<>();
    }

    @Override
    public void erode(Lithosphere l) {
        count++;
        for (ErosionStrategy strategy : map.keySet()) {
            if (count % map.get(strategy)==0) strategy.erode(l);
        }
    }

    public void add(ErosionStrategy strategy,int x) {
        map.put(strategy,x);
    }
}

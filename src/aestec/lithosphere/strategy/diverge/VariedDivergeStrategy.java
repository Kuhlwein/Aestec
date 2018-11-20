package aestec.lithosphere.strategy.diverge;

import aestec.lithosphere.Lithosphere;

import java.util.Random;

public class VariedDivergeStrategy implements DivergeStrategy {
    private static Random random = new Random(0);

    @Override
    public void diverge(Lithosphere l) {
        int level = -4000+(int)(random.nextGaussian()*20);
        for (int x=0; x<l.getXDim();x++) for(int y=0; y<l.getYDim();y++) {
            if (l.getPlateAt(x,y)==null) {
                l.setPlateAt(x,y,l.getOldPlateAt(x,y));
                l.getPlateAt(x,y).set(x,y,level);
            }
        }
    }
}

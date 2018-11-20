package aestec.lithosphere.strategy.diverge;

import aestec.lithosphere.Lithosphere;

public class SimpleDivergeStrategy implements DivergeStrategy {
    @Override
    public void diverge(Lithosphere l) {
        for (int x=0; x<l.getXDim();x++) for(int y=0; y<l.getYDim();y++) {
            if (l.getPlateAt(x,y)==null) {
                l.setPlateAt(x,y,l.getOldPlateAt(x,y));
                l.getPlateAt(x,y).set(x,y,0);
            }
        }
    }
}

package aestec.lithosphere.strategy.diverge;

import aestec.Plate;

public class SimpleDivergeStrategy implements DivergeStrategy {
    @Override
    public void diverge(Plate[][] platemap, Plate[][] oldplatemap) {


        for (int x=0; x<platemap.length;x++) for(int y=0; y<platemap[x].length;y++) {
            if (platemap[x][y]==null) {
                platemap[x][y]=oldplatemap[x][y];
                platemap[x][y].set(x,y,0);
            }
        }

    }
}

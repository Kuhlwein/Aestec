package aestec.lithosphere.strategy.erosion;

import aestec.lithosphere.Lithosphere;

public class RaiseLakesErosionStrategy implements ErosionStrategy {
    @Override
    public void erode(Lithosphere l) {
        int[] dx = {-1, 1, 0, 0, 1, 1, -1, -1}, dy = {0, 0, -1, 1, 1, -1, 1, -1};

        for (int x=0;x<l.getXDim();x++) for (int y=0;y<l.getYDim();y++) {
            if(l.get(x,y)>0) continue;

            int landmean=0, seacount=0;
            for (int i=0;i<8;i++) {
                int h=l.get(x+dx[i],y+dy[i]);
                if (h<0) seacount++;
                else landmean+=h;
            }

            if (seacount<2) {
                l.set(x,y,landmean/(8-seacount));
            }
        }
    }
}

package aestec.lithosphere.strategy.erosion;

import aestec.lithosphere.Lithosphere;

public class BlurErosionStrategy implements ErosionStrategy {
    private Lithosphere l;

    private int r;
    private double blend;

    public BlurErosionStrategy(int r, double blend) {
        this.r = r;
        this.blend = blend;
    }

    @Override
    public void erode(Lithosphere l) {
        this.l = l;
        int xdim = l.getXDim(), ydim=l.getYDim();
        int[][] map = new int[xdim][ydim];

        for (int x=0;x<xdim;x++) for(int y=0;y<ydim;y++) {
            map[x][y] = l.get(Math.floorMod(x, xdim),Math.floorMod(y, ydim));
        }

        //box blur
        for (int y = 0; y < ydim; y++) {
            for (int x = 0; x < xdim; x++) {
                int h = 0;
                for (int i = x - r; i < x + r + 1; i++) h += map[Math.floorMod(i,xdim)][y];
                h /= 2 * r + 1;
                map[x][y] = h;
            }
        }
        for (int x = 0; x < xdim; x++) {
            for (int y = 0; y < ydim; y++) {
                int h = 0;
                for (int i = y - r; i < y + r + 1; i++) h += map[x][Math.floorMod(i,ydim)];
                h /= 2 * r + 1;
                map[x][y] = h;
            }
        }

        for (int x = 0; x < xdim; x++) for (int y = 0; y < ydim; y++) {
            int h = (int)(l.get(x,y)*(1-blend));
            if(h<0){
                h+=(map[x][y]*blend);
                l.set(x,y,h);
            }
        }
    }
}

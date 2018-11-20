package aestec.lithosphere.strategy.erosion;

import aestec.lithosphere.Lithosphere;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class SimpleHydraulicErosion implements ErosionStrategy {
    double transfer=0.08;

    public SimpleHydraulicErosion() {}
    public SimpleHydraulicErosion(double transfer) {
        this.transfer=transfer;
    }

    @Override
    public void erode(Lithosphere l) {
        ArrayList<Tile> list = new ArrayList<>();
        for (int x=0;x<l.getXDim();x++) for (int y=0;y<l.getYDim();y++) {
            list.add(new Tile(l.get(x,y),x,y));
        }
        Collections.sort(list);

        int flowmap[][] = new int[l.getXDim()][l.getYDim()];
        for (int x=0;x<l.getXDim();x++) for (int y=0;y<l.getYDim();y++) flowmap[x][y]=1;

        int dx[] = {0, 0, 1, -1, 1, 1, -1, -1}, dy[] = {1, -1, 0, 0, 1, -1, 1, -1};

        double change[] = new double[8];


        for (Tile t : list) {
            //find biggest drop and change to all sides
            double sum=0, maxChange=0;
            for (int i=0;i<8;i++) {
                int cx=Math.floorMod(t.x+dx[i],l.getXDim());
                int cy=Math.floorMod(t.y+dy[i],l.getYDim());
                change[i] = (l.get(cx,cy)-l.get(t.x,t.y)); //difference from x,y, negative for down

                if(change[i]>0) change[i]=0;
                if (Math.abs(change[i])>maxChange) maxChange=Math.abs(change[i]);
                change[i]*=change[i];
                sum+=change[i];
            }
            for (int i=0;i<8;i++) change[i]/=sum;

            //perform change
            for (int i=0;i<8;i++) if (change[i]>0) {
                int cx=Math.floorMod(t.x+dx[i],l.getXDim());
                int cy=Math.floorMod(t.y+dy[i],l.getYDim());
                flowmap[cx][cy]+=flowmap[t.x][t.y]*change[i];

                double flux = maxChange/2*change[i]*(1-1./(Math.sqrt(flowmap[t.x][t.y])+2));

                l.set(cx,cy,l.get(cx,cy)+(int)(flux*transfer));
                l.set(t.x,t.y,l.get(t.x,t.y)-(int)(flux*transfer));
            }
        }


        for (Tile t: list) {
            //System.out.println(t.height);
        }
    }
}

class Tile implements Comparable<Tile> {
    int height,x,y;

    public Tile(int height, int x, int y) {
        this.height=height;
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Tile tile) {
        return Integer.compare(tile.height,height);
    }
}
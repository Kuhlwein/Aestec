package aestec;

import java.awt.*;
import java.util.HashMap;

public class PlateImpl implements Plate {
    private int xdim, ydim, vx, vy, dx, dy;
    private HashMap<Point,Integer> heightmap;

    private Point transform(int x,int y) {
        return new Point(Math.floorMod(x-dx,xdim),Math.floorMod(y-dy,ydim));
    }

    public PlateImpl(int xdim, int ydim) {
        this.xdim=xdim; this.ydim=ydim;
        heightmap = new HashMap<>();
    }

    @Override
    public void set(int x, int y, int value) {
        Point p = transform(x,y);
        heightmap.put(p,value);
    }

    @Override
    public void remove(int x, int y) {
        Point p = transform(x,y);
        heightmap.remove(p);
    }

    @Override
    public Integer get(int x, int y) {
        Point p = transform(x,y);
        return heightmap.get(p);
    }

    @Override
    public void accelerate(int x, int y) {
        vx+=x; vy+=y;
    }

    @Override
    public void move() {
        dx+=vx; dy+=vy;
    }

}

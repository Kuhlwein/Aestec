package aestec;

import java.awt.*;
import java.util.HashMap;

public class PlateImpl implements Plate {
    private int xdim, ydim;
    private HashMap<Point,Integer> heightmap;

    public PlateImpl(int xdim, int ydim) {
        this.xdim=xdim; this.ydim=ydim;
        heightmap = new HashMap<>();
    }

    @Override
    public void set(int x, int y, int value) {
        Point p = new Point(x,y);
        heightmap.put(p,value);
    }

    @Override
    public void remove(int x, int y) {
        Point p = new Point(x,y);
        heightmap.remove(p);
    }

    @Override
    public Integer get(int x, int y) {
        Point p = new Point(x,y);
        return heightmap.get(p);
    }


}

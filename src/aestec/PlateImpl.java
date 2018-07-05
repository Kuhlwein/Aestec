package aestec;

import java.awt.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class PlateImpl implements Plate {
    private int xdim, ydim, vx, vy, dx, dy, age;
    private HashMap<Point,Integer> heightmap;
    private static Random rand = new Random(0);
    private Point transform(int x,int y) {
        return new Point(Math.floorMod(x-dx,xdim),Math.floorMod(y-dy,ydim));
    }

    public PlateImpl(int xdim, int ydim) {
        this.xdim=xdim; this.ydim=ydim;
        heightmap = new HashMap<>();

        age=rand.nextInt(50);
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
        age++;
    }

    @Override
    public int getAge() {
        return age;
    }

    @Override
    public Iterator<Point> iterator() {
        return new Iterator<Point>() {
            Iterator<Point> internalIterator = heightmap.keySet().iterator();
            Point nextPoint;
            @Override
            public boolean hasNext() {
                return internalIterator.hasNext();
            }

            @Override
            public Point next() {
                nextPoint = internalIterator.next();
                return new Point(Math.floorMod(nextPoint.x+dx,xdim),Math.floorMod(nextPoint.y+dy,ydim));
            }
        };
    }
}

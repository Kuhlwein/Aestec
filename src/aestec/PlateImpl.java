package aestec;

import java.awt.*;
import java.util.*;
import java.util.List;

public class PlateImpl implements Plate {
    private int xdim, ydim, age;
    private double vx, vy, dx, dy;

    private HashMap<Point,Integer> heightmap;
    private static Random rand = new Random(0);

    private Point transform(int x,int y) {
        return new Point(Math.floorMod(x-(int)dx,xdim),Math.floorMod(y-(int)dy,ydim));
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
    public void accelerate(double x, double y) {
        vx+=x; vy+=y;
    }

    @Override
    public void move() {
        dx+=vx; dy+=vy;
        age++;
    }

    @Override
    public double getvx() {
        return vx;
    }

    @Override
    public double getvy() {
        return vy;
    }

    @Override
    public void expand() {
        xdim*=2; ydim*=2;
        HashMap<Point,Integer> oldHeightmap = heightmap;
        heightmap = new HashMap<>();
        for (Point p : oldHeightmap.keySet()) {
            int h = oldHeightmap.get(p);
            heightmap.put(new Point(2*p.x,2*p.y),h);
            heightmap.put(new Point(2*p.x+1,2*p.y),h);
            heightmap.put(new Point(2*p.x,2*p.y+1),h);
            heightmap.put(new Point(2*p.x+1,2*p.y+1),h);
        }
        dx*=2;dy*=2;
    }

    @Override
    public Plate split() {
        PlateImpl[] plates = {this, new PlateImpl(xdim,ydim)};
        ArrayList<Point>[] candidates = new ArrayList[]{new ArrayList<>(), new ArrayList<>()};
        Set<Point> points = new HashSet<>();
        for(Point p : this) points.add(p);

        //seeds
        for (int i=0; i<2; i++) {
            int index = rand.nextInt(points.size());
            Iterator<Point> iter = points.iterator();
            for (int j = 0; j < index; j++) iter.next();
            Point p = iter.next();
            int h = get(p.x, p.y);
            points.remove(p);
            remove(p.x, p.y);
            plates[i].set(p.x, p.y, h);
            addNeighbours(candidates[i],p);
        }

        //flood-fill
        while(candidates[0].size()>0 || candidates[1].size()>0) for (int i=0; i<2; i++) {
            if (candidates[i].size()==0) continue;
            Point p = candidates[i].get(rand.nextInt(candidates[i].size()));
            candidates[i].remove(p);
            if (points.contains(p)) {
                int h = get(p.x, p.y);
                points.remove(p);
                remove(p.x, p.y);
                plates[i].set(p.x, p.y, h);
                addNeighbours(candidates[i],p);
            }
        }

        plates[1].accelerate(getvx(),getvy());

        Point CM[] = {plates[0].getCM(), plates[1].getCM()};

        double dx = CM[1].x-CM[0].x; //distance from CM0 to CM1, right is positive
        if(Math.abs(dx)>xdim/2 && dx>0) dx-=xdim;
        else if(Math.abs(dx)>xdim/2 && dx<0) dx+=xdim;

        double dy = CM[1].y-CM[0].y; //distance from CM0 to CM1, right is positive
        if(Math.abs(dy)>ydim/2 && dy>0) dy-=ydim;
        else if(Math.abs(dy)>ydim/2 && dy<0) dy+=ydim;

        dx=Math.signum(dx)*rand.nextDouble()+0.5;
        dy=Math.signum(dy)*rand.nextDouble()+0.5;
        plates[0].accelerate(-dx,-dy);
        plates[1].accelerate(dx,dy);

        return plates[1];
    }

    private void addNeighbours(List<Point> list, Point p) {
        int[] dx = {-1, 1, 0, 0}, dy={0, 0, -1, 1};
        for (int i=0;i<4;i++) list.add(new Point(Math.floorMod(p.x+dx[i],xdim),Math.floorMod(p.y+dy[i],ydim)));
    }

    private Point getCM() {
        int x=0, y=0;
        for (Point p : heightmap.keySet()) {
            x+=p.x;
            y+=p.y;
        }
        x/=size();
        y/=size();
        return new Point(Math.floorMod(x+(int)dx,xdim),Math.floorMod(y+(int)dy,ydim));
    }

    @Override
    public int size() {
        return heightmap.size();
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
                return new Point(Math.floorMod(nextPoint.x+(int)dx,xdim),Math.floorMod(nextPoint.y+(int)dy,ydim));
            }

            @Override
            public void remove() {
                internalIterator.remove();
            }
        };
    }
}

package aestec.lithosphere;

import aestec.Plate;
import aestec.lithosphere.factory.StrategyFactory;
import aestec.lithosphere.strategy.collision.Collision;

import java.awt.*;
import java.util.*;
import java.util.List;

public class LithosphereImpl implements Lithosphere {
    private int xdim, ydim;
    private List<Plate> plates;
    private StrategyFactory factory;
    private Plate[][] platemap,oldplatemap;
    private static Random rand = new Random(0);

    public LithosphereImpl(int xdim, int ydim, StrategyFactory factory) {
        this.factory = factory;
        this.xdim=xdim; this.ydim=ydim;
        plates=factory.getCreatePlatesStrategy().createPlates(xdim,ydim);

        platemap = new Plate[xdim][ydim];
        for (Plate plate: plates) for(Point p: plate) {
            platemap[p.x][p.y]=plate;
        }
    }

    @Override
    public int getXDim() {
        return xdim;
    }

    @Override
    public int getYDim() {
        return ydim;
    }

    @Override
    public int getNumberOfPlates() {
        return plates.size();
    }

    @Override
    public void updateplates() {
        factory.getManagePlatesStrategy().managePlates(this);

        oldplatemap=platemap;
        platemap = new Plate[xdim][ydim];
        ArrayList<Collision> collisions = new ArrayList<>();
        for(Plate plate:this) for(Point p:plate) {
            if(platemap[p.x][p.y]==null) platemap[p.x][p.y] = plate;
            else collisions.add(new Collision(p,plate,platemap[p.x][p.y]));
        }

        for (Collision c: collisions) platemap[c.p.x][c.p.y]=factory.getCollisionStrategy().collide(c); //is the platemap correct???! change to getter method for platemap for diverge

        factory.getDivergeStrategy().diverge(this);

        factory.getErosionStrategy().erode(this);

        //merge small plates??
        //plate cleanup procedure??
        //plate age at split and combine

    }

    @Override
    public void add(Plate p) {
        plates.add(p);
    }

    @Override
    public void remove(Plate p) {
        plates.remove(p);
    }

    @Override
    public Plate get(int i) {
        return plates.get(i);
    }

    @Override
    public int get(int x, int y) {
        x = Math.floorMod(x,xdim); y=Math.floorMod(y,ydim);
        if(platemap[x][y].get(x,y)==null) return 0;
        return platemap[x][y].get(x,y);
    }

    @Override
    public void set(int x, int y, int z) {
        platemap[x][y].set(x,y,z);
    }

    @Override
    public void setPlateAt(int x, int y, Plate plate) {
        platemap[x][y]=plate;
    }

    @Override
    public Plate getPlateAt(int x, int y) {
        return platemap[x][y];
    }

    @Override
    public Plate getOldPlateAt(int x, int y) {
        return oldplatemap[x][y];
    }

    @Override
    public void expand() {
        for(Plate p : this) p.expand();

        Plate newPlatemap[][] = new Plate[2*xdim][2*ydim], newOldplatemap[][] = new Plate[2*xdim][2*ydim];
        for (int x=0; x<xdim;x++) for(int y=0; y<ydim; y++) {
            Plate p = platemap[x][y];
            newPlatemap[2*x][2*y]=p;
            newPlatemap[2*x+1][2*y]=p;
            newPlatemap[2*x][2*y+1]=p;
            newPlatemap[2*x+1][2*y+1]=p;

            p = oldplatemap[x][y];
            newOldplatemap[2*x][2*y]=p;
            newOldplatemap[2*x+1][2*y]=p;
            newOldplatemap[2*x][2*y+1]=p;
            newOldplatemap[2*x+1][2*y+1]=p;
        }
        platemap=newPlatemap;
        oldplatemap=newOldplatemap;

        xdim*=2; ydim*=2;
    }

    @Override
    public Iterator<Plate> iterator() {
        return plates.iterator();
    }
}


//hot spots
//Adjust number of plates - theory: fewer gives fewer and bigger continents

//erosion
    //weather
    //distribution
//divergence
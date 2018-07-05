package aestec.lithosphere;

import aestec.Plate;
import aestec.lithosphere.factory.StrategyFactory;
import aestec.lithosphere.strategy.collision.Collision;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LithosphereImpl implements Lithosphere {
    private int xdim, ydim;
    private List<Plate> plates;
    private StrategyFactory factory;
    private Plate[][] platemap,oldplatemap;

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
        for(Plate plate:this) plate.move();

        oldplatemap=platemap;
        platemap = new Plate[xdim][ydim];
        ArrayList<Collision> collisions = new ArrayList<>();
        for(Plate plate:this) for(Point p:plate) {
            if(platemap[p.x][p.y]==null) platemap[p.x][p.y] = plate;
            else collisions.add(new Collision(p,plate,platemap[p.x][p.y]));
        }

        for (Collision c: collisions) factory.getCollisionStrategy().collide(c); //is the platemap correct???!

        factory.getDivergeStrategy().diverge(platemap,oldplatemap);

    }

    @Override
    public Iterator<Plate> iterator() {
        return plates.iterator();
    }
}
package aestec.lithosphere;

import aestec.Plate;
import aestec.lithosphere.factory.StrategyFactory;

import java.util.Iterator;
import java.util.List;

public class LithosphereImpl implements Lithosphere {
    private int xdim, ydim;
    private List<Plate> plates;
    private StrategyFactory factory;

    public LithosphereImpl(int xdim, int ydim, StrategyFactory factory) {
        this.factory = factory;
        this.xdim=xdim; this.ydim=ydim;
        plates=factory.getCreatePlatesStrategy().createPlates(xdim,ydim);
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
    public Iterator<Plate> iterator() {
        return plates.iterator();
    }
}

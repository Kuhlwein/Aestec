package aestec.lithosphere.strategy.createplates;

import aestec.Plate;
import java.util.List;

public interface CreatePlatesStrategy {
    public List<Plate> createPlates(int xDim, int yDim);
}

package aestec.lithosphere.strategy.createplates;

import aestec.Plate;

import java.util.List;
import java.util.Random;

public class CreateAcceleratedPlatesStrategy implements CreatePlatesStrategy {
    CreatePlatesStrategy strategy;

    public CreateAcceleratedPlatesStrategy(CreatePlatesStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public List<Plate> createPlates(int xDim, int yDim) {
        List<Plate> plates = strategy.createPlates(xDim,yDim);

        Random random = new Random(1);
        for (Plate p : plates) p.accelerate(random.nextDouble()*3-1.5, random.nextDouble()*3-1.5);

        return plates;
    }
}

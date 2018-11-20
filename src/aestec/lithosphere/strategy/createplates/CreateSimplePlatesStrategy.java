package aestec.lithosphere.strategy.createplates;


import aestec.Plate;
import aestec.PlateImpl;
import aestec.lithosphere.strategy.generateterrain.GenerateTerrainStrategy;

import java.util.ArrayList;
import java.util.List;

public class CreateSimplePlatesStrategy implements CreatePlatesStrategy {
    private int plateNum;
    private GenerateTerrainStrategy strategy;

    public CreateSimplePlatesStrategy(int plateNum, GenerateTerrainStrategy strategy) {
        this.plateNum = plateNum;
        this.strategy = strategy;
    }

    @Override
    public List<Plate> createPlates(int xDim, int yDim) {
        ArrayList<Plate> plates = new ArrayList<>();
        for (int i = 0; i<plateNum; i++) {
            plates.add(new PlateImpl(xDim,yDim));
        }

        for (int i = 0; i<xDim; i++) {
            for (int j = 0; j<yDim; j++) {
                plates.get(i*plateNum/xDim).set(i,j,strategy.getTerrainAt(i,j));
            }
        }

        return plates;
    }
}

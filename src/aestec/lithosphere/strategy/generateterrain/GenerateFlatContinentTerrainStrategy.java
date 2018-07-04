package aestec.lithosphere.strategy.generateterrain;

import aestec.util.PerlinNoise;

import java.util.Random;

public class GenerateFlatContinentTerrainStrategy implements GenerateTerrainStrategy {
    private float[][] grid;
    private float oceanFraction;

    public GenerateFlatContinentTerrainStrategy(int xDim, int yDim, float oceanFraction) {
        this(xDim, yDim);
        this.oceanFraction = oceanFraction;
    }


    public GenerateFlatContinentTerrainStrategy(int xDim, int yDim) {
        Random random = new Random(1000);
        oceanFraction = 0.7f;
        PerlinNoise noise = new PerlinNoise(random);
        grid = noise.getPerlinNoise(xDim,yDim,(int) Math.floor(Math.log(xDim)/Math.log(2)));

    }

    @Override
    public int getTerrainAt(int x, int y) {
        if (Float.compare(oceanFraction,grid[x][y]) == 1) return -4500;
        return 2000;
    }

}

package aestec.lithosphere.strategy.generateterrain;

import aestec.util.PerlinNoise;

import java.util.Random;

public class GeneratePerlinTerrainStrategy implements GenerateTerrainStrategy {
    private float[][] grid;

    public GeneratePerlinTerrainStrategy(int xDim, int yDim) {
        Random random = new Random(1000);
        PerlinNoise noise = new PerlinNoise(random);
        grid = noise.getPerlinNoise(xDim,yDim,(int) Math.floor(Math.log(xDim)/Math.log(2)));
    }

    @Override
    public int getTerrainAt(int x, int y) {
        return (int)(grid[x][y]*20000-10000);
    }

}

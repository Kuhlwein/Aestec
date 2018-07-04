package aestec.lithosphere.strategy.generateterrain;

public class GenerateOceanTerrainStrategy implements GenerateTerrainStrategy {
    @Override
    public int getTerrainAt(int x, int y) {
        int averageDepth = -4500;
        return averageDepth;
    }

}

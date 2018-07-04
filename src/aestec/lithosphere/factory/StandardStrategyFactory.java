package aestec.lithosphere.factory;

import aestec.lithosphere.strategy.createplates.CreatePlatesStrategy;
import aestec.lithosphere.strategy.createplates.CreateSimplePlatesStrategy;
import aestec.lithosphere.strategy.generateterrain.GenerateOceanTerrainStrategy;
import aestec.lithosphere.strategy.generateterrain.GenerateTerrainStrategy;

public class StandardStrategyFactory implements StrategyFactory {
    public int platenum = 5;

    @Override
    public CreatePlatesStrategy getCreatePlatesStrategy() {
        return new CreateSimplePlatesStrategy(platenum,getGenerateTerrainStrategy());
    }

    @Override
    public GenerateTerrainStrategy getGenerateTerrainStrategy() {
        return new GenerateOceanTerrainStrategy();
    }
}

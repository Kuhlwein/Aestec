package aestec.lithosphere.factory;

import aestec.lithosphere.strategy.collision.CollisionStrategy;
import aestec.lithosphere.strategy.createplates.CreateGrownPlatesStrategy;
import aestec.lithosphere.strategy.createplates.CreatePlatesStrategy;
import aestec.lithosphere.strategy.createplates.CreateSimplePlatesStrategy;
import aestec.lithosphere.strategy.generateterrain.GenerateFlatContinentTerrainStrategy;
import aestec.lithosphere.strategy.generateterrain.GenerateOceanTerrainStrategy;
import aestec.lithosphere.strategy.generateterrain.GenerateTerrainStrategy;

public class AdvancedStrategyFactory extends AbstractStrategyFactory {
    @Override
    public CreatePlatesStrategy getCreatePlatesStrategy() {
        return new CreateGrownPlatesStrategy(5,getGenerateTerrainStrategy());
    }

    @Override
    public GenerateTerrainStrategy getGenerateTerrainStrategy() {
        return new GenerateFlatContinentTerrainStrategy(500,250,(float)0.5);
    }
}

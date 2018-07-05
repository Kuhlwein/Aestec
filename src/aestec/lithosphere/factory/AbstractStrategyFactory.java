package aestec.lithosphere.factory;

import aestec.lithosphere.strategy.collision.AdditionCollisionStrategy;
import aestec.lithosphere.strategy.collision.CollisionStrategy;
import aestec.lithosphere.strategy.createplates.CreatePlatesStrategy;
import aestec.lithosphere.strategy.createplates.CreateSimplePlatesStrategy;
import aestec.lithosphere.strategy.diverge.DivergeStrategy;
import aestec.lithosphere.strategy.diverge.SimpleDivergeStrategy;
import aestec.lithosphere.strategy.generateterrain.GenerateOceanTerrainStrategy;
import aestec.lithosphere.strategy.generateterrain.GenerateTerrainStrategy;

public abstract class AbstractStrategyFactory implements StrategyFactory {
    @Override
    public CreatePlatesStrategy getCreatePlatesStrategy() {
        return new CreateSimplePlatesStrategy(2,getGenerateTerrainStrategy());
    }

    @Override
    public CollisionStrategy getCollisionStrategy() {
        return new AdditionCollisionStrategy();
    }

    @Override
    public GenerateTerrainStrategy getGenerateTerrainStrategy() {
        return new GenerateOceanTerrainStrategy();
    }

    @Override
    public DivergeStrategy getDivergeStrategy() {
        return new SimpleDivergeStrategy();
    }
}

package aestec.lithosphere.factory;

import aestec.lithosphere.strategy.collision.CollisionStrategy;
import aestec.lithosphere.strategy.createplates.CreatePlatesStrategy;
import aestec.lithosphere.strategy.diverge.DivergeStrategy;
import aestec.lithosphere.strategy.generateterrain.GenerateTerrainStrategy;

public interface StrategyFactory {
    CreatePlatesStrategy getCreatePlatesStrategy();

    CollisionStrategy getCollisionStrategy();

    //ErosionStrategy getErosionStrategy();

    GenerateTerrainStrategy getGenerateTerrainStrategy();

    DivergeStrategy getDivergeStrategy();
}

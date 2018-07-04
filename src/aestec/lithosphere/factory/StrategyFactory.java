package aestec.lithosphere.factory;

import aestec.lithosphere.strategy.createplates.CreatePlatesStrategy;
import aestec.lithosphere.strategy.generateterrain.GenerateTerrainStrategy;

public interface StrategyFactory {

        CreatePlatesStrategy getCreatePlatesStrategy();

        //ErosionStrategy getErosionStrategy();

        GenerateTerrainStrategy getGenerateTerrainStrategy();
}

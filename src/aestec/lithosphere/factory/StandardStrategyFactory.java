package aestec.lithosphere.factory;

import aestec.lithosphere.strategy.createplates.CreatePlatesStrategy;
import aestec.lithosphere.strategy.createplates.CreateSimplePlatesStrategy;
import aestec.lithosphere.strategy.diverge.DivergeStrategy;

public class StandardStrategyFactory extends AbstractStrategyFactory {
    public int platenum = 5;

    @Override
    public CreatePlatesStrategy getCreatePlatesStrategy() {
        return new CreateSimplePlatesStrategy(platenum,getGenerateTerrainStrategy());
    }
}

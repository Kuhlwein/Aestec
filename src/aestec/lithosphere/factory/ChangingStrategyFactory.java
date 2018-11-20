package aestec.lithosphere.factory;

import aestec.lithosphere.strategy.collision.CollisionStrategy;
import aestec.lithosphere.strategy.collision.PenetratingCollisionStrategy;
import aestec.lithosphere.strategy.createplates.CreateAcceleratedPlatesStrategy;
import aestec.lithosphere.strategy.createplates.CreateGrownPlatesStrategy;
import aestec.lithosphere.strategy.createplates.CreatePlatesStrategy;
import aestec.lithosphere.strategy.diverge.DivergeStrategy;
import aestec.lithosphere.strategy.diverge.VariedDivergeStrategy;
import aestec.lithosphere.strategy.erosion.*;
import aestec.lithosphere.strategy.generateterrain.GeneratePerlinTerrainStrategy;
import aestec.lithosphere.strategy.generateterrain.GenerateTerrainStrategy;
import aestec.lithosphere.strategy.manageplates.AdvancedManagePlatesStrategy;
import aestec.lithosphere.strategy.manageplates.ManagePlatesStrategy;
import aestec.worldprinter.AbstractWorldPrinter;

import java.net.Inet4Address;

public class ChangingStrategyFactory extends AbstractStrategyFactory {
    public int platenum=10, plateTarget=8, blurradius=5,blurfreq=1, raisefreq=3, hydraulicfreq=10, distfreq=1;
    public int xdim, ydim;
    public double blurblend=0.02, inertiafactor=1e-5, foldingfactor=0.03, hydrotransfer=0.08;


    private AggregateErosionStrategy erosionStrategy;
    private CollisionStrategy collisionStrategy;
    private ManagePlatesStrategy managePlatesStrategy;

    public ChangingStrategyFactory(int xdim, int ydim) {
        this.xdim = xdim;
        this.ydim = ydim;

        erosionStrategy = new AggregateErosionStrategy();
        collisionStrategy = new PenetratingCollisionStrategy(inertiafactor,foldingfactor);

        erosionStrategy.add(new DistributionErosionStrategy(0.5),distfreq);
        erosionStrategy.add(new SimpleHydraulicErosion(hydrotransfer),hydraulicfreq);
        erosionStrategy.add(new RaiseLakesErosionStrategy(),raisefreq);
        erosionStrategy.add(new BlurErosionStrategy(blurradius,blurblend),blurfreq);

        managePlatesStrategy = new AdvancedManagePlatesStrategy(plateTarget);
    }

    @Override
    public CreatePlatesStrategy getCreatePlatesStrategy() {
        CreatePlatesStrategy strategy = new CreateGrownPlatesStrategy(platenum,getGenerateTerrainStrategy());
        return new CreateAcceleratedPlatesStrategy(strategy);
    }

    @Override
    public CollisionStrategy getCollisionStrategy() {
        return collisionStrategy;
    }

    @Override
    public DivergeStrategy getDivergeStrategy() {
        return new VariedDivergeStrategy();
    }

    @Override
    public GenerateTerrainStrategy getGenerateTerrainStrategy() {
        return new GeneratePerlinTerrainStrategy(xdim,ydim);
    }

    @Override
    public ErosionStrategy getErosionStrategy() {
        return erosionStrategy;
    }

    @Override
    public ManagePlatesStrategy getManagePlatesStrategy() {
        return managePlatesStrategy;
    }

    public void expand() {
        //plateTarget-=2;
        //blurfreq*=1;
        //blurblend*=0.7;
        //raisefreq*=1;
        //hydraulicfreq*=1;
        //distfreq*=1;
        //inertiafactor/=10;
        //hydrotransfer*=2;
        inertiafactor/=10;
        foldingfactor/=10;
        collisionStrategy = new PenetratingCollisionStrategy(inertiafactor,foldingfactor);
        plateTarget*=4;
        managePlatesStrategy = new AdvancedManagePlatesStrategy(plateTarget);
    }

    public void nomoresplit() {
        plateTarget-=1;
        managePlatesStrategy = new AdvancedManagePlatesStrategy(plateTarget);
    }
}

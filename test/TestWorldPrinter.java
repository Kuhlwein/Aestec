import aestec.Plate;
import aestec.lithosphere.*;
import aestec.lithosphere.factory.*;
import aestec.lithosphere.strategy.collision.CollisionStrategy;
import aestec.lithosphere.strategy.collision.PenetratingCollisionStrategy;
import aestec.lithosphere.strategy.createplates.*;
import aestec.lithosphere.strategy.diverge.DivergeStrategy;
import aestec.lithosphere.strategy.diverge.VariedDivergeStrategy;
import aestec.lithosphere.strategy.erosion.*;
import aestec.lithosphere.strategy.generateterrain.*;
import aestec.lithosphere.strategy.manageplates.AdvancedManagePlatesStrategy;
import aestec.lithosphere.strategy.manageplates.ManagePlatesStrategy;
import aestec.worldprinter.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

public class TestWorldPrinter {
    private Lithosphere lithosphere;

    @Before
    public void setUp() {}

    @Test
    public void printSimplePlates() {
        StandardStrategyFactory factory;

        factory = new StandardStrategyFactory();
        factory.platenum = 2;
        lithosphere = new LithosphereImpl(100,50, factory);
        WorldPrinter printer = new PlatePrinter(lithosphere);
        printer.printWorld("out/test/PlatePrinter1_2plates.png");

        factory.platenum = 5;
        lithosphere = new LithosphereImpl(100,50, factory);
        printer = new PlatePrinter(lithosphere);
        printer.printWorld("out/test/PlatePrinter2_5plates.png");
    }

    @Test
    public void printGrownPlates() {
        StandardStrategyFactory factory;

        factory = new TestStrategyFactory();
        factory.platenum = 2;
        lithosphere = new LithosphereImpl(300,150, factory);
        WorldPrinter printer = new PlatePrinter(lithosphere);
        printer.printWorld("out/test/PlatePrinter3_2grownplates.png");

        factory.platenum = 5;
        lithosphere = new LithosphereImpl(300,150, factory);
        printer = new PlatePrinter(lithosphere);
        printer.printWorld("out/test/PlatePrinter4_5grownplates.png");
    }

    @Test
    public void printMovingPlates() {
        StandardStrategyFactory factory;

        factory = new TestStrategyFactory();
        factory.platenum = 5;
        lithosphere = new LithosphereImpl(300,150, factory);
        WorldPrinter printer = new PlatePrinter(lithosphere);

        Random random = new Random();
        for (Plate p : lithosphere) {
            p.accelerate(random.nextInt(5),random.nextInt(5));
            p.move();
        }
        printer.printWorld("out/test/PlatePrinter5_movedplates.png");
    }

    @Test
    public void printOcean() {
        StandardStrategyFactory factory;
        factory = new StandardStrategyFactory();
        factory.platenum = 2;
        lithosphere = new LithosphereImpl(300,150, factory);
        WorldPrinter printer = new RelativeHeightmapPrinter(lithosphere);
        printer.printWorld("out/test/PlatePrinter6_oceanmap.png");
    }

    @Test
    public void printLand() {
        StandardStrategyFactory factory;
        factory = new TestStrategyFactory();
        factory.platenum = 2;
        lithosphere = new LithosphereImpl(300,150, factory);
        WorldPrinter printer = new RelativeHeightmapPrinter(lithosphere);
        printer.printWorld("out/test/PlatePrinter7_landmap.png");
    }

    @Test
    public void printCombo() {
        StandardStrategyFactory factory;
        factory = new TestStrategyFactory();
        factory.platenum = 5;
        lithosphere = new LithosphereImpl(300,150, factory);
        WorldPrinter printer = new SimpleAtlasPrinter(lithosphere);
        printer.printWorld("out/test/PlatePrinter8_combomap.png");
    }

    @Test
    public void printaction() {
        StandardStrategyFactory factory;
        factory = new TestCollisionStrategyFactory();
        factory.platenum = 8;

        int xdim=300, ydim=xdim/2;
        ChangingStrategyFactory changingStrategyFactory = new ChangingStrategyFactory(xdim,ydim);


        lithosphere = new LithosphereImpl(xdim,ydim, changingStrategyFactory);
        WorldPrinter printer = new ComboPrinter(new AtlasPrinter(lithosphere));

        int N=400;

        for (int i=0;i<N;i++) {lithosphere.updateplates();printer.printWorld("out/test/animation/"+(i+1000)+".png");}

        lithosphere.expand();changingStrategyFactory.expand();
        for (int i=N;i<N*2;i++) {lithosphere.updateplates();printer.printWorld("out/test/animation/"+(i+1000)+".png");}

        lithosphere.expand();changingStrategyFactory.expand();
        for (int i=N*2;i<N*3;i++) {lithosphere.updateplates();printer.printWorld("out/test/animation/"+(i+1000)+".png");}

        //lithosphere.expand();
        //

        for (int i=N*3;i<N*4;i++) {
            lithosphere.updateplates();printer.printWorld("out/test/animation/"+(i+1000)+".png");
            if(i % 4 == 0) changingStrategyFactory.nomoresplit();
        }

        //lithosphere.expand();changingStrategyFactory.expand ();
        //for(int i=N*2;i<N*3;i++) {lithosphere.updateplates();printer.printWorld("out/test/animation/"+(i+100)+".png");}


        ErosionStrategy rain = new SimpleHydraulicErosion();
        for(int i=0;i<10;i++) rain.erode(lithosphere);

        ErosionStrategy strat = new RaiseLakesErosionStrategy();
        strat.erode(lithosphere);

        printer.printWorld("out/test/PlatePrinter9_actionmap.png");


    }

    class TestStrategyFactory extends StandardStrategyFactory {
        @Override
        public CreatePlatesStrategy getCreatePlatesStrategy() {
            return new CreateGrownPlatesStrategy(platenum,getGenerateTerrainStrategy());
        }

        @Override
        public GenerateTerrainStrategy getGenerateTerrainStrategy() {
            return new GenerateFlatContinentTerrainStrategy(500,250,(float)0.5);
        }
    }

    class TestCollisionStrategyFactory extends StandardStrategyFactory {
        private AggregateErosionStrategy erosionStrategy = new AggregateErosionStrategy();

        public TestCollisionStrategyFactory() {
            erosionStrategy.add(new DistributionErosionStrategy(0.5),1);
            erosionStrategy.add(new SimpleHydraulicErosion(),10);
            erosionStrategy.add(new RaiseLakesErosionStrategy(),3);
            erosionStrategy.add(new BlurErosionStrategy(3,0.03),1);
        }

        @Override
        public CreatePlatesStrategy getCreatePlatesStrategy() {
            CreatePlatesStrategy strategy = new CreateGrownPlatesStrategy(platenum,getGenerateTerrainStrategy());
            return new CreateAcceleratedPlatesStrategy(strategy);
        }

        @Override
        public CollisionStrategy getCollisionStrategy() {
            return new PenetratingCollisionStrategy();
        }

        @Override
        public DivergeStrategy getDivergeStrategy() {
            return new VariedDivergeStrategy();
        }

        @Override
        public GenerateTerrainStrategy getGenerateTerrainStrategy() {
            return new GeneratePerlinTerrainStrategy(300,150);
        }

        @Override
        public ErosionStrategy getErosionStrategy() {
            return erosionStrategy;
        }

        @Override
        public ManagePlatesStrategy getManagePlatesStrategy() {
            return new AdvancedManagePlatesStrategy(8);
        }
    }
}

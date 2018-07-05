import aestec.Plate;
import aestec.lithosphere.*;
import aestec.lithosphere.factory.*;
import aestec.lithosphere.strategy.createplates.*;
import aestec.lithosphere.strategy.generateterrain.*;
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
        WorldPrinter printer = new ComboPrinter(lithosphere);

        Random random = new Random(1);
        for (Plate p : lithosphere) {
            p.accelerate(random.nextInt(5),random.nextInt(5));
        }
        lithosphere.updateplates();lithosphere.updateplates();lithosphere.updateplates();lithosphere.updateplates();lithosphere.updateplates();
        printer.printWorld("out/test/PlatePrinter8_combomap.png");
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
}

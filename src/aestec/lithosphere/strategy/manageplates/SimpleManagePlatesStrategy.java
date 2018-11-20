package aestec.lithosphere.strategy.manageplates;

import aestec.Plate;
import aestec.lithosphere.Lithosphere;

public class SimpleManagePlatesStrategy implements ManagePlatesStrategy {
    @Override
    public void managePlates(Lithosphere l) {
        for(Plate plate:l) plate.move();
    }
}

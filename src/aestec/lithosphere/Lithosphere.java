package aestec.lithosphere;

import aestec.Plate;

public interface Lithosphere extends Iterable<Plate> {
    int getXDim();
    int getYDim();

    int getNumberOfPlates();
}

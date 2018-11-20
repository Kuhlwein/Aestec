package aestec.lithosphere;

import aestec.Plate;

public interface Lithosphere extends Iterable<Plate> {
    int getXDim();
    int getYDim();

    int getNumberOfPlates();
    void updateplates();
    void add(Plate p);
    void remove(Plate p);
    Plate get(int i);

    int get(int x, int y);
    void set(int x, int y, int z);

    void setPlateAt(int x, int y, Plate plate);
    Plate getPlateAt(int x, int y);
    Plate getOldPlateAt(int x, int y);

    void expand();

}

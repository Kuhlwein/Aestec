package aestec;

import java.awt.*;

public interface Plate extends Iterable<Point> {

    void set(int x, int y,int value);
    void remove(int x, int y);
    Integer get(int x, int y);

    void accelerate(double x, double y);
    void move();

    double getvx();
    double getvy();

    void expand();
    Plate split();
    int size();
    int getAge();

}

package aestec;

import java.awt.*;

public interface Plate extends Iterable<Point> {

    void set(int x, int y,int value);
    void remove(int x, int y);
    Integer get(int x, int y);

    void accelerate(int x, int y);
    void move();


    //void expand();
    //int size();
    //List<Point> getOverlapWith(Plate plate);
    int getAge();
}

package aestec.lithosphere.strategy.collision;

import aestec.Plate;

import java.awt.*;

public class Collision {
    public Plate plate1, plate2;
    public Point p;
    public Collision(Point p, Plate p1, Plate p2) {
        plate1 = p1;
        plate2 = p2;
        this.p = p;
    }
}

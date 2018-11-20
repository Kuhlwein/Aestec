package aestec.lithosphere.strategy.collision;

import aestec.Plate;

public abstract class abstractCollisionStrategy implements CollisionStrategy {
    private int x,y;
    @Override
    public Plate collide(Collision c) {
        x = c.p.x; y = c.p.y;
        Integer h1 = c.plate1.get(x,y), h2 = c.plate2.get(x,y);
        if(get(c.plate1)==null||get(c.plate2)==null) return c.plate1==null ? c.plate2 : c.plate1;
        return collide(c.plate1,c.plate2);
    }
    abstract Plate collide(Plate p1, Plate p2);

    void set(Plate p, int a) {
        p.set(x,y,a);
    }
    Integer get(Plate p) {
        return p.get(x,y);
    }

    void remove(Plate p) {
        p.remove(x,y);
    }
}

package aestec.lithosphere.strategy.collision;

import aestec.Plate;

public class AdditionCollisionStrategy extends abstractCollisionStrategy {
    @Override
    Plate collide(Plate p1, Plate p2) {
        if (p1.getAge()>p2.getAge()) {
            set(p2,get(p1)+get(p2));
            remove(p1);
            return p2;
        } else {
            set(p1,get(p1)+get(p2));
            remove(p2);
            return p1;
        }
    }
}

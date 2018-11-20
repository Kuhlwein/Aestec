package aestec.lithosphere.strategy.collision;

import aestec.Plate;

public interface CollisionStrategy {
    Plate collide(Collision c);
}

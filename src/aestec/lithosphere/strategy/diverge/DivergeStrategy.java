package aestec.lithosphere.strategy.diverge;

import aestec.Plate;

public interface DivergeStrategy {
    void diverge(Plate[][] platemap,Plate[][] oldplatemap);
}

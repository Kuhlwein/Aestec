package aestec.lithosphere.strategy.collision;

import aestec.Plate;

import java.util.Random;

public class AdvancedCollisionStrategy extends abstractCollisionStrategy {
    private static Random random = new Random(0);

    @Override
    Plate collide(Plate p1, Plate p2) {
        Plate p0 = (p1.getAge()>p2.getAge()) ? p2 : p1;
        p2=(p0==p2) ? p1 : p2;
        p1=p0; //p1 youngest, p2 oldest



        if (get(p2)>0 && get(p1)<0) { // if oldest is land and youngest is ocean, MAKES STRANGE MOVING ISLANDS!!
            p0=p1; p1=p2; p2=p0;
            //if(random.nextDouble()<0.5) return p1;
        };

        int h1=get(p1), h2=get(p2);

        if(random.nextDouble()>0.5) {
            set(p1, h1 + (int) ((h2+10000) * 0.05));
            remove(p2);
        }



        //Ny teknik - swap så altid den laveste overfører!!!! SE HER
        // fill basins??

        double v1x=p1.getvx(), v1y=p1.getvy(), v2x=p2.getvx(), v2y=p2.getvy();
        double m1=p1.size(), m2=p2.size();

        double inertiafactor = 1e-5;
        if(h1>0 && h2>0) inertiafactor*=10;

        p1.accelerate((v1x-v2x)*((m1-m2)/(m1+m2)-1)*inertiafactor,(v1y-v2y)*((m1-m2)/(m1+m2)-1)*inertiafactor);
        p2.accelerate((v2x-v1x)*((m2-m1)/(m1+m2)-1)*inertiafactor,(v2y-v1y)*((m2-m1)/(m1+m2)-1)*inertiafactor);

        return p1;
    }
}

package aestec.lithosphere.strategy.collision;

import aestec.Plate;

import java.awt.*;
import java.util.Random;

public class PenetratingCollisionStrategy extends abstractCollisionStrategy {
    private static Random random = new Random(0);
    private double inertiafactor=1e-5;
    private double foldingfactor=0.03;

    public PenetratingCollisionStrategy() {}
    public PenetratingCollisionStrategy(double inertiafactor, double foldingfactor) {
        this.inertiafactor = inertiafactor;
        this.foldingfactor = foldingfactor;
    }

    @Override
    Plate collide(Plate p1, Plate p2) {
        Plate primary = (p1.getAge()>p2.getAge()) ? p2 : p1;
        Plate secondary=(primary==p2) ? p1 : p2;


        if (get(secondary)>0 && get(primary)<0 && random.nextDouble()>0.3) { // if oldest is land and youngest is ocean, MAKES STRANGE MOVING ISLANDS!!
            Plate p0=primary; primary=secondary; secondary=p0;
        }

        int h1=get(primary), h2=get(secondary);
        if(h1<h2 && h1>0) {set(primary,h2);set(secondary,h1);} //primary is highest
        h1=get(primary); h2=get(secondary);

        if(random.nextDouble()>0.5) {
            set(primary, h1 + (int) ((h2+10000) * foldingfactor));
            h2-=(h2+10000) * foldingfactor;
            set(secondary,h2);
            if(h2<0) remove(secondary);
        }

        double v1x=p1.getvx(), v1y=p1.getvy(), v2x=p2.getvx(), v2y=p2.getvy();
        double m1=p1.size(), m2=p2.size();

        inertiafactor = 1e-5;
        if(h1>0 && h2>0) inertiafactor*=20;

        p1.accelerate((v1x-v2x)*((m1-m2)/(m1+m2)-1)*inertiafactor,(v1y-v2y)*((m1-m2)/(m1+m2)-1)*inertiafactor);
        p2.accelerate((v2x-v1x)*((m2-m1)/(m1+m2)-1)*inertiafactor,(v2y-v1y)*((m2-m1)/(m1+m2)-1)*inertiafactor);


        //Ny teknik - swap så altid den laveste overfører!!!! SE HER
        // fill basins??


        return primary;
    }
}

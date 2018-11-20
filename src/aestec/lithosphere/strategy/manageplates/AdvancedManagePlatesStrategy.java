package aestec.lithosphere.strategy.manageplates;

import aestec.Plate;
import aestec.lithosphere.Lithosphere;

import java.awt.*;

public class AdvancedManagePlatesStrategy implements ManagePlatesStrategy {
    int targetPlates;

    public AdvancedManagePlatesStrategy(int targetPlates) {
        this.targetPlates = targetPlates;
    }

    public AdvancedManagePlatesStrategy() {
        targetPlates=8;
    }

    @Override
    public void managePlates(Lithosphere l) {
        //split
        if(l.getNumberOfPlates()<targetPlates) {
            int max = 0;
            Plate newplate = null;
            for (Plate plate : l) if(newplate==null || max<plate.size()) {max=plate.size();newplate=plate;}

            Plate newPlate = newplate.split();
            l.add(newPlate);
            for (Point p : newPlate) l.setPlateAt(p.x,p.y,newPlate);
        }

        //Merge plates
        for (int i=l.getNumberOfPlates()-1; i>0; i--) for(int j=i-1; j>=0; j--) {
            Plate p1=l.get(i), p2=l.get(j);
            if (p1==p2) continue;
            if (Math.abs(p1.getvx()-p2.getvx())<0.8e-1 && Math.abs(p1.getvy()-p2.getvy())<0.8e-1) {
                merge(p1,p2,l);
                break;
            }
        }
        for (int i=l.getNumberOfPlates()-1; i>0; i--) if(l.get(i).size()==0) l.remove(l.get(i));

        for (Plate plate : l) {//if less than 10% of average plate
            if (plate.size()<l.getXDim()*l.getYDim()/(l.getNumberOfPlates()*10)) {
                //Point point;
                //for (Point p : plate) {point=p;break;}
                if (plate!=l.get(0)) merge(plate,l.get(0),l);
                else merge(plate,l.get(1),l);
                break;
            }
        }

        //Movement balancer
        double px=0, py=0;
        for(Plate p : l) {
            px+=p.getvx()*p.size();
            py+=p.getvy()*p.size();
        }
        px/=l.getXDim()*l.getYDim();
        py/=l.getXDim()*l.getYDim();
        for(Plate p : l) {
            p.accelerate(-px,-py);
        }

        for(Plate plate:l) plate.move();
    }

    private void merge(Plate p1, Plate p2, Lithosphere l) {
        double dx= (p1.getvx()*p1.size()+p2.getvx()*p2.size())/(p1.size()+p2.size());
        double dy= (p1.getvy()*p1.size()+p2.getvy()*p2.size())/(p1.size()+p2.size());
        p1.accelerate(-p1.getvx()+dx,-p1.getvy()+dy);

        for (Point p : p1) if(p2.get(p.x,p.y)==null || p1.get(p.x,p.y)>p2.get(p.x,p.y)) {
            p2.set(p.x,p.y,p1.get(p.x,p.y));
            l.setPlateAt(p.x,p.y,p2);
        }
        l.remove(p1);
    }
}

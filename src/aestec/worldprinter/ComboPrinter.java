package aestec.worldprinter;

import aestec.Plate;
import aestec.lithosphere.Lithosphere;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ComboPrinter extends AbstractWorldPrinter {
    private Lithosphere lithosphere;

    public ComboPrinter(Lithosphere lithosphere) {
        this.lithosphere = lithosphere;
    }

    @Override
    public BufferedImage getRenderedWorld() {
        RelativeHeightmapPrinter printer = new RelativeHeightmapPrinter(lithosphere);
        BufferedImage img = printer.getRenderedWorld();

        int N = lithosphere.getNumberOfPlates();
        int i = 0;
        for (Plate plate : lithosphere) {
            for (Point point : plate) {
                boolean isEdge = false;
                for (int dx=-1;dx<=1;dx++) for (int dy=-1;dy<=1;dy++) if (plate.get(point.x+dx,point.y+dy)==null) isEdge=true;
                if (isEdge) {
                    img.setRGB(point.x, point.y,Color.HSBtoRGB((float) i / N, (float) 0.8, (float) 0.8));
                }
            }
            i++;
        }

        return img;
    }
}
